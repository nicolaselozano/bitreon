import jwt, { JsonWebTokenError } from "jsonwebtoken";
import dotenv from "dotenv";

dotenv.config();

const secretKey = process.env.SECRET_KEY as string;

if (!secretKey) {
    throw new Error("SECRET_KEY is not set in environment variables.");
}
const CreateToken = (username: string, email:string) => {

    if (!username || !email) {
        return new JsonWebTokenError("Username and password are required");
    }

    const token = jwt.sign({ username,email }, secretKey, { expiresIn: "1h" });
    return token;
}

const CheckToken = (headerToken: string) => {

    try {
        const token = headerToken.split(" ")[1];
        const payload = jwt.verify(token, secretKey);
        return payload;
    } catch (error) {
        return error;
    }

}

export const AuthUtils = {
    CreateToken,
    CheckToken
}