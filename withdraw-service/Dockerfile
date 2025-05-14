FROM openjdk:17-jdk-alpine
EXPOSE 8084
COPY build/libs/withdraw-service-1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "withdraw-service-1.0-SNAPSHOT.jar"]