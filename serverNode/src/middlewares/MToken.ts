import { Request, Response } from "express";
import { NextFunction } from "http-proxy-middleware/dist/types";
import { AuthUtils } from "../utils/auth/AuthUtils";
import { tokenCookieName } from "../config/config";

const Check = async (req: Request, res: Response, next: NextFunction): Promise<void> => {
  try {
    const header = req.header("Authorization") || req.cookies?.[tokenCookieName];

    if (!header) {
      res.status(401).json({ message: "Token not provided" });
    }

    console.log(`Token received: ${header}`);

    const payload = AuthUtils.CheckToken(header);

    console.log(`Payload: ${JSON.stringify(payload)}`);

    next();
  } catch (error) {
    console.error("Error in Check middleware:", error);
    res.status(401).json({ message: error || "Unauthorized" });
  }
};

export const MToken = {
  Check
}