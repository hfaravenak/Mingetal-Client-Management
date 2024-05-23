import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import HeaderComponents from "../Headers/HeaderComponents";
import styled from "styled-components";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Swal from "sweetalert2";

import clientes from "../../images/cliente.png"
import editar from "../../images/editar.png"
import OrdenesDeCompraClienteService from '../../services/OrdenesDeCompraClienteService'
import ClienteService from "../../services/ClienteService";

function ClienteComponents() {

    const initialState = {
        rut: "",
        nombre: "",
        email: "",
        telefono: "",
        empresa: "",
    };
    const [input, setInput] = useState(initialState);

    const [mostrarCard, setMostrarCard] = useState(false);

    const { cliente } = useParams();
    const datos = JSON.parse(decodeURIComponent(cliente));

    
    const navigate = useNavigate();
    const [OC_ClienteEntity, setOC_ClienteEntity] = useState([]);


    const verOrdenDeCompra = (OC_Cliente) => {
        let datosEnvio = {
            id: OC_Cliente.id,
            id_cliente: OC_Cliente.id_cliente,
            estado_factura: OC_Cliente.estado_factura,
            estado_pago: OC_Cliente.estado_pago,
            valor_pago: OC_Cliente.valor_pago,
            fecha_pago: OC_Cliente.fecha_pago,
            fecha_solicitud: OC_Cliente.fecha_solicitud,
            estado_entrega: OC_Cliente.estado_entrega,
            modo_pago: OC_Cliente.modo_pago,
            fecha_inicio_pago: OC_Cliente.fecha_inicio_pago,
            tiempo_de_pago: OC_Cliente.tiempo_de_pago,
            numero_cheque: OC_Cliente.numero_cheque,
            numero_factura: OC_Cliente.numero_factura,
            empresa_despacho: OC_Cliente.empresa_despacho,
            cliente: datos,

        };
        const datosComoTexto = JSON.stringify(datosEnvio);
        navigate(`/oc/cliente/mas info/${encodeURIComponent(datosComoTexto)}`);
    };
    const verOrdenDeCompraEditar = (OC_Cliente) => {
        Swal.fire({
            title: "¿Desea cancelar la edición del cliente?",
            icon: "question",   
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        }).then((result) => {
            if (result.isConfirmed) {
                let datosEnvio = {
                    id: OC_Cliente.id,
                    id_cliente: OC_Cliente.id_cliente,
                    estado_factura: OC_Cliente.estado_factura,
                    estado_pago: OC_Cliente.estado_pago,
                    valor_pago: OC_Cliente.valor_pago,
                    fecha_pago: OC_Cliente.fecha_pago,
                    fecha_solicitud: OC_Cliente.fecha_solicitud,
                    estado_entrega: OC_Cliente.estado_entrega,
                    modo_pago: OC_Cliente.modo_pago,
                    fecha_inicio_pago: OC_Cliente.fecha_inicio_pago,
                    tiempo_de_pago: OC_Cliente.tiempo_de_pago,
                    numero_cheque: OC_Cliente.numero_cheque,
                    numero_factura: OC_Cliente.numero_factura,
                    empresa_despacho: OC_Cliente.empresa_despacho,
                    cliente: datos,
        
                };
                const datosComoTexto = JSON.stringify(datosEnvio);
                navigate(`/oc/cliente/mas info/${encodeURIComponent(datosComoTexto)}`);
            }
        });
    };

    useEffect(() => {
        OrdenesDeCompraClienteService.getOCByCliente(datos.rut).then((res) => {
            setOC_ClienteEntity(res.data);
        });
    }, [datos.rut]);

    const changeNombreHandler = event => {
        setInput({ ...input, nombre: event.target.value });
    };
    const changeEmpresaHandler = event => {
        setInput({ ...input, empresa: event.target.value });
    };
    const changeEmailHandler = event => {
        setInput({ ...input, email: event.target.value });
    };
    const changeTelefonoHandler = event => {
        setInput({ ...input, telefono: event.target.value });
    };

    const changeMostrarCard = () => {
        setInput({
            rut: datos.rut,
            email: datos.email,
            telefono: datos.telefono,
            nombre: datos.nombre,
            empresa: datos.empresa,
        });
        setMostrarCard(true);
    }

    const enviarDatos = () => {
        Swal.fire({
            title: "¿Seguro de modificar este usuario?",
            icon: "question",   
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        }).then((result) => {
            if (result.isConfirmed) {
                let updateCliente = {
                    rut: input.rut,
                    nombre: input.nombre,
                    email: input.email,
                    telefono: input.telefono,
                    empresa: input.empresa
                }
                ClienteService.putCliente(updateCliente);
                navigate(`/clientes/mas info/${encodeURIComponent(JSON.stringify(updateCliente))}`);
                setMostrarCard(false);
            }
        });
    }
    const EliminarCliente = () => {
        Swal.fire({
            title: "¿Seguro de que desea eliminar este cliente?",
            text: "Esta acción es irreversible y no podrá recuperar al cliente perdido.",
            icon: "warning",   
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
            input: "text",
            inputPlaceholder: "Confirmo",
            inputValidator: (value) => {
                return new Promise((resolve) => {
                    if (value==="Confirmo") {
                        resolve();
                    } else {
                        resolve('ERROR al introducir la palabra');
                    }
                });
            }
        }).then((result) => {
            if (result.isConfirmed) {
                let rut = datos.rut;
                ClienteService.deleteCliente(rut);
                Swal.fire({
                    title: 'Eliminando...',
                    text: 'Por favor espera',
                    timer: 3000, // 3 segundos
                    didOpen: () => {
                        Swal.showLoading();
                    },
                    willClose: () => {
                        navigate("/clientes");
                    }
                });
            }
        });
    }
    const CancelarEdit = () => {
        setMostrarCard(false);
    }

    if(mostrarCard){
        return (
            <div>
                <NavStyle>
                    <HeaderComponents></HeaderComponents>
                    <div className="container">
                        <div className="container-1">
                            <div className="card">
                                <div className="contenedor-img">
                                    <img id="clientes" src={clientes} alt="clientes" />
                                </div>
                                <div className="inline-forms-container contenedor-informacion">
                                    <Form className="inline-form">
                                        <Form.Group className="mb-3" controlId="nombre">
                                            <Form.Control value = {input.nombre} onChange={changeNombreHandler} className="font-h3 no-border" type="text" name="Nombre" placeholder="Juan Perez"/>
                                        </Form.Group>
                                    </Form>
                                    <Form className="inline-form" style={{marginTop:"2.2%"}}>
                                        <Form.Group className="mb-3" controlId="empresa">
                                            <Form.Label className="font-h2">Empresa:</Form.Label>
                                            <Form.Control value = {input.empresa} onChange={changeEmpresaHandler} className="font-h2-control no-border" type="text" name="empresa" placeholder="Nombre Generico"/>
                                        </Form.Group>
                                    </Form>
                                    <h3 style={{color:"gray", marginTop:"2%", marginBottom:"2%"}}>Rut: {datos.rut}</h3>
                                    <Form className="inline-form">
                                        <Form.Group className="mb-3" controlId="email" value = {input.email} onChange={changeEmailHandler}>
                                            <Form.Label className="font-h2 eliminarMargen">Correo:</Form.Label>
                                            <Form.Control value={input.email} className="font-h2-control no-border" type="text" name="email" placeholder="abcedg@correo.com"/>
                                        </Form.Group>
                                    </Form>
                                    <Form className="inline-form">
                                        <Form.Group className="mb-3" controlId="telefono">
                                            <Form.Label className="font-h2">Telefono:</Form.Label>
                                            <Form.Control value = {input.telefono} onChange={changeTelefonoHandler} className="font-h2-control no-border" type="text" name="telefono" placeholder="+569 12345678"/>
                                        </Form.Group>
                                    </Form>
                                </div>
                            </div>
                            <Button className="aceptar" onClick={enviarDatos}>Aceptar</Button>
                            <Button className="cancelar" onClick={CancelarEdit}>Cancelar</Button>
                        </div>
                        <div className="container-2">
                        <h1><b> Ordenes de Compra</b></h1>
                            <table border="1" className="content-table">
                            <thead>
                                    <tr>
                                        <th>Ref #</th>
                                        <th>Estado del Pago</th>
                                        <th>Fecha del Pago</th>
                                        <th>Fecha de la Solicitud</th>
                                        <th>Valor del Pago</th>
                                        <th>Estado de la Entrega</th>
                                        <th>Más información</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        OC_ClienteEntity.map((OC_Cliente) => (
                                            <tr key= {OC_Cliente.id}>
                                                <td> #{OC_Cliente.id} </td>
                                                <td> {OC_Cliente.estado_pago} </td>
                                                <td> {OC_Cliente.fecha_pago} </td>
                                                <td> {OC_Cliente.fecha_solicitud} </td>
                                                <td> {OC_Cliente.valor_pago} </td>
                                                <td> {OC_Cliente.estado_entrega} </td>
                                                <td style={{textAlign: 'center', verticalAlign: 'middle', width:'1%'}}>
                                                <img id="editar" src={editar} alt="editar" onClick={()=>verOrdenDeCompraEditar(OC_Cliente)}/>
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
        )
    }
    else{
        return (
            <div>
                <NavStyle>
                    <HeaderComponents></HeaderComponents>
                    <div className="container">
                        <div className="container-1">
                            <div className="card">
                                <div className="contenedor-img">
                                    <img id="clientes" src={clientes} alt="clientes" />
                                </div>
                                <div className="contenedor-informacion">
                                    <h2>{datos.nombre}</h2>
                                    <h3>Empresa: {datos.empresa}</h3>
                                    <h3>Rut: {datos.rut}</h3>
                                    <h3>Correo: {datos.email}</h3>
                                    <h3>Telefono: {datos.telefono}</h3>
                                </div>
                            </div>
                            <Button className="editar" onClick={changeMostrarCard}>Editar</Button>
                            <Button className="eliminar" onClick={EliminarCliente}>Eliminar</Button>
                        </div>
                        <div className="container-2">
                        <h1><b> Ordenes de Compra</b></h1>
                            <table border="1" className="content-table">
                                <thead>
                                    <tr>
                                        <th>Ref #</th>
                                        <th>Estado del Pago</th>
                                        <th>Fecha del Pago</th>
                                        <th>Fecha de la Solicitud</th>
                                        <th>Valor del Pago</th>
                                        <th>Estado de la Entrega</th>
                                        <th>Más información</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        OC_ClienteEntity.map((OC_Cliente) => (
                                            <tr key= {OC_Cliente.id}>
                                                <td> #{OC_Cliente.id} </td>
                                                <td> {OC_Cliente.estado_pago} </td>
                                                <td> {OC_Cliente.fecha_pago} </td>
                                                <td> {OC_Cliente.fecha_solicitud} </td>
                                                <td> {OC_Cliente.valor_pago} </td>
                                                <td> {OC_Cliente.estado_entrega} </td>
                                                <td style={{textAlign: 'center', verticalAlign: 'middle', width:'1%'}}>
                                                <img id="editar" src={editar} alt="editar" onClick={()=>verOrdenDeCompra(OC_Cliente)}/>
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

    
}
export default ClienteComponents;

const NavStyle = styled.nav`

/* Separacion de las partes */

.container{
    margin: 2%;
    border: 2px solid #D5D5D5;
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
    max-height: calc(0px + 74.3vh); /* Asegura que el contenedor no exceda la altura de la ventana */
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
    background-color: white;
}
.card .contenedor-img{
    background-color: #F0F0F0;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.card .contenedor-informacion{
    background-color: white;
    height: 100%;
}

.card .contenedor-informacion h3,
.card .contenedor-informacion h2,
.font-h2, .font-h3{
    margin-left: 4%;
}

.card .contenedor-informacion h3,
.font-h2, .font-h2-control{
    font-size: 20px;
    font-weight: normal;
}

.font-h3{
    margin-top: 5%;
    font-size: 24px;
    font-weight: bold;
    width: 90%;
}

.font-h2, font-h3, .font-h2-control{
    padding-bottom: 3%;
    padding-top: 3%;
}

.no-border {
    border: none;
    box-shadow: none;
}

.editar, .eliminar, .cancelar, .aceptar {
    margin-left: 5px;
    margin-top: 10px;
    padding: 10px 20px;
    font-size: 16px;
    border-radius: 30px;
    border: none;
    cursor: pointer;
}

.eliminar, .cancelar{
    background-color: #550100;
    color: #fff;
}

.editar{
    background-color: #39BEAB;
    color: black;
}

.aceptar{
    background-color: #00A768;
    color: black;
}

.editar:hover, 
.eliminar:hover,
.aceptar:hover,
.cancelar:hover{
    border: 1px solid black;
}

/* Fuente de la letra*/

td, th, h1, h2, h3, Button, .font-h2, .font-h3, .font-h2-control{
    font-family: 'Pacifico',serif;
}
`