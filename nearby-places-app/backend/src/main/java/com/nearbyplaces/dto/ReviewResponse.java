package com.nearbyplaces.dto;

import java.math.BigDecimal;

public class ReviewResponse {
    
    private String authorName;
    private BigDecimal rating;
    private String relativeTimeDescription;
    private String text;
    
    // Constructors
    public ReviewResponse() {}
    
    public ReviewResponse(String authorName, BigDecimal rating, String relativeTimeDescription, String text) {
        this.authorName = authorName;
        this.rating = rating;
        this.relativeTimeDescription = relativeTimeDescription;
        this.text = text;
    }
    
    // Getters and Setters
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    public BigDecimal getRating() {
        return rating;
    }
    
    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
    
    public String getRelativeTimeDescription() {
        return relativeTimeDescription;
    }
    
    public void setRelativeTimeDescription(String relativeTimeDescription) {
        this.relativeTimeDescription = relativeTimeDescription;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
}