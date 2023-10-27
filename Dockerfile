FROM eclipse-temurin:17-jdk-alpine
LABEL authors="adungao"
VOLUME /tmp
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]