# Usamos una imagen ligera de Node
FROM node:18-alpine

WORKDIR /app

# Copiamos archivos de dependencias
COPY package*.json ./
RUN npm install

# Copiamos el resto del código
COPY . .

# Exponemos el puerto del front (usualmente 5173 para Vite o 3000 para CRA)
EXPOSE 5173

CMD ["npm", "run", "dev", "--", "--host"]