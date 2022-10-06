import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import './App.css';

function App() {

  const [message, setMessage] = useState("")
  const [transcripts, setTranscripts] = useState()
  const [name, setName] = useState("")
  let parsedTranscripts = []

  useEffect(() => {
    sayMyName();
    voiceFlowAPI();
  }, []);

  const sayMyName = () => {
    fetch('/hello?myName=' + name)
      .then(response => response.text())
      .then(message => {
        setMessage(message);
      })
  }

  const voiceFlowAPI = () => {
    // Simple GET request using fetch
    fetch('https://warm-everglades-89279.herokuapp.com/https://api-dm-test.voiceflow.fr/exportraw/VF.DM.6331204c9575ca00085c3fee.3xaArTcugE4obpnp?versionID=632b79c564484143a984b02e')
      .then(response => response.json())
      .then(data => { setTranscripts([...data]) });
  }

  const handleChange = (event) => {
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
        <p>
          {transcripts && transcripts[0].map(t => {
            return (
              <li>
                <h5>turnID: {t.turnID}</h5>
                <h5>startTime: {t.startTime}</h5>
              </li>
            );
          })
          }
        </p>
      </header>
    </div>
  );
}

export default App;
