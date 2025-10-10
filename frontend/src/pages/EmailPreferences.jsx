import React, { useState } from "react";
import "../styles/EmailPreferences.css";

function EmailPreferences() {
  const [email, setEmail] = useState("");
  const [city, setCity] = useState("");
  const [enabled, setEnabled] = useState(false);
  const [message, setMessage] = useState("");

  const validateCity = async () => {
    try {
      const res = await fetch(`http://localhost:8080/api/weather/${city}`);
      if (!res.ok) {
        setMessage("❌ Invalid city name");
        return false;
      }
      setMessage("✅ City is valid");
      return true;
    } catch {
      setMessage("❌ Error validating city");
      return false;
    }
  };

  const savePreferences = async () => {
    const valid = await validateCity();
    if (!valid) return;

    const data = { email, city, enabled };
    try {
      const res = await fetch("http://localhost:8080/api/preferences", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
      });

      if (res.ok) setMessage("✅ Preferences saved successfully!");
      else setMessage("❌ Failed to save preferences");
    } catch {
      setMessage("❌ Server error while saving");
    }
  };

  const handleBack = () => {
    window.history.back();
  };

  return (
    <div className="pref-container">
      <div className="pref-card">
        <h2>Enable Email Notification</h2>

        <input
          type="text"
          placeholder="Enter default city"
          value={city}
          onChange={(e) => setCity(e.target.value)}
        />

        <input
          type="email"
          placeholder="Enter email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        {/* ✅ Modern toggle slider */}
        <div className="toggle-container">
          <span className="toggle-label">Turn on email notification</span>
          <label className="switch">
            <input
              type="checkbox"
              checked={enabled}
              onChange={() => setEnabled(!enabled)}
            />
            <span className="slider"></span>
          </label>
        </div>

        {/* Button Container */}
        <div className="button-container">
          <button className="back-button" onClick={handleBack}>
            Back
          </button>
          <button onClick={savePreferences}>Save</button>
        </div>

        {message && <p className="message">{message}</p>}
      </div>
    </div>
  );
}

export default EmailPreferences;