import jwt, { JsonWebTokenError } from "jsonwebtoken";
import config from "../../config/config";

const CreateToken = (username: string, email: string) => {
    if (!config.SECRET_KEY) {
        throw new Error("SECRET_KEY is not set in environment variables.");
    }

    if (!username || !email) {
        throw new JsonWebTokenError("Username and email are required.");
    }

    const token = jwt.sign({ username, email }, config.SECRET_KEY, { expiresIn: "15m" });

    return token;
};

const CreateRefreshToken = (username: string, email: string) => {
    if (!config.REFRESH_SECRET_KEY) {
        throw new Error("REFRESH_SECRET_KEY is not set in environment variables.");
    }

    if (!username || !email) {
        throw new JsonWebTokenError("Username and email are required.");
    }

    const refreshToken = jwt.sign({ username, email }, config.REFRESH_SECRET_KEY, { expiresIn: "20d" });

    return refreshToken;
};

const RefreshTokens = (refreshToken: string) => {
    try {
        if (!config.REFRESH_SECRET_KEY || !config.SECRET_KEY) {
            throw new Error("SECRET_KEY or REFRESH_SECRET_KEY is not set in environment variables.");
        }

        const parts = refreshToken.split(" ");
        const token = parts.length > 1 ? parts[1] : null;
        if (!token) throw new Error("Token not provided.");
        
        // Verificar el refresh token
        const payload: any = jwt.verify(token, config.REFRESH_SECRET_KEY);
        console.log("Refresh token payload:", payload);

        // Crear nuevos tokens
        const newAccessToken = jwt.sign({ username: payload.username, email: payload.email }, config.SECRET_KEY, {
            expiresIn: "15m",
        });
        const newRefreshToken = jwt.sign({ username: payload.username, email: payload.email }, config.REFRESH_SECRET_KEY, {
            expiresIn: "20d",
        });

        return { accessToken: newAccessToken, refreshToken: newRefreshToken };
    } catch (error) {
        if (error === "TokenExpiredError") {
            console.error("Refresh token expired:", error);
            throw new Error("Refresh token has expired");
        } else {
            console.error("Error verifying refresh token:", error);
            throw new Error("Invalid refresh token");
        }
    }
};


const CheckToken = (headerToken: string) => {
    try {
        if (!config.SECRET_KEY) {
            throw new Error("SECRET_KEY is not set in environment variables.");
        }

        const parts = headerToken.split(" ");
        const token = parts.length > 1 ? parts[1] : null;

        if (!token) throw new Error("Token not provided.");

        const payload = jwt.verify(token, config.SECRET_KEY);

        return payload;
    } catch (error) {
        console.error("Error verifying token:", error);
        throw new Error("Invalid token");
    }
};

export const AuthUtils = {
    CreateToken,
    CreateRefreshToken,
    RefreshTokens,
    CheckToken,
};
