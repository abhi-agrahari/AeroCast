import React, { useState } from "react";
import "../styles/Home.css";

function Home() {
  const [city, setCity] = useState("");
  const [suggestions, setSuggestions] = useState([]);
  const [weather, setWeather] = useState(null);

  const getWeather = async (selectedCity) => {
    const cityName = selectedCity || city;
    if (!cityName) return;

    const response = await fetch(`http://localhost:8080/api/weather/${cityName}`);
    const data = await response.json();
    setWeather(data);
    setSuggestions([]);
  };

  const fetchSuggestions = async (input) => {
    setCity(input);
    if (input.length < 2) {
      setSuggestions([]);
      return;
    }

    const apiKey = "67374f067cd9c6517ffc50939cb50aa7";
    const response = await fetch(
      `https://api.openweathermap.org/geo/1.0/direct?q=${input}&limit=5&appid=${apiKey}`
    );
    const data = await response.json();

    setSuggestions(data.map((c) => c.name));
  };

  return (
    <div className="home-container">
      <h1 className="title">AeroCast</h1>

      <div className="search-container">
        <input
          type="text"
          placeholder="Enter city name"
          value={city}
          onChange={(e) => fetchSuggestions(e.target.value)}
        />
        {suggestions.length > 0 && (
          <ul className="suggestions">
            {suggestions.map((s, index) => (
              <li key={index} onClick={() => getWeather(s)}>
                {s}
              </li>
            ))}
          </ul>
        )}
        <button onClick={() => getWeather()}>Get Weather</button>
      </div>

      {weather && (
        <>
          <div className="main-card">
            <h2>{weather.city}</h2>
            <p>{weather.description}</p>
          </div>

          <div className="sub-cards">
            <div className="sub-card">
              <h3>Temp</h3>
              <p>{weather.temperature}°C</p>
            </div>
            <div className="sub-card">
              <h3>Humidity</h3>
              <p>{weather.humidity}%</p>
            </div>
            <div className="sub-card">
              <h3>Wind</h3>
              <p>{weather.windSpeed} m/s</p>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default Home;
