FROM maven:3.8.7-openjdk-18-slim
WORKDIR /app
COPY pom.xml /app
RUN mvn clean dependency:go-offline

COPY . /app/
CMD mvn spring-boot:run