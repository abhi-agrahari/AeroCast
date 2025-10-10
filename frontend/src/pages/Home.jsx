import React, { useState } from "react";
import "../styles/Home.css";

function Home() {
  const [city, setCity] = useState("");
  const [suggestions, setSuggestions] = useState([]);
  const [weather, setWeather] = useState(null);
  const [forecast, setForecast] = useState([]);
  const [showForecast, setShowForecast] = useState(false);

  const getWeather = async (selectedCity) => {
    const cityName = selectedCity || city;
    if (!cityName) return;

    try {
      const res = await fetch(`http://localhost:8080/api/weather/${cityName}`);
      const data = await res.json();
      setWeather(data);
      setForecast([]);
      setShowForecast(false);
      setSuggestions([]);
    } catch (error) {
      console.error("Weather fetch error:", error);
    }
  };

  const getForecast = async () => {
    if (!weather?.city) return;

    try {
      const res = await fetch(`http://localhost:8080/api/weather/forecast/${weather.city}`);
      const data = await res.json();
      console.log("Forecast:", data); // 👀 check console
      setForecast(data.forecast || []);
      setShowForecast(true);
    } catch (error) {
      console.error("Forecast fetch error:", error);
    }
  };

  const fetchSuggestions = async (input) => {
    setCity(input);
    if (input.length < 2) return setSuggestions([]);

    const apiKey = "67374f067cd9c6517ffc50939cb50aa7";
    const res = await fetch(`https://api.openweathermap.org/geo/1.0/direct?q=${input}&limit=5&appid=${apiKey}`);
    const data = await res.json();
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
            {suggestions.map((s, i) => (
              <li key={i} onClick={() => getWeather(s)}>{s}</li>
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
            <div className="sub-card"><h3>Temp</h3><p>{weather.temperature}°C</p></div>
            <div className="sub-card"><h3>Humidity</h3><p>{weather.humidity}%</p></div>
            <div className="sub-card"><h3>Wind</h3><p>{weather.windSpeed} m/s</p></div>
          </div>

          {!showForecast && (
            <button className="forecast-btn" onClick={getForecast}>
              Check 7-Day Forecast
            </button>
          )}

          {showForecast && forecast.length > 0 && (
            <div className="forecast-container">
              <h3>7-Day Forecast</h3>
              <div className="forecast-grid">
                {forecast.map((f, i) => (
                  <div key={i} className="forecast-card">
                    <p><strong>{f.date}</strong></p>
                    <p>{f.temp}°C</p>
                    <p>{f.description}</p>
                  </div>
                ))}
              </div>
            </div>
          )}
        </>
      )}
    </div>
  );
}

export default Home;
