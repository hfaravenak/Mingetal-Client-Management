import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import HeaderComponents from "../Headers/HeaderComponents";
import ProveedorService from "../../services/ProveedorService";

function ProveedorCrearComponent() {
    const navigate = useNavigate();

    const initialState = {
        empresa: "",
        rubro: "",
        id_contacto: "",
        id_contacto2: "",
        id_contacto3: "",
        comentario: "",
    };
    const [input, setInput] = useState(initialState);
    
    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setInput({ ...input, [name]: value });
    };

    const ingresarProveedor = (event) => {
        Swal.fire({
            title: "¿Desea ingresar un nuevo Proveedor?",
            icon: "question",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        }).then((result) => {
            if (result.isConfirmed) {
                let newProveedor = {
                    empresa: input.empresa,
                    rubro: input.rubro,
                    id_contacto: input.id_contacto,
                    id_contacto2: input.id_contacto2,
                    id_contacto3: input.id_contacto3,
                    comentario: input.comentario,
                };
                ProveedorService.createProveedor(newProveedor);
                Swal.fire({
                    title: "Enviado",
                    timer: 2000,
                    icon: "success",
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading();
                    },
                    willClose: () => {
                        navigate("/proveedores");
                    },
                });
            }
        });
    };

    return (
        <div className="general">
            <HeaderComponents></HeaderComponents>
            <NavStyle>
                <div className="container-create">
                    <Form>
                        <Form.Group controlId="empresa">
                            <Form.Label className="agregar">Nombre de la empresa:</Form.Label>
                            <Form.Control
                                className="agregar"
                                type="text"
                                name="pedido"
                                value={input.empresa}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group controlId="rubro">
                            <Form.Label className="agregar">
                                Ingrese el Rubro al que corresponde el proveedor:
                            </Form.Label>
                            <Form.Control
                                className="agregar"
                                type="text"
                                name="Rubro"
                                value={input.rubro}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group controlId="id_contacto">
                            <Form.Label className="agregar">Contacto:</Form.Label>
                            <Form.Control
                                className="agregar"
                                type="text"
                                name="id_contacto"
                                value={input.id_contacto}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group controlId="id_contacto2">
                            <Form.Label className="agregar">Email:</Form.Label>
                            <Form.Control
                                className="agregar"
                                type="text"
                                name="id_contacto2"
                                value={input.id_contacto2}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group controlId="id_contacto3" value={input.id_contacto3} onChange={handleInputChange}>
                            <Form.Label className="agregar">Teléfono:</Form.Label>
                            <Form.Control className="agregar" type="text" name="id_contacto3" />
                        </Form.Group>

                        <Form.Group controlId="comentario">
                            <Form.Label className="agregar">Comentario:</Form.Label>
                            <Form.Control
                                className="agregar"
                                type="text"
                                name="comentario"
                                value={input.comentario}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Button className="boton" onClick={ingresarProveedor}>
                            Crear Proveedor
                        </Button>
                    </Form>
                </div>
            </NavStyle>
        </div>
    );
}
export default ProveedorCrearComponent;

const NavStyle = styled.nav`
    .container-create {
        margin: 2%;
        padding: 2%;
        border: 2px solid #d5d5d5;
        background-color: #f0f0f0;
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
`;
