import React from 'react'
import { Star, User, Clock } from 'lucide-react'

const ReviewsList = ({ reviews, loading, error }) => {
  // Render star rating
  const renderStars = (rating) => {
    const stars = []
    const fullStars = Math.floor(rating)
    const hasHalfStar = rating % 1 !== 0

    for (let i = 0; i < fullStars; i++) {
      stars.push(
        <Star key={i} size={16} fill="#ffd700" color="#ffd700" />
      )
    }

    if (hasHalfStar) {
      stars.push(
        <Star key="half" size={16} fill="#ffd700" color="#ffd700" style={{ opacity: 0.5 }} />
      )
    }

    const emptyStars = 5 - Math.ceil(rating)
    for (let i = 0; i < emptyStars; i++) {
      stars.push(
        <Star key={`empty-${i}`} size={16} color="#ddd" />
      )
    }

    return stars
  }

  if (loading) {
    return (
      <div className="reviews-loading">
        <div className="loading-spinner"></div>
        <p>Loading reviews...</p>
      </div>
    )
  }

  if (error) {
    return (
      <div className="reviews-error">
        <p>Error loading reviews: {error}</p>
      </div>
    )
  }

  if (!reviews || reviews.length === 0) {
    return (
      <div className="reviews-empty">
        <p>No reviews available for this place.</p>
      </div>
    )
  }

  return (
    <div className="reviews-list">
      <h4 className="reviews-title">
        Customer Reviews ({reviews.length})
      </h4>
      
      {reviews.map((review, index) => (
        <div key={index} className="review-item">
          <div className="review-header">
            <div className="review-author">
              <User size={18} color="#666" />
              <span className="author-name">{review.authorName}</span>
            </div>
            <div className="review-meta">
              <div className="review-rating">
                {renderStars(review.rating)}
                <span className="rating-number">({review.rating})</span>
              </div>
              <div className="review-time">
                <Clock size={14} color="#999" />
                <span>{review.relativeTimeDescription}</span>
              </div>
            </div>
          </div>
          
          {review.text && (
            <div className="review-text">
              <p>{review.text}</p>
            </div>
          )}
        </div>
      ))}
    </div>
  )
}

export default ReviewsList