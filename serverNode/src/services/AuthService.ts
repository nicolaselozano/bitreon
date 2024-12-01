import axios from "axios"
import { AuthUserDTO } from "../utils/DTO/AuthUserDTO"
import { AuthUtils } from "../utils/auth/AuthUtils";
import config from '../config/config';

const RegisterUser = async (userData: AuthUserDTO): Promise<string | Error> => {

    try {
        const { data }: { data: (AuthUserDTO) } = await axios.post(`${config.API_JAVA}/users/register`, userData);

        if (!data.username || !data.email) throw new Error("No se obtuvo los datos del usuario");

        const newToken = AuthUtils.CreateToken(data.username, data.email);

        return newToken;

    } catch (error: any) {
        console.error(error);

        return error;
    }

}

const LoginUser = async (userData: AuthUserDTO): Promise<string | Error> => {

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
        const newToken = AuthUtils.CreateToken(data.username, data.email);

        return newToken;


    } catch (error: any) {
        return error;
    }


}

export const AuthService = {
    RegisterUser,
    LoginUser
}