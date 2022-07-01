FROM maven:3.8.6-jdk-11
LABEL maintainer="Dmitriy.Slokva@gmail.com"
WORKDIR /spring-boot-jwt-demo
COPY . .
RUN mvn clean install -DskipTests=true
CMD mvn spring-boot:run
