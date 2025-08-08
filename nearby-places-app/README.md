earby Places Finder

A full-stack web application for finding nearby places based on coordinates and radius. Built with Spring Boot (Backend) and React (Frontend).

Architecture

```
nearby-places-app/
├── backend/          # Spring Boot REST API
│   ├── src/main/java/com/nearbyplaces/
│   │   ├── controller/    # REST controllers
│   │   ├── service/       # Business logic
│   │   ├── repository/    # Data access layer
│   │   ├── model/         # JPA entities
│   │   └── dto/          # Data transfer objects
│   └── src/main/resources/
│       └── application.properties
└── frontend/         # React application
    ├── src/
    │   ├── components/    # React components
    │   ├── App.jsx        # Main app component
    │   └── index.css      # Global styles
    └── package.json
```
Technologies

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Spring WebFlux** (for API calls)
- **Maven**

### Frontend
- **React 18**
- **Vite** (build tool)
- **Leaflet.js** (maps)
- **Lucide React** (icons)
- **CSS3** (styling)

 Quick Start

### Prerequisites
- Java 17 or higher
- Node.js 16 or higher
- Maven 3.6 or higher

### Backend Setup

1. **Navigate to backend directory:**
   ```bash
   cd nearby-places-app/backend
   ```

2. **Compile and run:**
   ```bash
   mvn clean compile
   mvn spring-boot:run
   ```

3. **Verify backend is running:**
   - Open: http://localhost:8070/api/health
   - Should see: "Nearby Places API is running!"

### Frontend Setup

1. **Navigate to frontend directory:**
   ```bash
   cd nearby-places-app/frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start development server:**
   ```bash
   npm run dev
   ```

4. **Open application:**
   - Navigate to: http://localhost:3000
   - The app should load with the search interface



Mithrandir3010