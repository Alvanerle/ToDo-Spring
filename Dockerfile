FROM openjdk:16-jdk-alpine
COPY target/to-do-app-1.0.jar to-do-app-1.0.jar
ENTRYPOINT ["java","-jar","/to-do-app-1.0.jar"]