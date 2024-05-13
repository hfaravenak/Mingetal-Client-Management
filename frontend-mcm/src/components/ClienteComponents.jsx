import React, { useState, useEffect } from "react";
import HeaderComponents from "./Headers/HeaderComponents";
import ClienteService from '../services/ClienteService'
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import styled from "styled-components";


function ClienteComponents() {
    const initialState = {
        rut: "",
        nombre: "",
        empresa: "",
    };
    const [ClienteEntity, setClienteEntity] = useState([]);
    const [input, setInput] = useState(initialState);

    useEffect(() => {
        ClienteService.getClientes().then((res) => {
            console.log("Response data Cliente:", res.data);
            setClienteEntity(res.data);
        });
    }, []);

        
    const changeRutHandler = event => {
        setInput({ ...input, rut: event.target.value });
    };
    const changeNombreHandler = event => {
        setInput({ ...input, nombre: event.target.value });
    };
    const changeEmpresaHandler = event => {
        setInput({ ...input, empresa: event.target.value });
    };

    const buscarRut = (event) => {
        ClienteService.getClienteByRut(input.rut).then((res) => {
            console.log("Response data Cliente:", res.data);
            if (Array.isArray(res.data)) {
                setClienteEntity(res.data);
            } else if(res.data==""){
                setClienteEntity([]);
            }else{
                setClienteEntity([res.data]);
            }
        });
    }
    const buscarNombre = (event) => {
        ClienteService.getClienteByNombre(input.nombre).then((res) => {
            console.log("Response data Cliente:", res.data);
            if (Array.isArray(res.data)) {
                setClienteEntity(res.data);
            } else if(res.data==""){
                setClienteEntity([]);
            }else{
                setClienteEntity([res.data]);
            }
        });
    }
    const buscarEmpresa = (event) => {
        ClienteService.getClienteByEmpresa(input.empresa).then((res) => {
            console.log("Response data Cliente:", res.data);
            setClienteEntity(res.data);
        });
    }

    return(
        <div style={{background: '#F0F0F0'}}>
            <NavStyle>
                <HeaderComponents/>
                <div className="container">
                    <div className="container-1">
                        <div className="inline-forms-container">
                                <Form className="inline-form">
                                    <Form.Group className="mb-3" controlId="rut" value = {input.rut} onChange={changeRutHandler}>
                                        <Form.Label className="agregar">Rut:</Form.Label>
                                        <Form.Control className="agregar" type="text" name="rut"/>
                                    </Form.Group>
                                    <Button className="boton" onClick={buscarRut}>Buscar</Button>
                                </Form>
                                <Form className="inline-form">
                                    <Form.Group className="mb-3" controlId="nombre" value = {input.nombre} onChange={changeNombreHandler}>
                                        <Form.Label className="agregar">Nombre:</Form.Label>
                                        <Form.Control className="agregar" type="text" name="Nombre"/>
                                    </Form.Group>
                                    <Button className="boton" onClick={buscarNombre}>Buscar</Button>
                                </Form>
                                <Form className="inline-form">
                                    <Form.Group className="mb-3" controlId="empresa" value = {input.empresa} onChange={changeEmpresaHandler}>
                                        <Form.Label className="agregar">Empresa:</Form.Label>
                                        <Form.Control className="agregar" type="text" name="empresa"/>
                                    </Form.Group>
                                    <Button className="boton" onClick={buscarEmpresa}>Buscar</Button>
                                </Form>
                            </div>
                    </div>
                    <div align="center" className="container-2">
                        <h1><b> Listado de Cliente</b></h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>RUT</th>
                                    <th>Nombre</th>
                                    <th>Correo</th>
                                    <th>Telefono</th>
                                    <th>Empresa</th>
                                    <th>Más información</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    ClienteEntity.map((estudiante) => (
                                        <tr key= {estudiante.rut}>
                                            <td> {estudiante.rut} </td>
                                            <td> {estudiante.nombre} </td>
                                            <td> {estudiante.email} </td>
                                            <td> {estudiante.telefono} </td>
                                            <td> {estudiante.empresa} </td>
                                            <td style={{textAlign: 'center', verticalAlign: 'middle'}}>v</td>
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
    display: flex;
    flex-direction: row;
    gap: 20px;
    height: 100%;
}
.container-1{
    width: 10%;
    flex-shrink: 0; /* Hace que el contenedor no se encoja */
    overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
    padding: 5%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
}
.container-2{
    flex-grow: 1; /* El lado derecho es flexible y ocupará todo el espacio restante */
    overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
    padding: 1%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
    max-height: calc(82.7vh - 0px); /* Asegura que el contenedor no exceda la altura de la ventana */
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
    font-size: 13px;
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

/* Todo lo relacionado al form del filtro */

.inline-forms-container {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.inline-form {
    display: inline-block;
}

form {
    max-width: 500px;
    margin: 0 auto;
}
label {
    display: block;
    margin-bottom: 10px;
    margin-left: 15px;
    margin-top: 10px;
}
input[type="text"]{
    background-color: rgb(201, 201, 201);
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border-radius: 30px;
    border: 1px solid #ccc;
}
button{
    color: #fff;
    margin-left: 5px;
    margin-top: 10px;
    padding: 10px 20px;
    font-size: 16px;
    border-radius: 30px;
    border: none;
    cursor: pointer;
}
.boton{
    background-color: #D2712B;
}
button:hover{
    border: 2px solid #1b3039;
    transform: scale(1.1);
}
`