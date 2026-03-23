# tyiu-library

A full-stack library management system built for TYIU (Tyumen Industrial University). The backend is written in Java and the frontend in JavaScript with SCSS, split into two separate directories that work together as a single application.

---

## Project structure

```
tyiu-library/
├── library-back/    # Java backend (REST API)
└── library-front/   # JavaScript frontend (SCSS + HTML)
```

---

## Stack

- **Backend** — Java (Spring Boot)
- **Frontend** — JavaScript, SCSS, HTML
- **Communication** — REST API

---

## Getting started

### Backend

Navigate to the backend folder and run the application:

```bash
cd library-back
./mvnw spring-boot:run
```

The API will start on `http://localhost:8080` by default. Make sure you have Java 17+ installed and a database configured in `application.properties` before running.

### Frontend

Navigate to the frontend folder, install dependencies, and start the dev server:

```bash
cd library-front
npm install
npm start
```

The frontend will typically be served on `http://localhost:3000` and expects the backend to be running alongside it.

---

## Prerequisites

- Java 17 or later
- Maven (or use the included `mvnw` wrapper)
- Node.js and npm
- A running database instance (check `application.properties` for the connection config)

---

## What it does

The application handles the core functions you'd expect from a library system — managing books, tracking availability, and handling user interactions. The backend exposes a REST API consumed by the frontend, keeping the two sides cleanly separated.
