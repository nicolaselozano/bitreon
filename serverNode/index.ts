import express, { Request, Response } from 'express';
import swaggerUi from 'swagger-ui-express';
import authRoutes from './src/routes/Auth';
import path from 'path';
import cors from 'cors';
import fs from 'fs';
import { config } from "dotenv";
import { createProxyMiddleware } from "http-proxy-middleware";

config();

const app = express();

const port = process.env.PORT || 3000;
process.env.NODE_ENV = 'development';

const proxyMiddleware = createProxyMiddleware<Request,Response>({
  target:`${'http://localhost:8080'}`,
  changeOrigin:true
});

app.use(express.json());
app.use(cors());

app.use("/api",proxyMiddleware);

app.use(
    authRoutes,
);

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
