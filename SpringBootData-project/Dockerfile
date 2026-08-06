FROM eclipse-temurin:21-jdk-alpine-3.23

COPY target/*.jar app.jar
 
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]