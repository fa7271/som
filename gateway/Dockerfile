FROM openjdk:11

WORKDIR /gateway

COPY build/libs/*.jar gateway.jar

EXPOSE 8761

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "gateway.jar"]