# Environment Configuration Guide

This project uses environment variables to configure the Keycloak authentication system. The configuration is centralized in the `.env` file located in the root directory.

## Environment Variables Overview

### Keycloak Server Configuration
- `KEYCLOAK_SERVER_URL`: The base URL of your Keycloak server (default: http://localhost:8080)
- `KEYCLOAK_REALM`: The Keycloak realm name (default: plateforme-realm)

### JWT Configuration
- `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI`: JWT issuer URI
- `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI`: JWK Set URI for token validation

### Service Ports
- `AUTH_SERVICE_PORT`: Port for the Spring Boot auth service (default: 8090)
- `INVENTORY_SERVICE_PORT`: Port for the inventory service (default: 8091)
- `ANGULAR_PORT`: Port for the Angular frontend (default: 4200)

### Client Configuration
- `AUTH_SERVICE_CLIENT_ID`: Keycloak client ID for the auth service
- `AUTH_SERVICE_CLIENT_SECRET`: Keycloak client secret for the auth service
- `ANGULAR_CLIENT_ID`: Keycloak client ID for the Angular frontend

### Database Configuration
- `H2_CONSOLE_ENABLED`: Enable H2 console for development (default: true)
- `AUTH_DB_URL`: Database URL for auth service
- `INVENTORY_DB_URL`: Database URL for inventory service

## Setting Up Keycloak

1. **Start Keycloak Server**:
   ```bash
   # Download and start Keycloak
   docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev
   ```

2. **Create Realm**:
   - Access Keycloak admin console at http://localhost:8080
   - Login with admin/admin
   - Create a new realm called `plateforme-realm`

3. **Create Clients**:
   
   **For Auth Service (Spring Boot):**
   - Client ID: `plateforme-client`
   - Client Type: `confidential`
   - Authentication flow: `Standard flow` enabled
   - Valid redirect URIs: `http://localhost:8090/login/oauth2/code/keycloak`
   - Web origins: `http://localhost:8090`

   **For Angular Frontend:**
   - Client ID: `angular-client`
   - Client Type: `public`
   - Authentication flow: `Standard flow` enabled
   - Valid redirect URIs: `http://localhost:4200/*`
   - Web origins: `http://localhost:4200`

4. **Create Users**:
   - Create test users in the realm
   - Set passwords for the users

## Running the Application

1. **Load Environment Variables**:
   ```bash
   # On Windows
   start-services.bat
   
   # On Linux/Mac, source the .env file or manually export variables
   ```

2. **Start Services in Order**:
   ```bash
   # Terminal 1: Auth Service
   cd auth-service
   mvn spring-boot:run
   
   # Terminal 2: Inventory Service
   cd inventory-service
   mvn spring-boot:run
   
   # Terminal 3: Angular Frontend
   cd front-end
   npm install
   npm start
   ```

3. **Access the Application**:
   - Frontend: http://localhost:4200
   - Auth Service: http://localhost:8090
   - Inventory Service: http://localhost:8091
   - Keycloak Admin: http://localhost:8080

## Troubleshooting

### Common Issues:

1. **CORS Errors**: Make sure the `CORS_ALLOWED_ORIGINS` includes all your service URLs
2. **Authentication Failures**: Verify Keycloak realm and client configurations match the environment variables
3. **Token Validation Errors**: Ensure the JWT issuer URI and JWK Set URI are correct
4. **Service Discovery**: Make sure all services are running on their configured ports

### Environment Variable Validation:

To verify your environment variables are loaded correctly, you can check them in your application:
- Spring Boot services will log configuration on startup
- Angular service configurations are visible in browser developer tools

### Security Notes:

- Never commit real client secrets to version control
- Use different secrets for different environments (dev, staging, prod)
- Consider using encrypted environment variables for production
- Regularly rotate client secrets

## Development vs Production

For development, you can use the provided default values. For production:

1. Generate new client secrets in Keycloak
2. Update the `.env` file with production URLs and secrets
3. Use proper SSL certificates
4. Configure proper CORS policies
5. Set appropriate logging levels

## Additional Configuration

### Angular Environment Configuration

The Angular application uses the environment variables through the app configuration. If you need to modify the Keycloak configuration for Angular, update the `app.config.ts` file:

```typescript
provideKeycloak({
  config: {
    url: process.env['KEYCLOAK_SERVER_URL'] || 'http://localhost:8080/',
    realm: process.env['KEYCLOAK_REALM'] || 'plateforme-realm',
    clientId: process.env['ANGULAR_CLIENT_ID'] || 'angular-client'
  }
})
```

### Spring Boot Profile Configuration

You can also use Spring Boot profiles to manage different environments:

```properties
# application-dev.properties
spring.profiles.active=dev

# application-prod.properties
spring.profiles.active=prod
```