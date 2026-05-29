# Stage 1: Build from the root
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy the parent pom and the modules
COPY pom.xml .
COPY submodule-dto ./submodule-dto
COPY ingress-service ./ingress-service

# Build the entire project so DTOs are installed
RUN mvn clean install -DskipTests

# Stage 2: Run only the service JAR
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/ingress-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]