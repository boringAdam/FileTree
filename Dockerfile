FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /app

COPY . .

RUN ./gradlew clean build