import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import HeaderComponents from "../../Headers/HeaderComponents";
import ClienteService from "../../../services/ClienteService";
import ProveedorService from "../../../services/ProveedorService";
import OrdenesDeCompraClienteService from "../../../services/OrdenesDeCompraClienteService";

function OCClienteCrearComponents() {
    const obtenerFechaHoy = () => {
        const hoy = new Date();
        const dia = String(hoy.getDate()).padStart(2, "0");
        const mes = String(hoy.getMonth() + 1).padStart(2, "0");
        const año = hoy.getFullYear();
        return `${año}-${mes}-${dia}`; // Formato yyyy-mm-dd para ser compatible con el tipo de input date
    };

    const initialState = {
        nombre: "",
        fecha_solicitud: obtenerFechaHoy(),
        fecha_entrega: obtenerFechaHoy(),
        estado_entrega: "No Entregado",
        empresa_despacho: "",

        valor_pago: "",
        fecha_pago: "",
        modo_pago: "Transferencia",
        fecha_inicio_pago: obtenerFechaHoy(),
        tiempo_de_pago: "",
        numero_cheque: "",
        estado_pago: "No Pagado",

        numero_factura: "",
        estado_factura: "No Emitida",
    };
    const [input, setInput] = useState(initialState);

    const [id, setID] = useState();
    const [DespachoEntity, setDespachoEntity] = useState([]);
    useEffect(() => {
        ProveedorService.getDespacho().then((res) => {
            setDespachoEntity(res.data);
        });
        OrdenesDeCompraClienteService.getOCCliente().then((res) => {
            setID(res.data.length + 1);
        });
    }, []);

    const navigate = useNavigate();
    
    const [ListProducto, setListProducto] = useState([{ nombre: "", cantidad: "" }]);
    const handleInputChange = (event, index = null) => {
        const { name, value } = event.target;

        if (index !== null) {
            const newListProducto = [...ListProducto];
            newListProducto[index][name] = value;
            setListProducto(newListProducto);
        } else {
            setInput({ ...input, [name]: value });
        }
    };
    const handleAddRow = () => {
        setListProducto([...ListProducto, { nombre: "", cantidad: "" }]);
    };
    const handleMinusRow = () => {
        if (ListProducto.length > 1) {
            setListProducto(ListProducto.slice(0, -1));
        }
    };

    const validateForm = () => {
        const requiredFields = ["nombre", "empresa_despacho", "valor_pago", "tiempo_de_pago", "numero_factura"];
        for (let field of requiredFields) {
            if (!input[field]) {
                return false;
            }
        }
        for (let producto of ListProducto) {
            if (!producto.nombre || !producto.cantidad) {
                return false;
            }
        }
        return true;
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        if (validateForm()) {
            ingresarOCCliente();
        } else {
            Swal.fire({
                title: "Error",
                text: "Por favor, complete todos los campos requeridos.",
                icon: "error",
                confirmButtonText: "OK",
            });
        }
    };

    const ingresarOCCliente = () => {
        Swal.fire({
            title: "¿Desea registrar esta orden de compra?",
            text: "Luego podrá modificar los valores, pero no todos. Recomiendo revisar el contenido de este",
            icon: "question",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        }).then((result) => {
            if (result.isConfirmed) {
                ClienteService.getClienteByNombreTextual(input.nombre).then((res) => {
                    const rut = res.data.rut;

                    if (rut === "" || rut === null) {
                        Swal.fire({
                            title: "Cliente no encontrado",
                            timer: 2000,
                            icon: "warning",
                            timerProgressBar: true,
                            didOpen: () => {
                                Swal.showLoading();
                            },
                        });
                    } else {
                        let newOC = {
                            id_cliente: rut,
                            fecha_solicitud: input.fecha_solicitud,
                            fecha_entrega: input.fecha_entrega,
                            estado_entrega: input.estado_entrega,
                            empresa_despacho: input.empresa_despacho,
                            valor_pago: input.valor_pago,
                            fecha_pago: input.fecha_pago,
                            modo_pago: input.modo_pago,
                            fecha_inicio_pago: input.fecha_inicio_pago,
                            tiempo_de_pago: input.tiempo_de_pago,
                            numero_cheque: input.numero_cheque,
                            estado_pago: input.estado_pago,
                            numero_factura: input.numero_factura,
                            estado_factura: input.estado_factura,
                        };
                        OrdenesDeCompraClienteService.createOCCliente(newOC);

                        Swal.fire({
                            title: "Enviado",
                            timer: 2000,
                            icon: "success",
                            timerProgressBar: true,
                            didOpen: () => {
                                Swal.showLoading();
                            },
                            willClose: () => {
                                navigate("/oc/cliente");
                            },
                        });
                    }
                });
            }
        });
    };

    const [isTableVisibleOC, setisTableVisibleOC] = useState(false);
    const [isTableVisiblePago, setisTableVisiblePago] = useState(false);
    const [isTableVisibleFactura, setisTableVisibleFactura] = useState(false);
    const [isTableVisibleListaProducto, setisTableVisibleListaProducto] = useState(false);

    const toggleTableVisibilityOC = () => {
        setisTableVisibleOC(!isTableVisibleOC);
    };
    const toggleTableVisibilityPago = () => {
        setisTableVisiblePago(!isTableVisiblePago);
    };
    const toggleTableVisibilityFactura = () => {
        setisTableVisibleFactura(!isTableVisibleFactura);
    };
    const toggleTableVisibilityListaProducto = () => {
        setisTableVisibleListaProducto(!isTableVisibleListaProducto);
    };

    return (
        <div>
            <NavStyle>
                <HeaderComponents></HeaderComponents>
                <div className="container">
                    <h1 style={{ marginLeft: "1%" }}>
                        <b>N° O/C: {id}</b>
                    </h1>
                    <Form onSubmit={handleSubmit}>
                        <div className="container-2">
                            <h1 onClick={toggleTableVisibilityOC} style={{ cursor: "pointer" }}>
                                <b> Ordenes de Compra</b>
                                <span style={{ marginLeft: "10px" }}>{isTableVisibleOC ? "−" : "+"}</span>
                            </h1>
                            {isTableVisibleOC && (
                                <table border="1" className="content-table">
                                    <thead>
                                        <tr>
                                            <th>* Nombre</th>
                                            <th>Fecha de la Solicitud</th>
                                            <th>Fecha de la Entrega</th>
                                            <th>Estado de la Entrega</th>
                                            <th>* Empresa de Despacho</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <Form.Group controlId="nombre">
                                                    <Form.Control
                                                        style={{ width: "100%" }}
                                                        className="font-h2 no-border"
                                                        type="text"
                                                        value={input.nombre}
                                                        onChange={handleInputChange}
                                                        name="nombre"
                                                    />
                                                </Form.Group>
                                            </td>
                                            <td>
                                                <Form.Group controlId="fecha_solicitud">
                                                    <Form.Control
                                                        style={{ width: "100%" }}
                                                        className="font-h2 no-border"
                                                        type="date"
                                                        value={input.fecha_solicitud}
                                                        onChange={handleInputChange}
                                                        name="fecha_solicitud"
                                                    />
                                                </Form.Group>
                                            </td>
                                            <td>
                                                <Form.Group controlId="fecha_entrega">
                                                    <Form.Control
                                                        style={{ width: "100%" }}
                                                        className="font-h2 no-border"
                                                        type="date"
                                                        value={input.fecha_entrega}
                                                        onChange={handleInputChange}
                                                        name="fecha_entrega"
                                                    />
                                                </Form.Group>
                                            </td>
                                            <td>
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
                                            </td>
                                            <td>
                                                <Form.Group controlId="empresa_despacho">
                                                    <Form.Select
                                                        style={{ width: "100%" }}
                                                        className="font-h2 no-border"
                                                        name="empresa_despacho"
                                                        value={input.empresa_despacho}
                                                        onChange={handleInputChange}
                                                        required
                                                    >
                                                        <option value="" disabled hidden style={{ color: "gray" }}>
                                                            Elija empresa
                                                        </option>
                                                        {DespachoEntity.map((despacho) => (
                                                            <option
                                                                key={despacho.id_proveedor}
                                                                value={despacho.empresa}
                                                            >
                                                                {despacho.empresa}
                                                            </option>
                                                        ))}
                                                    </Form.Select>
                                                </Form.Group>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            )}
                            <h1 onClick={toggleTableVisibilityPago} style={{ cursor: "pointer" }}>
                                <b> Pago</b>
                                <span style={{ marginLeft: "10px" }}>{isTableVisiblePago ? "−" : "+"}</span>
                            </h1>
                            {isTableVisiblePago && (
                                <table border="1" className="content-table">
                                    <thead>
                                        <tr>
                                            <th>* Valor del Pago</th>
                                            <th>Modo del Pago</th>
                                            <th>Numero del cheque</th>
                                            <th>Estado del Pago</th>
                                            <th>Fecha de Inicio del Pago</th>
                                            <th>* Tiempo para pagar</th>
                                            <th>Fecha del Pago</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <Form.Group controlId="valor_pago">
                                                    <Form.Control
                                                        className="agregar"
                                                        type="number"
                                                        value={input.valor_pago}
                                                        onChange={handleInputChange}
                                                        name="valor_pago"
                                                        required
                                                    />
                                                </Form.Group>
                                            </td>
                                            <td>
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
                                            </td>
                                            <td>
                                                <Form.Group controlId="numero_cheque">
                                                    <Form.Control
                                                        className="agregar"
                                                        type="number"
                                                        value={input.numero_cheque}
                                                        onChange={handleInputChange}
                                                        name="numero_cheque"
                                                    />
                                                </Form.Group>
                                            </td>
                                            <td>
                                                <Form.Group controlId="estado_pago">
                                                    <Form.Select
                                                        style={{ width: "100%" }}
                                                        value={input.estado_pago}
                                                        onChange={handleInputChange}
                                                        className="font-h2 no-border"
                                                        name="estado_pago"
                                                    >
                                                        <option value="No Entregado">No Pagado</option>
                                                        <option value="Entregado">Pagado</option>
                                                    </Form.Select>
                                                </Form.Group>
                                            </td>
                                            <td>
                                                <Form.Group controlId="fecha_inicio_pago">
                                                    <Form.Control
                                                        style={{ width: "100%" }}
                                                        className="font-h2 no-border"
                                                        type="date"
                                                        value={input.fecha_inicio_pago}
                                                        onChange={handleInputChange}
                                                        name="fecha_inicio_pago"
                                                    />
                                                </Form.Group>
                                            </td>
                                            <td>
                                                <Form.Group controlId="tiempo_de_pago">
                                                    <Form.Control
                                                        className="agregar"
                                                        type="number"
                                                        value={input.tiempo_de_pago}
                                                        onChange={handleInputChange}
                                                        name="tiempo_de_pago"
                                                        required
                                                    />
                                                </Form.Group>
                                            </td>
                                            <td>
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
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            )}
                            <h1 onClick={toggleTableVisibilityFactura} style={{ cursor: "pointer" }}>
                                <b> Factura</b>
                                <span style={{ marginLeft: "10px" }}>{isTableVisibleFactura ? "−" : "+"}</span>
                            </h1>
                            {isTableVisibleFactura && (
                                <table border="1" className="content-table">
                                    <thead>
                                        <tr>
                                            <th>*Numero de la Factura</th>
                                            <th>Estado de la Factura</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <Form.Group controlId="numero_factura">
                                                    <Form.Control
                                                        className="agregar"
                                                        type="number"
                                                        name="numero_factura"
                                                        value={input.numero_factura}
                                                        onChange={handleInputChange}
                                                        required
                                                    />
                                                </Form.Group>
                                            </td>
                                            <td>
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
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            )}
                            <h1 onClick={toggleTableVisibilityListaProducto} style={{ cursor: "pointer" }}>
                                <b> Lista de Productos</b>
                                <span style={{ marginLeft: "10px" }}>{isTableVisibleListaProducto ? "−" : "+"}</span>
                            </h1>
                            {isTableVisibleListaProducto && (
                                <div>
                                    <table border="1" className="content-table">
                                        <thead>
                                            <tr>
                                                <th>* Nombre</th>
                                                <th>* Cantidad</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {ListProducto.map((row, index) => (
                                                <tr key={index}>
                                                    <td>
                                                        <Form.Group controlId={`nombre-${index}`}>
                                                            <Form.Control
                                                                type="text"
                                                                value={row.nombre}
                                                                onChange={(event) => handleInputChange(event, index)}
                                                                name="nombre"
                                                            />
                                                        </Form.Group>
                                                    </td>
                                                    <td>
                                                        <Form.Group controlId={`cantidad-${index}`}>
                                                            <Form.Control
                                                                type="number"
                                                                value={row.cantidad}
                                                                onChange={(event) => handleInputChange(event, index)}
                                                                name="cantidad"
                                                            />
                                                        </Form.Group>
                                                    </td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </table>
                                    <Button className="Aumentar" onClick={handleAddRow}>
                                        +
                                    </Button>
                                    <Button className="Disminuir" onClick={handleMinusRow}>
                                        -
                                    </Button>
                                </div>
                            )}
                        </div>
                        <div className="button-container">
                            <Button className="boton" type="submit">
                                Registrar OC Cliente
                            </Button>
                        </div>
                    </Form>
                </div>
            </NavStyle>
        </div>
    );
}
export default OCClienteCrearComponents;

const NavStyle = styled.nav`
}

.container{
    margin: 2%;
    border: 2px solid #D5D5D5;
    background-color: #F0F0F0;
    gap: 20px;
    height: 100%;
}
.container-2{
    
    background-color: #F0F0F0;
    flex-grow: 1; /* El lado derecho es flexible y ocupará todo el espacio restante */
    overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
    padding: 1%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
    max-height: calc(0px + 55.3vh); /* Asegura que el contenedor no exceda la altura de la ventana */
}

/* Tabla */

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



h1 {
    text-align: left;
}
label {
    display: block;
    margin-bottom: 10px;
    margin-left: 15px;
    margin-top: 10px;
}
input[type="text"], input[type="date"], input[type="number"], option, select{
    background-color: rgb(201, 201, 201);
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border-radius: 30px;
    border: 1px solid #ccc;
    box-sizing: border-box; /* Asegura que los inputs tengan el mismo ancho */
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

.Aumentar, .Disminuir{
    padding: 10px;
    background-color: #D2712B;
    margin-left: 1%;
    width:30px;
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
    margin: 2%;
}



.button-container {
    display: flex;
    justify-content: center;
    margin-top: 20px;
}

td, th, h1, Label, Control, Button{
    font-family: 'Pacifico',serif;
}
`;
