FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:21.0.4_7-jre

ARG JAR_FILE=/app/target/*.jar

WORKDIR /app

COPY --from=build ${JAR_FILE} app.jar

EXPOSE 8761

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
