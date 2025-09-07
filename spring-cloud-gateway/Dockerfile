FROM openjdk:17-jdk-slim

#working directory
WORKDIR /app
#copy jar file path
COPY target/spring-cloud-gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar","app.jar"]