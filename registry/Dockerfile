FROM ubuntu
EXPOSE 8080
ARG JAR_FILE=target/registry.jar
ADD ${JAR_FILE} app.jar


RUN apt update && \
    apt install default-jre -y

ENTRYPOINT ["java","-jar","/app.jar"]