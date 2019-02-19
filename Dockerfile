FROM maven:3.6.0-jdk-8-alpine
WORKDIR /usr/src/java-code
COPY pom.xml /usr/src/java-code/
COPY src /usr/src/java-code/src
RUN mvn package

WORKDIR /usr/src/java-app
RUN cp /usr/src/java-code/target/*.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]