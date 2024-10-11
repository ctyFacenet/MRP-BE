FROM openjdk:16
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN sed -i 's/TLSv1, //; s/, TLSv1.1//; s/,TLSv1.1//; s/,TLSv1//; s/TLSv1//; s/, ,/,/' /usr/java/openjdk-16/conf/security/java.security
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8313

