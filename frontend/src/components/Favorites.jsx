import React from "react";

function Favorites({ favorites, setCity, fetchWeather, deleteFavorite }) {
  return (
    <div className="favorites-section">
      <h3>Favorite Cities</h3>
      {favorites.length === 0 ? (
        <p>No favorite cities yet.</p>
      ) : (
        <ul>
          {favorites.map((f) => (
            <li key={f.id} className="favorite-item">
              <span>{f.cityName}</span>
              <div>
                <button onClick={() => { setCity(f.cityName); fetchWeather(f.cityName); }}>Check Weather</button>
                <button className="delete-btn" onClick={() => deleteFavorite(f.cityName)}>Delete</button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default Favorites;
