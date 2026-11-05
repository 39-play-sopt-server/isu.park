# 1. 이미지
FROM eclipse-temurin:21-jdk

# 2. 작업 디렉터리 지정
WORKDIR /app

# 3. Gradle 빌드로 생성된 JAR 파일을 이미지 내부로 복사
ARG JAR_FILE=build/libs/assignment-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 4. 컨테이너 노출 포트
EXPOSE 8080

# 5. 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]