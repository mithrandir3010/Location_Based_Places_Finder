package com.nearbyplaces.service;

import com.nearbyplaces.dto.FavoritePlaceRequest;
import com.nearbyplaces.dto.FavoritePlaceResponse;
import com.nearbyplaces.model.FavoritePlace;
import com.nearbyplaces.repository.FavoritePlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FavoritePlaceService {
    
    private static final Logger logger = LoggerFactory.getLogger(FavoritePlaceService.class);
    
    @Autowired
    private FavoritePlaceRepository favoritePlaceRepository;
    
    /**
     * Add a new favorite place
     * 
     * @param request The favorite place request
     * @return FavoritePlaceResponse if added successfully
     * @throws RuntimeException if place already exists
     */
    public FavoritePlaceResponse addFavorite(FavoritePlaceRequest request) {
        logger.info("Adding favorite place: {}", request.getName());
        
        // Check if place already exists by placeId
        if (request.getPlaceId() != null && favoritePlaceRepository.existsByPlaceId(request.getPlaceId())) {
            logger.warn("Place already exists in favorites: {}", request.getPlaceId());
            throw new RuntimeException("Place already exists in favorites");
        }
        
        // Check if place already exists by name and address
        Optional<FavoritePlace> existingPlace = favoritePlaceRepository.findByNameAndAddress(
            request.getName(), request.getAddress());
        if (existingPlace.isPresent()) {
            logger.warn("Place already exists in favorites: {} at {}", request.getName(), request.getAddress());
            throw new RuntimeException("Place already exists in favorites");
        }
        
        // Create new favorite place
        FavoritePlace favoritePlace = new FavoritePlace(
            request.getName(),
            request.getAddress(),
            request.getLatitude(),
            request.getLongitude(),
            request.getTypes(),
            request.getRating(),
            request.getPlaceId()
        );
        
        FavoritePlace savedPlace = favoritePlaceRepository.save(favoritePlace);
        logger.info("Successfully added favorite place: {} with ID: {}", savedPlace.getName(), savedPlace.getId());
        
        return convertToResponse(savedPlace);
    }
    
    /**
     * Get all favorite places
     * 
     * @return List of FavoritePlaceResponse
     */
    public List<FavoritePlaceResponse> getAllFavorites() {
        logger.info("Fetching all favorite places");
        List<FavoritePlace> favorites = favoritePlaceRepository.findAllByOrderByNameAsc();
        logger.info("Found {} favorite places", favorites.size());
        return favorites.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Delete a favorite place by ID
     * 
     * @param id The UUID of the favorite place
     * @return true if deleted successfully
     * @throws RuntimeException if place not found
     */
    public boolean deleteFavorite(UUID id) {
        logger.info("Deleting favorite place with ID: {}", id);
        
        Optional<FavoritePlace> favorite = favoritePlaceRepository.findById(id);
        if (favorite.isEmpty()) {
            logger.warn("Favorite place not found with ID: {}", id);
            throw new RuntimeException("Favorite place not found");
        }
        
        favoritePlaceRepository.deleteById(id);
        logger.info("Successfully deleted favorite place: {}", favorite.get().getName());
        return true;
    }
    
    /**
     * Check if a place is favorited by placeId
     * 
     * @param placeId The Google Places ID
     * @return true if favorited, false otherwise
     */
    public boolean isFavorited(String placeId) {
        if (placeId == null) {
            return false;
        }
        return favoritePlaceRepository.existsByPlaceId(placeId);
    }
    
    /**
     * Convert FavoritePlace entity to FavoritePlaceResponse DTO
     * 
     * @param favoritePlace The entity to convert
     * @return FavoritePlaceResponse
     */
    private FavoritePlaceResponse convertToResponse(FavoritePlace favoritePlace) {
        return new FavoritePlaceResponse(
            favoritePlace.getId(),
            favoritePlace.getName(),
            favoritePlace.getAddress(),
            favoritePlace.getLatitude(),
            favoritePlace.getLongitude(),
            favoritePlace.getTypes(),
            favoritePlace.getRating(),
            favoritePlace.getPlaceId()
        );
    }
} 