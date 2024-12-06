FROM openjdk:21-jdk-slim
WORKDIR /app
ARG app_path="target/backend-app-1.0.jar"
ARG app_target="/app/backend.jar"
COPY ${app_path} ${app_target}
COPY src/main/resources /app/src/main/resources
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]