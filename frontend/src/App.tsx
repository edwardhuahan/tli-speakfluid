import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import './App.css';
import Navbar from './components/Navbar'
import AnalyticsPage from './Pages/AnalyticsPage/AnalyticsBoard'
type Transcript = {
  turnID: string,
  startTime: string
}

function App() {

  return (
    <div className="App">
      {/* <Navbar /> */}
      <AnalyticsPage />
    </div>
  );
}

export default App;
