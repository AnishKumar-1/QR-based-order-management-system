FROM openjdk:17-jdk-slim

#working directory
WORKDIR /app

#copy jar file path
COPY target/menu-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar","app.jar"]