package com.nearbyplaces.repository;

import com.nearbyplaces.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    
    @Query(value = "SELECT * FROM places p WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(p.latitude)))) <= :radius", 
            nativeQuery = true)
    List<Place> findNearbyPlaces(@Param("latitude") BigDecimal latitude, 
                                 @Param("longitude") BigDecimal longitude, 
                                 @Param("radius") BigDecimal radius);
    
    List<Place> findByPlaceId(String placeId);
} 