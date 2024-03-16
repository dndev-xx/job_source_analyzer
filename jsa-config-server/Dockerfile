FROM openjdk:17-jdk-slim-buster AS builder
WORKDIR /workspace

COPY . .

RUN ./gradlew build -x test --no-daemon

FROM openjdk:17-jdk-slim-buster
WORKDIR /app

RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

ARG VER=dev

COPY --from=builder /workspace/build/libs/*.jar app.jar

COPY --from=builder /workspace/src/main/resources/application-${dev}.yml application.yml

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]
