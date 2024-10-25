# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY .mvn/ .mvn
COPY src ./src
COPY LICENSE mvnw pom.xml token.txt* ./
RUN ./mvnw install

ENTRYPOINT ["java", "-jar", "target/ordis-*.*.*.jar"]