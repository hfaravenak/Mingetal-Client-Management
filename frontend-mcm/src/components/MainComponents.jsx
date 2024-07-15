import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import clientes from "../images/cliente.png";
import proveedores from "../images/proveedores.png";
import inventario from "../images/inventario.png";
import ordenesCompra from "../images/ordenesCompra.png";
import ventas from "../images/ventas.png";
import estadistica from "../images/estadistica.png";
import cotizacion from "../images/cotizacion.png";
import alerta from "../images/alerta.png";

import HeaderComponents from "./Headers/HeaderComponents";
import AlertaPagoComponents from "./Alertas/AlertaPagoComponents";
import productoService from "../services/ProductoService";

function MainComponents() {
   const navigate = useNavigate();
   const handleClickClientes = () => {
      navigate("/clientes");
   };

   const handleClickOC = () => {
      navigate("/oc");
   };
   const handleClickCotizacion = () => {
      navigate("/cotizaciones");
   };
   const handleClickProductos = () => {
      navigate("/productos");
   };
   const handleClickProveedores = () => {
      navigate("/proveedores");
   };
   const handleClickVentas = () => {
      navigate("/ventas");
   };
   const handleClickEstadisticas = () => {
      navigate("/estadistica");
   };
   const handleClickGraficos = () => {
      navigate("/grafico")
   }

   const [ProductosEntity, setProductosEntity] = useState([]);
   useEffect(() => {
      productoService.getPocoProductos().then((res) => {
         setProductosEntity(res.data);
      });
   }, []);

   const productosVacios = () => {
      let salida = 2;
      if (ProductosEntity.length !== 0) {
         salida = 1;
      }
      ProductosEntity.map((producto) => {
         if (producto.cantidad === 0) {
            salida = 0;
         }
         return null;
      });
      return salida;
   };

   return (
      <div>
         <HeaderComponents></HeaderComponents>
         <NavStyle>
            <AlertaPagoComponents></AlertaPagoComponents>
            <div className="container_main">
               <div className="card" onClick={handleClickClientes}>
                  <img id="clientes" src={clientes} alt="clientes" className="img-card" />
                  <h2>Clientes</h2>
               </div>
               <div className="card" onClick={handleClickProveedores}>
                  <img id="proveedores" src={proveedores} alt="proveedores" className="img-card" />
                  <h2>Proveedores</h2>
               </div>
               <div className="card" onClick={handleClickProductos}>
                  <img id="inventario" src={inventario} alt="inventario" className="img-card" />
                  <div className="card-info">
                     {productosVacios() === 0 ? (
                        <img id="alerta" src={alerta} alt="alerta" className="alerta-icon" style={{ background: "red" }} />
                     ) : productosVacios() === 1 ? (
                        <img id="alerta" src={alerta} alt="alerta" className="alerta-icon" style={{ background: "orange" }} />
                     ) : (
                        <div />
                     )}
                     <h2>Inventario</h2>
                  </div>
               </div>
               <div className="card" onClick={handleClickOC}>
                  <img id="ordenes_compra" src={ordenesCompra} alt="ordenesCompra" className="img-card" />
                  <h2>Ordenes de compra</h2>
               </div>
               <div className="card" onClick={handleClickVentas}>
                  <img id="ventas" src={ventas} alt="ventas" className="img-card" />
                  <h2>Ventas</h2>
               </div>
               <div className="card" onClick={handleClickEstadisticas}>
                  <img id="estadistica" src={estadistica} alt="estadistica" className="img-card" />
                  <h2>Estadísticas</h2>
               </div>
               <div className="card" onClick={handleClickGraficos}>
                  <img id="estadistica" src={estadistica} alt="graficos" className="img-card"/>
                  <h2>Gráficos</h2>
               </div>
               <div className="card" onClick={handleClickCotizacion}>
                  <img id="cotizacion" src={cotizacion} alt="cotizacion" className="img-card" />
                  <h2>Cotizaciones</h2>
               </div>
            </div>
         </NavStyle>
      </div>
   );
}

export default MainComponents;

const NavStyle = styled.nav`
   .container_main {
      display: flex;
      justify-content: space-between;
      align-items: center;
      flex-wrap: wrap;
      margin: 2%;
      padding: 2%;
      border: 2px solid #d5d5d5;
      background-color: #f0f0f0;
   }
   .card {
      background-color: #fff;
      display: flex;
      flex-direction: column;
      align-items: center;
      margin: 10px;
      padding: 10px;
      width: 250px;
      height: 250px;
      border: 1px solid black;
      border-radius: 50px;
      transition: transform 0.5s ease;
   }
   .img-card {
      width: 50%;
      height: 50%;
      object-fit: cover;
      margin-bottom: 10px;
   }
   .card h2 {
      font-size: 20px;
      font-weight: bold;
      text-align: center;
      margin: 0;
      padding: 20px;
   }

   .card:hover {
      transform: scale(1.1);
      cursor: pointer;
   }

   .card-info {
      display: flex;
      align-items: center;
   }

   .alerta-icon {
      width: 20px;
      height: 20px;
      border: 1px solid black;
      border-radius: 100%;
      padding: 2px;
   }
`;
