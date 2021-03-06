FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:13-alpine As deploy
VOLUME /tmp
COPY --from=build /home/app/target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]