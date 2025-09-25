@echo off
setlocal enabledelayedexpansion

echo === Environment Configuration Validation ===
echo.

REM Load .env file if it exists
if exist .env (
    echo Loading environment variables from .env file...
    for /f "tokens=1,2 delims==" %%a in (.env) do (
        if not "%%a"=="" if not "%%b"=="" (
            set %%a=%%b
        )
    )
    echo ✓ .env file found and loaded
) else (
    echo ✗ .env file not found
    pause
    exit /b 1
)

echo.
echo Checking required environment variables:
echo.

set MISSING_COUNT=0

REM Check required variables
set "vars=KEYCLOAK_SERVER_URL KEYCLOAK_REALM SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI AUTH_SERVICE_CLIENT_ID AUTH_SERVICE_CLIENT_SECRET ANGULAR_CLIENT_ID"

for %%v in (%vars%) do (
    if defined %%v (
        echo ✓ %%v is set
    ) else (
        echo ✗ %%v is not set
        set /a MISSING_COUNT+=1
    )
)

echo.
echo Checking optional environment variables:
echo.

set "optional_vars=AUTH_SERVICE_PORT INVENTORY_SERVICE_PORT ANGULAR_PORT H2_CONSOLE_ENABLED ENVIRONMENT"

for %%v in (%optional_vars%) do (
    if defined %%v (
        call echo ✓ %%v is set to: %%%v%%
    ) else (
        echo - %%v is not set ^(using default^)
    )
)

echo.
echo === Validation Summary ===

if %MISSING_COUNT% equ 0 (
    echo ✓ All required environment variables are configured
    echo.
    echo Configuration Summary:
    echo - Keycloak Server: %KEYCLOAK_SERVER_URL%
    echo - Keycloak Realm: %KEYCLOAK_REALM%
    if defined AUTH_SERVICE_PORT (
        echo - Auth Service Port: %AUTH_SERVICE_PORT%
    ) else (
        echo - Auth Service Port: 8090 ^(default^)
    )
    if defined INVENTORY_SERVICE_PORT (
        echo - Inventory Service Port: %INVENTORY_SERVICE_PORT%
    ) else (
        echo - Inventory Service Port: 8091 ^(default^)
    )
    if defined ANGULAR_PORT (
        echo - Angular Port: %ANGULAR_PORT%
    ) else (
        echo - Angular Port: 4200 ^(default^)
    )
    echo.
    echo 🚀 Ready to start services!
    echo.
    echo Next steps:
    echo 1. Make sure Keycloak is running on %KEYCLOAK_SERVER_URL%
    echo 2. Run start-services.bat to get service startup commands
    echo 3. Start each service in separate terminals
) else (
    echo ✗ Missing %MISSING_COUNT% required environment variables
    echo.
    echo Please update your .env file with the missing variables
    echo Refer to ENVIRONMENT_SETUP.md for detailed configuration instructions
)

echo.
pause