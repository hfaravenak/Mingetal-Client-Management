import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { Line } from "react-chartjs-2";
import "chart.js/auto"; // Necesario para que funcione con la versión 3 de chart.js
import HeaderComponents from "../Headers/HeaderComponents";
import ordenesDeCompraProveedorService from "../../services/OrdenesDeCompraProveedorService";
import atras from "../../images/atras.png";

const monthNames = ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"];

function GraficoOOCProveedoresComponents() {
   const navigate = useNavigate();

   const [comprasData, setComprasData] = useState([]);

   useEffect(() => {
      ordenesDeCompraProveedorService
         .getPurchasesByYearAndMonth()
         .then((response) => {
            const sortedData = response.data.sort((a, b) => {
               const dateA = new Date(a[3], a[2] - 1); // Año y mes de a
               const dateB = new Date(b[3], b[2] - 1); // Año y mes de b
               return dateA - dateB;
            });
            setComprasData(sortedData);
         })
         .catch((error) => {
            console.error("Error fetching purchase data", error);
         });
   }, []);

   const prepareMontosChartData = () => {
      if (comprasData.length === 0) {
         return {
            labels: [],
            datasets: [],
         };
      }

      const labels = comprasData.map((data) => `${data[3]}-${monthNames[data[2] - 1]}`); // Año-Mes con abreviatura
      const montos = comprasData.map((data) => data[0]); // Monto total de compras

      return {
         labels: labels,
         datasets: [
            {
               label: "Monto Total de Compras a Proveedores por Mes",
               data: montos,
               fill: false,
               backgroundColor: "rgba(75,192,192,0.4)",
               borderColor: "rgba(75,192,192,1)",
               yAxisID: "y-axis-1",
            },
         ],
      };
   };

   const prepareCantidadesChartData = () => {
      if (comprasData.length === 0) {
         return {
            labels: [],
            datasets: [],
         };
      }

      const labels = comprasData.map((data) => `${data[3]}-${monthNames[data[2] - 1]}`); // Año-Mes con abreviatura
      const cantidades = comprasData.map((data) => data[1]); // Cantidad de compras

      return {
         labels: labels,
         datasets: [
            {
               label: "Cantidad de Compras por Mes",
               data: cantidades,
               fill: false,
               backgroundColor: "rgba(153,102,255,0.4)",
               borderColor: "rgba(153,102,255,1)",
               yAxisID: "y-axis-2",
            },
         ],
      };
   };

   const options = {
      scales: {
         yAxes: [
            {
               id: "y-axis-1",
               type: "linear",
               position: "left",
            },
            {
               id: "y-axis-2",
               type: "linear",
               position: "right",
            },
         ],
      },
   };
   const regresar = () => {
      navigate(`/grafico`);
   }
   return (
      <div>
         <NavStyle>
            <HeaderComponents />
            <div className="container-create">
                  <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} style={{width:"35px"}}/>
               </div>
            <div className="container_main">
               <h2>Estadísticas de OC de Proveedores</h2>
               <div className="charts">
                  <div className="chart">
                     <Line data={prepareMontosChartData()} options={options} width={800} height={400} />
                  </div>
                  <div className="chart">
                     <Line data={prepareCantidadesChartData()} options={options} width={800} height={400} />
                  </div>
               </div>
            </div>
         </NavStyle>
      </div>
   );
}

export default GraficoOOCProveedoresComponents;

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
   .img-back:hover{
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
   .charts {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      width: 100%;
   }
   .chart {
      width: 100%;
      max-width: 1000px;
      margin: 20px 0;
   }
`;
