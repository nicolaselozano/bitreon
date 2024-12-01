import { NextFunction, Request, Response, Router } from 'express';
import { AuthController } from '../controllers/AuthController';
import { bannedIPs } from '../services/security/BanIp';

const router = Router();

router.use((req: Request, res: Response, next: NextFunction):void => {
    const clientIP = req.ip;

    if (bannedIPs.has(clientIP)) {
        res.status(403).json({ error: 'Tu IP está bloqueada por actividad sospechosa.' });
    }

    next();
});

router.get('/auth', (req, res) => {
    res.json({ message: 'Auth endpoint' });
});

router.get("/login",AuthController.LoginUser);


router.post("/register",AuthController.RegisterUser);

export default router;
