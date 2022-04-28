import React from 'react';
import ReactDOM from 'react-dom/client';
import './styles/global.css';

import App from './App';
import { BrowserRouter } from "react-router-dom";


const rootElement = ReactDOM.createRoot(document.getElementById("root"));
rootElement.render(
  <React.StrictMode>
      <BrowserRouter>
          <App />
      </BrowserRouter>
  </React.StrictMode>
);
