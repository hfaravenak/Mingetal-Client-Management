import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Swal from "sweetalert2";
import HeaderComponents from "../../Headers/HeaderComponents";
import clientes from "../../../images/cliente.png"
import OrdenesDeCompraProveedorService from "../../../services/OrdenesDeCompraProveedorService";

function OCProveedorComponents() {
    const initialState = {
        estado_pago: "",
        estado_entrega: "",
        fecha_pago: "",
        factura: "",
        fecha_entrega: "",
    };
    const [input, setInput] = useState(initialState);
    const [mostrarCard, setMostrarCard] = useState(false);

    const { oc_proveedor } = useParams();
    const datos = JSON.parse(decodeURIComponent(oc_proveedor));

    const navigate = useNavigate();

    const modificacionFecha = (fechaOriginal) => {  
        const partesFecha = fechaOriginal.split("-");

        // Crea un nuevo objeto Date con el formato "yyyy-mm-dd"
        const fecha = new Date(partesFecha[0], partesFecha[1] - 1, partesFecha[2]);

        // Formatea la fecha en el formato deseado "dd-mm-yyyy"
        const dia = fecha.getDate().toString().padStart(2, "0");
        const mes = (fecha.getMonth() + 1).toString().padStart(2, "0");
        const anio = fecha.getFullYear().toString();

        const fechaSalida = `${dia}-${mes}-${anio}`;
        return fechaSalida;
    }

    
    const changeFacturaHandler = event => {
        setInput({ ...input, factura: event.target.value });
    };
    const changeEstadoPagoHandler = event => {
        setInput({ ...input, estado_pago: event.target.value });
    };
    const changeEstadoEntregaHandler = event => {
        setInput({ ...input, estado_entrega: event.target.value });
    };
    const changeFechaPagoHandler = event => {
        setInput({ ...input, fecha_pago: event.target.value });
    };
    const changeFechaEntregaHandler = event => {
        setInput({ ...input, fecha_entrega: event.target.value });
    };

    const changeMostrarCard = () => {
        setInput({
            estado_pago: datos.estado_pago,
            estado_entrega: datos.estado_entrega,
            fecha_pago: datos.fecha_pago,
            factura: datos.factura,
            fecha_entrega: datos.fecha_entrega,
        });
        setMostrarCard(true);
    }

    const enviarDatos = () => {
        Swal.fire({
            title: "¿Seguro de modificar esta orden de compra con estos valores?",
            icon: "question",   
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        }).then((result) => {
            if (result.isConfirmed) {
                let updateOC = {
                    id: datos.id,
                    id_proveedor: datos.id_proveedor,
                    estado_pago: input.estado_pago,
                    valor_pago: datos.valor_pago,
                    fecha_pago: input.fecha_pago,
                    fecha_solicitud: datos.fecha_solicitud,
                    fecha_entrega: input.fecha_entrega,
                    estado_entrega: input.estado_entrega,
                    factura: input.factura,
        
                };
                OrdenesDeCompraProveedorService.putOCProveedor(updateOC);
                let OtherUpdateOC = {
                    id: datos.id,
                    id_proveedor: datos.id_proveedor,
                    estado_pago: input.estado_pago,
                    valor_pago: datos.valor_pago,
                    fecha_pago: input.fecha_pago,
                    fecha_solicitud: datos.fecha_solicitud,
                    fecha_entrega: input.fecha_entrega,
                    estado_entrega: input.estado_entrega,
                    factura: input.factura,
                    proveedor: datos.proveedor,
                    contacto1: datos.contacto1,
                };
                Swal.fire({
                    title: 'Editando...',
                    text: 'Por favor espere',
                    timer: 3000, // 3 segundos
                    didOpen: () => {
                        Swal.showLoading();
                    },
                    willClose: () => {
                        navigate(`/oc/proveedor/mas info/${encodeURIComponent(JSON.stringify(OtherUpdateOC))}`);
                        setMostrarCard(false);
                    }
                });
            }
        });
    }
    const CancelarEdit = () => {
        setMostrarCard(false);
    }

    const EliminarCliente = () => {
        Swal.fire({
            title: "¿Seguro de que desea eliminar esta orden de compra?",
            text: "Esta acción es irreversible y no podrá recuperar la Orden de compra eliminada. Además no se recomienda hacer esta acción",
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
                let id = datos.id;
                OrdenesDeCompraProveedorService.deleteOCProveedor(id);
                Swal.fire({
                    title: 'Eliminando...',
                    text: 'Por favor espera',
                    timer: 3000, // 3 segundos
                    didOpen: () => {
                        Swal.showLoading();
                    },
                    willClose: () => {
                        navigate("/oc/proveedor");
                    }
                });
            }
        });
    }

    if(mostrarCard){
        return(
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
                                    <h2>{datos.contacto1.nombre}</h2>
                                    <h3><strong>Empresa: </strong>{datos.proveedor.empresa}</h3>
                                    <h3><strong>Rubro: </strong>{datos.proveedor.rubro}</h3>
                                    <h3><strong>Rut: </strong>{datos.contacto1.rut}</h3>
                                    <h3><strong>Correo: </strong>{datos.contacto1.correo}</h3>
                                    <h3><strong>Telefono Celular: </strong>{datos.contacto1.fono_cel}</h3>
                                    <h3><strong>Telefono Fijo: </strong>{datos.contacto1.fono_fijo}</h3>
                                    <h3><strong>Comentario: </strong> {datos.proveedor.comentario}</h3>
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
                                        <th>Fecha de la Solicitud</th>
                                        <th>Estado de la Entrega</th>
                                        <th>Fecha de la Entrega</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr key= {datos.id}>
                                        <td style={{color:"gray"}}> #{datos.id} </td>
                                        <td style={{color:"gray"}}> {modificacionFecha(datos.fecha_solicitud)} </td>
                                        <td> 
                                            <div className="inline-forms-container contenedor-informacion">
                                                <Form className="inline-form" style={{width: "150px"}}>
                                                    <Form.Group className="mb-3" controlId="estado_entrega" value = {input.estado_entrega} onChange={changeEstadoEntregaHandler}>
                                                        <Form.Select style={{width:"100%"}} value={input.estado_entrega} className="font-h2 no-border" name="estado_entrega">
                                                            <option value="No Entregado">No Entregado</option>
                                                            <option value="Entregado">Entregado</option>
                                                        </Form.Select>
                                                    </Form.Group>
                                                </Form>
                                            </div>     
                                        </td>
                                        <td> 
                                            <div className="inline-forms-container contenedor-informacion">
                                                <Form className="inline-form" style={{width: "150px"}}>
                                                    <Form.Group className="mb-3" controlId="fecha_entrega" value = {input.fecha_entrega} onChange={changeFechaEntregaHandler}>
                                                        <Form.Control style={{width:"100%"}} value={input.fecha_entrega} className="font-h2 no-border" type="date" name="fecha_entrega"/>
                                                    </Form.Group>
                                                </Form>
                                            </div> 
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <h1><b> Pago</b></h1>
                            <table border="1" className="content-table">
                                <thead>
                                    <tr>
                                        <th>Valor del Pago</th>
                                        <th>Estado del Pago</th>
                                        <th>Fecha del Pago</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr key= {datos.id}>
                                        <td style={{color:"gray"}}> {datos.valor_pago} </td>
                                        <td> 
                                            <div className="inline-forms-container contenedor-informacion">
                                                <Form className="inline-form" style={{width: "150px"}}>
                                                    <Form.Group className="mb-3" controlId="estado_pago" value = {input.estado_pago} onChange={changeEstadoPagoHandler}>
                                                        <Form.Select style={{width:"100%"}} value={input.estado_pago} className="font-h2 no-border" name="estado_pago">
                                                            <option value="No Pagado">No Pagado</option>
                                                            <option value="Pagado">Pagado</option>
                                                        </Form.Select>
                                                    </Form.Group>
                                                </Form>
                                            </div> 
                                        </td>
                                        <td> 
                                            <div className="inline-forms-container contenedor-informacion">
                                                <Form className="inline-form" style={{width: "150px"}}>
                                                    <Form.Group className="mb-3" controlId="fecha_pago" value = {input.fecha_pago} onChange={changeFechaPagoHandler}>
                                                        <Form.Control style={{width:"100%"}} value={input.fecha_pago} className="font-h2 no-border" type="date" name="fecha_pago"/>
                                                    </Form.Group>
                                                </Form>
                                            </div> 
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <h1><b> Factura</b></h1>
                            <table border="1" className="content-table">
                                <thead>
                                    <tr>
                                        <th>Numero de la Factura</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr key= {datos.id}>
                                        <td> 
                                            <div className="inline-forms-container contenedor-informacion">
                                                <Form className="inline-form" style={{width: "150px"}}>
                                                    <Form.Group className="mb-3" controlId="factura" value = {input.factura} onChange={changeFacturaHandler}>
                                                        <Form.Control style={{width:"100%"}} value={input.factura} className="font-h2 no-border" type="number" name="factura" placeholder="25"/>
                                                    </Form.Group>
                                                </Form>
                                            </div> 
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </NavStyle>
            </div>
        );
    }
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
                                <h2>{datos.contacto1.nombre}</h2>
                                <h3><strong>Empresa: </strong>{datos.proveedor.empresa}</h3>
                                <h3><strong>Rubro: </strong>{datos.proveedor.rubro}</h3>
                                <h3><strong>Rut: </strong>{datos.contacto1.rut}</h3>
                                <h3><strong>Correo: </strong>{datos.contacto1.correo}</h3>
                                <h3><strong>Telefono Celular: </strong>{datos.contacto1.fono_cel}</h3>
                                <h3><strong>Telefono Fijo: </strong>{datos.contacto1.fono_fijo}</h3>
                                <h3><strong>Comentario: </strong> {datos.proveedor.comentario}</h3>
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
                                    <th>Fecha de la Solicitud</th>
                                    <th>Estado de la Entrega</th>
                                    <th>Fecha de la Entrega</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr key= {datos.id}>
                                    <td> #{datos.id} </td>
                                    <td> {modificacionFecha(datos.fecha_solicitud)} </td>
                                    <td> {datos.estado_entrega} </td>
                                    <td> {modificacionFecha(datos.fecha_entrega)} </td>
                                </tr>
                            </tbody>
                        </table>
                        <h1><b> Pago</b></h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>Valor del Pago</th>
                                    <th>Estado del Pago</th>
                                    <th>Fecha del Pago</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr key= {datos.id}>
                                    <td> {datos.valor_pago} </td>
                                    <td> {datos.estado_pago} </td>
                                    <td> {modificacionFecha(datos.fecha_pago)} </td>
                                </tr>
                            </tbody>
                        </table>
                        <h1><b> Factura</b></h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>Numero de la Factura</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr key= {datos.id}>
                                    <td> {datos.factura} </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </NavStyle>
        </div>
    );
}
export default OCProveedorComponents

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
    background-color: #F0F0F0;
    width: 10%;
    flex-shrink: 0; /* Hace que el contenedor no se encoja */
    overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
    padding: 5%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
    display: flex;
    flex-direction: column;
    height: 58.9vh;
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

td, th{
    width:150px;
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

.font-h2{
    width: 100%;
    background-color: #F0F0F0F0;
}


.font-h3{
    margin-top: 5%;
    font-size: 24px;
    font-weight: bold;
    width: 90%;
}

.no-border {
    border: none;
    box-shadow: none;
}

/* Para WebKit (Chrome, Safari, Edge) */
input[type=number]::-webkit-outer-spin-button,
input[type=number]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}
/* Para Firefox */
input[type=number] {
    -moz-appearance: textfield;
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