FROM openjdk:11-jdk

WORKDIR /batch

COPY build/libs/*.jar batch.jar

EXPOSE 8004

CMD ["java", "-jar", "batch.jar"]