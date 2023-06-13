import { useState, useEffect, createContext } from 'react';

import Home from "./pages/Home/Home";
import Callback from "./pages/Callback/Callback";
import Login from "./pages/Auth/Login";
import Registration from "./pages/Auth/Registration";
import Orders from "./pages/Orders/Orders";
import Axios from './Axios';

import { Routes, Route } from "react-router-dom";
import { useAuth } from './Hooks/useAuth';

export const UserContext = createContext(null);

function App() {
  const { logIn, signUp, auth0Login, logOut, User } = useAuth();

  return (
    <UserContext.Provider value={ { logIn, signUp, auth0Login, logOut, User } }>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />}/>
          <Route path="/callback" element={<Callback />}/>
          <Route path="/orders" element={<Orders />}/>
          <Route path="/login" element={<Login />}/>
          <Route path="/register" element={<Registration />}/>
        </Routes>
      </div>
    </UserContext.Provider>
  );
}

export default App;
