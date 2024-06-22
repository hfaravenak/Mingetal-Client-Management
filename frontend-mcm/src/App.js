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

import ListCotizacionComponents from "./components/Cotizaciones/ListCotizacionComponents";
import CotizacionComponents from "./components/Cotizaciones/CotizacionComponents";
import CotizacionCrearComponents from "./components/Cotizaciones/CotizacionCrearComponents";

import ListProductosComponents from "./components/Productos/ListProductosComponents";
import ProductoComponents from "./components/Productos/ProductoComponents";
import ProductoCrearComponents from "./components/Productos/ProductoCrearComponents";

import MainEstadisticaComponents from "./components/Estadisticas/MainEstadisticaComponents";
import EstadisticaGeneralesComponents from "./components/Estadisticas/EstadisticaGeneralesComponents";
import EstadisticaProductosComponents from "./components/Estadisticas/EstadisticaProductosComponents";
import MainGraficosComponents from "./components/Estadisticas/MainGraficosComponents";
import BarChartComponents from "./components/Estadisticas/BarChartComponents";
import GraficoVentasComponents from "./components/Estadisticas/GraficoVentasComponents";
import GraficoOOCClientesComponents from "./components/Estadisticas/GraficoOOCClientesComponents";
import GraficoOOCProveedoresComponents from "./components/Estadisticas/GraficoOOCProveedoresComponents";

import ListVentasComponents from "./components/Ventas/ListVentasComponents";


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

                  <Route path="/cotizaciones" element={<ListCotizacionComponents/>} />
                  <Route path="/info-cotizacion/:cotizacion" element={<CotizacionComponents/>} />
                  <Route path="/crear-cotizacion" element={<CotizacionCrearComponents/>} />

                  <Route path="/productos" element={<ListProductosComponents/>} />
                  <Route path="/productos/mas-info/:producto" element={<ProductoComponents/>} />
                  <Route path="/productos/crear" element={<ProductoCrearComponents/>} /> 

                  <Route path="/estadistica" element={<MainEstadisticaComponents/>} />  
                  <Route path="/estadistica/generales" element={<EstadisticaGeneralesComponents/>} />  
                  <Route path="/estadistica/productos" element={<EstadisticaProductosComponents/>} />
                  <Route path="/estadistica/main-graficos" element={<MainGraficosComponents/>} />
                  <Route path="/estadistica/barchart" element={<BarChartComponents/>} />
                  <Route path="/estadistica/ventas-chart" element={<GraficoVentasComponents/>} />
                  <Route path="/estadistica/ooc-clientes-chart" element={<GraficoOOCClientesComponents/>} />
                  <Route path="/estadistica/ooc-proveedores-chart" element={<GraficoOOCProveedoresComponents/>} />
                  


                  <Route path="/ventas" element={<ListVentasComponents/>} />  
              </Routes>
          </Router>
      </div>
  );
}

export default App;
