FROM gradle:jdk11-alpine AS build_step
WORKDIR /app
COPY . .
RUN ./gradlew -p core shadowJar

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build_step /app/core/build/libs/*.jar /app
RUN mv /app/bot-core*.jar /app/bot-core.jar
ENTRYPOINT ["java", "-jar", "/app/bot-core.jar"]