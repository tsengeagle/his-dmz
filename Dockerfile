FROM amazoncorretto:8u342

COPY build/libs/DmzServer-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
