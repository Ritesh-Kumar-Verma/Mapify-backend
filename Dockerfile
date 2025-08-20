# Build stage using Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the project and skip tests
RUN mvn clean package -DskipTests -U

# Runtime stage using lightweight JDK
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /app/target/mapify-0.0.1-SNAPSHOT.jar .

# Expose Spring Boot port
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "mapify-0.0.1-SNAPSHOT.jar"]
