import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import './App.css';

function App() {

  const [message, setMessage] = useState("")

  const [name, setName] = useState("")

  const sayMyName = () => {
    fetch('/hello?myName=' + name)
      .then(response => response.text())
      .then(message => {
        setMessage(message);
      })
  }

  const handleChange = (event: any) => {
    setName(event.target.value);
  }

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <input 
        onChange={handleChange}
        ></input>

        <button onClick={sayMyName}> Enter </button>
        <p>
          {message}
        </p>
      </header>
    </div>
  );
}

export default App;
