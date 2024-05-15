import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MainComponents from "./components/MainComponents";
import ListClienteComponents from "./components/Clientes/ListClienteComponents";
import ClienteComponents from "./components/Clientes/ClienteComponents"
import ClienteCrearComponents from "./components/Clientes/ClienteCrearComponents"
import ListOCClienteComponents from "./components/Ordenes de Compra/Cliente/ListOCClienteComponents";

function App() {
  return (
      <div>
          <Router>
              <Routes>
                  <Route path="/" element={<MainComponents/>} />
                  <Route path="/clientes" element={<ListClienteComponents/>} />
                  <Route path="/clientes/mas info/:cliente" element={<ClienteComponents/>} />
                  <Route path="/clientes/crear" element={<ClienteCrearComponents/>} />
                  <Route path="/oc/cliente" element={<ListOCClienteComponents/>} />
              </Routes>
          </Router>
      </div>
  );
}

export default App;
