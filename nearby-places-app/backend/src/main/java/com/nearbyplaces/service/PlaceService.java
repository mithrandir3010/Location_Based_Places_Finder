package com.nearbyplaces.service;

import com.nearbyplaces.dto.PlaceResponse;
import com.nearbyplaces.model.Place;
import com.nearbyplaces.repository.PlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {
    
    private static final Logger logger = LoggerFactory.getLogger(PlaceService.class);
    
    @Autowired
    private PlaceRepository placeRepository;
    
    /**
     * Finds nearby places using mock data for now
     * Google Places API integration will be added later
     * 
     * @param latitude The latitude coordinate
     * @param longitude The longitude coordinate
     * @param radius The search radius in kilometers
     * @return List of PlaceResponse objects
     */
    public List<PlaceResponse> findNearbyPlaces(BigDecimal latitude, BigDecimal longitude, BigDecimal radius, String type) {
        logger.info("Finding nearby places at lat: {}, lng: {}, radius: {}km, type: {}", latitude, longitude, radius, type);
        
        // For now, return mock data
        // Google Places API integration will be added later
        return generateMockPlaces(latitude, longitude, radius, type);
    }
    
    /**
     * Generates mock places for development and testing
     * 
     * @param latitude The latitude coordinate
     * @param longitude The longitude coordinate
     * @param radius The search radius in kilometers
     * @return List of PlaceResponse objects with mock data
     */
    private List<PlaceResponse> generateMockPlaces(BigDecimal latitude, BigDecimal longitude, BigDecimal radius, String type) {
        logger.info("Generating mock places for coordinates: {}, {}, type: {}", latitude, longitude, type);
        
        List<PlaceResponse> places = new ArrayList<>();
        
        // Generate some mock places around the given coordinates
        List<PlaceResponse> allPlaces = new ArrayList<>();
        
        allPlaces.add(new PlaceResponse(
            "Starbucks Coffee",
            "123 Main Street, Downtown",
            latitude.add(new BigDecimal("0.001")),
            longitude.add(new BigDecimal("0.001")),
            new BigDecimal("4.2"),
            "mock_place_1",
            List.of("cafe", "food", "establishment")
        ));
        
        allPlaces.add(new PlaceResponse(
            "McDonald's",
            "456 Oak Avenue, Shopping District",
            latitude.add(new BigDecimal("-0.002")),
            longitude.add(new BigDecimal("0.003")),
            new BigDecimal("3.8"),
            "mock_place_2",
            List.of("restaurant", "food", "establishment")
        ));
        
        allPlaces.add(new PlaceResponse(
            "Local Library",
            "789 Pine Street, Cultural District",
            latitude.add(new BigDecimal("0.003")),
            longitude.add(new BigDecimal("-0.001")),
            new BigDecimal("4.5"),
            "mock_place_3",
            List.of("library", "establishment", "point_of_interest")
        ));
        
        allPlaces.add(new PlaceResponse(
            "City Park",
            "321 Elm Street, Recreation Area",
            latitude.add(new BigDecimal("-0.001")),
            longitude.add(new BigDecimal("-0.002")),
            new BigDecimal("4.7"),
            "mock_place_4",
            List.of("park", "establishment", "point_of_interest")
        ));
        
        allPlaces.add(new PlaceResponse(
            "Gas Station",
            "654 Maple Drive, Highway Exit",
            latitude.add(new BigDecimal("0.004")),
            longitude.add(new BigDecimal("0.002")),
            new BigDecimal("3.5"),
            "mock_place_5",
            List.of("gas_station", "establishment", "point_of_interest")
        ));
        
        // Filter places by type if specified
        if (type != null && !type.isEmpty()) {
            for (PlaceResponse place : allPlaces) {
                if (place.getTypes().contains(type.toLowerCase())) {
                    places.add(place);
                }
            }
        } else {
            places.addAll(allPlaces);
        }

        logger.info("Generated {} mock places after filtering by type: {}", places.size(), type);
        return places;
    }
    
    /**
     * Converts Place entities to PlaceResponse objects
     * 
     * @param places List of Place entities
     * @return List of PlaceResponse objects
     */
    private List<PlaceResponse> convertToResponseList(List<Place> places) {
        List<PlaceResponse> responses = new ArrayList<>();
        for (Place place : places) {
            responses.add(new PlaceResponse(
                place.getName(),
                place.getAddress(),
                place.getLatitude(),
                place.getLongitude(),
                place.getRating(),
                place.getPlaceId()
            ));
        }
        return responses;
    }
    
    /**
     * Finds a place by its Google Places ID
     * 
     * @param placeId The Google Places ID
     * @return Optional containing the place if found
     */
    public Optional<Place> findByPlaceId(String placeId) {
        List<Place> places = placeRepository.findByPlaceId(placeId);
        return places.isEmpty() ? Optional.empty() : Optional.of(places.get(0));
    }
} 