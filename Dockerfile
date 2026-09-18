FROM eclipse-temurin:8-jdk AS build

WORKDIR /app
COPY src ./src

RUN mkdir -p out \
    && find src -name "*.java" -print0 | xargs -0 javac -d out
