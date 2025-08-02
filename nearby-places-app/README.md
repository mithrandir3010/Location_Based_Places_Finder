# 📍 Nearby Places Finder

A full-stack web application for finding nearby places based on coordinates and radius. Built with Spring Boot (Backend) and React (Frontend).

## 🚀 Features

- **Search Places**: Enter coordinates and radius to find nearby places
- **Interactive Map**: View places on an interactive map with markers
- **Favorites System**: Add places to favorites and manage them
- **Responsive Design**: Works on desktop and mobile devices
- **Real-time Updates**: Dynamic search results and favorite management

## 🏗️ Architecture

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

## 🛠️ Technologies

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

## 🚀 Quick Start

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

## 📋 API Endpoints

### Places Search
- `GET /api/nearby?latitude={lat}&longitude={lng}&radius={km}` - Search nearby places
- `POST /api/nearby` - Search with JSON body
- `GET /api/health` - Health check

### Favorites Management
- `GET /api/favorites` - Get all favorites
- `POST /api/favorites` - Add new favorite
- `DELETE /api/favorites/{id}` - Remove favorite
- `GET /api/favorites/check/{placeId}` - Check if place is favorited

## 🎯 Usage

### Search for Places
1. Enter latitude, longitude, and radius
2. Click "Find Places" or use "Use My Location"
3. View results in list and on map
4. Click heart icon to add to favorites

### Manage Favorites
1. Click "Favorites" in navigation
2. View all saved places
3. Click trash icon to remove from favorites
4. Favorites persist in database

## 🔧 Configuration

### Backend Configuration
Edit `backend/src/main/resources/application.properties`:

```properties
# Server port
server.port=8070

# Database settings
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop

# Google Places API (for future integration)
google.places.api.key=YOUR_API_KEY_HERE
```

### Frontend Configuration
Edit `frontend/vite.config.js` for proxy settings:

```javascript
proxy: {
  '/api': {
    target: 'http://localhost:8070',
    changeOrigin: true,
  }
}
```

## 🗄️ Database

The application uses H2 in-memory database with the following tables:

- `places` - Stores place information
- `favorite_places` - Stores user favorites
- `favorite_place_types` - Stores favorite place categories

## 🔒 Security Features

- **Input Validation**: All user inputs are validated
- **CORS Configuration**: Cross-origin requests enabled
- **Error Handling**: Comprehensive error handling and logging
- **Duplicate Prevention**: Prevents adding same place to favorites

## 🎨 UI Features

- **Modern Design**: Clean, responsive interface
- **Interactive Map**: Leaflet.js integration
- **Loading States**: Visual feedback during operations
- **Error Messages**: User-friendly error handling
- **Mobile Responsive**: Works on all screen sizes

## 🚧 Future Enhancements

- [ ] Google Places API integration
- [ ] User authentication
- [ ] Advanced filtering options
- [ ] Place reviews and ratings
- [ ] Route planning
- [ ] Offline support
- [ ] Push notifications

## 🐛 Troubleshooting

### Backend Issues
- **Port already in use**: Change `server.port` in `application.properties`
- **Compilation errors**: Ensure Java 17 is installed
- **Database issues**: Check H2 console at http://localhost:8070/h2-console

### Frontend Issues
- **Dependencies**: Run `npm install` again
- **Proxy issues**: Check Vite config and backend URL
- **Map not loading**: Check internet connection for Leaflet tiles

## 📝 License

This project is open source and available under the MIT License.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📞 Support

For issues and questions:
- Check the troubleshooting section
- Review the API documentation
- Open an issue on GitHub

---

Mithrandir3010
