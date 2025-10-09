import React from "react";
import "../styles/WeatherCard.css";

function WeatherCard({ weather }) {
  return (
    <div className="weather-container">
      <div className="main-card">
        <h2>{weather.city}</h2>
        <p className="desc">{weather.description}</p>
      </div>

      <div className="sub-cards">
        <div className="sub-card">
          <h3>🌡️ Temp</h3>
          <p>{weather.temperature}°C</p>
        </div>
        <div className="sub-card">
          <h3>💧 Humidity</h3>
          <p>{weather.humidity}%</p>
        </div>
        <div className="sub-card">
          <h3>💨 Wind</h3>
          <p>{weather.windSpeed} m/s</p>
        </div>
      </div>
    </div>
  );
}

export default WeatherCard;
