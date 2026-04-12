#!/bin/bash

echo "Starting Eureka..."
cd service
# java -Xms64m -Xmx128m -jar target/.jar &
java -Xms64m -Xmx128m -jar build/libs/*.jar &
sleep 10  # 

echo "Starting API Gateway..."
cd ../api
java -Xms64m -Xmx256m -jar target/api-gateway-0.0.1-SNAPSHOT.war &
sleep 5

echo "Starting User Service..."
cd ../LikeService
java -Xms64m -Xmx256m -jar target/LikeService-0.0.1-SNAPSHOT.war &

echo "Starting Post Service..."
cd ../PostsService
java -Xms64m -Xmx256m -jar target/PostService-0.0.1-SNAPSHOT.war &

echo "Starting Users Service..."
cd ../UserService
java -Xms64m -Xmx256m -jar target/UserService-0.0.1-SNAPSHOT.war &

echo "All services started!"
