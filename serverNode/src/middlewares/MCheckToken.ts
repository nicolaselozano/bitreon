import { Request, Response } from "express";
import { NextFunction } from "http-proxy-middleware/dist/types";
import { Jwt,JwtHeader,JwtPayload } from "jsonwebtoken";
import { AuthUtils } from "../utils/auth/AuthUtils";

const CheckToken = async (req: Request, res: Response, next: NextFunction) => {

    try {
        
        const header = req.header("Authorization") || null;
        if (!header) {
          return res.status(401).json({ message: "Token not provied" });
        }

        const payload = AuthUtils.CheckToken(header);

        

    } catch (error) {
        
    }

}