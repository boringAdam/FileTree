FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /workspace/app
COPY . .
RUN chmod +x gradlew
RUN sh ./gradlew clean build

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=builder /workspace/app/build/libs/FileTree-0.0.1-SNAPSHOT.jar /app/app.jar
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

ENTRYPOINT ["sh", "/app/start.sh"]