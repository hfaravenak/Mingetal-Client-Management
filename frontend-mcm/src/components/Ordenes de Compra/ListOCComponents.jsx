import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import styled from "styled-components";
import editar from "../../images/editar.png"
import HeaderComponents from "../Headers/HeaderComponents";
import OrdenesDeCompraService from "../../services/OrdenesDeCompraClienteService";
import OrdenesDeCompraClienteService from "../../services/OrdenesDeCompraClienteService";
import ClienteService from "../../services/ClienteService";

function ListOCComponents() {
    const initialState = {
        id: "",
        nombre: "",
        empresa: "",
    };
    const [input, setInput] = useState(initialState);

    const [ClienteEntity, setClienteEntity] = useState([]);
    const [OCClienteEntity, setOCClienteEntity] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        OrdenesDeCompraService.getOCCliente().then((res) => {
            setOCClienteEntity(res.data);
        });
        ClienteService.getClientes().then((res) => {
            console.log(res.data);
            setClienteEntity(res.data);
        });
    }, []);
        
    const changeIdHandler = event => {
        setInput({ ...input, id: event.target.value });
    };
    const changeNombreHandler = event => {
        setInput({ ...input, nombre: event.target.value });
    };
    const changeEmpresaHandler = event => {
        setInput({ ...input, empresa: event.target.value });
    };

    const busquedaNombre = (rut) => {
        let variable = "";
        ClienteEntity.forEach(cliente => {
            if(cliente.rut===rut){
                variable=cliente.nombre;
            }
        });
        console.log("rut= "+rut+"  variable= "+variable);
        return variable;
    };
    

    const buscarId = () => {
        OrdenesDeCompraClienteService.getOCClienteById(input.id).then((res) => {
            console.log("Response data OC-CLiente:", res.data);
            if (Array.isArray(res.data)) {
                setOCClienteEntity(res.data);
            } else if(res.data===""){
                setOCClienteEntity([]);
            }else{
                setOCClienteEntity([res.data]);
            }
        });
    }
    const buscarNombre = () => {
    }
    const buscarEmpresa = () => {
    }

    const crearCliente = () => {
        navigate("crear");
    }

    const handleKeyPressId = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarId(); // Llama a la función que deseas ejecutar
        }
    };
    const handleKeyPressNombre = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarNombre(); // Llama a la función que deseas ejecutar
        }
    };
    const handleKeyPressEmpresa = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarEmpresa(); // Llama a la función que deseas ejecutar
        }
    };

    const ChangeViendoCliente = (rut, nombre, email, empresa, telefono) => {
        console.log(rut, nombre, email, telefono, empresa);
        const datos = {
            rut: rut,
            nombre: nombre,
            email: email,
            empresa: empresa,
            telefono: telefono
        };
        const datosComoTexto = JSON.stringify(datos);
        console.log(datosComoTexto)
        console.log(datos)
        navigate(`/clientes/mas info/${encodeURIComponent(datosComoTexto)}`);
    };

    return(
        <div>
            <NavStyle>
                <HeaderComponents/>
                <div className="container">
                    <div className="container-1">
                        <div className="inline-forms-container">
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="id" value = {input.id} onChange={changeIdHandler}>
                                    <Form.Label className="agregar">Id de OC:</Form.Label>
                                    <Form.Control className="agregar" type="text" name="id" placeholder="2044111" onKeyPress={handleKeyPressId}/>
                                </Form.Group>
                                <Button className="boton" onClick={buscarId}>Buscar</Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="nombre" value = {input.nombre} onChange={changeNombreHandler}>
                                    <Form.Label className="agregar">Nombre Cliente:</Form.Label>
                                    <Form.Control className="agregar" type="text" name="Nombre" placeholder="Juan Perez" onKeyPress={handleKeyPressNombre}/>
                                </Form.Group>
                                <Button className="boton" onClick={buscarNombre}>Buscar</Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="empresa" value = {input.empresa} onChange={changeEmpresaHandler} >
                                    <Form.Label className="agregar">Empresa del Cliente:</Form.Label>
                                    <Form.Control className="agregar" type="text" name="empresa" placeholder="Nombre Generico" onKeyPress={handleKeyPressEmpresa}/>
                                </Form.Group>
                                <Button className="boton" onClick={buscarEmpresa}>Buscar</Button>
                            </Form>
                        </div>
                        <div className="btn-inf">
                            <Button className="boton" onClick={crearCliente}>Ingresar Cliente</Button>
                        </div>
                    </div>
                    <div align="center" className="container-2">
                        <h1><b> Ordenes de Compra de Clientes</b></h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>id</th>
                                    <th>Nombre</th>
                                    <th>Estado Pago</th>
                                    <th>Fecha Solicitud</th>
                                    <th>Valor del Pago</th>
                                    <th>Más información</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    OCClienteEntity.map((OCCliente) => (
                                        <tr key= {OCCliente.id}>
                                            <td> {OCCliente.id} </td>
                                            <td>{busquedaNombre(OCCliente.id_cliente)}</td>
                                            <td> {OCCliente.estado_pago} </td>
                                            <td> {OCCliente.fecha_solicitud} </td>
                                            <td> {OCCliente.valor_pago} </td>
                                            <td style={{textAlign: 'center', verticalAlign: 'middle', width:'1%'}}>
                                            <img id="editar" src={editar} alt="editar" onClick={() => ChangeViendoCliente(OCCliente)}/>
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

export default ListOCComponents;

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
    background-color: #F0F0F0;
    width: 10%;
    flex-shrink: 0; /* Hace que el contenedor no se encoja */
    overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
    padding: 5%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
    display: flex;
    flex-direction: column;
    height: 59vh;
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

img {
    width: 50%;
    object-fit: cover;
}

img:hover{
    cursor: pointer;
}

th:hover, td:hover{
    cursor: default;
}

/* Todo lo relacionado al form del filtro */

.inline-forms-container {
    flex-grow: 1;
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

/* Apartado del boton de crear */

.btn-inf .boton{
    font-size: 20px;
}

.boton:hover{
    border: 1px solid black;
}

/* Fuente de la letra*/

td, th, h1, Label, Control, Button{
    font-family: 'Pacifico',serif;
}
`