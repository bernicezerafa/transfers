FROM openjdk:8-jdk-alpine
LABEL maintainer="bernice.zerafa11@gmail.com"
VOLUME /tmp
ADD transactions-spring-boot.jar transactions.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/transactions.jar"]