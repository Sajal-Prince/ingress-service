# Stage 1: Build
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build
WORKDIR /app

# 1. Install git
RUN apk add --no-cache git

# 2. Copy the entire project AND the .git folder
# We need the .git folder so 'git submodule' commands know where they are
COPY . .

# 3. Initialize and pull the submodules
# The --recursive flag ensures that if your submodule has its own submodules, they get pulled too
RUN git submodule init && git submodule update --recursive

# 4. Now build the project (Maven now sees the files in the submodule folder)
RUN mvn clean install -f ingress-service/project-pay-dto/submodule-dto/pom.xml -DskipTests
RUN mvn clean package -f ingress-service/pom.xml -DskipTests

# Stage 2: Runtime (Clean and light)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/ingress-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]