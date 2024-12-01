import jwt, { JsonWebTokenError } from "jsonwebtoken";
import config from "../../config/config";

if (!config.SECRET_KEY) {
    throw new Error("SECRET_KEY is not set in environment variables.");
}
const CreateToken = (username: string, email:string) => {

    if (!username || !email) {
        return new JsonWebTokenError("Username and password are required");
    }

    const token = jwt.sign({ username,email }, config.SECRET_KEY, { expiresIn: "1h" });
    return token;
}

const CheckToken = (headerToken: string) => {

    try {
        const token = headerToken.split(" ")[1];
        const payload = jwt.verify(token, config.SECRET_KEY);
        return payload;
    } catch (error) {
        return error;
    }

}

export const AuthUtils = {
    CreateToken,
    CheckToken
}