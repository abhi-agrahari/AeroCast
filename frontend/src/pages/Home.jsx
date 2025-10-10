import React, { useState, useEffect } from "react";
import SearchBar from "../components/SearchBar";
import WeatherDisplay from "../components/WeatherDisplay";
import Favorites from "../components/Favorites";
import "../styles/Home.css";
import { useNavigate } from "react-router-dom";
import toast, { Toaster } from 'react-hot-toast';


function Home() {
  const [city, setCity] = useState("");
  const [weather, setWeather] = useState(null);
  const [forecast, setForecast] = useState([]);
  const [showForecast, setShowForecast] = useState(false);
  const [favorites, setFavorites] = useState([]);

  const navigate = useNavigate();

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
      toast.error("Error fetching weather.");
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

  const fetchFavorites = () => {
    const stored = JSON.parse(localStorage.getItem("favorites")) || [];
    setFavorites(stored);
  };

  const addFavorite = (cityName) => {
    if (!cityName) return;
    const stored = JSON.parse(localStorage.getItem("favorites")) || [];
    if (!stored.includes(cityName)) {
      stored.push(cityName);
      localStorage.setItem("favorites", JSON.stringify(stored));
      setFavorites(stored);
    } else {
      toast("City already in favorites!");
    }
  };

  const deleteFavorite = (cityName) => {
    const stored = JSON.parse(localStorage.getItem("favorites")) || [];
    const updated = stored.filter((city) => city !== cityName);
    localStorage.setItem("favorites", JSON.stringify(updated));
    setFavorites(updated);
  };


  useEffect(() => {
    fetchFavorites();
  }, []);

  return (
    <div className="home-container">
      <Toaster />
      <div className="header">
        <h1 className="title">AeroCast</h1>
        <button className="pref-btn" onClick={() => navigate("/preferences")}>
          ⚙️ Email Preferences
        </button>
      </div>

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
