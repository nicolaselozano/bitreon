import jwt, { JsonWebTokenError } from "jsonwebtoken";
import config from "../../config/config";


const CreateToken = (username: string, email: string) => {
    if (!config.SECRET_KEY) {
        throw new Error("SECRET_KEY is not set in environment variables.");
    }
    if (!username || !email) {
        return new JsonWebTokenError("Username and password are required");
    }

    const token = jwt.sign({ username, email }, config.SECRET_KEY, { expiresIn: "15m" });
    return token;
}

const CreateRefreshToken = (username: string, email: string) => {
    if (!config.REFRESH_SECRET_KEY) {
        throw new Error("SECRET_KEY is not set in environment variables.");
    }
    if (!username || !email) {
        return new JsonWebTokenError("Username and password are required");
    }

    const token = jwt.sign({ username, email }, config.REFRESH_SECRET_KEY, { expiresIn: "20d" });
    return token;
}


const CheckToken = (headerToken: string) => {

    try {
        if (!config.SECRET_KEY) {
            throw new Error("SECRET_KEY is not set in environment variables.");
        }
        const parts = headerToken.split(" ");
        const token = parts.length > 1 ? parts[1] : null;

        if (!token) throw Error("No se proporciono el token");

        const payload = jwt.verify(token, config.SECRET_KEY);
        return payload;
    } catch (error) {
        return error;
    }

}

export const AuthUtils = {
    CreateToken,
    CheckToken,
    CreateRefreshToken
}