FROM gradle:jdk17-alpine AS build_step
WORKDIR /app
COPY . .
RUN apk add gcompat
RUN ./gradlew -p puller buildFatJar

FROM openjdk:17
WORKDIR /app
COPY --from=build_step /app/puller/build/libs/*.jar /app
ENTRYPOINT ["java", "-jar", "/app/puller.jar"]