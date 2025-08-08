package com.nearbyplaces.controller;

import com.nearbyplaces.dto.NearbyPlacesRequest;
import com.nearbyplaces.dto.PlaceResponse;
import com.nearbyplaces.service.PlaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PlaceController {
    
    @Autowired
    private PlaceService placeService;
    
    @GetMapping("/nearby")
    public ResponseEntity<List<PlaceResponse>> findNearbyPlaces(
            @RequestParam BigDecimal latitude,
            @RequestParam BigDecimal longitude,
            @RequestParam BigDecimal radius,
            @RequestParam(required = false) String type) {
        
        List<PlaceResponse> places = placeService.findNearbyPlaces(latitude, longitude, radius, type);
        return ResponseEntity.ok(places);
    }
    
    @PostMapping("/nearby")
    public ResponseEntity<List<PlaceResponse>> findNearbyPlacesPost(
            @Valid @RequestBody NearbyPlacesRequest request) {
        
        List<PlaceResponse> places = placeService.findNearbyPlaces(
            request.getLatitude(), 
            request.getLongitude(), 
            request.getRadius(),
            request.getType()
        );
        return ResponseEntity.ok(places);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Nearby Places API is running!");
    }
} 