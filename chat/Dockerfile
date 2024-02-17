FROM openjdk:11-jdk

WORKDIR /chat

COPY build/libs/*.jar chat.jar

EXPOSE 8003

CMD ["java", "-jar", "chat.jar"]

