FROM gradle:jdk17-alpine AS build_step
WORKDIR /app
COPY . .
RUN apk add gcompat
RUN ./gradlew -p core buildFatJar

FROM openjdk:17
WORKDIR /app
COPY --from=build_step /app/core/build/libs/*.jar /app
ENTRYPOINT ["java", "-jar", "/app/core.jar"]