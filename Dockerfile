#FROM openjdk:latest
#WORKDIR /app
#COPY --from=build /app/build/libs/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]


FROM openjdk:latest
WORKDIR /app
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]