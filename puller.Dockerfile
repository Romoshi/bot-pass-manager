FROM gradle:jdk17-alpine AS build_step
WORKDIR /app
COPY . .
RUN apk add gcompat
RUN ./gradlew -p puller shadowJar

FROM openjdk:17
WORKDIR /app
COPY --from=build_step /app/puller/build/libs/*.jar /app
RUN mv /app/bot-pull*.jar /app/bot-pull.jar
ENTRYPOINT ["java", "-jar", "/app/bot-pull.jar"]