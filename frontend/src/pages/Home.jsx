import React, { useState, useEffect } from "react";
import SearchBar from "../components/SearchBar";
import WeatherDisplay from "../components/WeatherDisplay";
import Favorites from "../components/Favorites";
import "../styles/Home.css";

function Home() {
  const [city, setCity] = useState("");
  const [weather, setWeather] = useState(null);
  const [forecast, setForecast] = useState([]);
  const [showForecast, setShowForecast] = useState(false);
  const [favorites, setFavorites] = useState([]);

  const fetchWeather = async (selectedCity) => {
    const cityName = selectedCity || city;
    if (!cityName) return;
    try {
      const res = await fetch(`http://localhost:8080/api/weather/${cityName}`);
      const data = await res.json();
      setWeather(data);
      setShowForecast(false);
    } catch (err) {
      console.error(err);
      alert("Error fetching weather.");
    }
  };

  const fetchForecast = async () => {
    if (!weather?.city) return;
    try {
      const res = await fetch(`http://localhost:8080/api/weather/forecast/${weather.city}`);
      const data = await res.json();
      setForecast(data.forecast || []);
      setShowForecast(true);
    } catch (err) {
      console.error(err);
    }
  };

  const fetchFavorites = async () => {
    const res = await fetch("http://localhost:8080/api/cities");
    const data = await res.json();
    setFavorites(data);
  };

  const addFavorite = async (cityName) => {
    const res = await fetch(`http://localhost:8080/api/cities/${cityName}`, { method: "PUT" });
    if (res.ok) {
      fetchFavorites();
    } else {
      alert("City already exists or invalid.");
    }
  };

  const deleteFavorite = async (cityName) => {
    await fetch(`http://localhost:8080/api/cities/${cityName}`, { method: "DELETE" });
    fetchFavorites();
  };

  useEffect(() => {
    fetchFavorites();
  }, []);

  return (
    <div className="home-container">
      <h1 className="title">AeroCast</h1>

      <SearchBar city={city} setCity={setCity} fetchWeather={fetchWeather} />

      {weather && (
        <WeatherDisplay
          weather={weather}
          forecast={forecast}
          showForecast={showForecast}
          getForecast={fetchForecast}
          addFavorite={addFavorite}
        />
      )}

      <Favorites
        favorites={favorites}
        setCity={setCity}
        fetchWeather={fetchWeather}
        deleteFavorite={deleteFavorite}
      />
    </div>
  );
}

export default Home;
