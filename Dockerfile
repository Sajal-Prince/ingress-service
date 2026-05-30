# 1. Start with a base image that already has Java and Maven
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build

# 2. Install Git
RUN apk add --no-cache git

WORKDIR /app

# 3. Clone your main ingress-service repository
# Replace with your actual repository URL
RUN git clone https://github.com/Sajal-Prince/ingress-service.git .

# 4. Initialize and update submodules
RUN git submodule update --init --recursive

# 5. Build the submodule-dto first
# We assume the path based on your previous structure
RUN mvn clean install -f project-pay-dto/submodule-dto/pom.xml -DskipTests

# 6. Build the main ingress-service
RUN mvn clean package -DskipTests

COPY src/main/resources/client.truststore.jks /app/certs/client.truststore.jks

# Stage 2: Minimal Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]