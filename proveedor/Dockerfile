FROM eclipse-temurin:17-jre-jammy
ENV JAVA_TOOL_OPTIONS="-XX:+ExitOnOutOfMemoryError"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} proveedor-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/proveedor-0.0.1-SNAPSHOT.jar"]