FROM openjdk:8-jdk-alpine
LABEL maintainer="tuxotpub"
VOLUME /tmp
EXPOSE 8080
ARG APP_JAR=target/booksmanagement-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Xmx160m", "-jar", "/booksmanagement.jar"]
ADD ${APP_JAR} booksmanagement.jar