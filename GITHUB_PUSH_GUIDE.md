# Guide: Fichiers à Pousser sur GitHub

## ✅ Fichiers à INCLURE dans GitHub

### 📁 Fichiers de Code Source
- **Tous les fichiers Java** (`.java`)
- **Fichiers de configuration Spring Boot** (`application.properties`)
- **Fichiers Angular** (`.ts`, `.html`, `.css`, `.json`)
- **Fichiers Maven** (`pom.xml`, `mvnw`, `mvnw.cmd`)
- **Fichiers Angular** (`package.json`, `package-lock.json`, `angular.json`, `tsconfig.json`)

### 📁 Fichiers de Configuration
- **`.gitignore`** ✅ (Créé)
- **`.env.example`** ✅ (Créé - template sans secrets)
- **Scripts d'aide** (`start-services.bat`, `validate-env.bat`, `validate-env.sh`)

### 📁 Documentation
- **README.md** ✅
- **ENVIRONMENT_SETUP.md** ✅ (Guide de configuration)
- **ENV_CONFIG_SUMMARY.md** ✅ (Résumé de la configuration)
- **LICENSE** ✅

## ❌ Fichiers à EXCLURE de GitHub

### 🔒 Fichiers Sensibles (Secrets)
- **`.env`** ❌ (Contient des secrets réels)
- **Fichiers avec des mots de passe/secrets**
- **Certificats et clés privées**

### 🗂️ Fichiers Générés/Temporaires
- **`target/`** (Fichiers compilés Maven)
- **`node_modules/`** (Dépendances Node.js)
- **`.angular/cache/`** (Cache Angular)
- **`dist/`** (Build Angular)
- **Fichiers IDE** (`.idea/`, `.vscode/`)
- **Fichiers OS** (`.DS_Store`, `Thumbs.db`)
- **"New Text Document.txt"** ❌ (Fichier temporaire)

## 🚀 Commandes Git pour Pousser

### 1. Vérifier le Statut
```bash
git status
```

### 2. Ajouter les Nouveaux Fichiers Utiles
```bash
# Ajouter les fichiers de documentation et scripts
git add .gitignore
git add .env.example
git add ENVIRONMENT_SETUP.md
git add ENV_CONFIG_SUMMARY.md
git add start-services.bat
git add validate-env.bat
git add validate-env.sh

# Ajouter les modifications des fichiers de configuration
git add auth-service/src/main/resources/application.properties
git add inventory-service/src/main/resources/application.properties
```

### 3. Exclure les Fichiers Sensibles/Temporaires
```bash
# Ne PAS ajouter ces fichiers:
# git add .env                    ❌ (Contient des secrets)
# git add .idea/                  ❌ (Fichiers IDE)
# git add "New Text Document.txt" ❌ (Fichier temporaire)
```

### 4. Faire le Commit
```bash
git commit -m "feat: Configure environment variables and add documentation

- Add comprehensive .env.example template
- Update application.properties to use environment variables
- Add environment validation scripts
- Add detailed setup and configuration documentation
- Create .gitignore to exclude sensitive files"
```

### 5. Pousser vers GitHub
```bash
git push origin main
```

## 📋 Liste de Vérification Avant Push

### ✅ À Vérifier:
- [ ] `.env` est dans `.gitignore`
- [ ] `.env.example` est créé (sans secrets réels)
- [ ] Tous les secrets sont remplacés par des placeholders
- [ ] Documentation mise à jour
- [ ] Scripts de validation inclus
- [ ] Fichiers temporaires exclus

### ⚠️ Sécurité:
- [ ] Aucun mot de passe réel dans le code
- [ ] Aucune clé API dans les fichiers publics
- [ ] Client secrets remplacés par des placeholders
- [ ] URLs de production non exposées

## 🔄 Structure Recommandée pour GitHub

```
spring-keycloak-angular/
├── .gitignore                    ✅ Inclure
├── .env.example                  ✅ Inclure (template)
├── README.md                     ✅ Inclure
├── ENVIRONMENT_SETUP.md          ✅ Inclure
├── ENV_CONFIG_SUMMARY.md         ✅ Inclure
├── pom.xml                       ✅ Inclure
├── start-services.bat            ✅ Inclure
├── validate-env.bat              ✅ Inclure
├── validate-env.sh               ✅ Inclure
├── auth-service/                 ✅ Inclure (tous les .java, pom.xml, etc.)
├── inventory-service/            ✅ Inclure (tous les .java, pom.xml, etc.)
├── front-end/                    ✅ Inclure (sauf node_modules/, dist/)
├── .env                          ❌ Exclure (secrets)
├── .idea/                        ❌ Exclure (IDE)
├── New Text Document.txt         ❌ Exclure (temporaire)
└── target/                       ❌ Exclure (généré)
```

## 💡 Conseils

1. **Toujours vérifier** le contenu avec `git diff` avant de commit
2. **Utiliser `.env.example`** pour documenter la structure nécessaire
3. **Mettre à jour la documentation** à chaque changement important
4. **Tester** que l'application fonctionne avec `.env.example` après clonage
5. **Utiliser des commits descriptifs** avec des messages clairs

## 🛡️ Sécurité GitHub

- Ne jamais committer de secrets réels
- Utiliser GitHub Secrets pour les variables d'environnement en production
- Revoir régulièrement l'historique git pour détecter d'éventuels secrets
- Configurer des branch protection rules si nécessaire