FROM maven:3-eclipse-temurin-11

COPY . /project
RUN  cd /project && mvn package

#run the spring boot application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/project/target/backend-0.0.1-SNAPSHOT.jar"]