FROM eclipse-temurin:17-jdk AS build

WORKDIR /app
COPY src ./src

RUN mkdir -p out \
    && find src -name "*.java" -print0 | xargs -0 javac -d out

FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=build /app/out ./out

CMD ["sh"]