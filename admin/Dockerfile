FROM openjdk:11-jdk

WORKDIR /som/admin

COPY build/libs/*.jar admin.jar

EXPOSE 8080

CMD ["java", "-jar", "admin.jar"]