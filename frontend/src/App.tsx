import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import './App.css';

function App() {

  const [message, setMessage] = useState("")
  const [transcripts, setTranscripts] = useState()

  useEffect(() => {
    sayMyName();
    voiceFlowAPI();
  }, []);

  const sayMyName = () => {
    fetch('/hello?myName=React App')
      .then(response => response.text())
      .then(message => {
        setMessage(message);
      })
  }

  const voiceFlowAPI = () => {
    // Simple GET request using fetch
    fetch('https://warm-everglades-89279.herokuapp.com/https://api-dm-test.voiceflow.fr/exportraw/VF.DM.6331204c9575ca00085c3fee.3xaArTcugE4obpnp?versionID=632b79c564484143a984b02e')
        .then(response => response.json())
        .then(data => console.log(data));
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
