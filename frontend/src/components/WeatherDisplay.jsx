import React, { useState } from "react";

function WeatherDisplay({ weather, forecast, showForecast, getForecast, addFavorite }) {
  const { city, description, temperature, humidity, windSpeed } = weather;
  const [unit, setUnit] = useState("C");

  const convertTemp = (tempC) => (unit === "C" ? tempC : (tempC * 9) / 5 + 32);

  return (
    <div className="weather-section">
      {/* Weather Header Box */}
      <div className="weather-box">
        <div className="weather-header">
          <h2 className="city-name">{city}</h2>
          <button className="fav-btn" onClick={() => addFavorite(city)}>
            ⭐ Add to Favorite
          </button>
        </div>

        <div className="weather-subheader">
          <p className="description">🌤️ {description}</p>

          <div className="unit-toggle">
            <label className="switch">
              <input
                type="checkbox"
                checked={unit === "F"}
                onChange={() => setUnit(unit === "C" ? "F" : "C")}
              />
              <span className="slider"></span>
            </label>
            <span className="unit-label">
              {unit === "C" ? "°C" : "°F"}
            </span>
          </div>
        </div>
      </div>

      {/* Weather Stats */}
      <div className="stats-row">
        <div className="stat-box">
          <h3>🌡️ Temp</h3>
          <p>{convertTemp(temperature).toFixed(1)}°{unit}</p>
        </div>
        <div className="stat-box">
          <h3>💧 Humidity</h3>
          <p>{humidity}%</p>
        </div>
        <div className="stat-box">
          <h3>🌬️ Wind</h3>
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
              <p>{convertTemp(day.temp).toFixed(1)}°{unit}</p>
              <p>{day.description}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default WeatherDisplay;
