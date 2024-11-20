import { Router } from 'express';
import { AuthController } from '../controllers/AuthController';

const router = Router();

router.get('/auth', (req, res) => {
    res.json({ message: 'Auth endpoint' });
});

router.get("/login",AuthController.LoginUser)

router.post("/register",AuthController.RegisterUser);

export default router;
