import React, { useEffect, useState } from 'react';
import './styles/App.css';
import ClippersDrawer from './Pages/ClipperDrawer'

type Transcript = {
  turnID: string,
  startTime: string
}

function App() {

  return (
    <div className="App">
      {/* <Home /> */}
      <ClippersDrawer />
    </div>
  );
}

export default App;
