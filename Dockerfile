# Dockerfile for Java 21
FROM bellsoft/liberica-openjdk-alpine:21

COPY build/libs/toastit_v2-2.0.0-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
