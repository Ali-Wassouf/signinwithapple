FROM maven:3.6.2-jdk-8 AS maven
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src src
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:8-jdk-alpine
ENV PROFILE local
COPY --from=maven target/*.jar ./app.jar
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=${PROFILE} -jar /app.jar
