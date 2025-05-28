FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

CMD ["java", "-jar", "target/isad_test-0.0.1-SNAPSHOT.jar"]
