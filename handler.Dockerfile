FROM gradle:jdk11-alpine AS build_step
WORKDIR /app
COPY . .
RUN ./gradlew -p handler shadowJar

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build_step /app/handler/build/libs/*.jar /app
RUN mv /app/handler*.jar /app/handler.jar
ENTRYPOINT ["java", "-jar", "/app/handler.jar"]