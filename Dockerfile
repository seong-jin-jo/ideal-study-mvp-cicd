# JDK21 이미지 기반 프로젝트빌드
FROM openjdk:21-jdk-slim
LABEL authors="jin66"

# 작업공간
RUN mkdir /app
WORKDIR /app

# Gradle Wrapper 및 build.gradle 파일 복사
COPY ./gradlew /app/gradlew
COPY ./gradlew.bat /app/gradlew.bat
COPY ./build.gradle /app/build.gradle
COPY ./gradle/ /app/gradle/
COPY ./src/ /app/src/

# 필수 패키지 설치 (Debian 기반)
RUN apt-get update && apt-get install -y findutils

# 프로젝트를 빌드
RUN chmod +x /app/gradlew
RUN /app/gradlew build -x test

# 빌드된 JAR 파일을 임시 디렉토리로 복사
RUN cp /app/build/libs/*.jar /app/
RUN ls

# 빌드된 결과를 실행
ENTRYPOINT ["java", "-jar", "/app/app-0.0.1-SNAPSHOT.jar"]