#!/bin/bash

echo "Stopping all services..."

pkill -f "service_registry_application-0.0.1-SNAPSHOT.war"
pkill -f "api-gateway-0.0.1-SNAPSHOT.war"
pkill -f "LikeAPI-0.0.1-SNAPSHOT.war"
pkill -f "PostService-0.0.1-SNAPSHOT.jar"
pkill -f "UserService-0.0.1-SNAPSHOT.war"

echo "All services stopped."
