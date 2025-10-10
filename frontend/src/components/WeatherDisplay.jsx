import React from "react";

function WeatherDisplay({ weather, forecast, showForecast, getForecast, addFavorite }) {
  const { city, description, temperature, humidity, windSpeed } = weather;

  return (
    <div className="weather-section">
      <div className="weather-box">
        <div className="weather-header">
          <h2>{city}</h2>
          <button className="fav-btn" onClick={() => addFavorite(city)}>
            ⭐ Add to Favorites
          </button>
        </div>
        <p>{description}</p>
      </div>

      <div className="stats-row">
        <div className="stat-box">
          <h3>Temp</h3>
          <p>{temperature}°C</p>
        </div>
        <div className="stat-box">
          <h3>Humidity</h3>
          <p>{humidity}%</p>
        </div>
        <div className="stat-box">
          <h3>Wind Speed</h3>
          <p>{windSpeed} m/s</p>
        </div>
      </div>

      {!showForecast && (
        <button className="forecast-btn" onClick={getForecast}>
          Get 5-Day Forecast
        </button>
      )}

      {showForecast && (
        <div className="forecast-grid">
          {forecast.map((day, i) => (
            <div key={i} className="forecast-card">
              <p><strong>{day.date}</strong></p>
              <p>{day.temp}°C</p>
              <p>{day.description}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default WeatherDisplay;
