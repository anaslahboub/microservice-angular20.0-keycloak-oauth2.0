@echo off
echo ========================================
echo   CLEAN REPOSITORY SETUP SCRIPT
echo ========================================
echo.
echo This script will:
echo 1. Reset git history to remove secrets
echo 2. Add all files with clean configuration
echo 3. Push to GitHub safely
echo.
echo WARNING: This will rewrite git history!
echo.
pause

echo.
echo Step 1: Resetting to initial commit...
git reset --hard 86b5953

echo.
echo Step 2: Adding clean files...
git add .gitignore
git add .env.example
git add ENVIRONMENT_SETUP.md
git add ENV_CONFIG_SUMMARY.md
git add GITHUB_PUSH_GUIDE.md
git add GIT_COMMANDS.md
git add SECRET_CLEANUP_SOLUTION.md
git add start-services.bat
git add validate-env.bat
git add validate-env.sh

echo.
echo Step 3: Adding source code with clean configuration...
git add auth-service/src/main/resources/application.properties
git add inventory-service/src/main/resources/application.properties
git add inventory-service/pom.xml

echo.
echo Step 4: Adding all other source files...
git add auth-service/src/ auth-service/pom.xml auth-service/mvnw.cmd
git add inventory-service/src/ inventory-service/mvnw.cmd
git add front-end/src/ front-end/package.json front-end/angular.json front-end/tsconfig.json
git add pom.xml README.md LICENSE

echo.
echo Step 5: Checking what will be committed...
git status

echo.
echo Step 6: Creating clean commit...
git commit -m "feat: Complete Spring Boot + Angular + Keycloak authentication system

- Add comprehensive microservices architecture
- Configure environment variables with secure placeholders
- Add Angular frontend with Keycloak integration
- Include detailed documentation and setup guides
- Add validation scripts and helper tools
- Implement proper .gitignore for security
- Remove all hardcoded secrets and use environment variables"

echo.
echo Step 7: Force pushing clean history...
git push --force-with-lease origin main

echo.
echo ========================================
echo   SETUP COMPLETE!
echo ========================================
echo.
echo Your repository now contains:
echo - Clean source code without secrets
echo - Environment configuration templates
echo - Comprehensive documentation
echo - Helper scripts for development
echo.
echo Next steps:
echo 1. Your local .env file still contains your real secrets for development
echo 2. Follow ENVIRONMENT_SETUP.md to configure Keycloak
echo 3. Use the validation scripts to verify your setup
echo.
pause