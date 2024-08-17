FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ordenes-de-compra-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ordenes-de-compra-0.0.1-SNAPSHOT.jar"]