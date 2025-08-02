import React, { useState, useEffect } from 'react'
import { Heart, MapPin, Star, Tag, Trash2 } from 'lucide-react'

const Favorites = () => {
  const [favorites, setFavorites] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchFavorites()
  }, [])

  const fetchFavorites = async () => {
    try {
      setLoading(true)
      const response = await fetch('/api/favorites')
      
      if (!response.ok) {
        throw new Error('Failed to fetch favorites')
      }

      const data = await response.json()
      setFavorites(data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  const removeFavorite = async (id) => {
    try {
      const response = await fetch(`/api/favorites/${id}`, {
        method: 'DELETE'
      })

      if (!response.ok) {
        throw new Error('Failed to remove favorite')
      }

      // Remove from local state
      setFavorites(favorites.filter(fav => fav.id !== id))
      
      // Show success message (you can add a toast notification here)
      alert('Favorite removed successfully!')
    } catch (err) {
      alert('Failed to remove favorite: ' + err.message)
    }
  }

  if (loading) {
    return (
      <div className="loading">
        <Heart className="loading-icon" />
        <p>Loading favorites...</p>
      </div>
    )
  }

  if (error) {
    return (
      <div className="error">
        <strong>Error:</strong> {error}
      </div>
    )
  }

  if (favorites.length === 0) {
    return (
      <div className="empty-state">
        <Heart />
        <h3>No favorites yet</h3>
        <p>Start adding places to your favorites to see them here!</p>
      </div>
    )
  }

  return (
    <div className="favorites-container">
      <div className="favorites-header">
        <h2>❤️ My Favorites ({favorites.length})</h2>
        <p>Your saved places</p>
      </div>

      <div className="favorites-list">
        {favorites.map((favorite) => (
          <div key={favorite.id} className="favorite-item">
            <div className="favorite-content">
              <div className="favorite-name">{favorite.name}</div>
              <div className="favorite-address">
                <MapPin size={14} style={{ marginRight: '4px' }} />
                {favorite.address}
              </div>
              <div className="favorite-rating">
                <Star size={16} fill="#f39c12" />
                {favorite.rating ? `${favorite.rating}/5` : 'No rating'}
              </div>
              {favorite.types && favorite.types.length > 0 && (
                <div className="favorite-types">
                  <Tag size={14} style={{ marginRight: '4px', color: '#3498db' }} />
                  <span style={{ fontSize: '0.8em', color: '#3498db' }}>
                    {favorite.types.join(', ')}
                  </span>
                </div>
              )}
              <div style={{ 
                fontSize: '0.8em', 
                color: '#95a5a6', 
                marginTop: '4px' 
              }}>
                📍 {favorite.latitude.toFixed(6)}, {favorite.longitude.toFixed(6)}
              </div>
            </div>
            
            <button
              onClick={() => removeFavorite(favorite.id)}
              className="remove-favorite-btn"
              title="Remove from favorites"
            >
              <Trash2 size={16} />
            </button>
          </div>
        ))}
      </div>
    </div>
  )
}

export default Favorites 