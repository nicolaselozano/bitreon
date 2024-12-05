import { Router } from 'express';
import passport from 'passport';
import { GoogleAuthController } from '../controllers/GoogleAuthController';

const router = Router();

/**
 * @swagger
 * /auth/google:
 *   get:
 *     tags:
 *       - Google Auth
 *     summary: Redirige al flujo de autenticación de Google.
 *     description: Inicia el proceso de autenticación con Google.
 *     responses:
 *       302:
 *         description: Redirección al login de Google.
 */
router.get('/google', passport.authenticate('google', { scope: ['profile', 'email'] }));

/**
 * @swagger
 * /auth/google/callback:
 *   get:
 *     tags:
 *       - Google Auth
 *     summary: Callback de autenticación de Google.
 *     description: Endpoint de callback al que Google redirige después de la autenticación.
 *     responses:
 *       200:
 *         description: Autenticación exitosa.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 newToken:
 *                   type: string
 *                   example: "jwt_token_example"
 *       401:
 *         description: Error durante la autenticación.
 */
router.get('/google/callback', passport.authenticate('google', { session: false }), GoogleAuthController.googleCallback);

export default router;
