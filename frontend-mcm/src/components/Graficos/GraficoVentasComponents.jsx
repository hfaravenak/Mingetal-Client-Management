import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { Line } from "react-chartjs-2";
import "chart.js/auto"; // Necesario para que funcione con la versión 3 de chart.js
import HeaderComponents from "../Headers/HeaderComponents";
import ventasService from "../../services/VentasService";
import atras from "../../images/atras.png";

const monthNames = ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"];

function GraficoVentasComponents() {
   const navigate = useNavigate();
   const [ventasData, setVentasData] = useState([]);

   useEffect(() => {
      ventasService
         .getVentasByMonth()
         .then((response) => {
            const sortedData = response.data.sort((a, b) => {
               const dateA = new Date(a[3], a[2] - 1); // Año y mes de a
               const dateB = new Date(b[3], b[2] - 1); // Año y mes de b
               return dateA - dateB;
            });
            setVentasData(sortedData);
         })
         .catch((error) => {
            console.error("Error fetching sales data", error);
         });
   }, []);

   const prepareMontosChartData = () => {
      if (ventasData.length === 0) {
         return {
            labels: [],
            datasets: [],
         };
      }

      const labels = ventasData.map((data) => `${data[3]}-${monthNames[data[2] - 1]}`); // Año-Mes con abreviatura

      const montos = ventasData.map((data) => data[0]); // Monto vendido por mes

      return {
         labels: labels,
         datasets: [
            {
               label: "Monto Vendido por Mes",
               data: montos,
               fill: false,
               backgroundColor: "rgba(75,192,192,0.4)",
               borderColor: "rgba(75,192,192,1)",
            },
         ],
      };
   };

   const prepareCantidadesChartData = () => {
      if (ventasData.length === 0) {
         return {
            labels: [],
            datasets: [],
         };
      }

      const labels = ventasData.map((data) => `${data[3]}-${monthNames[data[2] - 1]}`); // Año-Mes con abreviatura

      const cantidades = ventasData.map((data) => data[1]); // Cantidad de ventas por mes

      return {
         labels: labels,
         datasets: [
            {
               label: "Cantidad de Ventas por Mes",
               data: cantidades,
               fill: false,
               backgroundColor: "rgba(153,102,255,0.4)",
               borderColor: "rgba(153,102,255,1)",
            },
         ],
      };
   };

   const options = {
      scales: {
         y: {
            beginAtZero: true,
         },
      },
   };

   const regresar = () => {
      navigate(`/grafico`);
   };

   return (
      <div>
         <NavStyle>
            <HeaderComponents />
            <div className="container-create">
               <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} style={{ width: "35px" }} />
            </div>
            <div className="container_main">
               <h2>Ventas Históricas</h2>
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

export default GraficoVentasComponents;

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
      flex-direction: column;
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
      width: 100%; /* Asegurar que el contenedor del gráfico ocupe el 100% del espacio disponible */
      max-width: 1000px; /* Tamaño máximo de los gráficos */
      margin: 20px 0;
   }
`;
