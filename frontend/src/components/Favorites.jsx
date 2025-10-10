import React from "react";

function Favorites({ favorites, setCity, fetchWeather, deleteFavorite }) {
  return (
    <div className="favorites-section">
      <h2>Favorite Cities</h2>

      {favorites.length === 0 ? (
        <p>No favorite cities yet.</p>
      ) : (
        <ul className="favorites-list">
          {favorites.map((city, index) => (
            <li key={index} className="favorite-item">
              <span className="city-name">{city}</span>

              <div className="fav-actions">
                <button
                  className="check-btn"
                  onClick={() => {
                    setCity(city);
                    fetchWeather(city);
                  }}
                >
                  🌤️ Check Weather
                </button>

                <button
                  className="delete-btn"
                  onClick={() => deleteFavorite(city)}
                >
                  Delete
                </button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default Favorites;
