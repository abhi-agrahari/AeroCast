import { useEffect, useState } from 'react'

export default function WeatherCard({city}){
  const [data,setData]=useState(null)
  useEffect(()=>{
    if(!city) return setData(null)
    const key = import.meta.env.VITE_OPENWEATHER_KEY
    if(!key) return setData({error: 'No API key. Add VITE_OPENWEATHER_KEY to .env'})
    fetch(
      `https://api.openweathermap.org/data/2.5/weather?q=${encodeURIComponent(city)}&units=metric&appid=${key}`
    )
    .then(r=>r.json())
    .then(j=>setData(j))
    .catch(()=>setData({error:'fetch error'}))
  },[city])

  if(!city) return <div className="card">Search a city</div>
  if(!data) return <div className="card">Loading...</div>
  if(data.error) return <div className="card">Error: {data.error}</div>
  if(data.cod && data.cod !== 200) return <div className="card">Error: {data.message}</div>

  return (
    <div className="card">
      <h2>{data.name}{data.sys?.country ? `, ${data.sys.country}` : ''}</h2>
      <div className="row">
        <div className="temp">{Math.round(data.main.temp)}°C</div>
        <div className="cond">{data.weather?.[0]?.main}</div>
      </div>
      <div className="meta">Hum {data.main.humidity}% · Wind {data.wind.speed} m/s</div>
    </div>
  )
}
