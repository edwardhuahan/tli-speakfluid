FROM maven:3-eclipse-temurin-11

COPY . /project
RUN  cd /project && mvn package

#run the spring boot application
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/project/target/backend-0.0.1-SNAPSHOT.jar"]