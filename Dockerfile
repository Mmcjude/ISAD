# Stage 1: Build with Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the app with JRE
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/isad_test-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
