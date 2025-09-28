# OAuth2 Configuration Detailed Explanation

## 🔐 Keycloak OAuth2 Configuration Parameters

This document explains each OAuth2 configuration parameter used in the auth-service for Keycloak integration.

### 1. Client Name Configuration
```properties
spring.security.oauth2.client.registration.keycloak.client-name=keycloak
```
**Purpose**: Internal name for this OAuth2 provider
- **Usage**: Spring Boot uses this to identify the Keycloak configuration
- **URL Impact**: Creates login URL like `/oauth2/authorization/keycloak`
- **Note**: This is just an internal identifier, not sent to Keycloak

### 2. Client ID Configuration
```properties
spring.security.oauth2.client.registration.keycloak.client-id=${AUTH_SERVICE_CLIENT_ID}
```
**Purpose**: Your application's client ID in Keycloak
- **Value Source**: Environment variable `AUTH_SERVICE_CLIENT_ID = themlef-client`
- **What it does**: Identifies your app to the Keycloak server
- **Keycloak Setup**: This must match the client ID created in Keycloak admin console

### 3. Client Secret Configuration
```properties
spring.security.oauth2.client.registration.keycloak.client-secret=${AUTH_SERVICE_CLIENT_SECRET}
```
**Purpose**: Secret key for Keycloak authentication
- **Value Source**: Environment variable `AUTH_SERVICE_CLIENT_SECRET`
- **Value**: `CRtvfJuhGyWqzFoiF5SVfVG9qDYzkUNE`
- **Security**: Kept secret, used to authenticate your app with Keycloak
- **Important**: Never expose this in public repositories

### 4. Authorization Grant Type
```properties
spring.security.oauth2.client.registration.keycloak.authorization-grant-type=authorization_code
```
**Purpose**: Defines the OAuth2 flow type
- **authorization_code**: Most secure OAuth2 flow
- **Process**: 
  1. User → Login Page 
  2. Authorization Code → Your App 
  3. App exchanges code for token
- **Security**: Prevents token exposure in browser URL

### 5. Scope Configuration
```properties
spring.security.oauth2.client.registration.keycloak.scope=openid,email,offline_access
```
**Purpose**: Defines what information/permissions to request from Keycloak

#### Scope Details:
- **`openid`**: Enables OpenID Connect (gets user identity)
- **`email`**: Access to user's email address
- **`offline_access`**: Allows getting refresh tokens for long-term access

### 6. Redirect URI Configuration
```properties
spring.security.oauth2.client.registration.keycloak.redirect-uri=${AUTH_SERVICE_REDIRECT_URI}
```
**Purpose**: Where Keycloak sends users after successful login
- **Value Source**: Environment variable `AUTH_SERVICE_REDIRECT_URI`
- **Value**: `http://localhost:8090/login/oauth2/code/keycloak`
- **Process**: User logs in → Keycloak redirects to this URL with auth code

## 🌐 Google OAuth2 Configuration Parameters

### 1. Google Client ID
```properties
spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}
```
**Purpose**: Your application's client ID from Google Cloud Console
- **Setup**: Get from [Google Cloud Console](https://console.developers.google.com/)
- **Usage**: Identifies your app to Google's OAuth2 server

### 2. Google Client Secret
```properties
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}
```
**Purpose**: Secret key for Google OAuth2 authentication
- **Security**: Keep confidential, used to authenticate with Google
- **Source**: Google Cloud Console OAuth2 credentials

### 3. Google Scopes
```properties
spring.security.oauth2.client.registration.google.scope=profile,email
```
**Purpose**: Permissions requested from Google
- **`profile`**: Access to user's basic profile information
- **`email`**: Access to user's email address

## 🔧 Manual Provider Endpoint Configuration

### Why Manual Configuration?
Manual endpoint configuration is used instead of auto-discovery to avoid issues in containerized environments.

### Keycloak Provider Endpoints
```properties
spring.security.oauth2.client.provider.keycloak.authorization-uri=${KEYCLOAK_SERVER_URL}/realms/plateforme-realm/protocol/openid-connect/auth
spring.security.oauth2.client.provider.keycloak.token-uri=${KEYCLOAK_SERVER_URL}/realms/plateforme-realm/protocol/openid-connect/token
spring.security.oauth2.client.provider.keycloak.jwk-set-uri=${KEYCLOAK_SERVER_URL}/realms/plateforme-realm/protocol/openid-connect/certs
spring.security.oauth2.client.provider.keycloak.user-info-uri=${KEYCLOAK_SERVER_URL}/realms/plateforme-realm/protocol/openid-connect/userinfo
spring.security.oauth2.client.provider.keycloak.user-name-attribute=preferred_username
```

#### Endpoint Explanations:

1. **Authorization URI**: Where users are redirected to log in
2. **Token URI**: Where authorization codes are exchanged for tokens
3. **JWK Set URI**: Public keys for token verification
4. **User Info URI**: Endpoint to get user information
5. **User Name Attribute**: Which field to use as username

## 🔑 Environment Variables Reference

### Required Environment Variables
Create these in your `.env.auth` file:

```bash
# Keycloak Configuration
AUTH_SERVICE_CLIENT_ID=themlef-client
AUTH_SERVICE_CLIENT_SECRET=your-keycloak-client-secret
AUTH_SERVICE_REDIRECT_URI=http://localhost:8090/login/oauth2/code/keycloak

# Google OAuth2 Configuration  
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret

# Keycloak Server Configuration
KEYCLOAK_SERVER_URL=http://keycloak:8080
```

### Environment Variable Sources
- **From `.env.global`**: Shared infrastructure variables (KEYCLOAK_SERVER_URL)
- **From `.env.auth`**: Service-specific authentication variables

## 🔐 Security Best Practices

### 1. Credential Management
- ✅ Use environment variables for all sensitive data
- ✅ Never hardcode secrets in application.properties
- ✅ Use `.env.example` files for templates
- ❌ Never commit real credentials to version control

### 2. OAuth2 Flow Security
- ✅ Use `authorization_code` grant type (most secure)
- ✅ Implement proper redirect URI validation
- ✅ Use HTTPS in production environments
- ✅ Implement proper session management

### 3. Container Security
- ✅ Use container-specific URLs (keycloak:8080) for internal communication
- ✅ Avoid localhost in containerized environments
- ✅ Implement proper health checks
- ✅ Use manual endpoint configuration for reliability

## 🚀 Testing OAuth2 Flows

### Test Keycloak Login
```bash
curl -i http://localhost:8090/oauth2/authorization/keycloak
```

### Test Google Login
```bash
curl -i http://localhost:8090/oauth2/authorization/google
```

### Expected Responses
Both should return `302 Found` with redirect to respective OAuth2 providers.

## 🛠️ Troubleshooting

### Common Issues

#### 1. Issuer URI Mismatch
**Error**: `The Issuer "http://localhost:8080/..." did not match "http://keycloak:8080/..."`
**Solution**: Use manual endpoint configuration (already implemented)

#### 2. Invalid Client Credentials
**Error**: `invalid_client`
**Solution**: Verify CLIENT_ID and CLIENT_SECRET match Keycloak configuration

#### 3. Redirect URI Mismatch
**Error**: `Invalid redirect URI`
**Solution**: Ensure redirect URI in environment matches Keycloak client configuration

#### 4. Scope Issues
**Error**: `invalid_scope`
**Solution**: Verify requested scopes are enabled in Keycloak client

## 📚 Additional Resources

- [Spring Security OAuth2 Client Documentation](https://docs.spring.io/spring-security/reference/servlet/oauth2/client/index.html)
- [Keycloak Client Configuration](https://www.keycloak.org/docs/latest/server_admin/#_clients)
- [Google OAuth2 Setup Guide](https://developers.google.com/identity/protocols/oauth2)
- [OAuth2 Authorization Code Flow](https://tools.ietf.org/html/rfc6749#section-4.1)