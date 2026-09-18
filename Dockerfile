FROM eclipse-temurin:8-jdk

WORKDIR /app
COPY src ./src

RUN mkdir -p out \
    && find src -name "*.java" -print0 | xargs -0 javac -d out

CMD ["java", "-cp", "out", "main.Main"]