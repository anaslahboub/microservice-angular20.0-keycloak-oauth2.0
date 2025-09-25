# Solution: Remove Secrets from Git History

## Problem
GitHub's secret scanning detected Google OAuth credentials in your git commits, even though we removed them from the current version.

## Solution: Clean Git History

### Option 1: Reset and Force Push (Recommended for new repositories)

```bash
# 1. Find the commit before the secrets were added
git log --oneline

# 2. Reset to a commit before secrets were introduced (if repository is new)
# Replace 'commit-hash' with the actual hash before secrets
git reset --hard commit-hash

# 3. Add all clean files
git add .gitignore .env.example ENVIRONMENT_SETUP.md ENV_CONFIG_SUMMARY.md GITHUB_PUSH_GUIDE.md GIT_COMMANDS.md
git add start-services.bat validate-env.bat validate-env.sh
git add auth-service/src/main/resources/application.properties
git add inventory-service/src/main/resources/application.properties

# 4. Commit with clean configuration
git commit -m "feat: Add environment configuration with placeholders

- Configure environment variables for all services
- Add comprehensive documentation and setup guides
- Remove hardcoded secrets and use placeholders
- Add validation scripts and .gitignore for security"

# 5. Force push to overwrite history
git push --force-with-lease origin main
```

### Option 2: Use BFG Repo-Cleaner (For repositories with important history)

```bash
# 1. Install BFG Repo-Cleaner
# Download from: https://rtyley.github.io/bfg-repo-cleaner/

# 2. Create a file with secrets to remove
echo "your-google-client-id" > secrets.txt
echo "your-google-client-secret" >> secrets.txt
echo "your-keycloak-client-secret" >> secrets.txt

# 3. Clean repository
java -jar bfg.jar --replace-text secrets.txt

# 4. Clean up and push
git reflog expire --expire=now --all && git gc --prune=now --aggressive
git push --force
```

### Option 3: Create New Repository (Simplest)

If this is a new project and you don't need the history:

```bash
# 1. Remove git history
rm -rf .git

# 2. Initialize new repository
git init
git branch -m main

# 3. Add remote
git remote add origin https://github.com/anaslahboub/microservice-angular20.0-keycloak-oauth2.0

# 4. Add clean files
git add .gitignore .env.example ENVIRONMENT_SETUP.md ENV_CONFIG_SUMMARY.md GITHUB_PUSH_GUIDE.md
git add start-services.bat validate-env.bat validate-env.sh
git add auth-service/ inventory-service/ front-end/ pom.xml README.md LICENSE

# 5. Commit and push
git commit -m "Initial commit: Spring Boot + Angular + Keycloak authentication system

- Complete microservices architecture with auth and inventory services
- Angular frontend with Keycloak integration
- Environment variables configuration with .env.example
- Comprehensive documentation and setup guides
- Validation scripts for environment configuration
- Secure configuration without hardcoded secrets"

git push -u origin main
```

## Current File Status

✅ **Files Ready to Push:**
- All source code files (.java, .ts, .html, etc.)
- Configuration files with placeholder values
- Documentation (README.md, ENVIRONMENT_SETUP.md, etc.)
- Scripts (validate-env.bat, start-services.bat, etc.)
- .gitignore and .env.example

❌ **Secrets Removed:**
- Google OAuth Client ID: Now uses placeholder "your-google-client-id"
- Google OAuth Client Secret: Now uses placeholder "your-google-client-secret"  
- Keycloak Client Secret: Now uses placeholder "your-keycloak-client-secret"

## Next Steps

1. **Choose Option 3** (recommended for new projects) - create fresh repository
2. Or **choose Option 1** if you want to keep some history
3. After successful push, update your local .env file with real secrets for development
4. Never commit the real .env file - it's protected by .gitignore

## Prevention

The new setup prevents future secret leaks:
- ✅ .gitignore excludes .env files
- ✅ .env.example shows structure without secrets
- ✅ application.properties uses placeholders
- ✅ Documentation explains secure practices