FROM openjdk:17-jdk-slim
WORKDIR /app
COPY pom.xml ./
COPY src ./src
COPY /target/*.jar /slhs.jar
CMD ["java", "-jar", "/slhs.jar"]
