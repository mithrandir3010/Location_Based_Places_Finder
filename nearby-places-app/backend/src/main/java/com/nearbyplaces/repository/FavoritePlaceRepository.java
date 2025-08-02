package com.nearbyplaces.repository;

import com.nearbyplaces.model.FavoritePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoritePlaceRepository extends JpaRepository<FavoritePlace, UUID> {
    
    /**
     * Find all favorite places ordered by name
     */
    List<FavoritePlace> findAllByOrderByNameAsc();
    
    /**
     * Find favorite place by placeId to prevent duplicates
     */
    Optional<FavoritePlace> findByPlaceId(String placeId);
    
    /**
     * Check if a place is already favorited by placeId
     */
    boolean existsByPlaceId(String placeId);
    
    /**
     * Find favorite place by name and address (alternative duplicate check)
     */
    Optional<FavoritePlace> findByNameAndAddress(String name, String address);
} 