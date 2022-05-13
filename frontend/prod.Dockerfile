# Multi-stage container description: we build first, then use the build-container just temporarily
# Only the prod container is really kept
# Idea from https://mherman.org/blog/dockerizing-a-react-app/

# Build environment
FROM node:16.15-alpine3.15 as build
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json ./
COPY package-lock.json ./
RUN npm ci --silent
RUN npm install react-scripts@5.0.0 -g --silent
COPY . ./
RUN npm run build

# production environment
FROM nginx:1.20.2-alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY container-config/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
