import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import HeaderComponents from "../Headers/HeaderComponents";

function GraficoVentasComponents() {

    return (
        <div>
            <NavStyle>
                <HeaderComponents></HeaderComponents>
                <div className="container_main">
                </div>
            </NavStyle>
        </div>
    );
}

export default GraficoVentasComponents;


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
`