package com.nearbyplaces.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class NearbyPlacesRequest {
    
    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private BigDecimal latitude;
    
    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private BigDecimal longitude;
    
    @NotNull(message = "Radius is required")
    @DecimalMin(value = "0.1", message = "Radius must be at least 0.1")
    @DecimalMax(value = "50000.0", message = "Radius must be at most 50000")
    private BigDecimal radius;
    
    // Constructors
    public NearbyPlacesRequest() {}
    
    public NearbyPlacesRequest(BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }
    
    // Getters and Setters
    public BigDecimal getLatitude() {
        return latitude;
    }
    
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    public BigDecimal getLongitude() {
        return longitude;
    }
    
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    
    public BigDecimal getRadius() {
        return radius;
    }
    
    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }
} 