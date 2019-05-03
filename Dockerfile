FROM openjdk:8-jdk-alpine
RUN apk update && apk add bash
VOLUME /tmp

RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app

ARG JAR_FILE
COPY ${JAR_FILE}/target/simian-service-0.0.1-SNAPSHOT.jar $PROJECT_HOME/simian-service.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-Dspring.data.mongodb.uri=mongodb://simian-mongo:27017/simian","-Djava.security.egd=file:/dev/./urandom","-jar","./simian-service.jar"]