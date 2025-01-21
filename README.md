### README: TrustMe

# Overview
TrustMe is a platform designed to provide users with reliable and comprehensive information about various companies. Instead of searching multiple websites for data, TrustMe consolidates all relevant information in one place. This includes:

- Customer reviews
- Geographical locations (maps)
- AI-generated summaries of reviews
The system utilizes APIs like Google Places, Foursquare Places, and OpenAI to gather and process data dynamically.

# Technical Stack
### Backend
Language: Kotlin/Java
Framework: Javalin
Build Tool: Maven
Key Features:
RESTful API design
Integration with external APIs for real-time data
Lightweight server-side processing

### Frontend
Technologies: React, TypeScript, JavaScript, HTML, CSS
Dependencies:
@react-google-maps/api: Integration with Google Maps
react-router-dom: For routing
react-places-autocomplete: Autocomplete for places
react-icons: Icon library
Other essential libraries for testing and building UI.

# Installation Instructions
### Prerequisites
Ensure you have the following installed:

Node.js (for the frontend)
Java Development Kit (JDK 21) (for the backend)
Maven (for managing backend dependencies)
An IDE like IntelliJ or VS Code.

## Steps to Run
### Backend
### Start the Server
### Option 1: Using IntelliJ

1. Open the project in IntelliJ.
2. Navigate to:
- cd TrustMe/backend/src/main/java/org.example
3. Right-click the Main class and select Run Main.
4. The server will now be running.

### Option 2: Using Terminal

1. Navigate to the backend folder:
- cd backend/src/main/java/org/example
2. Compile and run the Main class:
a. javac Main.java
b. java Main

### Frontend 
1. Navigate to the frontend directory:
- cd frontend/trust-me
2. Install dependencies:
- npm install
3. Start the frontend:
npm run start
4. Open your browser and go to http://localhost:3000 to view the interface.

# Usage
1. Enter the name of a company (e.g., "IKEA Malmö") in the search box.
2. Press the magnifying glass icon to search.
3. View detailed information about the company, including:
- User reviews
- AI-generated insights
- Location map.

# API Keys
To make the project functional, you need to add your own API keys for the following services:

- Google Places API: For fetching user reviews and company details.
- Foursquare Places API: Complementary data about companies.
- OpenAI API: For summarizing user reviews.
### The API keys provided in the code are placeholders and are not active. Replace them with your own keys in the appropriate configuration files.

# Dependencies Overview
### Frontend Dependencies
- React: UI library.
- TypeScript: Type safety.
- React Router DOM: For routing.
- Google Maps Integration: Map views and autocomplete for places.

### Backend Dependencies (Defined in pom.xml)
- Javalin: Lightweight web framework.
- SLF4J: Logging framework.
- Jackson: For JSON processing.
- Gson: For handling JSON objects.
