FROM --platform=linux/arm64 maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src src
RUN mvn package -DskipTests -P!dev -q

FROM --platform=linux/arm64 eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/wordie-1.0.0.jar app.jar
EXPOSE 3000
RUN mkdir -p /tmp/wordie-db
ENTRYPOINT ["java", "-jar", "app.jar"]
