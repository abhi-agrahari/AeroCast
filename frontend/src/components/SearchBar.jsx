import React, { useState } from "react";

function SearchBar({ city, setCity, fetchWeather }) {
  const [suggestions, setSuggestions] = useState([]);

  // Fetch city name suggestions
  const fetchSuggestions = async (input) => {
    setCity(input);
    if (input.length < 2) return setSuggestions([]);
    const apiKey = "67374f067cd9c6517ffc50939cb50aa7";
    try {
      const res = await fetch(
        `https://api.openweathermap.org/geo/1.0/direct?q=${input}&limit=5&appid=${apiKey}`
      );
      const data = await res.json();
      setSuggestions(data.map((c) => c.name));
    } catch (err) {
      console.error("Error fetching suggestions:", err);
    }
  };

  // Handle when user clicks on a suggested city
  const handleSelectCity = (selectedCity) => {
    setCity(selectedCity);      // fill selected city into input
    setSuggestions([]);         // hide suggestion dropdown
    fetchWeather(selectedCity); // immediately fetch weather
  };

  // Handle when user clicks "Get Weather"
  const handleGetWeather = () => {
    fetchWeather(city);
    setSuggestions([]); // hide dropdown when button clicked
  };

  return (
    <div className="search-section">
      <input
        type="text"
        placeholder="Enter city name"
        value={city}
        onChange={(e) => fetchSuggestions(e.target.value)}
      />
      <button onClick={handleGetWeather}>Get Weather</button>

      {suggestions.length > 0 && (
        <ul className="suggestions">
          {suggestions.map((s, i) => (
            <li key={i} onClick={() => handleSelectCity(s)}>
              {s}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default SearchBar;
