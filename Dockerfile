FROM maven:3.9-eclipse-temurin-17-alpine AS builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package

FROM eclipse-temurin:17-jre-alpine
COPY --from=builder /usr/src/app/target/rssaggregator-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]