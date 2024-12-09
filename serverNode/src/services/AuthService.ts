import axios from "axios"
import { AuthUserDTO, TokenResponse } from "../utils/DTO/AuthUserDTO"
import { AuthUtils } from "../utils/auth/AuthUtils";
import config from '../config/config';

const RegisterUser = async (userData: AuthUserDTO): Promise<TokenResponse | Error> => {

    try {
        const { data }: { data: (AuthUserDTO) } = await axios.post(`${config.API_JAVA}/users/register`, userData);
        
        if (!data.username || !data.email) throw new Error("No se obtuvo los datos del usuario");

        const newToken = AuthUtils.CreateToken(data.username, data.email) as string;
        const newRToken = AuthUtils.CreateRefreshToken(data.username, data.email) as string;

        return {
            token:newToken,
            refreshToken:newRToken
        };

    } catch (error: any) {
        console.log(error.message);
        
        throw error;
    }

}

const LoginUser = async (userData: AuthUserDTO): Promise<TokenResponse | Error> => {

    try {

        const { username, email, password } = userData;
        console.log(userData);

        if (!username || !email || !password) throw Error("Faltan datos para el login");

        const url = `${config.API_JAVA}/users/login?` +
            `username=${userData.username}` +
            `&email=${userData.email}` +
            `&password=${userData.password}`;



        const { data }: { data: (AuthUserDTO) } = await axios.get(url);

        if (!data.username || !data.email) throw new Error("No se obtuvo los datos del usuario");

        const newToken = AuthUtils.CreateToken(data.username, data.email) as string;
        const newRToken = AuthUtils.CreateRefreshToken(data.username, data.email) as string;
        
        return {
            token:newToken,
            refreshToken:newRToken
        };


    } catch (error: any) {
        return error;
    }


}

export const AuthService = {
    RegisterUser,
    LoginUser
}