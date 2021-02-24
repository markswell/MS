FROM markswell/java-11:1

ARG JAR_FILE=target/cadastro.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]