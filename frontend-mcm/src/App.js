import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import MainComponents from "./components/MainComponents";

import ListClienteComponents from "./components/Clientes/ListClienteComponents";
import ClienteComponents from "./components/Clientes/ClienteComponents"
import ClienteCrearComponents from "./components/Clientes/ClienteCrearComponents"

import ListProveedorComponents from "./components/Proveedores/ListProveedorComponents";
import ProveedorComponents from "./components/Proveedores/ProveedorComponents";
import ProveedorCrearComponent from "./components/Proveedores/ProveedorCrearComponent";

import MainOCComponents from "./components/Ordenes de Compra/MainOCComponents";

import ListOCClienteComponents from "./components/Ordenes de Compra/Cliente/ListOCClienteComponents";
import OCClienteComponents from "./components/Ordenes de Compra/Cliente/OCClienteComponents";
import OCClienteCrearComponents from "./components/Ordenes de Compra/Cliente/OCClienteCrearComponents";

import ListOCProveedorComponents from "./components/Ordenes de Compra/Proveedor/ListOCProveedorComponents";
import OCProveedorComponents from "./components/Ordenes de Compra/Proveedor/OCProveedorComponents";
import OCProveedorCrearComponents from "./components/Ordenes de Compra/Proveedor/OCProveedorCrearComponents";

import ListCotizacionComponent from "./components/Cotizaciones/ListCotizacionComponent";
import CotizacionComponent from "./components/Cotizaciones/CotizacionComponent";
import CotizacionCrearComponent from "./components/Cotizaciones/CotizacionCrearComponent";

import ListProductosComponent from "./components/Productos/ListProductosComponents";
import ProductoComponent from "./components/Productos/ProductoComponents";
import ProductoCrearComponent from "./components/Productos/ProductoCrearComponents";

import MainVentasComponents from "./components/Ventas/MainVentasComponents";
import VentasGeneralesComponents from "./components/Ventas/VentasGeneralesComponents";
import VentasProductosComponents from "./components/Ventas/VentasProductosComponents";


function App() {
  return (
      <div>
          <Router>
              <Routes>
                  <Route path="/" element={<MainComponents/>} />

                  <Route path="/clientes" element={<ListClienteComponents/>} />
                  <Route path="/clientes/mas info/:cliente" element={<ClienteComponents/>} />
                  <Route path="/clientes/crear" element={<ClienteCrearComponents/>} />

                  <Route path="/proveedores" element={<ListProveedorComponents/>} />
                  <Route path="/proveedores/mas info/:proveedor" element={<ProveedorComponents/>} />
                  <Route path="/proveedores/crear" element={<ProveedorCrearComponent/>} />

                  <Route path="/oc" element={<MainOCComponents/>} />

                  <Route path="/oc/cliente" element={<ListOCClienteComponents/>} />
                  <Route path="/oc/cliente/mas info/:oc_cliente" element={<OCClienteComponents/>} />
                  <Route path="/oc/cliente/crear" element={<OCClienteCrearComponents/>} />

                  <Route path="/oc/proveedor" element={<ListOCProveedorComponents/>} />
                  <Route path="/oc/proveedor/mas info/:oc_proveedor" element={<OCProveedorComponents/>} />
                  <Route path="/oc/proveedor/crear" element={<OCProveedorCrearComponents/>} />

                  <Route path="/cotizaciones" element={<ListCotizacionComponent/>} />
                  <Route path="/info-cotizacion/:cotizacion" element={<CotizacionComponent/>} />
                  <Route path="/crear-cotizacion" element={<CotizacionCrearComponent/>} />

                  <Route path="/productos" element={<ListProductosComponent/>} />
                  <Route path="/productos/mas-info/:producto" element={<ProductoComponent/>} />
                  <Route path="/productos/crear" element={<ProductoCrearComponent/>} /> 

                  <Route path="/ventas" element={<MainVentasComponents/>} />  
                  <Route path="/ventas/generales" element={<VentasGeneralesComponents/>} />  
                  <Route path="/ventas/productos" element={<VentasProductosComponents/>} />  
              </Routes>
          </Router>
      </div>
  );
}

export default App;
