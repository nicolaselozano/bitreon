import { Request, Response, NextFunction } from "express";
import { AuthUtils } from "../utils/auth/AuthUtils";
import { tokenCookieName, refreshTokenCookieName } from "../config/config";

const Check = async (req: Request, res: Response, next: NextFunction): Promise<void> => {
  try {
    const token = req.header("Authorization") || req.cookies?.[tokenCookieName];


    try {

      if (!token) {
        throw Error("Token not provided");
      }
      const payload = AuthUtils.CheckToken(token);
      console.log(`Payload: ${JSON.stringify(payload)}`);
      next();
    } catch (tokenError) {
      console.warn("Token invalid or expired, attempting refresh...");

      const refreshToken = req.cookies?.[refreshTokenCookieName];
      if (!refreshToken) {
        res.status(401).json({ message: "Unauthorized: No refresh token provided" });
      }

      try {
        

        console.log(refreshToken);
        const newTokens = AuthUtils.RefreshTokens(refreshToken);


        console.log(refreshToken);
        
        res.cookie(tokenCookieName, newTokens.accessToken, {
          httpOnly: true,
          secure: process.env.NODE_ENV === "production",
        });
        res.cookie(refreshTokenCookieName, newTokens.refreshToken, {
          httpOnly: true,
          secure: process.env.NODE_ENV === "production",
        });

        console.log("Tokens refreshed successfully");
        next();
      } catch (refreshError) {
        console.error("Failed to refresh tokens:", refreshError);
        res.status(401).json({ message: "Unauthorized: Failed to refresh tokens" });
      }
    }
  } catch (error) {
    console.error("Error in Check middleware:", error);
    res.status(401).json({ message: "Unauthorized" });
  }
};



export const MToken = {
  Check,
};
