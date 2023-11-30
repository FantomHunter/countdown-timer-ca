#
# Build stage
#
# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM gradle:8.5-jdk17-alpine as build
#FROM openjdk:17-jdk-slim as build
WORKDIR /code
#
# # Copy local code to the container image.
COPY . .
#
# # Build a release artifact.
RUN gradle clean build --no-daemon -x test


#
# Package stage
#
# It's important to use OpenJDK 8u191 or above that has container support enabled.
# https://hub.docker.com/r/adoptopenjdk/openjdk8
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM openjdk:21-jdk-slim

WORKDIR /app
# Copy the jar to the production image from the builder stage.
COPY --from=build /code/configuration/build/libs/configuration-0.0.1-SNAPSHOT.jar app.jar

# ENV PORT=8080
EXPOSE 8080

# Run the web service on container startup.
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","app.jar"]