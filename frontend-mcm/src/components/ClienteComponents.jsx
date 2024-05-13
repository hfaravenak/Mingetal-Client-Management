import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import HeaderComponents from "./Headers/HeaderComponents";
import styled from "styled-components";


import clientes from "../images/cliente.png"
import editar from "../images/editar.png"
import OrdenesDeCompraService from '../services/OrdenesDeCompraService'

function ClienteComponents() {

    const { cliente } = useParams();
    const datos = JSON.parse(decodeURIComponent(cliente));

    console.log(datos);
    const navigate = useNavigate();
    const [OC_ClienteEntity, setOC_ClienteEntity] = useState([]);


    const nathing = () => {
        navigate("");
    };

    useEffect(() => {
        OrdenesDeCompraService.getOCByCliente(datos.rut).then((res) => {
            console.log("Response data Cliente:", res.data);
            setOC_ClienteEntity(res.data);
        });
    }, []);

    return (
        <div>
            <NavStyle>
                <HeaderComponents></HeaderComponents>
                <div className="container">
                    <div className="container-1">
                        <div className="card" onClick={nathing}>
                            <div className="contenedor-img">
                                <img id="clientes" src={clientes} alt="clientes" />
                            </div>
                            <div className="contenedor-informacion">
                                <h2>{datos.nombre}</h2>
                                <h3>rut: {datos.rut}</h3>
                                <h3>correo: {datos.email}</h3>
                                <h3>telefono: {datos.telefono}</h3>
                            </div>
                        </div>
                    </div>
                    <div className="container-2">
                    <h1><b> Ordenes de Compra</b></h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>Ref #</th>
                                    <th>Fecha</th>
                                    <th>Estado</th>
                                    <th>Monto</th>
                                    <th>Modo Pago</th>
                                    <th>Más información</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    OC_ClienteEntity.map((OC_Cliente) => (
                                        <tr key= {OC_Cliente.id}>
                                            <td> {OC_Cliente.id} </td>
                                            <td> {OC_Cliente.fecha_solicitud} </td>
                                            <td> {OC_Cliente.estado_pago} </td>
                                            <td> {OC_Cliente.valor_pago} </td>
                                            <td> {OC_Cliente.modo_pago} </td>
                                            <td style={{textAlign: 'center', verticalAlign: 'middle', width:'1%'}}>
                                            <img id="editar" src={editar} alt="editar" onClick={() => nathing}/>
                                            </td>
                                        </tr>
                                    ))
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            </NavStyle>
        </div>
    );
}
export default ClienteComponents;

const NavStyle = styled.nav`

/* Separacion de las partes */

.container{
    margin: 2%;
    border: 1px solid gray;
    background-color: #F0F0F0;
    display: flex;
    flex-direction: row;
    gap: 20px;
    height: 100%;
}
.container-1{
    height: 80%;
    background-color: #F0F0F0;
    width: 20%;
    flex-shrink: 0; /* Hace que el contenedor no se encoja */
    overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
    padding: 5%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
}
.container-2{
    background-color: #F0F0F0;
    flex-grow: 1; /* El lado derecho es flexible y ocupará todo el espacio restante */
    overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
    padding: 1%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
    max-height: calc(0px + 74.5vh); /* Asegura que el contenedor no exceda la altura de la ventana */
}

/* Todo la parte de la tabla */

.content-table{
    border-collapse: collapse;
    margin-left: 1;
    font-size: 0.9em;
    min-width: 100px;
    border-radius: 5px 5px 0 0;
    overflow: hidden;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
}
.content-table thead tr{
    background-color: #D2712B;
    color: #ffffff;
    text-align: left;
    font-weight: bold;
}
.content-table th, .content-table td{
    padding: 12px 15px;
}

.content-table td{
    font-size: 18px;
}

.content-table tbody tr{
    border-bottom: 1px solid #dddddd;
}
.content-table tbody tr:nth-of-type(even){
    background-color: #f3f3f3;
}
.content-table tbody tr:last-of-type{
    border-bottom: 2px solid #009879;
}
.content-table tbody tr.active-row{
    font-weight: bold;
    color: #009879;
}

td img {
    width: 50%;
    object-fit: cover;
}

td img:hover{
    cursor: pointer;
}

th:hover, td:hover{
    cursor: default;
}

/* Por el lado de la información del cliente*/

.card{
    border: 1px solid black;
    border-radius: 10px;
}
.card .contenedor-img{
    display: flex;
    flex-direction: column;
    align-items: center;
}
.card .contenedor-informacion{
    background-color: white;
}

.card .contenedor-informacion h3,
.card .contenedor-informacion h2{
    margin-left: 4%;
}

.card .contenedor-informacion h3{
    font-size: 20px;
    font-weight: normal;
}

/* Fuente de la letra*/

td, th, h1, h2, h3{
    font-family: 'Pacifico',serif;
}
`