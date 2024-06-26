import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import HeaderComponents from "../Headers/HeaderComponents";
import estadistica from "../../images/estadistica.png"

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

    return (
        <div>
            <NavStyle>
                <HeaderComponents></HeaderComponents>
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
    transition: transform .5s ease;

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
`