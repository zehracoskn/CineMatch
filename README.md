# CineMatch
CineMatch is a personalized movie recommendation web application built with Java and Spring Boot. It helps users discover new movies based on their interests using a content-based filtering approach.

## Features
- User Registration and Login
- AI-powered movie recommendations (KNN algorithm)
- Movie database with genres, ratings, and details
- User watchlist
- Swipe-based preference input
- Responsive UI (frontend handled by team)

## Recommendation System
The app uses a **content-based filtering approach** with a custom implementation of the **K-Nearest Neighbors (KNN)** algorithm to suggest similar movies based on genre, rating, and other metadata.


## Technologies Used
- Java, Spring Boot
- MySQL
- HTML, CSS, JavaScript (for frontend)
- Bootstrap (for styling)
- KNN Algorithm (Java-based implementation)

## Project Structure
CineMatch/
├── Backend/
│ ├── src/
│ │ └── main/java/com/cinematch/
│ │ ├── controller/
│ │ ├── service/
│ │ ├── entity/
│ │ └── recommendation/ ← KNN logic here
│ └── resources/
│ └── application.properties
├── .env ← Stores your TMDB_API_KEY
├── README.md
└── output.sql (optional database init)

## How to Run
1. Clone the repo.
2. Set up MySQL database (import the provided .sql file).
3. Run the Spring Boot application.
4. Access it via http://localhost:8080.

License
This project is for educational purposes.