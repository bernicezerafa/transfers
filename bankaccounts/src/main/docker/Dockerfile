FROM openjdk:8-jdk-alpine
LABEL maintainer="bernice.zerafa11@gmail.com"
VOLUME /tmp
ADD bankaccounts.jar bankaccounts.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/bankaccounts.jar"]