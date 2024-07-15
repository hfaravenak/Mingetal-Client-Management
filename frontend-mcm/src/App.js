import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route } from "react-router-dom";

import LoginComponents from "./components/Authentication/LoginComponents";

import MainComponents from "./components/MainComponents";

import ListClienteComponents from "./components/Clientes/ListClienteComponents";
import ClienteComponents from "./components/Clientes/ClienteComponents"
import ClienteCrearComponents from "./components/Clientes/ClienteCrearComponents"
import CargaMasivaClientesComponents from "./components/Clientes/CargaMasivaClientesComponents"

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

import MainGraficosComponents from "./components/Graficos/MainGraficosComponents";
import GraficoVentasComponents from "./components/Graficos/GraficoVentasComponents";
import GraficoOOCProveedoresComponents from "./components/Graficos/GraficoOOCProveedoresComponents";
import BarChartComponents from "./components/Graficos/BarChartComponents";

import ListVentasComponents from "./components/Ventas/ListVentasComponents";
import AuthProvider from "./services/AuthProvider";
import Routes from "./components/Routes";


function App() {
  return (
      <AuthProvider>
        <Routes />
      </AuthProvider>
  );
}

export default App;
