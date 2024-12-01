import { NextFunction, Request, Response } from "express";
import { rateLimit } from "express-rate-limit";

const SetConfig = (minutes: number = 10, limit: number = 10, handler?: (req: Request, res: Response, next: NextFunction) => void) => {
    return rateLimit({
        windowMs: minutes * 60 * 1000,
        max: limit,
        message: 'Demasiadas solicitudes desde esta IP. Por favor, inténtalo más tarde.',
        handler,
        standardHeaders: 'draft-7',
        skipFailedRequests:true,
        legacyHeaders: false,
    });
};

export const RateLimitService = {
    SetTime: SetConfig,
};
