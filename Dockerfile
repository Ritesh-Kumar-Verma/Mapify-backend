FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY . .

RUN chmod +x mvnw

# Force clean build and skip tests
RUN ./mvnw clean package -DskipTests -U

EXPOSE 8080
CMD ["java", "-jar", "target/mapify-0.0.1-SNAPSHOT.jar"]
