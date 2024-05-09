# Use OpenJDK 17 Alpine as the base image
FROM openjdk:17-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/*.jar /app/app.jar

# Run the jar file
CMD ["java", "-jar", "app.jar"]


