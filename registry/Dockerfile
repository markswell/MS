FROM markswell/java-11:1

ARG JAR_FILE=target/registry.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]