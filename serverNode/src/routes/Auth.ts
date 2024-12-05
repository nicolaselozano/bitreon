import { Router } from 'express';
import { AuthController } from '../controllers/AuthController';
import { MToken } from '../middlewares/MToken';

const router = Router();

/**
 * @swagger
 * /auth/:
 *   get:
 *     tags:
 *       - Auth
 *     summary: Verifica el acceso al endpoint de autenticación.
 *     description: Este endpoint comprueba si el usuario está autenticado.
 *     responses:
 *       200:
 *         description: Acceso permitido.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: "Auth endpoint"
 *       401:
 *         description: Token inválido o no proporcionado.
 */
router.get('/', MToken.Check, (req, res) => {
  res.json({ message: 'Auth endpoint' });
});

/**
 * @swagger
 * /auth/refresh:
 *   post:
 *     tags:
 *       - Auth
 *     summary: Refresca el token de acceso.
 *     description: Este endpoint permite al usuario obtener un nuevo token de acceso usando su refresh token.
 *     responses:
 *       200:
 *         description: Token refrescado correctamente.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: "Auth endpoint"
 *       401:
 *         description: Token inválido o no proporcionado.
 */
router.post('/refresh', MToken.Check, (req, res) => {
  res.json({ message: 'Auth endpoint' });
});

/**
 * @swagger
 * /auth/login:
 *   get:
 *     tags:
 *       - Auth
 *     summary: Inicia sesión.
 *     description: Este endpoint permite a los usuarios iniciar sesión proporcionando sus credenciales.
 *     parameters:
 *       - in: query
 *         name: username
 *         schema:
 *           type: string
 *         required: true
 *         description: Nombre de usuario.
 *       - in: query
 *         name: password
 *         schema:
 *           type: string
 *         required: true
 *         description: Contraseña del usuario.
 *       - in: query
 *         name: email
 *         schema:
 *           type: string
 *         required: true
 *         description: Correo electrónico del usuario.
 *     responses:
 *       200:
 *         description: Inicio de sesión exitoso.
 *         content:
 *           application/json:
 *             schema:
 *               type: string
 *               example: "jwt_token_example"
 *       404:
 *         description: Credenciales inválidas.
 */
router.get('/login', AuthController.LoginUser);

/**
 * @swagger
 * /auth/register:
 *   post:
 *     tags:
 *       - Auth
 *     summary: Registra un nuevo usuario.
 *     description: Permite a los usuarios registrarse proporcionando sus datos.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               username:
 *                 type: string
 *                 example: "new_user"
 *               email:
 *                 type: string
 *                 example: "new_user@example.com"
 *               password:
 *                 type: string
 *                 example: "securepassword"
 *     responses:
 *       200:
 *         description: Registro exitoso.
 *         content:
 *           application/json:
 *             schema:
 *               type: string
 *               example: "jwt_token_example"
 *       404:
 *         description: Error durante el registro.
 */
router.post('/register', AuthController.RegisterUser);

export default router;
