FROM amazoncorretto:25.0.1-alpine
EXPOSE 8080
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
