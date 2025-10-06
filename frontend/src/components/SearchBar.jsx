import { useState } from 'react'

export default function SearchBar({onSearch}){
  const [v,setV]=useState('')
  const submit=e=>{
    e.preventDefault()
    if(!v) return
    onSearch(v)
  }
  return (
    <form onSubmit={submit} className="search">
      <input value={v} onChange={e=>setV(e.target.value)} placeholder="Search city"/>
      <button type="submit">Search</button>
    </form>
  )
}
