
# 기본 이미지 설정
FROM eclipse-temurin:17-jdk-alpine
# 작업 디렉토리 생성
WORKDIR /app

# 빌드된 JAR 파일을 Docker 컨테이너로 복사
COPY ./build/libs/one-comment-0.0.1-SNAPSHOT.jar app.jar

# Spring Boot 애플리케이션 실행
CMD ["java", "-jar", "app.jar"]