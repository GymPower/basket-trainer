# Build stage
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Package stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY src/main/resources/ssl/truststore.jks ssl/truststore.jks
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-Djavax.net.ssl.trustStore=/app/ssl/truststore.jks", "-jar", "app.jar"]