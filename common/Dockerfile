FROM openjdk:11-jdk

WORKDIR /common

COPY build/libs/*.jar common.jar

EXPOSE 8005

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "common.jar"]