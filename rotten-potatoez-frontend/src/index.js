import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import axios from "axios";

axios.defaults.baseURL = "https://rotten-potatoez-backend.herokuapp.com/api/v1/";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <App />
  </React.StrictMode>
);
