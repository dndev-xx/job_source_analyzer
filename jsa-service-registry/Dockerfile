FROM openjdk:17-jdk-slim-buster AS builder
WORKDIR /workspace

COPY . .

RUN ./gradlew build -x test

FROM openjdk:17-jdk-slim-buster
WORKDIR /app

RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

COPY --from=builder /workspace/build/libs/*.jar app.jar

COPY src/main/resources/application.yml /app/application.yml

ENV PORT 8761
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]