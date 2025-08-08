import React, { useState } from 'react'
import { Search, MapPin } from 'lucide-react'

const SearchForm = ({ onSearch, loading }) => {
  const [latitude, setLatitude] = useState('')
  const [longitude, setLongitude] = useState('')
  const [radius, setRadius] = useState('5')
  const [type, setType] = useState('')
  const [errors, setErrors] = useState({})

  const placeTypes = [
    { value: '', label: 'All Types' },
    { value: 'restaurant', label: 'Restaurants' },
    { value: 'cafe', label: 'Cafes' },
    { value: 'park', label: 'Parks' },
    { value: 'school', label: 'Schools' },
    { value: 'hospital', label: 'Hospitals' }
  ]

  const validateForm = () => {
    const newErrors = {}

    if (!latitude) {
      newErrors.latitude = 'Latitude is required'
    } else if (isNaN(latitude) || parseFloat(latitude) < -90 || parseFloat(latitude) > 90) {
      newErrors.latitude = 'Latitude must be between -90 and 90'
    }

    if (!longitude) {
      newErrors.longitude = 'Longitude is required'
    } else if (isNaN(longitude) || parseFloat(longitude) < -180 || parseFloat(longitude) > 180) {
      newErrors.longitude = 'Longitude must be between -180 and 180'
    }

    if (!radius) {
      newErrors.radius = 'Radius is required'
    } else if (isNaN(radius) || parseFloat(radius) < 0.1 || parseFloat(radius) > 50) {
      newErrors.radius = 'Radius must be between 0.1 and 50 km'
    }

    setErrors(newErrors)
    return Object.keys(newErrors).length === 0
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    
    if (validateForm()) {
      onSearch(parseFloat(latitude), parseFloat(longitude), parseFloat(radius), type)
    }
  }

  const getCurrentLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setLatitude(position.coords.latitude.toFixed(6))
          setLongitude(position.coords.longitude.toFixed(6))
          setErrors({})
        },
        (error) => {
          alert('Error getting location: ' + error.message)
        }
      )
    } else {
      alert('Geolocation is not supported by this browser.')
    }
  }

  return (
    <form onSubmit={handleSubmit} className="search-form">
      <div className="form-grid">
        <div className="form-group">
          <label htmlFor="latitude">Latitude</label>
          <input
            type="number"
            id="latitude"
            value={latitude}
            onChange={(e) => setLatitude(e.target.value)}
            placeholder="e.g., 41.0082"
            step="any"
            className={errors.latitude ? 'error' : ''}
          />
          {errors.latitude && <span className="error-text">{errors.latitude}</span>}
        </div>

        <div className="form-group">
          <label htmlFor="longitude">Longitude</label>
          <input
            type="number"
            id="longitude"
            value={longitude}
            onChange={(e) => setLongitude(e.target.value)}
            placeholder="e.g., 28.9784"
            step="any"
            className={errors.longitude ? 'error' : ''}
          />
          {errors.longitude && <span className="error-text">{errors.longitude}</span>}
        </div>

        <div className="form-group">
          <label htmlFor="radius">Radius (km)</label>
          <input
            type="number"
            id="radius"
            value={radius}
            onChange={(e) => setRadius(e.target.value)}
            placeholder="e.g., 5"
            min="0.1"
            max="50"
            step="0.1"
            className={errors.radius ? 'error' : ''}
          />
          {errors.radius && <span className="error-text">{errors.radius}</span>}
        </div>

        <div className="form-group">
          <label htmlFor="type">Place Type</label>
          <select
            id="type"
            value={type}
            onChange={(e) => setType(e.target.value)}
            className="type-select"
          >
            {placeTypes.map((option) => (
              <option key={option.value} value={option.value}>
                {option.label}
              </option>
            ))}
          </select>
        </div>
      </div>

      <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
        <button
          type="submit"
          className="search-button"
          disabled={loading}
        >
          {loading ? (
            <>
              <Search className="loading-icon" />
              Searching...
            </>
          ) : (
            <>
              <Search size={20} />
              Find Places
            </>
          )}
        </button>

        <button
          type="button"
          onClick={getCurrentLocation}
          style={{
            background: 'transparent',
            border: '2px solid #3498db',
            color: '#3498db',
            padding: '15px 20px',
            borderRadius: '8px',
            cursor: 'pointer',
            display: 'flex',
            alignItems: 'center',
            gap: '8px',
            fontWeight: '600'
          }}
        >
          <MapPin size={20} />
          Use My Location
        </button>
      </div>
    </form>
  )
}

export default SearchForm 