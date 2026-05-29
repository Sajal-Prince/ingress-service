# Stage 1: Build
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Install git so we can pull the submodule
RUN apk add --no-cache git

# Copy your root repo (but git modules aren't initialized yet)
COPY . .

# Initialize and pull the submodule
RUN git submodule init && git submodule update --recursive

# Now build the project
RUN mvn clean install -DskipTests

# Stage 2: Run (same as before)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/ingress-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]