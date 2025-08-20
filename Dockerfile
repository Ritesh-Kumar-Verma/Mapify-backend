FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY . .

# Force mvnw to be executable inside container
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/mapify-0.0.1-SNAPSHOT.jar"]
