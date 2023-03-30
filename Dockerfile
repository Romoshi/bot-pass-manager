FROM gradle:jdk11-alpine AS build_step
WORKDIR /app
COPY . .
RUN cd /app/core
RUN gradle clean shadowJar

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build_step /app/core/build/libs/*.jar /app
ENTRYPOINT ["java", "-jar", "/app/t-bot.jar"]