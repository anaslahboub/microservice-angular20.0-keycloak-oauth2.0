@echo off
echo Loading environment variables from .env file...

REM Read .env file and set environment variables
for /f "tokens=1,2 delims==" %%a in (.env) do (
    if not "%%a"=="" if not "%%b"=="" (
        set %%a=%%b
    )
)

echo.
echo Environment variables loaded successfully!
echo.
echo Available services:
echo 1. Auth Service (port %AUTH_SERVICE_PORT%)
echo 2. Inventory Service (port %INVENTORY_SERVICE_PORT%)
echo 3. Angular Frontend (port %ANGULAR_PORT%)
echo.
echo To start a service:
echo - Auth Service: cd auth-service && mvn spring-boot:run
echo - Inventory Service: cd inventory-service && mvn spring-boot:run
echo - Angular Frontend: cd front-end && npm start
echo.
echo Make sure Keycloak is running on %KEYCLOAK_SERVER_URL%
pause