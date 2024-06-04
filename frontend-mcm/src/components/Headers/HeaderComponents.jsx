import React from 'react';
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
                        <div className="dropdown">
                            <a className="btn" href="/clientes">Clientes</a>
                            <div className="dropdown-menu">
                                <a className="dropdown-item" href="/clientes/crear">Crear Cliente</a>
                            </div>
                        </div>
                        <div className="dropdown">
                            <a className="btn" href="/proveedores">Proveedores</a>
                            <div className="dropdown-menu">
                                <a className="dropdown-item" href="/proveedores/crear">Crear Proveedor</a>
                            </div>
                        </div>
                        <a className="btn" href="/">Ventas</a>
                        <div className="dropdown">
                            <a className="btn" href="/oc">Ordenes de Compra</a>
                            <div className="dropdown-menu">
                                <a className="dropdown-item" href="/oc/cliente">OC Clientes</a>
                                <a className="dropdown-item" href="/oc/proveedor">OC Proveedores</a>
                            </div>
                        </div>
                    </div>
                    <div className="header_der">

                        <a className="btn" href="/">Usuario</a>

                        <a className="btn" href="#">Usuario</a>

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
    cursor: pointer;
}

.logo:hover{
    cursor: pointer;
}

.dropdown, .dropdown-2{
    position: relative;
    display: subgrid;
    justify-content: center;
}
.dropdown-menu, .dropdown-menu-2 {
    display: none; /* Oculta el menú por defecto */
    flex-direction: column;
    position: absolute;
    top: 100%; /* Posiciona el menú justo debajo del botón */
    left: 0;
    background-color: #0193C0;
    box-shadow: 0px 8px 16px rgba(0,0,0,0.2);
    z-index: 1;
}
.dropdown:hover .dropdown-menu, .dropdown-2:hover .dropdown-menu-2{
    display: flex; /* Muestra el menú cuando se pasa el mouse sobre .dropdown */
}
.dropdown-item, .dropdown-item-2 {
    padding: 10px 20px;
    color: #EBFCFF;
    text-decoration: none;
    font-family: 'Pacifico', serif;
    font-weight: bold;
    font-size: 20px;
    white-space: nowrap;
}
.dropdown-item:hover, .dropdown-item-2:hover{
    background-color: #00375E;
    color: white;
}

.dropdown-menu-2{
    left:100%;
}
`