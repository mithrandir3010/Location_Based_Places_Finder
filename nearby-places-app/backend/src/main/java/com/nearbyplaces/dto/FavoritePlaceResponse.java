package com.nearbyplaces.dto;

import java.util.List;
import java.util.UUID;

public class FavoritePlaceResponse {
    
    private UUID id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<String> types;
    private Double rating;
    private String placeId;
    
    // Constructors
    public FavoritePlaceResponse() {}
    
    public FavoritePlaceResponse(UUID id, String name, String address, Double latitude, Double longitude, List<String> types, Double rating, String placeId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.types = types;
        this.rating = rating;
        this.placeId = placeId;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public List<String> getTypes() {
        return types;
    }
    
    public void setTypes(List<String> types) {
        this.types = types;
    }
    
    public Double getRating() {
        return rating;
    }
    
    public void setRating(Double rating) {
        this.rating = rating;
    }
    
    public String getPlaceId() {
        return placeId;
    }
    
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
} 