import React, { useState } from 'react'
import SearchForm from './components/SearchForm'
import PlacesList from './components/PlacesList'
import Map from './components/Map'
import Favorites from './components/Favorites'
import Navbar from './components/Navbar'
import { Search, MapPin, Star } from 'lucide-react'
import './App.css'

function App() {
  const [places, setPlaces] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)
  const [searchParams, setSearchParams] = useState(null)
  const [currentPage, setCurrentPage] = useState('search')

  const handleSearch = async (latitude, longitude, radius) => {
    setLoading(true)
    setError(null)
    setSearchParams({ latitude, longitude, radius })

    try {
      const response = await fetch(`/api/nearby?latitude=${latitude}&longitude=${longitude}&radius=${radius}`)
      
      if (!response.ok) {
        throw new Error('Failed to fetch nearby places')
      }

      const data = await response.json()
      setPlaces(data)
    } catch (err) {
      setError(err.message)
      setPlaces([])
    } finally {
      setLoading(false)
    }
  }

  const handlePageChange = (page) => {
    setCurrentPage(page)
  }

  const renderSearchPage = () => (
    <>
      <div className="header">
        <h1>📍 Nearby Places Finder</h1>
        <p>Enter coordinates and radius to discover places around you</p>
      </div>

      <SearchForm onSearch={handleSearch} loading={loading} />

      {error && (
        <div className="error">
          <strong>Error:</strong> {error}
        </div>
      )}

      {loading && (
        <div className="loading">
          <Search className="loading-icon" />
          <p>Searching for nearby places...</p>
        </div>
      )}

      {!loading && places.length > 0 && (
        <div className="results-container">
          <PlacesList places={places} />
          <Map places={places} searchParams={searchParams} />
        </div>
      )}

      {!loading && !error && places.length === 0 && searchParams && (
        <div className="empty-state">
          <MapPin />
          <h3>No places found</h3>
          <p>Try adjusting your search parameters or increasing the radius.</p>
        </div>
      )}

      {!loading && !error && places.length === 0 && !searchParams && (
        <div className="empty-state">
          <Search />
          <h3>Ready to explore?</h3>
          <p>Enter coordinates and radius above to find nearby places.</p>
        </div>
      )}
    </>
  )

  const renderFavoritesPage = () => (
    <Favorites />
  )

  return (
    <div className="app">
      <Navbar currentPage={currentPage} onPageChange={handlePageChange} />
      
      <div className="main-content">
        {currentPage === 'search' ? renderSearchPage() : renderFavoritesPage()}
      </div>
    </div>
  )
}

export default App 