FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} config-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/config-0.0.1-SNAPSHOT.jar"]