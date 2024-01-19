FROM gradle:8.5-jdk-alpine AS build
WORKDIR /bank
COPY build.gradle .
COPY settings.gradle .
COPY src src/
RUN gradle clean build -x test
RUN mv build/libs/app.jar app.jar

FROM openjdk:17
EXPOSE 8080
RUN mkdir /app
COPY --from=build /bank/app.jar .
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","app.jar"]