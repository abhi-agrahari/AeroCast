import { useState } from 'react'
import SearchBar from './components/SearchBar'
import WeatherCard from './components/WeatherCard'

export default function App(){
const [city,setCity]=useState('')
return ( <div className="app"> <header className="header"> <h1>AeroCast</h1> </header> <main> <SearchBar onSearch={setCity}/> <WeatherCard city={city}/> </main> </div>
)
}
