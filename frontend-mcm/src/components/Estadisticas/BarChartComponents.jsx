import React, { useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import VentasService from "../../services/VentasService";
import styled from "styled-components";
import HeaderComponents from "../Headers/HeaderComponents";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';

// Register the components with ChartJS
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const BarChartComponents = () => {
  const [chartData, setChartData] = useState({
    labels: [],
    datasets: []
  });

  const [anioSeleccionado, setAnioSeleccionado] = useState(2024)

  useEffect(() => {
    const fetchData = async (anio) => {
      try {
        const response = await VentasService.getSimilVentasAnteriores();
        const rawdata = response.data;
        const data = rawdata.filter(item => item[item.length - 1] === anio)

        if (data && data.length > 0) {
          // Preparar estructuras para almacenar datos
          let montosVentas = [];
          let ventasTotales = [];
          let labels = [];

          // Agrupar datos por Mes y Año
          const groupedData = {};
          data.forEach(item => {
            const mes = item[2];
            const año = item[3];
            const key = `${mes}-${año}`;
            if (!groupedData[key]) {
              groupedData[key] = {
                montosVentas: 0,
                ventasTotales: 0
              };
            }
            groupedData[key].montosVentas += item[0];
            groupedData[key].ventasTotales += item[1];
          });

          // Crear arrays para el gráfico
          Object.keys(groupedData).forEach(key => {
            const [mes, año] = key.split("-");
            labels.push(`${mes}-${año}`);
            montosVentas.push(groupedData[key].montosVentas);
            ventasTotales.push(groupedData[key].ventasTotales);
          });

          // Actualizar el estado del gráfico
          setChartData({
            labels: labels,
            datasets: [
              {
                label: "Monto de ventas",
                data: montosVentas,
                backgroundColor: "rgba(54, 162, 235, 0.6)"
              },
              {
                label: "Ventas totales",
                data: ventasTotales,
                backgroundColor: "rgba(255, 99, 132, 0.6)"
              }
            ]
          });
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData(anioSeleccionado);
  }, [anioSeleccionado]);

  return (
    <div>
      <NavStyle>
        <HeaderComponents />
        <div className="container_main">
            <select value={anioSeleccionado} onChange={(e)=>setAnioSeleccionado(parseInt(e.target.value))}>
                <option value={2022}>2022</option>
                <option value={2023}>2023</option>
                <option value={2024}>2024</option>
            </select>
          <h2>Comparación de Ventas por mes y su símil de años anteriores</h2>
          <div className="chart-container">
            <Bar
              data={chartData}
              options={{
                scales: {
                  xAxes: [{
                    ticks: {
                      autoSkip: false, // Evita que las etiquetas se omitan si hay muchas
                      maxRotation: 90, // Rotación máxima de las etiquetas
                      minRotation: 45 // Rotación mínima de las etiquetas
                    },
                    scaleLabel: {
                      display: true,
                      labelString: 'Mes y Año' // Etiqueta del eje X
                    }
                  }],
                  yAxes: [{
                    stacked: true // Para apilar las barras en el eje Y
                  }]
                }
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
    .container_main {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-wrap: wrap;
        margin: 2%;
        padding: 2%;
        border: 2px solid #D5D5D5;
        background-color: #f0f0f0;
    }

    .chart-container {
        width: 80%; /* Ajusta el ancho según tus necesidades */
        margin-top: 20px;
    }
`;
