import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import HeaderComponents from "../../Headers/HeaderComponents";
import ProveedorService from "../../../services/ProveedorService";
import OrdenesDeCompraProveedorService from "../../../services/OrdenesDeCompraProveedorService";

function OCProveedorCrearComponents() {
    const obtenerFechaHoy = () => {
        const hoy = new Date();
        const dia = String(hoy.getDate()).padStart(2, "0");
        const mes = String(hoy.getMonth() + 1).padStart(2, "0");
        const año = hoy.getFullYear();
        return `${año}-${mes}-${dia}`; // Formato yyyy-mm-dd para ser compatible con el tipo de input date
    };
    const navigate = useNavigate();

    const initialState = {
        nombre: "",
        fecha_solicitud: obtenerFechaHoy(),
        fecha_entrega: obtenerFechaHoy(),
        estado_entrega: "No Entregado",

        valor_pago: "",
        fecha_pago: "",
        estado_pago: "No Pagado",

        numero_factura: "",
    };
    const [input, setInput] = useState(initialState);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setInput({ ...input, [name]: value });
    };

    const handleSubmit = (event) => {
        event.preventDefault(); // Previene el comportamiento predeterminado del formulario
        ingresarOCProveedor(); // Llama a la función para procesar el formulario
    };

    const ingresarOCProveedor = () => {
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
                ProveedorService.getProveedorByNombreTextual(input.nombre).then((res) => {
                    const id_proveedor = res.data.id_proveedor;

                    if (id_proveedor === "" || id_proveedor === null) {
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
                            id_proveedor: id_proveedor,
                            fecha_solicitud: input.fecha_solicitud,
                            fecha_entrega: input.fecha_entrega,
                            estado_entrega: input.estado_entrega,
                            valor_pago: input.valor_pago,
                            fecha_pago: input.fecha_pago,
                            estado_pago: input.estado_pago,
                            factura: input.numero_factura,
                        };
                        OrdenesDeCompraProveedorService.createOCProveedor(newOC);

                        Swal.fire({
                            title: "Enviado",
                            timer: 2000,
                            icon: "success",
                            timerProgressBar: true,
                            didOpen: () => {
                                Swal.showLoading();
                            },
                            willClose: () => {
                                navigate("/oc/proveedor");
                            },
                        });
                    }
                });
            }
        });
    };

    return (
        <div className="general">
            <HeaderComponents></HeaderComponents>
            <NavStyle>
                <div className="container-create">
                    <h1>Crear Orden de Compra de Proveedor</h1>
                    <Form onSubmit={handleSubmit}>
                        <div className="Table-Column">
                            <div className="column column-izq">
                                <Form.Group controlId="nombre">
                                    <Form.Label className="agregar">* Nombre:</Form.Label>
                                    <Form.Control
                                        className="agregar"
                                        type="text"
                                        value={input.nombre}
                                        onChange={handleInputChange}
                                        name="nombre"
                                        required
                                    />
                                </Form.Group>
                                <Form.Group controlId="fecha_solicitud">
                                    <Form.Label className="agregar">Fecha de Solicitud:</Form.Label>
                                    <Form.Control
                                        style={{ width: "100%" }}
                                        className="font-h2 no-border"
                                        type="date"
                                        value={input.fecha_solicitud}
                                        onChange={handleInputChange}
                                        name="fecha_solicitud"
                                    />
                                </Form.Group>
                                <Form.Group controlId="fecha_entrega">
                                    <Form.Label className="agregar">Fecha de la Entrega:</Form.Label>
                                    <Form.Control
                                        style={{ width: "100%" }}
                                        className="font-h2 no-border"
                                        type="date"
                                        value={input.fecha_entrega}
                                        onChange={handleInputChange}
                                        name="fecha_entrega"
                                    />
                                </Form.Group>
                                <Form.Group controlId="estado_entrega">
                                    <Form.Label className="agregar">Estado de la Entrega:</Form.Label>
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
                            <div className="column column-cen">
                                <Form.Group controlId="valor_pago">
                                    <Form.Label className="agregar">* Valor del Pago:</Form.Label>
                                    <Form.Control
                                        className="agregar"
                                        type="number"
                                        value={input.valor_pago}
                                        onChange={handleInputChange}
                                        name="valor_pago"
                                        required
                                    />
                                </Form.Group>
                                <Form.Group controlId="estado_pago">
                                    <Form.Label className="agregar">Estado del Pago:</Form.Label>
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
                                <Form.Group controlId="fecha_pago">
                                    <Form.Label className="agregar">Fecha de cuando Pagó:</Form.Label>
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
                            <div className="column column-der">
                                <Form.Group controlId="numero_factura">
                                    <Form.Label className="agregar">* Numero Factura:</Form.Label>
                                    <Form.Control
                                        className="agregar"
                                        type="number"
                                        value={input.numero_factura}
                                        onChange={handleInputChange}
                                        name="numero_factura"
                                        required
                                    />
                                </Form.Group>
                            </div>
                        </div>
                        <div className="button-container">
                            <Button className="boton" type="submit">
                                Registrar OC Proveedor
                            </Button>
                        </div>
                    </Form>
                </div>
            </NavStyle>
        </div>
    );
}
export default OCProveedorCrearComponents;

const NavStyle = styled.nav`
    .Table-Column {
        display: flex;
        justify-content: space-between;
    }

    .column-izq {
        padding-left: 10%;
    }
    .column-der {
        padding-right: 10%;
    }

    .container-create {
        margin: 2%;
        padding: 2%;
        border: 2px solid #d5d5d5;
        background-color: #f0f0f0;
    }

    h1 {
        text-align: center;
    }
    label {
        display: block;
        margin-bottom: 10px;
        margin-left: 15px;
        margin-top: 10px;
    }
    input[type="text"],
    input[type="date"],
    input[type="number"],
    option,
    select {
        background-color: rgb(201, 201, 201);
        width: 100%;
        padding: 10px;
        font-size: 16px;
        border-radius: 30px;
        border: 1px solid #ccc;
        box-sizing: border-box; /* Asegura que los inputs tengan el mismo ancho */
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

    .button-container {
        display: flex;
        justify-content: center;
        margin-top: 20px;
    }

    td,
    th,
    h1,
    Label,
    Control,
    Button {
        font-family: "Pacifico", serif;
    }
`;
