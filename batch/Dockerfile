# 베이스 이미지로 OpenJDK가 포함된 이미지를 사용합니다. 여기서는 OpenJDK 11을 사용합니다.
FROM openjdk:11-jdk-slim as build
# 애플리케이션 파일이 복사될 작업 디렉터리를 설정합니다.
WORKDIR /batch
# Gradle 빌드 시 필요한 파일들을 이미지에 복사합니다.
COPY gradlew2 .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
# 애플리케이션을 빌드. 결과물은 build/libs 디렉터리에 생성
#  -x 옵션은 뒤에 오는 특정 task를 실행하지 않도록 지시하는 것.
# 즉, 테스트를 실행하지 않도록 하는 명령
RUN ./gradlew2 build -x test
# 실제 실행할 이미지를 위한 새로운 단계를 시작합니다.
FROM openjdk:11-jre-slim
WORKDIR /batch
# 빌드 단계에서 생성된 실행 가능한 jar 파일을 복사합니다.
COPY --from=build /batch/build/libs/*.jar batch.jar
# 컨테이너가 시작될 때 애플리케이션을 실행합니다.
ENTRYPOINT ["java","-jar","app.jar"]