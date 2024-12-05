import { Request, Response } from "express";
import { AuthUtils } from "../utils/auth/AuthUtils";
import { CookieConfig } from "../utils/auth/CookieConfig";
import { tokenCookieName } from "../config/config";

const googleCallback = (req: Request, res: Response) => {
    const user = req.user as any;
    console.log("soy el callback", req.user);
    
    const newToken = AuthUtils.CreateToken(user.username, user.email);
    const RefreshToken = AuthUtils.CreateRefreshToken(user.username, user.email);
    res.cookie(tokenCookieName, `Bearer ${RefreshToken}`, CookieConfig());
    res.json({ newToken });
  };

export const GoogleAuthController = {
    googleCallback
}