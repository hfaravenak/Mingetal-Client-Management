FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cliente-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/cliente-0.0.1-SNAPSHOT.jar"]