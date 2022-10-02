import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import './App.css';

function App() {

  const [message, setMessage] = useState("")

  useEffect(() => {
    sayMyName();
  }, []);

  const sayMyName = () => {
    fetch('/hello?myName=React App')
      .then(response => response.text())
      .then(message => {
        setMessage(message);
      })
  }

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          {message}
          Test test
        </p>
      </header>
    </div>
  );
}

export default App;
