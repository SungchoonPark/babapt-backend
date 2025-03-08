FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

# Gradle 캐시 최적화 (필수 파일만 먼저 복사)
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle
RUN chmod +x gradlew

# Gradle 의존성 미리 다운로드 (속도 최적화)
RUN ./gradlew dependencies --no-daemon || true

# 프로젝트 전체 복사 및 빌드 실행
COPY . .
RUN ./gradlew clean build -x test

# 2️⃣ 실행 단계: 최적화된 JDK 21 이미지에서 실행
FROM eclipse-temurin:21-jdk
WORKDIR /app

# 환경변수 설정 (EC2에서 실행 시 적용)
ENV SERVER_PORT=8080
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

# 빌드된 JAR 파일을 실행용 컨테이너로 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 컨테이너 실행 시 환경변수 적용하여 JAR 실행
CMD ["java", "-jar", "app.jar"]

