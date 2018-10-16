FROM openjdk:8-jdk-alpine
LABEL maintainer="bernice.zerafa11@gmail.com"
VOLUME /tmp
ADD transfers.jar transfers.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/transfers.jar"]