FROM openjdk:17.0.10-jdk-alpine
EXPOSE 8989
COPY build/libs/gateway-service-1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "gateway-service-1.0-SNAPSHOT.jar"]