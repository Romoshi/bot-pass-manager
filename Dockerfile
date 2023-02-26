FROM gradle:jdk11-alpine AS build_step
WORKDIR /app
COPY . .
RUN gradle build

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build_step /app/build/libs/*jar ./
ENTRYPOINT ["java", "-jar", "/app/tbot.jar"]