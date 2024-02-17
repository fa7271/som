FROM openjdk:11

WORKDIR /eureka

COPY build/libs/*.jar eureka.jar

EXPOSE 8761

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "eureka.jar"]