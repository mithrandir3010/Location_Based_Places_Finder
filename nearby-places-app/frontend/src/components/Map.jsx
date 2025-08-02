import React, { useEffect, useRef } from 'react'
import L from 'leaflet'

// Fix for default markers
delete L.Icon.Default.prototype._getIconUrl
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png',
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
})

const Map = ({ places, searchParams }) => {
  const mapRef = useRef(null)
  const mapInstanceRef = useRef(null)

  useEffect(() => {
    if (!mapRef.current) return

    // Initialize map
    if (!mapInstanceRef.current) {
      const centerLat = searchParams ? searchParams.latitude : 41.0082
      const centerLng = searchParams ? searchParams.longitude : 28.9784

      mapInstanceRef.current = L.map(mapRef.current).setView([centerLat, centerLng], 13)

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
      }).addTo(mapInstanceRef.current)
    }

    // Clear existing markers
    mapInstanceRef.current.eachLayer((layer) => {
      if (layer instanceof L.Marker || layer instanceof L.Circle) {
        mapInstanceRef.current.removeLayer(layer)
      }
    })

    // Add search center and radius circle
    if (searchParams) {
      const centerMarker = L.marker([searchParams.latitude, searchParams.longitude])
        .addTo(mapInstanceRef.current)
        .bindPopup('Search Center')

      const radiusCircle = L.circle([searchParams.latitude, searchParams.longitude], {
        color: '#3498db',
        fillColor: '#3498db',
        fillOpacity: 0.1,
        radius: searchParams.radius * 1000 // Convert km to meters
      }).addTo(mapInstanceRef.current)
    }

    // Add place markers
    places.forEach((place) => {
      const marker = L.marker([place.latitude, place.longitude])
        .addTo(mapInstanceRef.current)
        .bindPopup(`
          <div style="min-width: 200px;">
            <h3 style="margin: 0 0 8px 0; color: #2c3e50;">${place.name}</h3>
            <p style="margin: 0 0 8px 0; color: #7f8c8d; font-size: 0.9em;">
              📍 ${place.address}
            </p>
            <p style="margin: 0 0 8px 0; color: #f39c12; font-weight: 600;">
              ⭐ ${place.rating ? `${place.rating}/5` : 'No rating'}
            </p>
            ${place.types && place.types.length > 0 ? `
            <p style="margin: 0; color: #3498db; font-size: 0.8em;">
              🏷️ ${place.types.join(', ')}
            </p>
            ` : ''}
          </div>
        `)
    })

    // Fit bounds to show all markers
    if (places.length > 0) {
      const group = new L.featureGroup(places.map(place => 
        L.marker([place.latitude, place.longitude])
      ))
      mapInstanceRef.current.fitBounds(group.getBounds().pad(0.1))
    }

    return () => {
      if (mapInstanceRef.current) {
        mapInstanceRef.current.remove()
        mapInstanceRef.current = null
      }
    }
  }, [places, searchParams])

  return (
    <div className="map-container">
      <h2>Map View</h2>
      <div ref={mapRef} style={{ height: '100%', width: '100%' }} />
    </div>
  )
}

export default Map 