FROM openjdk:17-jdk-alpine
EXPOSE 8989
COPY build/libs/gateway-1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "gateway-1.0-SNAPSHOT.jar"]