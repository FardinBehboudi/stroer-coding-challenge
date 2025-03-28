# Ströer Coding Challenge – Java Backend Engineer

This project is a solution to the Ströer coding challenge using **Spring WebFlux**.  
It demonstrates how to fetch data asynchronously from two REST API endpoints, merge the results, and return them via a reactive REST controller.

---

## Tech Stack

- Java 21
- Spring Boot 3.4.4
- Spring WebFlux (Reactive Web)
- Maven
- JUnit 5 + WebTestClient for testing
- Project Lombok

---

## Task Overview

> **Fetch asynchronous data from an API**  
> Your task is to write a script or app, which gathers data from two endpoints asynchronously, merges the responses and displays them in any way, for example as a JSON response from a REST API.

### Used Endpoints (via `jsonplaceholder.typicode.com`) :

- `https://jsonplaceholder.typicode.com/users/1` → Fetch user info
- `https://jsonplaceholder.typicode.com/posts?userId=1` → Fetch user's posts

---

## 🔗 Endpoint

**GET /api/users/{id}**

### Example:
GET http://localhost:8080/api/users/1

### Response format:
```json
{
  "user": {
    "id": 1,
    "name": "Leanne Graham",
    "username": "Bret",
    "email": "Sincere@april.biz",
    ...
  },
  "posts": [
    {
      "userId": 1,
      "id": 1,
      "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
      "body": "quia et suscipit..."
    },
    ...
  ]
}
```
### Features:
-  Asynchronous, non-blocking WebClient usage
- Fetches user and posts in parallel using Mono.zip
-  Error handling for client and server errors
- Global exception handling with user-friendly messages
- Minimal, clean architecture
-  Unit test using @WebFluxTest


### Run the App:
./mvnw spring-boot:run

### Run Tests:
./mvnw test
