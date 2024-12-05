import swaggerAutogen from 'swagger-autogen';
import path from 'path';

const doc = {
  info: {
    version: '1.0.0',
    title: 'REST API',
    description: 'Descripción de la API de autenticación',
  },
  servers: [
    {
      url: 'http://localhost:3005',
      description: 'Servidor local de desarrollo',
    },
  ],
  tags: [
    {
      name: 'Auth',
      description: 'Operaciones relacionadas con la autenticación',
    },
    {
      name: 'Google Auth',
      description: 'Operaciones relacionadas con la autenticación mediante Google',
    },
  ],
  components: {
    schemas: {},
  },
};

const outputFile = './swagger-output.json';
const endpointsFiles = [path.resolve(__dirname, '../routes/auth.ts'), path.resolve(__dirname, '../routes/googleAuth.ts')];

swaggerAutogen({ openapi: '3.0.0' })(outputFile, endpointsFiles, doc)
  .then(() => {
    console.log('swagger-output.json generado correctamente');
  })
  .catch((error) => {
    console.error('Error al generar swagger-output.json:', error);
  });
