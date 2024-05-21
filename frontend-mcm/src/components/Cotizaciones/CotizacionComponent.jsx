import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import HeaderComponents from "../Headers/HeaderComponents";
import styled from "styled-components";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Swal from "sweetalert2";

import cotizaciones from "../../images/cotizacion.png";
import CotizacionService from "../../services/CotizacionService";

function CotizacionComponent() {

    const initialState = {
        idCotizacion: "",
        pedido: "",
        fecha: "",
        estado: "",
        rutCliente: "",
    };
    const [input, setInput] = useState(initialState);

    const [mostrarCard, setMostrarCard] = useState(false);

    const { cotizacion } = useParams();
    const datos = JSON.parse(decodeURIComponent(cotizacion));

    const formatFecha = (fecha) => {
        const [year, month, day] = fecha.split("-");
        return `${day}-${month}-${year}`;
    };
   
    const navigate = useNavigate();

    const nathing = () => {
        navigate("");
    };

    const changePedidoHandler = event => {
        setInput({ ...input, pedido: event.target.value });
    };
    const changeFechaHandler = event => {
        setInput({ ...input, fecha: event.target.value });
    };
    const changeEstadoHandler = event => {
        setInput({ ...input, estado: event.target.value });
    };
    const changeRutClienteHandler = event => {
        setInput({ ...input, rutCliente: event.target.value });
    };

    const changeMostrarCard = () => {
        setInput({
            idCotizacion: datos.idCotizacion,
            pedido: datos.pedido,
            fecha: datos.fecha,
            estado: datos.estado,
            rutCliente: datos.rutCliente,
        });
        setMostrarCard(true);
    }

    const enviarDatos = () => {
        Swal.fire({
            title: "¿Seguro que desea modificar esta cotización?",
            icon: "question",   
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        }).then((result) => {
            if (result.isConfirmed) {
                let updateCotizacion = {
                    idCotizacion: input.idCotizacion,
                    pedido: input.pedido,
                    fecha: input.fecha,
                    estado: input.estado,
                    rutCliente: input.rutCliente,
                }
                CotizacionService.putCotizacion(updateCotizacion);
                navigate(`/info-cotizacion/${encodeURIComponent(JSON.stringify(updateCotizacion))}`);
                setMostrarCard(false);
            }
        });
    }
    const EliminarCotizacion = () => {
        Swal.fire({
            title: "¿Seguro que desea eliminar esta cotizacion?",
            text: "Esta acción es irreversible y no podrá recuperar la cotizacion perdida.",
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
                let idCotizacion = datos.idCotizacion;
                CotizacionService.deleteCotizacion(idCotizacion);
                Swal.fire({
                    title: 'Eliminando...',
                    text: 'Por favor espera',
                    timer: 1000, // 3 segundos
                    didOpen: () => {
                        Swal.showLoading();
                    },
                    willClose: () => {
                        navigate("/cotizaciones");
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
                    <div className="container"style={{ display: "flex", justifyContent: "center", alignItems: "flex-start", height: "100vh" }}>
                            <div className="card" onClick={nathing}>
                                <div className="contenedor-img">
                                    <img id="cotizaciones" src={cotizaciones} alt="cotizaciones" style={{ width: "400px" }}/>
                                </div>
                                <div className="inline-forms-container contenedor-informacion">
                                    <Form className="inline-form">
                                        <Form.Group className="mb-3" controlId="pedido" value = {input.pedido} onChange={changePedidoHandler}>
                                            <Form.Control value={input.pedido} className="font-h3 no-border" type="text" name="Pedido" placeholder="PEDIDO X"/>
                                        </Form.Group>
                                    </Form>
                                    <Form className="inline-form" style={{marginTop:"2.2%"}}>
                                        <Form.Group className="mb-3" controlId="fecha" value = {input.fecha} onChange={changeFechaHandler}>
                                            <Form.Label className="font-h2">Fecha:</Form.Label>
                                            <Form.Control
                                                className="agregar"
                                                type="date"
                                                name="fecha"
                                                value={input.fecha}
                                                onChange={changeFechaHandler}
                                                />
                                        </Form.Group>
                                    </Form>
                                    <Form className="inline-form">
                                        <Form.Group className="mb-3" controlId="estado" value = {input.estado} onChange={changeEstadoHandler}>
                                            <Form.Label className="font-h2 eliminarMargen">Estado:</Form.Label>
                                            <Form.Select
                                                className="agregar"
                                                name="estado"
                                                value={input.estado}
                                                onChange={changeEstadoHandler}
                                            >
                                                <option value="">Seleccionar estado</option>
                                                <option value="Listo">Listo</option>
                                                <option value="En espera">En espera</option>
                                            </Form.Select>
                                        </Form.Group>
                                    </Form>
                                    <Form className="inline-form">
                                        <Form.Group className="mb-3" controlId="rutCliente" value = {input.rutCliente} onChange={changeRutClienteHandler}>
                                            <Form.Label className="font-h2">Rut Cliente:</Form.Label>
                                            <Form.Control value={input.rutCliente} className="font-h2-control no-border" type="text" name="Rut del cliente" placeholder="12.345.678-9"/>
                                        </Form.Group>
                                    </Form>
                                </div>
                                <div style={{ display: "flex", justifyContent: "center", padding:"5px"}}>
                            <Button className="aceptar" onClick={enviarDatos}>Aceptar</Button>
                            <Button className="cancelar" onClick={CancelarEdit}>Cancelar</Button>
                            </div>
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
                    <div className="container" style={{ display: "flex", justifyContent: "center", alignItems: "flex-start", height: "100vh" }}>
                            <div className="card" onClick={nathing} style={{ display: "inline"}}>
                                <div className="contenedor-img">
                                    <img id="cotizaciones" src={cotizaciones} alt="cotizaciones" style={{ width: "400px" }} />
                                </div>
                                <div className="contenedor-informacion">
                                    <h2>{datos.pedido}</h2>
                                    <h3>fecha: {formatFecha(datos.fecha)}</h3>
                                    <h3>estado: {datos.estado}</h3>
                                    <h3>Rut del cliente: {datos.rutCliente}</h3>
                                </div>
                                <div style={{ display: "flex", justifyContent: "center", padding:"5px"}}>
                                <Button className="editar"  onClick={changeMostrarCard}>Editar</Button>
                                <Button className="eliminar"  onClick={EliminarCotizacion}>Eliminar</Button>
                                </div>
                            </div>

                    </div>
                </NavStyle>
            </div>
        );
    }

    
}
export default CotizacionComponent;

const NavStyle = styled.nav`

/* Separacion de las partes */

.container{
    margin: 2%;
    border: 2px solid #D5D5D5;
    background-color: #F0F0F0;
    display: flex;
    flex-direction: row;
    gap: 10px;
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