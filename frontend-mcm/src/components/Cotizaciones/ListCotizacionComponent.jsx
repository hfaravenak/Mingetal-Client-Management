import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

import editar from "../../images/editar.png";

import HeaderComponents from "../Headers/HeaderComponents";
import CotizacionService from "../../services/CotizacionService";
import ClienteService from "../../services/ClienteService";

function ListCotizacionComponent() {
    const formatFecha = (fecha) => {
        if(fecha===null){
            return "-";
        }
        const [year, month, day] = fecha.split("-");
        return `${day}-${month}-${year}`;
    };

    const navigate = useNavigate();

    const initialState = {
        pedido: "",
        fecha: "",
        estado: "",
        rutCliente: "",
        nombre: "", // Añadido para la búsqueda por nombre del cliente
    };
    const [input, setInput] = useState(initialState);
    
    const [CotizacionEntity, setCotizacionEntity] = useState([]);
    const [ClienteEntity, setClienteEntity] = useState([]);
    const [filtered, setFiltered] = useState(false); // Nuevo estado para controlar si la tabla está filtrada

    useEffect(() => {
        CotizacionService.getCotizaciones().then((res) => {
            setCotizacionEntity(res.data);
        });
        ClienteService.getClientes().then((res) => {
            setClienteEntity(res.data);
        });
    }, []);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setInput({ ...input, [name]: value });
    };

    const buscarPedido = () => {
        CotizacionService.getCotizacionByPedido(input.pedido).then((res) => {
            setCotizacionEntity(res.data);
            setFiltered(true);
            setInput({...input, pedido: ""});
        });
    };
    const buscarEstado = () => {
        CotizacionService.getCotizacionByEstado(input.estado).then((res) => {
            setCotizacionEntity(res.data);
            setFiltered(true);
            setInput({...input, estado: ""});
        });
    };
    const buscarFecha = () => {
        CotizacionService.getCotizacionByFecha(input.fecha).then((res) => {
            setCotizacionEntity(res.data);
            setFiltered(true);
            setInput({...input, fecha: ""});
        });
    };
    const buscarRutCliente = () => {
        CotizacionService.getCotizacionByRutCliente(input.rutCliente).then((res) => {
            setCotizacionEntity(res.data);
            setFiltered(true);
            setInput({...input, rutCliente: ""});
        });
    };

    const buscarNombreCliente = () => {
        const clienteEncontrado = ClienteEntity.find(cliente => cliente.nombre.toLowerCase() === input.nombre.toLowerCase());
        if (clienteEncontrado) {
            CotizacionService.getCotizacionByRutCliente(clienteEncontrado.rut).then((res) => {
                setCotizacionEntity(res.data);
                setFiltered(true);
                setInput({...input, nombre: ""});
            });
        } 
    };

    const handleKeyPressPedido = (event) => {
        if (event.key === "Enter") {
            event.preventDefault();
            buscarPedido();
        }
    };
    const handleKeyPressEstado = (event) => {
        if (event.key === "Enter") {
            event.preventDefault();
            buscarEstado();
        }
    };
    const handleKeyPressFecha = (event) => {
        if (event.key === "Enter") {
            event.preventDefault();
            buscarFecha();
        }
    };
    const handleKeyPressRutCliente = (event) => {
        if (event.key === "Enter") {
            event.preventDefault();
            buscarRutCliente();
        }
    };
    const handleKeyPressNombreCliente = (event) => {
        if (event.key === "Enter") {
            event.preventDefault();
            buscarNombreCliente();
        }
    };

    const crearCotizacion = () => {
        navigate("/crear-cotizacion");
    };

    const busquedaCliente = (rut) => {
        return ClienteEntity.find(cliente => cliente.rut === rut) || {};
    };

    const ChangeViendoCotizacion = (todoElDato) => {
        const datos = {
            idCotizacion: todoElDato.idCotizacion,
            pedido: todoElDato.pedido,
            fecha: todoElDato.fecha,
            estado: todoElDato.estado,
            rutCliente: todoElDato.rutCliente,
            cliente: busquedaCliente(todoElDato.rutCliente),
        };
        const datosComoTexto = JSON.stringify(datos);
        navigate(`/info-cotizacion/${encodeURIComponent(datosComoTexto)}`);
    };

    const mostrarTodasCotizaciones = () => {
        CotizacionService.getCotizaciones().then((res) => {
            setCotizacionEntity(res.data);
            setFiltered(false);
        });
    };

    return (
        <div>
            <NavStyle>
                <HeaderComponents />
                <div className="container">
                    <div className="container-1">
                        <div className="inline-forms-container">
                            <Form className="inline-form">
                                <Form.Group controlId="pedido">
                                    <Form.Label className="agregar">Buscar cotización por Nombre de pedido:</Form.Label>
                                    <Form.Control
                                        className="agregar"
                                        type="text"
                                        name="pedido"
                                        placeholder="Ingrese nombre del pedido"
                                        onKeyPress={handleKeyPressPedido}
                                        value={input.pedido}
                                        onChange={handleInputChange}
                                    />
                                </Form.Group>
                                <Button className="boton" onClick={buscarPedido}>
                                    Buscar por pedido
                                </Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group controlId="nombre">
                                    <Form.Label className="agregar">Buscar cotización por Nombre de cliente:</Form.Label>
                                    <Form.Control
                                        className="agregar"
                                        type="text"
                                        name="nombre"
                                        placeholder="Ingrese nombre del cliente"
                                        onKeyPress={handleKeyPressNombreCliente}
                                        value={input.nombre}
                                        onChange={handleInputChange}
                                    />
                                </Form.Group>
                                <Button className="boton" onClick={buscarNombreCliente}>
                                    Buscar por nombre
                                </Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group controlId="estado">
                                    <Form.Label className="agregar">Buscar por Estado del pedido:</Form.Label>
                                    <Form.Select
                                        className="agregar"
                                        name="estado"
                                        value={input.estado}
                                        onChange={handleInputChange}
                                        onKeyPress={handleKeyPressEstado}
                                    >
                                        <option value="">Seleccionar estado</option>
                                        <option value="En espera">En espera</option>
                                        <option value="Listo">Listo</option>
                                    </Form.Select>
                                </Form.Group>
                                <Button className="boton" onClick={buscarEstado}>
                                    Buscar por estado
                                </Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group controlId="fecha">
                                    <Form.Label className="agregar">Buscar por fecha de pedido:</Form.Label>
                                    <Form.Control
                                        className="agregar"
                                        type="date"
                                        name="fecha"
                                        value={input.fecha}
                                        onChange={handleInputChange}
                                        onKeyPress={handleKeyPressFecha}
                                    />
                                </Form.Group>
                                <Button className="boton" onClick={buscarFecha}>
                                    Buscar por fecha
                                </Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group controlId="rutCliente">
                                    <Form.Label className="agregar">Buscar pedido por Rut del Cliente:</Form.Label>
                                    <Form.Control
                                        className="agregar"
                                        type="text"
                                        name="rutCliente"
                                        placeholder="12.345.678-9"
                                        onKeyPress={handleKeyPressRutCliente}
                                        value={input.rutCliente}
                                        onChange={handleInputChange}
                                    />
                                </Form.Group>
                                <Button className="boton" onClick={buscarRutCliente}>
                                    Buscar por Rut de Cliente
                                </Button>
                            </Form>
                        </div>
                        <div className="btn-inf">
                            <Button className="boton" onClick={crearCotizacion}>
                                Ingresar Cotización
                            </Button>
                        </div>
                    </div>
                    <div align="center" className="container-2">
                        <h1>
                            <b> Listado de Cotizaciones</b>
                        </h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>Pedido</th>
                                    <th>Nombre Cliente</th>
                                    <th>Rut Cliente</th>
                                    <th>Fecha</th>
                                    <th>Estado</th>
                                    <th>Más información</th>
                                </tr>
                            </thead>
                            <tbody>
                                {CotizacionEntity.map((cotizacion) => (
                                    <tr key={cotizacion.idCotizacion}>
                                        <td> {cotizacion.pedido} </td>
                                        <td> {busquedaCliente(cotizacion.rutCliente).nombre} </td>
                                        <td> {cotizacion.rutCliente} </td>
                                        <td> {formatFecha(cotizacion.fecha)} </td>
                                        <td> {cotizacion.estado} </td>
                                        <td style={{ textAlign: "center", verticalAlign: "middle", width: "1%" }}>
                                            <img
                                                id="editar"
                                                src={editar}
                                                alt="editar"
                                                onClick={() => ChangeViendoCotizacion(cotizacion)}
                                            />
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                        {filtered && (
                            <Button className="boton-atras" onClick={mostrarTodasCotizaciones}>
                                Atrás
                            </Button>
                        )}
                    </div>
                </div>
            </NavStyle>
        </div>
    );
}

export default ListCotizacionComponent;

const NavStyle = styled.nav`
    /* Separacion de las partes */

    .container {
        margin: 2%;
        border: 2px solid #d5d5d5;
        background-color: #f0f0f0;
        display: flex;
        flex-direction: row;
        gap: 20px;
        height: 100%;
    }
    .container-1 {
        background-color: #f0f0f0;
        width: 10%;
        flex-shrink: 0; /* Hace que el contenedor no se encoja */
        overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
        padding: 5%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
        display: flex;
        flex-direction: column;
        height: 58.9vh;
    }
    .container-2 {
        background-color: #f0f0f0;
        flex-grow: 1; /* El lado derecho es flexible y ocupará todo el espacio restante */
        overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
        padding: 1%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
        max-height: calc(0px + 74.3vh); /* Asegura que el contenedor no exceda la altura de la ventana */
    }

    /* Todo la parte de la tabla */

    .content-table {
        border-collapse: collapse;
        margin-left: 1;
        font-size: 0.9em;
        min-width: 100px;
        border-radius: 5px 5px 0 0;
        overflow: hidden;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    }
    .content-table thead tr {
        background-color: #d2712b;
        color: #ffffff;
        text-align: left;
        font-weight: bold;
    }
    .content-table th,
    .content-table td {
        padding: 12px 15px;
    }

    .content-table td {
        font-size: 18px;
    }

    .content-table tbody tr {
        border-bottom: 1px solid #dddddd;
    }
    .content-table tbody tr:nth-of-type(even) {
        background-color: #f3f3f3;
    }
    .content-table tbody tr:last-of-type {
        border-bottom: 2px solid #009879;
    }
    .content-table tbody tr.active-row {
        font-weight: bold;
        color: #009879;
    }

    img {
        width: 50%;
        object-fit: cover;
    }

    img:hover {
        cursor: pointer;
    }

    th:hover,
    td:hover {
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
        flex-grow: 1;
        display: flex;
        flex-direction: column;
        gap: 20px;
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
    input[type="text"],
    input[type="date"] {
        background-color: rgb(201, 201, 201);
        width: 100%;
        padding: 10px;
        font-size: 14px;
        border-radius: 30px;
        border: 1px solid #ccc;
        font-family: "Segoe UI";
    }
    select {
        background-color: rgb(201, 201, 201);
        width: 100%;
        padding: 10px;
        font-size: 16px;
        border-radius: 30px;
        border: 1px solid #ccc;
    }
    button {
        color: #fff;
        margin-left: 5px;
        margin-top: 10px;
        padding: 10px 20px;
        font-size: 16px;
        border-radius: 30px;
        border: none;
        cursor: pointer;
    }
    .boton {
        background-color: #d2712b;
    }

    /* Apartado del boton de crear */

    .btn-inf .boton {
        font-size: 18px;
    }

    .boton:hover {
        border: 1px solid black;
    }

    /* Boton "Atrás" */
    .boton-atras {
        margin-top: 20px;
        background-color: #d2712b;
        color: white;
        font-size: 16px;
        border-radius: 30px;
        border: none;
        cursor: pointer;
    }

    /* Fuente de la letra */

    td,
    th,
    h1,
    Label,
    Control,
    Button {
        font-family: "Pacifico", serif;
    }
`;

