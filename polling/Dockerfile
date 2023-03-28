FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY /app/polling/build/libs/*.jar /app
ENTRYPOINT ["java", "-jar", "/app/t-bot.jar"]