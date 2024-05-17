import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import HeaderComponents from "./../Headers/HeaderComponents";
import clientes from "../../images/cliente.png"
import proveedores from "../../images/proveedores.png"

function MainOCComponents() {
    const navigate = useNavigate();
    const handleClickClientes = () => {
        navigate("/oc/cliente");
    };

    const handleClickProveedores = () => {
        navigate("/oc/proveedor");
    };
    const nathing = () => {
        navigate("/");
    };
    return (
        <div>
            <NavStyle>
                <HeaderComponents></HeaderComponents>
                <div className="container_main">
                    <div className="card" onClick={handleClickClientes}>
                        <img id="client" src={clientes} alt="client" />
                        <h2>Clientes</h2>
                    </div>
                    <div className="card" onClick={handleClickProveedores}>
                        <img id="proveedor" src={proveedores} alt="proveedor" />
                        <h2>Proveedores</h2>
                    </div>
                </div>
            </NavStyle>
        </div>
    );
}

export default MainOCComponents;


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