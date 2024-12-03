import { NextFunction, Request, Response, Router } from 'express';
import { AuthController } from '../controllers/AuthController';
import { MToken } from '../middlewares/MToken';

const router = Router();

router.get('/',MToken.Check, (req, res) => {
    res.json({ message: 'Auth endpoint' });
});

router.get("/login",AuthController.LoginUser);


router.post("/register",AuthController.RegisterUser);

export default router;
