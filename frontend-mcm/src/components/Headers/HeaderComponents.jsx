import React from "react";
import { useNavigate } from "react-router-dom";
import Logo from "../../images/logo.jpg";
import styled from "styled-components";

function HeaderComponents() {
   const navigate = useNavigate();
   const handleClick = () => {
      navigate("/main");
   };

   const handleLogout = () => {
      localStorage.removeItem("token");
      navigate("/");
   };

   return (
      <div>
         <NavStyle>
            <header className="header">
               <div className="header_izq">
                  <div className="logo" onClick={handleClick}>
                     <img style={{ width: "100px" }} id="Logo" src={Logo} alt="Logo" />
                  </div>
                  <div className="dropdown">
                     <a className="btn" href="/clientes">
                        Clientes
                     </a>
                     <div className="dropdown-menu">
                        <a className="dropdown-item" href="/clientes/crear">
                           Crear Cliente
                        </a>
                        <a className="dropdown-item" href="/clientes/cargaMasivaClientes">
                           Carga Masiva
                        </a>
                     </div>
                  </div>
                  <div className="dropdown">
                     <a className="btn" href="/proveedores">
                        Proveedores
                     </a>
                     <div className="dropdown-menu">
                        <a className="dropdown-item" href="/proveedores/crear">
                           Crear Proveedor
                        </a>
                        <a className="dropdown-item" href="/proveedores/cargaMasivaProveedores">
                           Carga Masiva
                        </a>
                     </div>
                  </div>
                  <div className="dropdown">
                     <a className="btn" href="/productos">
                        Inventario
                     </a>
                     <div className="dropdown-menu">
                        <a className="dropdown-item" href="/productos/crear">
                           Crear Producto
                        </a>
                        <a className="dropdown-item" href="/productos/cargaMasivaProductos">
                           Carga Masiva
                        </a>
                     </div>
                  </div>
                  <div className="dropdown">
                     <a className="btn" href="/oc">
                        Ordenes de Compra
                     </a>
                     <div className="dropdown-menu">
                        <a className="dropdown-item" href="/oc/cliente">
                           OC Clientes
                        </a>
                        <a className="dropdown-item" href="/oc/proveedor">
                           OC Proveedores
                        </a>
                     </div>
                  </div>
                  <div className="dropdown">
                     <a className="btn" href="/estadistica">
                        Estadísticas
                     </a>
                     <div className="dropdown-menu">
                        <a className="dropdown-item" href="/estadistica/generales">
                           Ventas Generales
                        </a>
                        <a className="dropdown-item" href="/estadistica/productos">
                           Ventas de Productos
                        </a>
                     </div>
                  </div>
                  <div className="dropdown">
                     <a className="btn" href="/grafico">
                        Gráficos
                     </a>
                     <div className="dropdown-menu">
                        <a className="dropdown-item" href="/grafico/barchart">
                           Comparativo de Montos
                        </a>
                        <a className="dropdown-item" href="/grafico/ventas-chart">
                           Ventas Históricas
                        </a>
                        <a className="dropdown-item" href="/grafico/ooc-proveedores-chart">
                           Estadísticas OC de Proveedores
                        </a>
                     </div>
                  </div>
                  <div className="dropdown">
                     <a className="btn" href="/ventas">
                        Ventas
                     </a>
                  </div>
                  <div className="dropdown">
                     <a className="btn" href="/cotizaciones">
                        Cotizaciones
                     </a>
                     <div className="dropdown-menu">
                        <a className="dropdown-item" href="/crear-cotizacion">
                           Crear Cotizacion
                        </a>
                        <a className="dropdown-item" href="/cotizaciones/cargaMasivaCotizaciones">
                           Carga Masiva
                        </a>
                     </div>
                  </div>
               </div>
               <div className="header_der">
                  <button className="btn-button" onClick={handleLogout}>
                     Cerrar Sesión
                  </button>
               </div>
            </header>
         </NavStyle>
      </div>
   );
}

export default HeaderComponents;

const NavStyle = styled.nav`
   .header {
      display: flex;
      justify-content: space-between; /* Para separar los elementos a los extremos */
      padding: 10px;
      background-color: #61c9f9;
      color: white;
   }
   .header_izq,
   .header_der {
      display: flex;
      align-items: center;
      text-align: center;
   }
   .header .btn, .header .btn-button{
      display: inline-block;
      padding: 10px 20px;
      color: #ebfcff;
      text-decoration: none; /* Elimina la subrayado del enlace */
      margin-right: 10px;
      font-family: "Pacifico", serif;
      font-weight: bold;
      font-size: 20px;
   }

   .header .btn-button {
      border: 0px;
      background-color: #61c9f9;
   }

   .header .btn:hover, .header .btn-button:hover {
      color: #00375e;
      cursor: pointer;
   }

   .logo:hover {
      cursor: pointer;
   }

   .dropdown,
   .dropdown-2 {
      position: relative;
      display: subgrid;
      justify-content: center;
   }
   .dropdown-menu,
   .dropdown-menu-2 {
      display: none; /* Oculta el menú por defecto */
      flex-direction: column;
      position: absolute;
      top: 100%; /* Posiciona el menú justo debajo del botón */
      left: 0;
      background-color: #0193c0;
      box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
      z-index: 1000;
   }
   .dropdown:hover .dropdown-menu,
   .dropdown-2:hover .dropdown-menu-2 {
      display: flex; /* Muestra el menú cuando se pasa el mouse sobre .dropdown */
   }
   .dropdown-item,
   .dropdown-item-2 {
      padding: 10px 20px;
      color: #ebfcff;
      text-decoration: none;
      font-family: "Pacifico", serif;
      font-weight: bold;
      font-size: 20px;
      white-space: nowrap;
   }
   .dropdown-item:hover,
   .dropdown-item-2:hover {
      background-color: #00375e;
      color: white;
   }

   .dropdown-menu-2 {
      left: 100%;
   }
`;
