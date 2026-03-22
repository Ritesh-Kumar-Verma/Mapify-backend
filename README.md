# 🌍 Mapify Backend – REST API (Spring Boot)

Mapify Backend is the **core backend service** for the Mapify real-time location sharing application.

This repository contains the **Spring Boot backend implementation without authentication**, focusing on the core backend logic such as user management, friend requests, and location sharing.

It provides REST APIs that are consumed by the **React frontend application**.

---

# 🚀 Features

- User management APIs
- Friend request system
- Location sharing APIs
- PostgreSQL database integration
- RESTful API architecture
- Integration with React frontend

---

# 🛠 Tech Stack

## Backend
- Java
- Spring Boot

## Database
- PostgreSQL

## Tools & Frameworks
- Maven
- Spring Data JPA
- Hibernate
- REST APIs

---

# 🧩 System Architecture

Frontend (React + Tailwind + Leaflet)

⬇ REST API Calls

Spring Boot Backend

⬇

PostgreSQL Database

The backend handles **data processing, friend management, and location storage** while the frontend handles **UI and map visualization**.

---

# 📂 Project Structure
Mapify-backend
│
├── src
│ ├── main
│ │ ├── java
│ │ │ ├── controller
│ │ │ ├── service
│ │ │ ├── repository
│ │ │ ├── model
│ │ │ └── config
│ │ │
│ │ └── resources
│ │ └── application.properties
│
├── pom.xml
└── README.md


---

# ⚙️ Installation & Setup

## 1️⃣ Clone the repository


git clone https://github.com/ritesh-kumar-verma/Mapify-backend.git


---

## 2️⃣ Navigate to the project directory


cd Mapify-backend


---

## 3️⃣ Configure Database

Open `application.properties` and update:


spring.datasource.url=jdbc:postgresql://localhost:5432/mapify
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


---

## 4️⃣ Build the project


mvn clean install


---

## 5️⃣ Run the application


mvn spring-boot:run


Server will start at:


http://localhost:8080


---

# 📡 API Overview

Example endpoints:

### Users


GET /users
POST /users
GET /users/{id}


### Friend Requests


POST /friends/request
POST /friends/accept
GET /friends


### Location


POST /location/update
GET /location/{userId}


---

# 🔗 Related Repositories

### Frontend
Mapify (React + Tailwind CSS + Leaflet)


https://github.com/ritesh-kumar-verma/Mapify


### Secure Backend Version
Mapify-Backend-v2 (Spring Security + JWT)


https://github.com/ritesh-kumar-verma/Mapify-Backend-v2


---

# 🌟 Future Improvements

- Add JWT authentication
- Implement WebSocket for real-time location updates
- Add push notifications
- Improve API performance
- Docker deployment

---

# 👨‍💻 Author

**Ritesh Kumar Verma**

Full Stack Developer  
React • Java • Spring Boot • PostgreSQL

---

# 📜 License

This project is licensed under the MIT License.
