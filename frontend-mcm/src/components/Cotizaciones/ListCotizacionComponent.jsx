import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import styled from "styled-components";
import editar from "../../images/editar.png"
import HeaderComponents from "../Headers/HeaderComponents";
import CotizacionService from '../../services/CotizacionService'

function ListCotizacionComponent() {
    const initialState = {
        pedido: "",
        fecha: "",
        estado: "",
        rutCliente: "",
    };
    const [CotizacionEntity, setCotizacionEntity] = useState([]);
    const [input, setInput] = useState(initialState);
    const navigate = useNavigate();

    useEffect(() => {
        CotizacionService.getCotizaciones().then((res) => {
            setCotizacionEntity(res.data);
        });
    }, []);

        
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


    const buscarPedido = () => {
        CotizacionService.getCotizacionByPedido(input.pedido).then((res) => {
            setCotizacionEntity(res.data);
        });
    }
    const buscarEstado = () => {
        CotizacionService.getCotizacionByEstado(input.estado).then((res) => {
            setCotizacionEntity(res.data);
        });
    }

    const buscarFecha = () => {
        CotizacionService.getCotizacionByFecha(input.fecha).then((res) => {
            setCotizacionEntity(res.data);
        });
    }

    const buscarRutCliente = () => {
        CotizacionService.getCotizacionByRutCliente(input.rutCliente).then((res) => {
            setCotizacionEntity(res.data);
        });
    }

    const crearCotizacion = () => {
        navigate("/crear-cotizacion");
    }

    const handleKeyPressPedido = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarPedido(); // Llama a la función que deseas ejecutar
        }
    };
    const handleKeyPressEstado = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarEstado(); // Llama a la función que deseas ejecutar
        }
    };

    const handleKeyPressFecha = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarFecha(); // Llama a la función que deseas ejecutar
        }
    };

    const handleKeyPressRutCliente = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarRutCliente(); // Llama a la función que deseas ejecutar
        }
    };


    const ChangeViendoCotizacion = (idCotizacion, pedido, fecha, estado, rutCliente) => {
        const datos = {
            idCotizacion: idCotizacion,
            pedido: pedido,
            fecha: fecha,
            estado: estado,
            rutCliente: rutCliente
        };
        const datosComoTexto = JSON.stringify(datos);
        navigate(`/info-cotizacion/${encodeURIComponent(datosComoTexto)}`);
    };
    return(
        <div>
            <NavStyle>
                <HeaderComponents/>
                <div className="container">
                    <div className="container-1">
                        <div className="inline-forms-container">
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="pedido" value = {input.pedido} onChange={changePedidoHandler}>
                                    <Form.Label className="agregar">Buscar cotización por Nombre de pedido:</Form.Label>
                                    <Form.Control className="agregar" type="text" name="pedido" placeholder="Ingrese nombre del pedido" onKeyPress={handleKeyPressPedido}/>
                                </Form.Group>
                                <Button className="boton" onClick={buscarPedido}>Buscar por pedido</Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="estado" value = {input.estado} onChange={changeEstadoHandler}>
                                    <Form.Label className="agregar">Buscar por Estado del pedido:</Form.Label>
                                    <Form.Control className="agregar" type="text" name="estado" placeholder="En espera, listo, etc." onKeyPress={handleKeyPressEstado}/>
                                </Form.Group>
                                <Button className="boton" onClick={buscarEstado}>Buscar por estado</Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="fecha" value = {input.fecha} onChange={changeFechaHandler}>
                                    <Form.Label className="agregar">Buscar por fecha de pedido:</Form.Label>
                                    <Form.Control className="agregar" type="text" name="fecha" placeholder="yyyy-MM-dd" onKeyPress={handleKeyPressFecha}/>
                                </Form.Group>
                                <Button className="boton" onClick={buscarFecha}>Buscar por fecha</Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="rutCliente" value = {input.rutCliente} onChange={changeRutClienteHandler}>
                                    <Form.Label className="agregar">Buscar pedido por Rut del Cliente:</Form.Label>
                                    <Form.Control className="agregar" type="text" name="rutCliente" placeholder="12.345.678-8" onKeyPress={handleKeyPressRutCliente}/>
                                </Form.Group>
                                <Button className="boton" onClick={buscarRutCliente}>Buscar por Rut de Cliente</Button>
                            </Form>
                        </div>
                        <div className="btn-inf">
                            <Button className="boton" onClick={crearCotizacion}>Ingresar Cotización</Button>
                        </div>
                    </div>
                    <div align="center" className="container-2">
                        <h1><b> Listado de Cotizaciones</b></h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>Pedido</th>
                                    <th>Fecha</th>
                                    <th>Estado</th>
                                    <th>Rut Cliente</th>
                                    <th>Más información</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    CotizacionEntity.map((cotizacion) => (
                                        <tr key= {cotizacion.idCotizacion}>
                                            <td> {cotizacion.pedido} </td>
                                            <td> {cotizacion.fecha} </td>
                                            <td> {cotizacion.estado} </td>
                                            <td> {cotizacion.rutCliente} </td>
                                            <td style={{textAlign: 'center', verticalAlign: 'middle', width:'1%'}}>
                                            <img id="editar" src={editar} alt="editar" onClick={() => ChangeViendoCotizacion(cotizacion.idCotizacion, cotizacion.pedido, cotizacion.fecha, cotizacion.estado, cotizacion.rutCliente)}/>
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

export default ListCotizacionComponent;

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
    font-size: 14px;
    border-radius: 30px;
    border: 1px solid #ccc;
    font-family: "Segoe UI";
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
    font-size: 18px;
}

.boton:hover{
    border: 1px solid black;
}

/* Fuente de la letra*/

td, th, h1, Label, Control, Button{
    font-family: 'Pacifico',serif;
}
`