import express, { Request, Response } from 'express';
import swaggerUi from 'swagger-ui-express';
import path from 'path';
import cors from 'cors';
import fs from 'fs';
import { config } from "dotenv";
import { createProxyMiddleware } from "http-proxy-middleware";
import passport from 'passport';
import './src/config/passport';
import authRoutes from './src/routes/Auth';
import googleAuthRoutes from './src/routes/GoogleAuth';
import { RateLimitService } from './src/services/RateLimitService';
import cookieParser from 'cookie-parser';

config();

const app = express();
const port = process.env.PORT || 3000;

app.use(express.json());
app.use(cookieParser());
app.use(cors());
app.use(passport.initialize());

// Proxy para api Java
const proxyMiddleware = createProxyMiddleware<Request, Response>({
  target: 'http://localhost:8080',
  changeOrigin: true,
});

app.use("/api", proxyMiddleware);

// Rutas de autenticación
app.use("/auth",
  RateLimitService.SetTime(2, 15,
    (req, res, next) => {
      const clientIP = req.ip;

      console.log(`IP bloqueada: ${clientIP}`);
      res.status(429).json({ error: "Demasiadas solicitudes. Tu IP ha sido bloqueada temporalmente." });
    }),
  RateLimitService.SetTime(1, 5),
  authRoutes,
  googleAuthRoutes
);

// Configuración Swagger
const swaggerFilePath = path.join(__dirname, './src/config/swagger.json');

if (fs.existsSync(swaggerFilePath)) {
  console.log('swagger.json se ha generado correctamente');
} else {
  console.log('swagger.json no se ha generado');
}

app.use('/swagger.json', (req, res) => {
  res.sendFile(swaggerFilePath);
});

app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(undefined, {
  swaggerOptions: {
    url: '/swagger.json',
  },
}));

app.listen(port, () => {
  console.log(`API corriendo en http://localhost:${port}`);
  console.log(`Swagger UI disponible en http://localhost:${port}/api-docs`);
});
