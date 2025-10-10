import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import EmailPreferences from "./pages/EmailPreferences";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/preferences" element={<EmailPreferences />} />
      </Routes>
    </Router>
  );
}

export default App;
