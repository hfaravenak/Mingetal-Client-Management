import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import HeaderComponents from "../Headers/HeaderComponents";
import estadistica from "../../images/estadistica.png";
import atras from "../../images/atras.png";

function MainEstadisticaComponents() {
   const navigate = useNavigate();

   const handleClickBarChart = () => {
      navigate("/estadistica/barchart");
   };

   const handleClickGraficoVentas = () => {
      navigate("/estadistica/ventas-chart");
   };

   const handleClickGraficoProveedores = () => {
      navigate("/estadistica/ooc-proveedores-chart");
   };

   const regresar = () => {
      navigate(`/estadistica`);
   };

   return (
      <div>
         <NavStyle>
            <HeaderComponents></HeaderComponents>
            <div className="container-create">
               <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} style={{ width: "35px" }} />
            </div>
            <div className="container_main">
               <div className="card" onClick={handleClickBarChart}>
                  <img id="estadistica" src={estadistica} alt="BarChart" />
                  <h2>Comparación de Ventas por mes y su símil de años anteriores</h2>
               </div>
               <div className="card" onClick={handleClickGraficoVentas}>
                  <img id="estadistica" src={estadistica} alt="Historico" />
                  <h2>Histórico Ventas</h2>
               </div>
               <div className="card" onClick={handleClickGraficoProveedores}>
                  <img id="estadistica" src={estadistica} alt="Estatisticas-proveedor" />
                  <h2>Estadísticas de Órdenes de compra de Proveedores</h2>
               </div>
            </div>
         </NavStyle>
      </div>
   );
}

export default MainEstadisticaComponents;

const NavStyle = styled.nav`
   .container-create {
      margin: 2%;
      margin-bottom: 0;
      padding: 1%;
      padding-bottom: 0;
      border: 2px solid #d5d5d5;
      border-bottom: 0;
      background-color: #f0f0f0;
   }
   .img-back:hover {
      cursor: pointer;
   }

   .container_main {
      display: flex;
      justify-content: center;
      align-items: center;
      flex-wrap: wrap;
      margin: 2%;
      margin-top: 0;
      padding: 2%;
      padding-top: 0;
      border: 2px solid #d5d5d5;
      border-top: 0;
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
   .card img {
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
`;
