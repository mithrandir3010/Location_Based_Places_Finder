import React, { useState, useEffect } from 'react'
import { Star, MapPin, Tag, Heart, HeartOff, MessageCircle } from 'lucide-react'
import ReviewsList from './ReviewsList'

const PlacesList = ({ places }) => {
  const [favoritedPlaces, setFavoritedPlaces] = useState(new Set())
  const [expandedReviews, setExpandedReviews] = useState(new Set())
  const [reviewsData, setReviewsData] = useState({})
  const [reviewsLoading, setReviewsLoading] = useState(new Set())
  const [reviewsErrors, setReviewsErrors] = useState({})

  useEffect(() => {
    // Check which places are already favorited
    places.forEach(place => {
      if (place.placeId) {
        checkIfFavorited(place.placeId)
      }
    })
  }, [places])

  const checkIfFavorited = async (placeId) => {
    try {
      const response = await fetch(`/api/favorites/check/${placeId}`)
      if (response.ok) {
        const isFavorited = await response.json()
        setFavoritedPlaces(prev => {
          const newSet = new Set(prev)
          if (isFavorited) {
            newSet.add(placeId)
          } else {
            newSet.delete(placeId)
          }
          return newSet
        })
      }
    } catch (error) {
      console.error('Error checking favorite status:', error)
    }
  }

  const addToFavorites = async (place) => {
    try {
      const response = await fetch('/api/favorites', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: place.name,
          address: place.address,
          latitude: place.latitude,
          longitude: place.longitude,
          types: place.types || [],
          rating: place.rating,
          placeId: place.placeId
        })
      })

      if (response.ok) {
        setFavoritedPlaces(prev => new Set([...prev, place.placeId]))
        alert('Added to favorites!')
      } else {
        const errorData = await response.json()
        alert(errorData.error || 'Failed to add to favorites')
      }
    } catch (error) {
      alert('Failed to add to favorites: ' + error.message)
    }
  }

  const removeFromFavorites = async (place) => {
    try {
      // First, get the favorite ID by placeId
      const favoritesResponse = await fetch('/api/favorites')
      if (favoritesResponse.ok) {
        const favorites = await favoritesResponse.json()
        const favorite = favorites.find(fav => fav.placeId === place.placeId)
        
        if (favorite) {
          const deleteResponse = await fetch(`/api/favorites/${favorite.id}`, {
            method: 'DELETE'
          })

          if (deleteResponse.ok) {
            setFavoritedPlaces(prev => {
              const newSet = new Set(prev)
              newSet.delete(place.placeId)
              return newSet
            })
            alert('Removed from favorites!')
          } else {
            alert('Failed to remove from favorites')
          }
        }
      }
    } catch (error) {
      alert('Failed to remove from favorites: ' + error.message)
    }
  }

  const fetchReviews = async (placeId) => {
    if (reviewsData[placeId]) {
      // Reviews already loaded, just toggle visibility
      setExpandedReviews(prev => {
        const newSet = new Set(prev)
        if (newSet.has(placeId)) {
          newSet.delete(placeId)
        } else {
          newSet.add(placeId)
        }
        return newSet
      })
      return
    }

    // Show loading state
    setReviewsLoading(prev => new Set([...prev, placeId]))
    setReviewsErrors(prev => ({ ...prev, [placeId]: null }))

    try {
      const response = await fetch(`/api/places/${placeId}/reviews`)
      
      if (response.ok) {
        const reviews = await response.json()
        setReviewsData(prev => ({ ...prev, [placeId]: reviews }))
        setExpandedReviews(prev => new Set([...prev, placeId]))
      } else {
        throw new Error('Failed to fetch reviews')
      }
    } catch (error) {
      setReviewsErrors(prev => ({ ...prev, [placeId]: error.message }))
    } finally {
      setReviewsLoading(prev => {
        const newSet = new Set(prev)
        newSet.delete(placeId)
        return newSet
      })
    }
  }

  if (!places || places.length === 0) {
    return (
      <div className="places-list">
        <h2>Nearby Places</h2>
        <div className="empty-state">
          <MapPin />
          <p>No places found in this area</p>
        </div>
      </div>
    )
  }

  return (
    <div className="places-list">
      <h2>Nearby Places ({places.length})</h2>
      {places.map((place, index) => {
        const isFavorited = favoritedPlaces.has(place.placeId)
        
        return (
          <div key={place.placeId || index} className="place-item">
            <div className="place-content">
              <div className="place-name">{place.name}</div>
              <div className="place-address">
                <MapPin size={14} style={{ marginRight: '4px' }} />
                {place.address}
              </div>
              <div className="place-rating">
                <Star size={16} fill="#f39c12" />
                {place.rating ? `${place.rating}/5` : 'No rating'}
              </div>
              {place.types && place.types.length > 0 && (
                <div className="place-types">
                  <Tag size={14} style={{ marginRight: '4px', color: '#3498db' }} />
                  <span style={{ fontSize: '0.8em', color: '#3498db' }}>
                    {place.types.join(', ')}
                  </span>
                </div>
              )}
              <div style={{ 
                fontSize: '0.8em', 
                color: '#95a5a6', 
                marginTop: '4px' 
              }}>
                📍 {place.latitude.toFixed(6)}, {place.longitude.toFixed(6)}
              </div>

              <button
                onClick={() => fetchReviews(place.placeId)}
                className="reviews-button"
                title="View customer reviews"
              >
                <MessageCircle size={16} />
                {expandedReviews.has(place.placeId) ? 'Hide Reviews' : 'View Reviews'}
              </button>
            </div>
            
            <button
              onClick={() => isFavorited ? removeFromFavorites(place) : addToFavorites(place)}
              className={`favorite-btn ${isFavorited ? 'favorited' : ''}`}
              title={isFavorited ? 'Remove from favorites' : 'Add to favorites'}
            >
              {isFavorited ? <HeartOff size={16} /> : <Heart size={16} />}
            </button>

            {expandedReviews.has(place.placeId) && (
              <div className="reviews-section">
                <ReviewsList
                  reviews={reviewsData[place.placeId]}
                  loading={reviewsLoading.has(place.placeId)}
                  error={reviewsErrors[place.placeId]}
                />
              </div>
            )}
          </div>
        )
      })}
    </div>
  )
}

export default PlacesList 