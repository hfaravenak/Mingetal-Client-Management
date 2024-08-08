import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { useAuth } from "../../services/AuthProvider";
import { ProtectedRoute } from "./ProtectedRoute";

import LoginComponents from "../Authentication/LoginComponents";
import PasswordChangeComponents from "../Authentication/PasswordChangeComponents";

import MainComponents from "../MainComponents";
import NotFoundComponent from "../NotFoundComponent";

import ListClienteComponents from "../Clientes/ListClienteComponents";
import ClienteComponents from "../Clientes/ClienteComponents";
import ClienteCrearComponents from "../Clientes/ClienteCrearComponents";
import CargaMasivaClientesComponents from "../Clientes/CargaMasivaClientesComponents";

import ListProveedorComponents from "../Proveedores/ListProveedorComponents";
import ProveedorComponents from "../Proveedores/ProveedorComponents";
import ProveedorCrearComponent from "../Proveedores/ProveedorCrearComponent";
import CargaMasivaProveedoresComponents from "../Proveedores/CargaMasivaProveedoresComponents";

import MainOCComponents from "../Ordenes de Compra/MainOCComponents";

import ListOCClienteComponents from "../Ordenes de Compra/Cliente/ListOCClienteComponents";
import OCClienteComponents from "../Ordenes de Compra/Cliente/OCClienteComponents";
import OCClienteCrearComponents from "../Ordenes de Compra/Cliente/OCClienteCrearComponents";

import ListOCProveedorComponents from "../Ordenes de Compra/Proveedor/ListOCProveedorComponents";
import OCProveedorComponents from "../Ordenes de Compra/Proveedor/OCProveedorComponents";
import OCProveedorCrearComponents from "../Ordenes de Compra/Proveedor/OCProveedorCrearComponents";

import ListCotizacionComponents from "../Cotizaciones/ListCotizacionComponents";
import CotizacionComponents from "../Cotizaciones/CotizacionComponents";
import CotizacionCrearComponents from "../Cotizaciones/CotizacionCrearComponents";
import CargaMasivaCotizacionesComponents from "../Cotizaciones/CargaMasivaCotizacionesComponents"

import ListProductosComponents from "../Productos/ListProductosComponents";
import ProductoComponents from "../Productos/ProductoComponents";
import ProductoCrearComponents from "../Productos/ProductoCrearComponents";
import CargaMasivaProductosComponents from "../Productos/CargaMasivaProductosComponents";

import MainEstadisticaComponents from "../Estadisticas/MainEstadisticaComponents";
import EstadisticaGeneralesComponents from "../Estadisticas/EstadisticaGeneralesComponents";
import EstadisticaProductosComponents from "../Estadisticas/EstadisticaProductosComponents";

import MainGraficosComponents from "../Graficos/MainGraficosComponents";
import GraficoVentasComponents from "../Graficos/GraficoVentasComponents";
import GraficoOOCProveedoresComponents from "../Graficos/GraficoOOCProveedoresComponents";
import BarChartComponents from "../Graficos/BarChartComponents";

import ListVentasComponents from "../Ventas/ListVentasComponents";


const Routes = () => {
    const { token } = useAuth();

    const routesForPublic = [
        {
          path: "/",
          element: <LoginComponents />,
        },
        {
          path: "/changePass/:email",
          element: <PasswordChangeComponents />,
        },
        {
          path: "*", // Ruta para manejar errores
          element: <NotFoundComponent />, // Un componente para mostrar un mensaje de error 404
        },
    ]
    
    // Define routes accessible only to authenticated users
    const routesForAuthenticatedOnly = [
      {
        path: "/",
        element: <ProtectedRoute />, // Wrap the component in ProtectedRoute
        children: [
            {
                path: "/main",
                element: <MainComponents />
              },
              {
                path: "/clientes",
                element: <ListClienteComponents />
              },
              {
                path: "/clientes/mas info/:cliente",
                element: <ClienteComponents />
              },
              {
                path: "/clientes/crear",
                element: <ClienteCrearComponents />
              },
              {
                path: "/clientes/cargaMasivaClientes",
                element: <CargaMasivaClientesComponents />
              },
              {
                path: "/proveedores",
                element: <ListProveedorComponents />
              },
              {
                path: "/proveedores/mas info/:proveedor",
                element: <ProveedorComponents />
              },
              {
                path: "/proveedores/crear",
                element: <ProveedorCrearComponent />
              },
              {
                path: "/proveedores/cargaMasivaProveedores",
                element: <CargaMasivaProveedoresComponents />
              },
              {
                path: "/oc",
                element: <MainOCComponents />
              },
              {
                path: "/oc/cliente",
                element: <ListOCClienteComponents />
              },
              {
                path: "/oc/cliente/mas info/:oc_cliente",
                element: <OCClienteComponents />
              },
              {
                path: "/oc/cliente/crear",
                element: <OCClienteCrearComponents />
              },
              {
                path: "/oc/proveedor",
                element: <ListOCProveedorComponents />
              },
              {
                path: "/oc/proveedor/mas info/:oc_proveedor",
                element: <OCProveedorComponents />
              },
              {
                path: "/oc/proveedor/crear",
                element: <OCProveedorCrearComponents />
              },
              {
                path: "/cotizaciones",
                element: <ListCotizacionComponents />
              },
              {
                path: "/info-cotizacion/:cotizacion",
                element: <CotizacionComponents />
              },
              {
                path: "/crear-cotizacion",
                element: <CotizacionCrearComponents />
              },
              {
                path: "/cotizaciones/cargaMasivaCotizaciones",
                element: <CargaMasivaCotizacionesComponents />
              },
              {
                path: "/productos",
                element: <ListProductosComponents />
              },
              {
                path: "/productos/mas-info/:producto",
                element: <ProductoComponents />
              },
              {
                path: "/productos/crear",
                element: <ProductoCrearComponents />
              },
              { path: "/productos/cargaMasivaProductos",
                element: <CargaMasivaProductosComponents />
              },
              {
                path: "/estadistica",
                element: <MainEstadisticaComponents />
              },
              {
                path: "/estadistica/generales",
                element: <EstadisticaGeneralesComponents />
              },
              {
                path: "/estadistica/productos",
                element: <EstadisticaProductosComponents />
              },
              {
                path: "/grafico",
                element: <MainGraficosComponents />
              },
              {
                path: "/grafico/ventas-chart",
                element: <GraficoVentasComponents />
              },
              {
                path: "/grafico/ooc-proveedores-chart",
                element: <GraficoOOCProveedoresComponents />
              },
              {
                path: "/grafico/barchart",
                element: <BarChartComponents />
              },
              {
                path: "/ventas",
                element: <ListVentasComponents />
              },
        ],
      },
      {
        path: "*", // Ruta para manejar errores en las rutas protegidas
        element: <NotFoundComponent />, // Un componente para mostrar un mensaje de error 404
      },
    ];
  
  
    // Combine and conditionally include routes based on authentication status
    const router = createBrowserRouter([
      ...routesForPublic,  
      ...(!token ? [] : routesForAuthenticatedOnly),
    ]);
  
    // Provide the router configuration using RouterProvider
    return <RouterProvider router={router} />;
  };
  
  export default Routes;