import dotenv from 'dotenv';

dotenv.config();

export const config = {
  PORT: process.env.PORT || 3005,
  SECRET_KEY: process.env.SECRET_KEY || 'komo',
  API_JAVA: process.env.API_JAVA || 'http://localhost:8080',
  GOOGLE_CLIENT_ID: process.env.GOOGLE_CLIENT_ID || null,
  GOOGLE_CLIENT_SECRET: process.env.GOOGLE_CLIENT_SECRET || null,
  CALLBACK_URL:process.env.CALLBACK_URL || ""
};

export default config;
