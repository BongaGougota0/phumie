#!/bin/bash

echo "Stopping all services..."

pkill -f "service/build/libs"
pkill -f "api-gateway-0.0.1-SNAPSHOT.war"
pkill -f "LikeService-0.0.1-SNAPSHOT.war"
pkill -f "PostService-0.0.1-SNAPSHOT.war"
pkill -f "UserService-0.0.1-SNAPSHOT.war"

echo "All services stopped."
