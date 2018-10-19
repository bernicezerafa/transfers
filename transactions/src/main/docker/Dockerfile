FROM openjdk:8-jdk-alpine
LABEL maintainer="bernice.zerafa11@gmail.com"
VOLUME /tmp
ADD transactions-spring-boot.jar transactions.jar
COPY ./schema.sql /docker-entrypoint-initdb.d/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/transactions.jar"]