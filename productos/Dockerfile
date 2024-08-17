FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} productos-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/productos-0.0.1-SNAPSHOT.jar"]