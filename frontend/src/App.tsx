import React, { useEffect, useState } from 'react';
import './styles/App.css';

type Transcript = {
  turnID: string,
  startTime: string
}

function App() {

  return (
    <div className="App">
      <header className="App-header">
        <input
          onChange={handleChange}
        ></input>
        <button onClick={sayMyName}> Enter </button>
        <p>
          {message}
        </p>
        <p>
          {transcripts && transcripts[0].map((t: Transcript) => {
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
