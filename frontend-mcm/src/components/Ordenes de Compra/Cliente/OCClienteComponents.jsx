import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import clientes from "../../../images/cliente.png";
import editar from "../../../images/editar.png";

import HeaderComponents from "../../Headers/HeaderComponents";
import OrdenesDeCompraClienteService from "../../../services/OrdenesDeCompraClienteService";
import ProductoService from "../../../services/ProductoService";

function OCClienteComponents() {
    const formatFecha = (fecha) => {
        const [year, month, day] = fecha.split("-");
        return `${day}-${month}-${year}`;
    };
    const navigate = useNavigate();

    const { oc_cliente } = useParams();
    const datos = JSON.parse(decodeURIComponent(oc_cliente));

    const initialState = {
        estado_factura: "",
        estado_pago: "",
        estado_entrega: "",
        modo_pago: "",
        fecha_pago: "",
        empresa_despacho: "",
        tiempo_de_pago: "",
        numero_cheque: "",
        numero_factura: "",
    };
    const [input, setInput] = useState(initialState);

    const [ListProductos, setListProductos] = useState([]);
    useEffect(() => {
        ProductoService.getListByOCCliente(datos.id).then((res) => {
            setListProductos(res.data);
        });
    }, [datos.id]);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setInput({ ...input, [name]: value });
    };

    const [mostrarCard, setMostrarCard] = useState(false);
    const changeMostrarCard = () => {
        setInput({
            estado_factura: datos.estado_factura,
            estado_pago: datos.estado_pago,
            estado_entrega: datos.estado_entrega,
            modo_pago: datos.modo_pago,
            fecha_pago: datos.fecha_pago,
            empresa_despacho: datos.empresa_despacho,
            tiempo_de_pago: datos.tiempo_de_pago,
            numero_cheque: datos.numero_cheque,
            numero_factura: datos.numero_factura,
        });
        setMostrarCard(true);
    };

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
                    id_cliente: datos.id_cliente,
                    estado_factura: input.estado_factura,
                    estado_pago: input.estado_pago,
                    valor_pago: datos.valor_pago,
                    fecha_pago: input.fecha_pago,
                    fecha_solicitud: datos.fecha_solicitud,
                    estado_entrega: input.estado_entrega,
                    modo_pago: input.modo_pago,
                    fecha_inicio_pago: datos.fecha_inicio_pago,
                    tiempo_de_pago: input.tiempo_de_pago,
                    numero_cheque: input.numero_cheque,
                    numero_factura: input.numero_factura,
                    empresa_despacho: input.empresa_despacho,
                };
                OrdenesDeCompraClienteService.putOCCliente(updateOC);
                Swal.fire({
                    title: "Eliminando...",
                    text: "Por favor espera",
                    timer: 1000, // 3 segundos
                    didOpen: () => {
                        Swal.showLoading();
                    },
                    willClose: () => {
                        let OtherUpdateOC = {
                            id: datos.id,
                            id_cliente: datos.id_cliente,
                            estado_factura: input.estado_factura,
                            estado_pago: input.estado_pago,
                            valor_pago: datos.valor_pago,
                            fecha_pago: input.fecha_pago,
                            fecha_solicitud: datos.fecha_solicitud,
                            estado_entrega: input.estado_entrega,
                            modo_pago: input.modo_pago,
                            fecha_inicio_pago: datos.fecha_inicio_pago,
                            tiempo_de_pago: input.tiempo_de_pago,
                            numero_cheque: input.numero_cheque,
                            numero_factura: input.numero_factura,
                            empresa_despacho: input.empresa_despacho,
                            cliente: datos.cliente,
                        };
                        navigate(`/oc/cliente/mas info/${encodeURIComponent(JSON.stringify(OtherUpdateOC))}`);
                        setMostrarCard(false);
                    },
                });
            }
        });
    };
    const CancelarEdit = () => {
        setMostrarCard(false);
    };

    const EliminarCliente = () => {
        Swal.fire({
            title: "¿Seguro de que desea eliminar esta orden de compra?",
            text: "Esta acción es irreversible y no podrá recuperar la Orden de compra perdida. Además no se recomienda hacer esta acción",
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
                    if (value === "Confirmo") {
                        resolve();
                    } else {
                        resolve("ERROR al introducir la palabra");
                    }
                });
            },
        }).then((result) => {
            if (result.isConfirmed) {
                let id = datos.id;
                OrdenesDeCompraClienteService.deleteOCCliente(id);
                Swal.fire({
                    title: "Eliminando...",
                    text: "Por favor espera",
                    timer: 3000, // 3 segundos
                    didOpen: () => {
                        Swal.showLoading();
                    },
                    willClose: () => {
                        navigate("/oc/cliente");
                    },
                });
            }
        });
    };

    const ChangeViendoProducto = (todoElDato) => {
        const datos = {
            id: todoElDato.id,
            tipo: todoElDato.tipo,
            nombre: todoElDato.nombre,
            valor: todoElDato.valor,
            valor_final: todoElDato.valor_final,
            cantidad: todoElDato.cantidad,
        };
        const datosComoTexto = JSON.stringify(datos);
        navigate(`/productos/mas-info/${encodeURIComponent(datosComoTexto)}`);
    };

    if (mostrarCard) {
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
                                    <h2>{datos.cliente.nombre}</h2>
                                    <h3>Empresa: {datos.cliente.empresa}</h3>
                                    <h3>Rut: {datos.cliente.rut}</h3>
                                    <h3>Correo: {datos.cliente.email}</h3>
                                    <h3>Telefono: {datos.cliente.telefono}</h3>
                                </div>
                            </div>
                            <Button className="aceptar" onClick={enviarDatos}>
                                Aceptar
                            </Button>
                            <Button className="cancelar" onClick={CancelarEdit}>
                                Cancelar
                            </Button>
                        </div>
                        <div className="container-2">
                            <h1>
                                <b> Ordenes de Compra</b>
                            </h1>
                            <Form>
                                <table border="1" className="content-table">
                                    <thead>
                                        <tr>
                                            <th>Ref #</th>
                                            <th>Fecha de la Solicitud</th>
                                            <th>Estado de la Entrega</th>
                                            <th>Empresa de Despacho</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr key={datos.id}>
                                            <td style={{ color: "gray" }}> #{datos.id} </td>
                                            <td style={{ color: "gray" }}> {datos.fecha_solicitud} </td>
                                            <td>
                                                <div className=" contenedor-informacion">
                                                    <Form.Group controlId="estado_entrega">
                                                        <Form.Select
                                                            style={{ width: "100%" }}
                                                            value={input.estado_entrega}
                                                            onChange={handleInputChange}
                                                            className="font-h2 no-border"
                                                            name="estado_entrega"
                                                        >
                                                            <option value="No Entregado">No Entregado</option>
                                                            <option value="Entregado">Entregado</option>
                                                        </Form.Select>
                                                    </Form.Group>
                                                </div>
                                            </td>
                                            <td>
                                                <div className=" contenedor-informacion">
                                                    <Form.Group controlId="empresa_despacho">
                                                        <Form.Control
                                                            value={input.empresa_despacho}
                                                            onChange={handleInputChange}
                                                            className="font-h2 no-border"
                                                            type="text"
                                                            name="empresa_despacho"
                                                            placeholder="TransporteZ"
                                                        />
                                                    </Form.Group>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <h1>
                                    <b> Pago</b>
                                </h1>
                                <table border="1" className="content-table">
                                    <thead>
                                        <tr>
                                            <th>Valor del Pago</th>
                                            <th>Modo del Pago</th>
                                            <th>Numero del cheque</th>
                                            <th>Estado del Pago</th>
                                            <th>Fecha de Inicio del Pago</th>
                                            <th>Tiempo para pagar</th>
                                            <th>Fecha del Pago</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr key={datos.id}>
                                            <td style={{ color: "gray" }}> {datos.valor_pago} </td>
                                            <td>
                                                <div className=" contenedor-informacion">
                                                    <Form.Group controlId="modo_pago">
                                                        <Form.Select
                                                            style={{ width: "100%" }}
                                                            value={input.modo_pago}
                                                            onChange={handleInputChange}
                                                            className="font-h2 no-border"
                                                            name="modo_pago"
                                                        >
                                                            <option value="Transferencia">Transferencia</option>
                                                            <option value="Efectivo">Efectivo</option>
                                                            <option value="Cheque">Cheque</option>
                                                        </Form.Select>
                                                    </Form.Group>
                                                </div>
                                            </td>
                                            <td>
                                                <div className=" contenedor-informacion">
                                                    <Form.Group controlId="numero_cheque">
                                                        <Form.Control
                                                            style={{ width: "100%" }}
                                                            value={input.numero_cheque}
                                                            onChange={handleInputChange}
                                                            className="font-h2 no-border"
                                                            type="number"
                                                            name="numero_cheque"
                                                            placeholder="25"
                                                        />
                                                    </Form.Group>
                                                </div>
                                            </td>
                                            <td>
                                                <div className=" contenedor-informacion">
                                                    <Form.Group controlId="estado_pago">
                                                        <Form.Select
                                                            style={{ width: "100%" }}
                                                            value={input.estado_pago}
                                                            onChange={handleInputChange}
                                                            className="font-h2 no-border"
                                                            name="estado_pago"
                                                        >
                                                            <option value="No Pagado">No Pagado</option>
                                                            <option value="Pagado">Pagado</option>
                                                        </Form.Select>
                                                    </Form.Group>
                                                </div>
                                            </td>
                                            <td style={{ color: "gray" }}> {datos.fecha_inicio_pago} </td>
                                            <td>
                                                <div className=" contenedor-informacion">
                                                    <Form.Group controlId="tiempo_de_pago">
                                                        <Form.Control
                                                            style={{ width: "100%" }}
                                                            value={input.tiempo_de_pago}
                                                            onChange={handleInputChange}
                                                            className="font-h2 no-border"
                                                            type="number"
                                                            name="tiempo_de_pago"
                                                            placeholder="25"
                                                        />
                                                    </Form.Group>
                                                </div>
                                            </td>
                                            <td>
                                                <div className=" contenedor-informacion">
                                                    <Form.Group controlId="fecha_pago">
                                                        <Form.Control
                                                            style={{ width: "100%" }}
                                                            value={input.fecha_pago}
                                                            onChange={handleInputChange}
                                                            className="font-h2 no-border"
                                                            type="date"
                                                            name="fecha_pago"
                                                        />
                                                    </Form.Group>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <h1>
                                    <b> Factura</b>
                                </h1>
                                <table border="1" className="content-table">
                                    <thead>
                                        <tr>
                                            <th>Numero de la Factura</th>
                                            <th>Estado de la Factura</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr key={datos.id}>
                                            <td>
                                                <div className=" contenedor-informacion">
                                                    <Form.Group controlId="numero_factura">
                                                        <Form.Control
                                                            style={{ width: "100%" }}
                                                            value={input.numero_factura}
                                                            onChange={handleInputChange}
                                                            className="font-h2 no-border"
                                                            type="number"
                                                            name="numero_factura"
                                                            placeholder="25"
                                                        />
                                                    </Form.Group>
                                                </div>
                                            </td>
                                            <td>
                                                <div className=" contenedor-informacion">
                                                    <Form.Group controlId="estado_factura">
                                                        <Form.Select
                                                            style={{ width: "100%" }}
                                                            value={input.estado_factura}
                                                            onChange={handleInputChange}
                                                            className="font-h2 no-border"
                                                            name="estado_factura"
                                                        >
                                                            <option value="No Emitida">No Emitida</option>
                                                            <option value="Emitida">Emitida</option>
                                                        </Form.Select>
                                                    </Form.Group>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </Form>
                            <h1>
                                <b> Lista de Productos</b>
                            </h1>
                            <table border="1" className="content-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Nombre</th>
                                        <th>Tipo</th>
                                        <th>Valor Final</th>
                                        <th>Cantidad</th>
                                        <th>Más información</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {ListProductos.map((productos) => (
                                        <tr key={productos.id}>
                                            <td> #{productos.id} </td>
                                            <td> {productos.nombre}</td>
                                            <td> {productos.tipo} </td>
                                            <td> {productos.valor_final} </td>
                                            <td> {productos.cantidad} </td>
                                            <td style={{ textAlign: "center", verticalAlign: "middle", width: "1%" }}>
                                                <img
                                                    id="editar"
                                                    src={editar}
                                                    alt="editar"
                                                    onClick={() => ChangeViendoProducto(productos)}
                                                />
                                            </td>
                                        </tr>
                                    ))}
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
                                <h2>{datos.cliente.nombre}</h2>
                                <h3>Empresa: {datos.cliente.empresa}</h3>
                                <h3>Rut: {datos.cliente.rut}</h3>
                                <h3>Correo: {datos.cliente.email}</h3>
                                <h3>Telefono: {datos.cliente.telefono}</h3>
                            </div>
                        </div>
                        <Button className="editar" onClick={changeMostrarCard}>
                            Editar
                        </Button>
                        <Button className="eliminar" onClick={EliminarCliente}>
                            Eliminar
                        </Button>
                    </div>
                    <div className="container-2">
                        <h1>
                            <b> Ordenes de Compra</b>
                        </h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>Ref #</th>
                                    <th>Fecha de la Solicitud</th>
                                    <th>Estado de la Entrega</th>
                                    <th>Empresa de Despacho</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr key={datos.id}>
                                    <td> #{datos.id} </td>
                                    <td> {formatFecha(datos.fecha_solicitud)} </td>
                                    <td> {datos.estado_entrega} </td>
                                    <td> {datos.empresa_despacho} </td>
                                </tr>
                            </tbody>
                        </table>
                        <h1>
                            <b> Pago</b>
                        </h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>Valor del Pago</th>
                                    <th>Modo del Pago</th>
                                    <th>Numero del cheque</th>
                                    <th>Estado del Pago</th>
                                    <th>Fecha de Inicio del Pago</th>
                                    <th>Tiempo para pagar</th>
                                    <th>Fecha del Pago</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr key={datos.id}>
                                    <td> {datos.valor_pago} </td>
                                    <td> {datos.modo_pago} </td>
                                    <td> {datos.numero_cheque} </td>
                                    <td> {datos.estado_pago} </td>
                                    <td> {formatFecha(datos.fecha_inicio_pago)} </td>
                                    <td> {datos.tiempo_de_pago} </td>
                                    <td> {formatFecha(datos.fecha_pago)} </td>
                                </tr>
                            </tbody>
                        </table>
                        <h1>
                            <b> Factura</b>
                        </h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>Numero de la Factura</th>
                                    <th>Estado de la Factura</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr key={datos.id}>
                                    <td> {datos.numero_factura} </td>
                                    <td> {datos.estado_factura} </td>
                                </tr>
                            </tbody>
                        </table>
                        <h1>
                            <b> Lista de Productos</b>
                        </h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Tipo</th>
                                    <th>Valor Final</th>
                                    <th>Cantidad</th>
                                    <th>Más información</th>
                                </tr>
                            </thead>
                            <tbody>
                                {ListProductos.map((productos) => (
                                    <tr key={productos.id}>
                                        <td> #{productos.id} </td>
                                        <td> {productos.nombre}</td>
                                        <td> {productos.tipo} </td>
                                        <td> {productos.valor_final} </td>
                                        <td> {productos.cantidad} </td>
                                        <td style={{ textAlign: "center", verticalAlign: "middle", width: "1%" }}>
                                            <img
                                                id="editar"
                                                src={editar}
                                                alt="editar"
                                                onClick={() => ChangeViendoProducto(productos)}
                                            />
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </NavStyle>
        </div>
    );
}
export default OCClienteComponents;

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
        height: 80%;
        background-color: #f0f0f0;
        width: 20%;
        flex-shrink: 0; /* Hace que el contenedor no se encoja */
        overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
        padding: 5%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
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

    td img {
        width: 50%;
        object-fit: cover;
    }

    td img:hover {
        cursor: pointer;
    }

    th:hover,
    td:hover {
        cursor: default;
    }

    td,
    th {
        width: 150px;
    }

    /* Por el lado de la información del cliente*/

    .card {
        border: 1px solid black;
        border-radius: 10px;
        background-color: white;
    }
    .card .contenedor-img {
        background-color: #f0f0f0;
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .card .contenedor-informacion {
        background-color: white;
        height: 100%;
    }

    .card .contenedor-informacion h3,
    .card .contenedor-informacion h2,
    .font-h2,
    .font-h3 {
        margin-left: 4%;
    }

    .card .contenedor-informacion h3,
    .font-h2,
    .font-h2-control {
        font-size: 20px;
        font-weight: normal;
    }

    .font-h2 {
        width: 100%;
        background-color: #f0f0f0f0;
    }

    .font-h3 {
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
    input[type="number"]::-webkit-outer-spin-button,
    input[type="number"]::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }
    /* Para Firefox */
    input[type="number"] {
        -moz-appearance: textfield;
    }

    .editar,
    .eliminar,
    .cancelar,
    .aceptar {
        margin-left: 5px;
        margin-top: 10px;
        padding: 10px 20px;
        font-size: 16px;
        border-radius: 30px;
        border: none;
        cursor: pointer;
    }

    .eliminar,
    .cancelar {
        background-color: #550100;
        color: #fff;
    }

    .editar {
        background-color: #39beab;
        color: black;
    }

    .aceptar {
        background-color: #00a768;
        color: black;
    }

    .editar:hover,
    .eliminar:hover,
    .aceptar:hover,
    .cancelar:hover {
        border: 1px solid black;
    }

    /* Fuente de la letra*/

    td,
    th,
    h1,
    h2,
    h3,
    Button,
    .font-h2,
    .font-h3,
    .font-h2-control {
        font-family: "Pacifico", serif;
    }
`;
