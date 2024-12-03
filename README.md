# API para Plataforma Estilo Patreon

Esta API está diseñada para una plataforma similar a Patreon, desarrollada utilizando **Express con TypeScript** y **Java Spring Boot**. Proporciona autenticación robusta y una serie de características clave para gestionar usuarios, contenido y suscripciones.

## Características Principales

### 1. **Autenticación**
- Soporte para inicio de sesión mediante **cuentas de Google** (OAuth2).
- Autenticación mediante **email y contraseña**.
- Generación de **tokens de acceso** para sesiones seguras.
- Implementación de **JWT (JSON Web Tokens)** para la gestión de sesiones.

### 2. **Control de Flujo y Seguridad**
- **Rate Limit**: Protege la API de abuso y ataques al limitar la cantidad de solicitudes permitidas por unidad de tiempo.
- **Validación de entrada**: Todas las solicitudes son validadas para garantizar integridad y seguridad.

### 3. **Lógica de Negocio**
- API enfocada en la gestión de **creadores de contenido** y **suscriptores**.
- Soporte para:
  - Gestión de cuentas de usuario (creadores y suscriptores).
  - Creación, actualización y eliminación de contenido exclusivo.
  - Gestión de suscripciones y niveles de membresía.

### 4. **Tecnologías Utilizadas**
- **Backend**:
  - **Express** con **TypeScript**: Proporciona un servidor escalable y flexible para manejar la lógica de negocio y la interacción con clientes.
  - **Spring Boot**: Implementa características adicionales y provee un entorno robusto para manejar aspectos como autenticación y control de datos.
- **Base de Datos**:
  - Soporte para bases de datos relacionales.
  - Configuración para persistencia y pruebas mediante H2 (Spring Boot).

## Instalación y Uso

### Requisitos Previos
- **Node.js** y **npm** instalados.
- **Java** 11 o superior.
- Configuración de una base de datos (opcional, dependiendo del entorno de despliegue).
