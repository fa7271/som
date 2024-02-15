FROM openjdk:11-jdk

WORKDIR /admin

COPY build/libs/*.jar admin.jar

EXPOSE 8001

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "admin.jar"]
