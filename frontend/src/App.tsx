import React, { useEffect, useState } from 'react';
import './styles/App.css';
import Navbar from './components/Navbar'
import AnalyticsBoard from './Pages/AnalyticsPage/AnalyticsBoard'

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
