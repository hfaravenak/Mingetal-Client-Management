import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { Line } from "react-chartjs-2";
import 'chart.js/auto'; // Necesario para que funcione con la versión 3 de chart.js
import HeaderComponents from "../Headers/HeaderComponents";
import ordenesDeCompraProveedorService from "../../services/OrdenesDeCompraProveedorService";

const monthNames = ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"];

function GraficoOOCProveedoresComponents() {
    const [comprasData, setComprasData] = useState([]);

    useEffect(() => {
        ordenesDeCompraProveedorService.getPurchasesByYearAndMonth()
            .then(response => {
                setComprasData(response.data);
            })
            .catch(error => {
                console.error("Error fetching purchase data", error);
            });
    }, []);

    const prepareMontosChartData = () => {
        if (comprasData.length === 0) {
            return {
                labels: [],
                datasets: []
            };
        }

        const labels = comprasData.map(data => `${data[3]}-${monthNames[data[2] - 1]}`); // Año-Mes con abreviatura
        const montos = comprasData.map(data => data[0]); // Monto total de compras

        return {
            labels: labels,
            datasets: [
                {
                    label: 'Monto Total de Compras a Proveedores por Mes',
                    data: montos,
                    fill: false,
                    backgroundColor: 'rgba(75,192,192,0.4)',
                    borderColor: 'rgba(75,192,192,1)',
                    yAxisID: 'y-axis-1',
                }
            ]
        };
    };

    const prepareCantidadesChartData = () => {
        if (comprasData.length === 0) {
            return {
                labels: [],
                datasets: []
            };
        }

        const labels = comprasData.map(data => `${data[3]}-${monthNames[data[2] - 1]}`); // Año-Mes con abreviatura
        const cantidades = comprasData.map(data => data[1]); // Cantidad de compras

        return {
            labels: labels,
            datasets: [
                {
                    label: 'Cantidad de Compras por Mes',
                    data: cantidades,
                    fill: false,
                    backgroundColor: 'rgba(153,102,255,0.4)',
                    borderColor: 'rgba(153,102,255,1)',
                    yAxisID: 'y-axis-2',
                }
            ]
        };
    };

    const options = {
        scales: {
            yAxes: [
                {
                    id: 'y-axis-1',
                    type: 'linear',
                    position: 'left',
                },
                {
                    id: 'y-axis-2',
                    type: 'linear',
                    position: 'right',
                }
            ]
        }
    };

    return (
        <div>
            <NavStyle>
                <HeaderComponents />
                <div className="container_main">
                    <h2>Estadísticas OOCC de Proveedores</h2>
                    <div className="charts">
                        <div className="chart">
                            <Line data={prepareMontosChartData()} options={options} />
                        </div>
                        <div className="chart">
                            <Line data={prepareCantidadesChartData()} options={options} />
                        </div>
                    </div>
                </div>
            </NavStyle>
        </div>
    );
}

export default GraficoOOCProveedoresComponents;

const NavStyle = styled.nav`
.container_main {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: 2%;
    padding: 2%;
    border: 2px solid #D5D5D5;
    background-color: #f0f0f0;
    width: 80%;
}
.charts {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
}
.chart {
    width: 90%;
    max-width: 800px;
    margin: 20px 0;
}
`;
