#!/bin/bash

echo "Building all services..."

# Build Eureka (Gradle)
echo "Building Service Registry..."
cd service
./gradlew clean build -x test
cd ..

# Build API Gateway (Maven)
echo "Building API Gateway..."
cd api
mvn clean package -DskipTests
cd ..

# Build Like Service (Maven)
echo "Building Like Service..."
cd LikeService
mvn clean package -DskipTests
cd ..

# Build Posts Service (Maven)
echo "Building Posts Service..."
cd PostsService
mvn clean package -DskipTests
cd ..

# Build User Service (Maven)
echo "Building User Service..."
cd UserService
mvn clean package -DskipTests
cd ..

echo "All services built successfully!"
