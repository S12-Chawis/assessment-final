# Cambiamos 18-alpine por 22-alpine
FROM node:22-alpine

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

EXPOSE 5173

# El flag --host es vital para que Docker permita ver la web desde fuera
CMD ["npm", "run", "dev", "--", "--host"]