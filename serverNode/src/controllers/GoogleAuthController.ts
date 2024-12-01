import { Request, Response } from "express";
import { AuthUtils } from "../utils/auth/AuthUtils";

const googleCallback = (req: Request, res: Response) => {
    const user = req.user as any;
    console.log("soy el callback", req.user);
    
    const newToken = AuthUtils.CreateToken(user.username, user.email);
  
    res.json({ newToken });
  };

export const GoogleAuthController = {
    googleCallback
}