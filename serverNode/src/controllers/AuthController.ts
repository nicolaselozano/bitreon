import { Request, Response } from "express";
import { AuthUtils } from "../utils/auth/AuthUtils";
import { AuthService } from "../services/AuthService";


const RegisterUser = async (req: Request, res: Response) => {

    try {

        const { username, password, email } = req.body;

        const newToken = await AuthService.RegisterUser({
            email,
            password,
            username
        });

        res.status(200).json(newToken);

    } catch (error) {

        res.status(404).json(error);

    }

}

const LoginUser = async (req: Request, res: Response) => {

    try {

        const { username, password, email } = req.query;

        const newToken = await AuthService.LoginUser({
            email,
            password,
            username
        });

        res.status(200).json(newToken);

    } catch (error) {

        res.status(404).json(error);

    }

}



export const AuthController = {
    RegisterUser,
    LoginUser
};