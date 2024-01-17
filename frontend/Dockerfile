FROM node:18-alpine AS build
WORKDIR /app
COPY package.json ./
COPY package-lock.json ./
COPY tsconfig.json ./
RUN npm install
COPY . .  
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/build /etc/nginx/html
COPY /src/ssl /etc/nginx/ssl
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 443
CMD ["nginx", "-g", "daemon off;"]