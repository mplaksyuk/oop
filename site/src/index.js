import React from 'react';
import ReactDOM from 'react-dom/client';
import { Auth0Provider } from "@auth0/auth0-react";

import { BrowserRouter } from "react-router-dom";

// Bootstrap CSS
import "bootstrap/dist/css/bootstrap.min.css";
// Bootstrap Bundle JS
import "bootstrap/dist/js/bootstrap.bundle.min";

import './index.css';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Auth0Provider
      domain="dev-csdha7zk8pnnscq8.us.auth0.com"
      clientId="5HaXFIR8LeD2VcT4KbCSpd2zTS6J4FF2"
      authorizationParams={{
        redirect_uri: window.location.origin + '/callback'
      }}>
      <App />
    </Auth0Provider>
  </BrowserRouter>
);
