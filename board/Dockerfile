FROM openjdk:11-jdk

WORKDIR /board

COPY build/libs/*.jar board.jar

EXPOSE 8002

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "board.jar"]