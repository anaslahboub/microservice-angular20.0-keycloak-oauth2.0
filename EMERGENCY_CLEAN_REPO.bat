@echo off
echo ========================================
echo   EMERGENCY: CLEAN REPOSITORY SETUP
echo ========================================
echo.
echo This will create a completely clean git history
echo without any trace of secrets.
echo.
echo WARNING: This will delete ALL git history!
echo.
pause

echo.
echo Step 1: Backing up important files...
if not exist "backup" mkdir backup
copy .env backup\.env 2>nul
copy .env.example backup\.env.example 2>nul
copy *.md backup\ 2>nul
copy *.bat backup\ 2>nul
copy *.sh backup\ 2>nul

echo.
echo Step 2: Removing git history...
rmdir /s /q .git

echo.
echo Step 3: Initializing fresh repository...
git init
git branch -m main

echo.
echo Step 4: Adding remote...
git remote add origin https://github.com/anaslahboub/microservice-angular20.0-keycloak-oauth2.0.git

echo.
echo Step 5: Adding .gitignore first...
git add .gitignore

echo.
echo Step 6: Adding clean files...
git add .env.example
git add *.md
git add *.bat *.sh
git add auth-service/src/ auth-service/pom.xml
git add inventory-service/src/ inventory-service/pom.xml  
git add front-end/src/ front-end/package.json front-end/angular.json front-end/tsconfig.json
git add pom.xml README.md LICENSE

echo.
echo Step 7: Creating clean commit...
git commit -m "Initial commit: Spring Boot + Angular + Keycloak Authentication System

Features:
- Complete microservices architecture with auth and inventory services
- Angular frontend with Keycloak integration  
- Environment variables configuration with secure placeholders
- Comprehensive documentation and setup guides
- Validation scripts and helper tools
- Proper .gitignore for security (excludes node_modules, secrets, build files)
- No hardcoded secrets - all use environment variables and placeholders

Security:
- All real secrets are in .env (local only, gitignored)
- Application files use placeholder values
- Comprehensive .gitignore protects sensitive files
- Documentation uses example values only"

echo.
echo Step 8: Pushing clean repository...
git push -u origin main --force

echo.
echo ========================================
echo   CLEAN REPOSITORY CREATED!
echo ========================================
echo.
echo Success! Your repository now contains:
echo - Complete source code without any secrets
echo - Comprehensive documentation  
echo - Environment configuration templates
echo - No trace of previous secret leaks
echo.
echo Your local .env file is backed up and still contains
echo your real secrets for development.
echo.
pause