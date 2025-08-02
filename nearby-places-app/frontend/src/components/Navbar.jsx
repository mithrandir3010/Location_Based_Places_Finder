import React from 'react'
import { MapPin, Heart } from 'lucide-react'

const Navbar = ({ currentPage, onPageChange }) => {
  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <MapPin size={24} />
        <span>Nearby Places</span>
      </div>
      
      <div className="navbar-links">
        <button
          className={`nav-link ${currentPage === 'search' ? 'active' : ''}`}
          onClick={() => onPageChange('search')}
        >
          <MapPin size={16} />
          Search
        </button>
        
        <button
          className={`nav-link ${currentPage === 'favorites' ? 'active' : ''}`}
          onClick={() => onPageChange('favorites')}
        >
          <Heart size={16} />
          Favorites
        </button>
      </div>
    </nav>
  )
}

export default Navbar 