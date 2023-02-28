FROM gradle:jdk11-alpine AS build_step
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build_step /app/build/libs/*.jar /app
ENTRYPOINT ["java", "-jar", "/app/t-bot.jar"]