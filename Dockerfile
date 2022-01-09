FROM openjdk:11-jdk
EXPOSE 8080:8080
ADD build/libs/echo-bot-1.0.jar echo-bot-1.0.jar
CMD ["java", "-jar", "echo-bot-1.0.jar"]