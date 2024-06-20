import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import HeaderComponents from "../Headers/HeaderComponents";
import ClienteService from "../../services/ClienteService";

function ClienteCrearComponents() {
    const navigate = useNavigate();

    const initialState = {
        rut: "",
        nombre: "",
        empresa: "",
        telefono: "",
        correo: "",
    };
    const [input, setInput] = useState(initialState);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setInput({ ...input, [name]: value });
    };

    const ingresarEstudiante = () => {
        Swal.fire({
            title: "¿Desea registrar al Cliente?",
            icon: "question",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        }).then((result) => {
            if (result.isConfirmed) {
                let newCliente = {
                    rut: input.rut,
                    nombre: input.nombre,
                    empresa: input.empresa,
                    email: input.correo,
                    telefono: input.telefono,
                };
                ClienteService.createCliente(newCliente);
                Swal.fire({
                    title: "Enviado",
                    timer: 2000,
                    icon: "success",
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading();
                    },
                    willClose: () => {
                        navigate("/clientes");
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
                        <Form.Group controlId="rut">
                            <Form.Label>Rut:</Form.Label>
                            <Form.Control 
                                type="text" 
                                name="rut" 
                                value={input.rut} 
                                onChange={handleInputChange} 
                            />
                        </Form.Group>

                        <Form.Group controlId="nombre">
                            <Form.Label>Nombre:</Form.Label>
                            <Form.Control 
                                type="text" 
                                name="nombre" 
                                value={input.nombre} 
                                onChange={handleInputChange} 
                            />
                        </Form.Group>

                        <Form.Group controlId="empresa">
                            <Form.Label>Empresa:</Form.Label>
                            <Form.Control
                                type="text"
                                name="empresa"
                                value={input.empresa}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group controlId="telefono">
                            <Form.Label>Telefono:</Form.Label>
                            <Form.Control
                                type="text"
                                name="telefono"
                                value={input.telefono}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group controlId="correo">
                            <Form.Label>Correo:</Form.Label>
                            <Form.Control type="text" name="correo" value={input.correo} onChange={handleInputChange} />
                        </Form.Group>
                        <Button className="boton" onClick={ingresarEstudiante}>
                            Registrar Cliente
                        </Button>
                    </Form>
                </div>
            </NavStyle>
        </div>
    );
}
export default ClienteCrearComponents;

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
    input[type="text"] {
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
