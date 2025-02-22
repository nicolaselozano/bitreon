import { Request, Response } from "express";
import { AuthUtils } from "../utils/auth/AuthUtils";
import { AuthService } from "../services/AuthService";
import { refreshTokenCookieName } from "../config/config";
import { CookieConfig } from "../utils/auth/CookieConfig";
import { TokenResponse } from "../utils/DTO/AuthUserDTO";


const RegisterUser = async (req: Request, res: Response) => {

    try {

        const { username, password, email } = req.body;

        const newToken: TokenResponse = await AuthService.RegisterUser({
            email,
            password,
            username
        }) as TokenResponse;

        res.cookie(refreshTokenCookieName, `Bearer ${newToken.refreshToken}`, CookieConfig(
            {
                maxAge: 20 * 24 * 60 * 60 * 1000
            }
        ));
        res.status(200).json(newToken.token);




    } catch (error) {

        res.status(404).json(error);

    }

}

const LoginUser = async (req: Request, res: Response) => {

    try {

        const { username, password, email } = req.query;

        const newToken: TokenResponse | Error = await AuthService.LoginUser({
            email,
            password,
            username
        }) as TokenResponse;



        res.cookie(refreshTokenCookieName, `Bearer ${newToken.refreshToken}`, CookieConfig(
            {
                maxAge: 20 * 24 * 60 * 60 * 1000
            }
        ));
        res.status(200).json(newToken.token);

    } catch (error) {

        res.status(404).json(error);

    }

}



export const AuthController = {
    RegisterUser,
    LoginUser
};