import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route, } from "react-router-dom";

/***ASSETS***/

/***COMPONENTS***/

/***PAGES***/
import Home from './pages/Home';
import AnalyticsPage from './pages/AnalyticsPage';

/***STYLES***/
import './styles/index.css';

/***TYPES***/

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/analytics" element={<AnalyticsPage />} />
        </Routes>
    </BrowserRouter>
  </React.StrictMode>
);
