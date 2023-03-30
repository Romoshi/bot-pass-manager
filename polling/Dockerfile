FROM gradle:jdk11-alpine AS build_step
WORKDIR /app
COPY . .
RUN gradle clean shadowJar

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build_step /app/polling/build/libs/*.jar /app
ENTRYPOINT ["java", "-jar", "/app/poll.jar"]