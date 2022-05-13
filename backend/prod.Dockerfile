# Dockerfile for the backend in the production environment

FROM maven:3.8.5-amazoncorretto-17
WORKDIR /app
COPY ./lib /app/lib
COPY ./src /app/src
COPY ./pom.xml /app/pom.xml
ENTRYPOINT ["mvn","spring-boot:run"]
