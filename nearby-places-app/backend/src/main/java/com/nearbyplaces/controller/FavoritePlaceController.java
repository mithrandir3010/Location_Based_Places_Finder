package com.nearbyplaces.controller;

import com.nearbyplaces.dto.FavoritePlaceRequest;
import com.nearbyplaces.dto.FavoritePlaceResponse;
import com.nearbyplaces.service.FavoritePlaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*")
public class FavoritePlaceController {
    
    @Autowired
    private FavoritePlaceService favoritePlaceService;
    
    /**
     * Get all favorite places
     * 
     * @return List of favorite places
     */
    @GetMapping
    public ResponseEntity<List<FavoritePlaceResponse>> getAllFavorites() {
        try {
            List<FavoritePlaceResponse> favorites = favoritePlaceService.getAllFavorites();
            return ResponseEntity.ok(favorites);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Add a new favorite place
     * 
     * @param request The favorite place request
     * @return Created favorite place
     */
    @PostMapping
    public ResponseEntity<?> addFavorite(@Valid @RequestBody FavoritePlaceRequest request) {
        try {
            FavoritePlaceResponse favorite = favoritePlaceService.addFavorite(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\": \"Failed to add favorite place\"}");
        }
    }
    
    /**
     * Delete a favorite place by ID
     * 
     * @param id The UUID of the favorite place
     * @return Success response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFavorite(@PathVariable UUID id) {
        try {
            boolean deleted = favoritePlaceService.deleteFavorite(id);
            if (deleted) {
                return ResponseEntity.ok().body("{\"message\": \"Favorite place deleted successfully\"}");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\": \"Failed to delete favorite place\"}");
        }
    }
    
    /**
     * Check if a place is favorited by placeId
     * 
     * @param placeId The Google Places ID
     * @return Boolean response
     */
    @GetMapping("/check/{placeId}")
    public ResponseEntity<Boolean> isFavorited(@PathVariable String placeId) {
        try {
            boolean isFavorited = favoritePlaceService.isFavorited(placeId);
            return ResponseEntity.ok(isFavorited);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 