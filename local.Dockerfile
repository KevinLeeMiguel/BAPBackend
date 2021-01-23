FROM openjdk:13-alpine As deploy
VOLUME /tmp
COPY ./target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]