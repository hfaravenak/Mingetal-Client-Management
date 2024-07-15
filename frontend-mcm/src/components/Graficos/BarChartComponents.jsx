import React, { useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import { useNavigate } from "react-router-dom";
import VentasService from "../../services/VentasService";
import styled from "styled-components";
import HeaderComponents from "../Headers/HeaderComponents";
import atras from "../../images/atras.png";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from "chart.js";

// Register the components with ChartJS
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const BarChartComponents = () => {
   const navigate = useNavigate();
   const [chartDataMonto, setChartDataMonto] = useState({
      labels: [],
      datasets: [],
   });

   const [chartDataVentas, setChartDataVentas] = useState({
      labels: [],
      datasets: [],
   });

   useEffect(() => {
      const fetchData = async () => {
         try {
            const response = await VentasService.getSimilVentasAnteriores();
            const rawdata = response.data;

            if (rawdata && rawdata.length > 0) {
               const uniqueYears = [...new Set(rawdata.map((item) => item[3]))].sort((a, b) => a - b);
               const yearDataMonto = uniqueYears.reduce((acc, year) => {
                  acc[year] = Array(12).fill(0);
                  return acc;
               }, {});
               const yearDataVentas = uniqueYears.reduce((acc, year) => {
                  acc[year] = Array(12).fill(0);
                  return acc;
               }, {});

               // Agrupar datos por Mes y Año
               rawdata.forEach((item) => {
                  const monto = item[0];
                  const ventas = item[1];
                  const mes = item[2] - 1; // Los meses están en rango 1-12, necesitamos 0-11 para indexar
                  const año = item[3];

                  if (yearDataMonto[año]) {
                     yearDataMonto[año][mes] = monto;
                  }
                  if (yearDataVentas[año]) {
                     yearDataVentas[año][mes] = ventas;
                  }
               });

               // datasets de mes a mes
               const colors = ["rgba(255, 99, 132, 0.6)", "rgba(75, 192, 192, 0.6)", "rgba(54, 162, 235, 0.6)"];
               const datasetsMonto = uniqueYears.map((year, index) => ({
                  label: `${year}`,
                  data: yearDataMonto[year],
                  backgroundColor: colors[index % colors.length],
               }));
               const datasetsVentas = uniqueYears.map((year, index) => ({
                  label: `${year}`,
                  data: yearDataVentas[year],
                  backgroundColor: colors[index % colors.length],
               }));

               const labels = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];

               // Actualizo estado del grfico
               setChartDataMonto({
                  labels: labels,
                  datasets: datasetsMonto,
               });
               setChartDataVentas({
                  labels: labels,
                  datasets: datasetsVentas,
               });
            }
         } catch (error) {
            console.error("Error fetching data:", error);
         }
      };

      fetchData();
   }, []);

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
               <h2>Comparación de Montos de Ventas por mes y su símil de años anteriores</h2>
               <div className="chart-container">
                  <Bar
                     data={chartDataMonto}
                     options={{
                        scales: {
                           x: {
                              stacked: false,
                              title: {
                                 display: true,
                                 text: "Mes",
                              },
                           },
                           y: {
                              stacked: false,
                              title: {
                                 display: true,
                                 text: "Monto de ventas",
                              },
                           },
                        },
                     }}
                  />
               </div>
               <h2>Comparación de Ventas Totales por mes y su símil de años anteriores</h2>
               <div className="chart-container">
                  <Bar
                     data={chartDataVentas}
                     options={{
                        scales: {
                           x: {
                              stacked: false,
                              title: {
                                 display: true,
                                 text: "Mes",
                              },
                           },
                           y: {
                              stacked: false,
                              title: {
                                 display: true,
                                 text: "Ventas totales",
                              },
                           },
                        },
                     }}
                  />
               </div>
            </div>
         </NavStyle>
      </div>
   );
};

export default BarChartComponents;

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

   .chart-container {
      width: 80%; /* Ajusta el ancho según tus necesidades */
      margin-top: 20px;
   }
`;
