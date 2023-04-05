FROM gradle:jdk11-alpine AS build_step
WORKDIR /app
COPY . .
RUN ./gradlew -p puller shadowJar

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build_step /app/puller/build/libs/*.jar /app
RUN mv /app/bot-pull*.jar /app/bot-pull.jar
ENTRYPOINT ["java", "-jar", "/app/bot-pull.jar"]