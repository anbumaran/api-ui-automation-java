# Use an official OpenJDK runtime as the base image
FROM gradle:6.5.1-jdk11

# Set the working directory inside the container
WORKDIR /asapp

# Copy the Gradle build files to the container
COPY build.gradle .
COPY settings.gradle .
COPY gradle.properties .
# Copy the source code to the container
COPY src ./src

# Run Gradle to download dependencies and build the project
RUN gradle clean build
RUN gradle apiIntTest

RUN cp -R build/reports/tests/apiIntTest /asapp/test-reports/apiIntTest
VOLUME /asapp/test-reports
