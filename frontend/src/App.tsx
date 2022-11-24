import React, { useEffect, useState } from 'react';
import './styles/App.css';
import Navbar from './components/NavBar'
import AnalyticsBoard from './pages/AnalyticsPage'

type Transcript = {
  turnID: string,
  startTime: string
}

function App() {
  const [showMenu, setShowMenu] = useState(true)

  const handleToggle = () => {
    setShowMenu(!showMenu)
  }

  return (
    <div className="App">
      <Navbar handleToggle={handleToggle} />
      <AnalyticsBoard showMenu={showMenu} />
    </div>
  );
}

export default App;
