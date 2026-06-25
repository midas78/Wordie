FROM maven:3.9.9-eclipse-temurin-21 AS build
COPY settings.xml /usr/share/maven/ref/settings.xml
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn package -DskipTests -P!dev -B

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/wordie-1.0.0.jar app.jar
EXPOSE 8080
VOLUME /app/data
ENTRYPOINT ["java", "-jar", "app.jar"]
