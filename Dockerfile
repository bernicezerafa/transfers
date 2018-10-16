FROM openjdk:8-jdk-alpine
LABEL maintainer="bernice.zerafa11@gmail.com"
VOLUME /tmp
ARG JAR_FILE=target/transfers-0.1.0.jar
ADD ${JAR_FILE} transfers.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/transfers.jar"]