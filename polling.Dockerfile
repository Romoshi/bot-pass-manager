FROM gradle:jdk11-alpine AS build_step
WORKDIR /app
COPY . .
RUN ./gradlew -p polling shadowJar

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build_step /app/polling/build/libs/*.jar /app
RUN mv /app/t-bot*.jar /app/t-bot.jar
ENTRYPOINT ["java", "-jar", "/app/t-bot.jar"]