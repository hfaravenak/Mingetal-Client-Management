import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MainComponents from "./components/MainComponents";
import ClienteComponents from "./components/ClienteComponents";

function App() {
  return (
      <div>
          <Router>
              <Routes>
                  <Route path="/" element={<MainComponents/>} />
                  <Route path="/clientes" element={<ClienteComponents/>} />
              </Routes>
          </Router>
      </div>
  );
}

export default App;
