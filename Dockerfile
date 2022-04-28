FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
ARG PROPERTIES_FILE=src/main/resources/application-prod.properties
COPY ${JAR_FILE} app.jar
COPY ${PROPERTIES_FILE} application-prod.properties
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]