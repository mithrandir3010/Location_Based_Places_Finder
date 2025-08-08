package com.nearbyplaces.service;

import com.nearbyplaces.dto.ReviewResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);
    
    @Value("${google.places.api.key}")
    private String apiKey;
    
    @Value("${google.places.api.base-url}")
    private String baseUrl;
    
    @Value("${google.places.api.place-details-path}")
    private String placeDetailsPath;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public ReviewService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Fetches reviews for a specific place from Google Places API
     * 
     * @param placeId The Google Places ID
     * @return List of ReviewResponse objects
     */
    public List<ReviewResponse> getPlaceReviews(String placeId) {
        logger.info("Fetching reviews for place ID: {}", placeId);
        
        // For development, return mock reviews if API key is not configured
        if ("YOUR_API_KEY_HERE".equals(apiKey)) {
            logger.info("Using mock reviews for development");
            return generateMockReviews(placeId);
        }
        
        try {
            String url = buildPlaceDetailsUrl(placeId);
            logger.debug("Making request to Google Places API: {}", url);
            
            String response = restTemplate.getForObject(url, String.class);
            return parseReviewsFromResponse(response);
            
        } catch (Exception e) {
            logger.error("Error fetching reviews for place {}: {}", placeId, e.getMessage());
            return generateMockReviews(placeId); // Fallback to mock data
        }
    }
    
    /**
     * Builds the Google Places API URL for place details
     */
    private String buildPlaceDetailsUrl(String placeId) {
        return String.format("%s%s?place_id=%s&fields=reviews&key=%s",
            baseUrl, placeDetailsPath, placeId, apiKey);
    }
    
    /**
     * Parses reviews from Google Places API response
     */
    private List<ReviewResponse> parseReviewsFromResponse(String response) {
        List<ReviewResponse> reviews = new ArrayList<>();
        
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode result = root.get("result");
            
            if (result != null && result.has("reviews")) {
                JsonNode reviewsNode = result.get("reviews");
                
                for (JsonNode reviewNode : reviewsNode) {
                    ReviewResponse review = new ReviewResponse();
                    
                    if (reviewNode.has("author_name")) {
                        review.setAuthorName(reviewNode.get("author_name").asText());
                    }
                    
                    if (reviewNode.has("rating")) {
                        review.setRating(new BigDecimal(reviewNode.get("rating").asDouble()));
                    }
                    
                    if (reviewNode.has("relative_time_description")) {
                        review.setRelativeTimeDescription(reviewNode.get("relative_time_description").asText());
                    }
                    
                    if (reviewNode.has("text")) {
                        review.setText(reviewNode.get("text").asText());
                    }
                    
                    reviews.add(review);
                }
            }
        } catch (Exception e) {
            logger.error("Error parsing reviews response: {}", e.getMessage());
        }
        
        logger.info("Parsed {} reviews", reviews.size());
        return reviews;
    }
    
    /**
     * Generates mock reviews for development and testing
     */
    private List<ReviewResponse> generateMockReviews(String placeId) {
        List<ReviewResponse> mockReviews = new ArrayList<>();
        
        // Generate different mock reviews based on placeId
        switch (placeId) {
            case "mock_place_1": // Starbucks
                mockReviews.add(new ReviewResponse(
                    "Sarah Johnson", 
                    new BigDecimal("4.0"), 
                    "2 weeks ago",
                    "Great coffee and friendly staff. The atmosphere is perfect for working on my laptop. WiFi is fast and reliable."
                ));
                mockReviews.add(new ReviewResponse(
                    "Mike Chen", 
                    new BigDecimal("5.0"), 
                    "1 month ago",
                    "Best Starbucks location in the area! Always clean, quick service, and my order is always correct."
                ));
                mockReviews.add(new ReviewResponse(
                    "Emma Davis", 
                    new BigDecimal("3.0"), 
                    "3 days ago",
                    "Coffee is good but can get quite crowded during peak hours. Limited seating available."
                ));
                break;
                
            case "mock_place_2": // McDonald's
                mockReviews.add(new ReviewResponse(
                    "John Smith", 
                    new BigDecimal("4.0"), 
                    "1 week ago",
                    "Fast service and food was hot. Drive-thru was efficient even during lunch rush."
                ));
                mockReviews.add(new ReviewResponse(
                    "Lisa Wang", 
                    new BigDecimal("2.0"), 
                    "4 days ago",
                    "Food was cold when I received it. Had to wait longer than expected for a simple order."
                ));
                break;
                
            case "mock_place_3": // Library
                mockReviews.add(new ReviewResponse(
                    "David Brown", 
                    new BigDecimal("5.0"), 
                    "2 months ago",
                    "Excellent library with a great selection of books. Staff is very helpful and knowledgeable."
                ));
                mockReviews.add(new ReviewResponse(
                    "Maria Garcia", 
                    new BigDecimal("4.0"), 
                    "1 week ago",
                    "Quiet study areas and good computer access. Perfect place for research and reading."
                ));
                break;
                
            case "mock_place_4": // City Park
                mockReviews.add(new ReviewResponse(
                    "Alex Thompson", 
                    new BigDecimal("5.0"), 
                    "3 weeks ago",
                    "Beautiful park with well-maintained trails. Great for jogging and family picnics."
                ));
                mockReviews.add(new ReviewResponse(
                    "Jennifer Lee", 
                    new BigDecimal("4.0"), 
                    "5 days ago",
                    "Nice playground for kids and plenty of green space. Could use more benches though."
                ));
                break;
                
            default:
                mockReviews.add(new ReviewResponse(
                    "Anonymous User", 
                    new BigDecimal("4.0"), 
                    "1 week ago",
                    "Good place overall. Worth visiting!"
                ));
                break;
        }
        
        logger.info("Generated {} mock reviews for place {}", mockReviews.size(), placeId);
        return mockReviews;
    }
}