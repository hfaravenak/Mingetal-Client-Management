import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import clientes from "../images/cliente.png"
import proveedores from "../images/proveedores.png"
import inventario from "../images/inventario.png"
import ordenesCompra from "../images/ordenesCompra.png"
import ventas from "../images/ventas.png"
import estadistica from "../images/estadistica.png"
import cotizacion from "../images/cotizacion.png"

import HeaderComponents from "./Headers/HeaderComponents";

function MainComponents() {
    const navigate = useNavigate();
    const handleClickClientes = () => {
        navigate("/clientes");
    };

    const handleClickOC = () => {
        navigate("/oc");
    };
    const nathing = () => {
        navigate("/");
    };
    const handleClickCotizacion = () => {
        navigate("/cotizaciones");
    };
    const handleClickProductos = () => {
        navigate("/productos")
    };
    const handleClickProveedores = () => {
        navigate("/proveedores")
    };
    const handleClickVentas = () => {
        navigate("/ventas")
    };


    return (
        <div>
            <NavStyle>
                <HeaderComponents></HeaderComponents>
                <div className="general">
                <div className="container_main">
                    <div className="card" onClick={handleClickClientes}>
                        <img id="clientes" src={clientes} alt="clientes" />
                        <h2>Clientes</h2>
                    </div>
                    <div className="card" onClick={handleClickProveedores}>
                        <img id="proveedores" src={proveedores} alt="proveedores" />
                        <h2>Proveedores</h2>
                    </div>
                    <div className="card" onClick={handleClickProductos}>
                        <img id="inventario" src={inventario} alt="inventario" />
                        <h2>Inventario</h2>
                    </div>
                    <div className="card" onClick={handleClickOC}>
                        <img id="ordenes_compra" src={ordenesCompra} alt="ordenesCompra" />
                        <h2>Ordenes de compra</h2>
                    </div>
                    <div className="card" onClick={handleClickVentas}>
                        <img id="ventas" src={ventas} alt="ventas" />
                        <h2>Ventas</h2>
                    </div>
                    <div className="card" onClick={nathing}>
                        <img id="estadistica" src={estadistica} alt="estadistica" />
                        <h2>Estadistica</h2>
                        <h2 style={{ color: 'gray' }}>- Proximamente - </h2>
                    </div>
                    <div className="card" onClick={handleClickCotizacion}>
                        <img id="cotizacion" src={cotizacion} alt="cotizacion" />
                        <h2>Cotizaciones</h2>
                    </div>
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