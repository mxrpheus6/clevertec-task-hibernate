FROM openjdk:21

WORKDIR /app

COPY build/libs/clevertec-task-hibernate-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]