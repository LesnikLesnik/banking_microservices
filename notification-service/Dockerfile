FROM openjdk:17-jdk-alpine
EXPOSE 9999
COPY build/libs/notification-service-1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "notification-service-1.0-SNAPSHOT.jar"]