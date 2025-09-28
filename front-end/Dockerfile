# Étape 1 : Build Angular
FROM node:20-alpine AS build

WORKDIR /usr/local/app

# Copier les fichiers package.json et installer les dépendances
COPY package*.json ./
RUN npm install

# Copier tout le projet Angular et builder
COPY . .
RUN npm run build --prod

# Étape 2 : Servir avec Nginx
FROM nginx:alpine

# Supprimer la conf par défaut de Nginx
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copier le build Angular vers Nginx (⚡ Angular 20 → sous /browser)
COPY --from=build /usr/local/app/dist/front-end/browser /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
