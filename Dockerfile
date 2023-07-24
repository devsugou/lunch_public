# ビルドステージ
FROM openjdk:17-jdk-alpine3.13 AS builder
WORKDIR /app
COPY . .
# テストスキップ
RUN ./gradlew clean build -x test
# RUN ./gradlew clean build --stacktrace --info -x test

# 実行用イメージの生成
FROM openjdk:17-jdk-alpine3.13
WORKDIR /app
COPY --from=builder /app/build/libs/lunch-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]