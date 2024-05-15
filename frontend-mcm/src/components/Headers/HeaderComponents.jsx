import React from 'react'
import { useNavigate } from 'react-router-dom';
import Logo from "../../images/logo.jpg"
import styled from "styled-components";

function HeaderComponents() {
    const navigate = useNavigate();
    const handleClick = () => {
        navigate("/");
    }
    return (
        <div>
            <NavStyle>
                <header className="header">
                    <div className="header_izq">
                        <div className="logo" onClick={handleClick}>
                            <img style={{ width: '100px' }} id="Logo" src={Logo} alt="Logo" />
                        </div>
                        <a className="btn" href="/clientes">Clientes</a>
                        <a className="btn" href="">Proveedores</a>
                        <a className="btn" href="">Ventas</a>
                        <nav></nav>
                        <a className="btn" href="/oc">Ordenes de Compra</a>
                        </div>
                    <div className="header_der">
                        <a className="btn" href="">user</a>
                    </div>
                </header>
            </NavStyle>
        </div>
    )
}

export default HeaderComponents

const NavStyle = styled.nav`
.header{
    display: flex;
    justify-content: space-between; /* Para separar los elementos a los extremos */
    padding: 10px;
    background-color: #61c9f9;
    color: white;
}
.header_izq, .header_der{
    display: flex;
    align-items: center;
    text-align: center;
}
.header .btn{
    display: inline-block;
    padding: 10px 20px;
    color: #EBFCFF;
    text-decoration: none; /* Elimina la subrayado del enlace */
    margin-right: 10px;
    font-family: 'Pacifico',serif;
    font-weight: bold;
    font-size: 20px;
}

.header .btn:hover{
    color: #00375E;
}

.logo:hover{
    cursor: pointer;
}
`