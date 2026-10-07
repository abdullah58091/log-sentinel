# Build stage
FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

COPY .mvn .mvn
COPY mvnw .

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline -DskipTests

COPY src src

RUN ./mvnw clean package -DskipTests


# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/log-sentinel-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]