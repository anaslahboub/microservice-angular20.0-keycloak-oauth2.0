#!/bin/bash

# Environment Validation Script
echo "=== Environment Configuration Validation ==="
echo ""

# Load .env file if it exists
if [ -f .env ]; then
    export $(grep -v '^#' .env | xargs)
    echo "✓ .env file found and loaded"
else
    echo "✗ .env file not found"
    exit 1
fi

# Required variables
REQUIRED_VARS=(
    "KEYCLOAK_SERVER_URL"
    "KEYCLOAK_REALM" 
    "SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI"
    "SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI"
    "AUTH_SERVICE_CLIENT_ID"
    "AUTH_SERVICE_CLIENT_SECRET"
    "ANGULAR_CLIENT_ID"
)

echo ""
echo "Checking required environment variables:"
echo ""

MISSING_VARS=()

for var in "${REQUIRED_VARS[@]}"; do
    if [ -z "${!var}" ]; then
        echo "✗ $var is not set"
        MISSING_VARS+=("$var")
    else
        echo "✓ $var is set"
    fi
done

echo ""
echo "Checking optional environment variables:"
echo ""

OPTIONAL_VARS=(
    "AUTH_SERVICE_PORT"
    "INVENTORY_SERVICE_PORT"
    "ANGULAR_PORT"
    "H2_CONSOLE_ENABLED"
    "ENVIRONMENT"
)

for var in "${OPTIONAL_VARS[@]}"; do
    if [ -z "${!var}" ]; then
        echo "- $var is not set (using default)"
    else
        echo "✓ $var is set to: ${!var}"
    fi
done

echo ""
echo "=== Validation Summary ==="

if [ ${#MISSING_VARS[@]} -eq 0 ]; then
    echo "✓ All required environment variables are configured"
    echo ""
    echo "Configuration Summary:"
    echo "- Keycloak Server: $KEYCLOAK_SERVER_URL"
    echo "- Keycloak Realm: $KEYCLOAK_REALM"
    echo "- Auth Service Port: ${AUTH_SERVICE_PORT:-8090}"
    echo "- Inventory Service Port: ${INVENTORY_SERVICE_PORT:-8091}"
    echo "- Angular Port: ${ANGULAR_PORT:-4200}"
    echo ""
    echo "🚀 Ready to start services!"
    exit 0
else
    echo "✗ Missing required environment variables:"
    for var in "${MISSING_VARS[@]}"; do
        echo "  - $var"
    done
    echo ""
    echo "Please update your .env file with the missing variables"
    exit 1
fi