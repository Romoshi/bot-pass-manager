FROM gradle:jdk17-alpine AS build_step
WORKDIR /app
COPY . .
RUN apk add gcompat
RUN ./gradlew -p core shadowJar

FROM openjdk:17
WORKDIR /app
COPY --from=build_step /app/core/build/libs/*.jar /app
RUN mv /app/bot-core*.jar /app/bot-core.jar
ENTRYPOINT ["java", "-jar", "/app/bot-core.jar"]