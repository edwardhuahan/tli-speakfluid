FROM maven:3.8.6-eclipse-temurin-11

COPY . /backend
RUN  cd /backend && mvn package

#run the spring boot application
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/backend/target/backend-0.0.1-SNAPSHOT.jar"]