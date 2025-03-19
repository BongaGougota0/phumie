# phumie, a Social Media Application 🌟

A microservices-based social media platform built with **Java Spring Boot**, **Angular**, and **Apache Kafka**. Designed to enhance my Java/Kafka skills while implementing core social media features with scalable architecture.


## Features 🚀

### Core Features
- **User Authentication**: JWT-based registration/login with Spring Security.
- **Profile Management**: Create/update user profiles with bio, profile picture, etc.
- **Post Creation**: Create, edit, and delete posts (text/image support).
- **Real-Time Feed**: Follow users and view aggregated posts in real-time using Kafka.
- **Notifications**: Real-time alerts for likes, comments, and follows via Kafka.
- **Likes/Comments**: Interact with posts and receive notifications.

### Features
- **Analytics Dashboard**: Admin metrics for user activity and posts.
- **Microservices Architecture**: 3+ independent services with API Gateway.

## Technologies 💻

### Backend
- **Java 17** | **Spring Boot 3**
- **Apache Kafka** (Event streaming & real-time updates)
- **MySQL** (Relational data)
- **Redis/Caffeine** (Caching)
- **Spring Security** + **JWT** (Authentication)

### Frontend
- **Angular 15+** | **RxJS**
- **WebSocket API** (Real-time notifications)

### Infrastructure
- **Docker** | **Docker Compose** (Containerization) maybe at a later stage...
- **Netflix Eureka** (Service discovery) | **Spring Cloud Gateway** (API Gateway)
- **Prometheus** + **Grafana** (Monitoring)

## Architecture 🏗️