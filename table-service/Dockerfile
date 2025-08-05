#user lightweight image
FROM openjdk:17-jdk-slim

#working directly inside docker container
WORKDIR /app

#copy jar file
#source and destination of jar
COPY target/table-service-0.0.1-SNAPSHOT.jar app.jar

#expose the port
EXPOSE 8080

#RUN THE APPPLICATION
ENTRYPOINT ["java", "-jar", "app.jar"]