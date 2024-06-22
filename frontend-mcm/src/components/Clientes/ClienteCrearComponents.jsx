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
    const [errors, setErrors] = useState({});

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setInput({ ...input, [name]: value });
    };

    const validateForm = () => {
        const errors = {};

        const rutPattern = /^[0-9]{1,3}(\.[0-9]{3}){2}-[0-9kK]$/;
        if (!input.rut.match(rutPattern)) {
            errors.rut = "El RUT no es válido. Ejemplo: 12.345.678-9";
        }
        if (input.nombre.trim().length === 0) {
            errors.nombre = "El nombre no puede estar vacío.";
        }
        if (input.empresa.trim().length === 0) {
            errors.empresa = "La empresa no puede estar vacía.";
        }
        const telefonoPattern = /^\+569[0-9]{8}(\s?[0-9]{1,4})?$/;
        if (!input.telefono.match(telefonoPattern)) {
            errors.telefono = "El teléfono no es válido. Ejemplo: +569 1234 5678";
        }
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!input.correo.match(emailPattern)) {
            errors.correo = "El correo no es válido.";
        }

        setErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (validateForm()) {
            try {
                await ingresarCliente();
            } catch (error) {
                if (error.response && error.response.status === 400) {
                    Swal.fire({
                        title: "Error",
                        text: "Ya existe un cliente registrado con este RUT.",
                        icon: "error",
                        confirmButtonText: "OK",
                    });
                } else {
                    Swal.fire({
                        title: "Error",
                        text: "Ocurrió un error al registrar el cliente.",
                        icon: "error",
                        confirmButtonText: "OK",
                    });
                }
            }
        } else {
            Swal.fire({
                title: "Error",
                text: "Por favor, corrija los errores en el formulario.",
                icon: "error",
                confirmButtonText: "OK",
            });
        }
    };

    const ingresarCliente = async () => {
        const result = await Swal.fire({
            title: "¿Desea registrar al Cliente?",
            icon: "question",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        });

        if (result.isConfirmed) {
            let newCliente = {
                rut: input.rut,
                nombre: input.nombre,
                empresa: input.empresa,
                email: input.correo,
                telefono: input.telefono,
            };

            try {
                await ClienteService.createCliente(newCliente);
                Swal.fire({
                    title: "Cliente registrado",
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
            } catch (error) {
                throw error;
            }
        }
    };

    return (
        <div className="general">
            <HeaderComponents />
            <NavStyle>
                <div className="container-create">
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="rut">
                            <Form.Label>Rut:</Form.Label>
                            <Form.Control 
                                type="text" 
                                name="rut" 
                                value={input.rut} 
                                onChange={handleInputChange} 
                                placeholder="12.345.678-9"
                                required 
                            />
                            {errors.rut && <span className="text-danger">{errors.rut}</span>}
                        </Form.Group>

                        <Form.Group controlId="nombre">
                            <Form.Label>Nombre:</Form.Label>
                            <Form.Control 
                                type="text" 
                                name="nombre" 
                                value={input.nombre} 
                                onChange={handleInputChange} 
                                placeholder="Juan Perez"
                                required 
                            />
                            {errors.nombre && <span className="text-danger">{errors.nombre}</span>}
                        </Form.Group>

                        <Form.Group controlId="empresa">
                            <Form.Label>Empresa:</Form.Label>
                            <Form.Control
                                type="text"
                                name="empresa"
                                value={input.empresa}
                                onChange={handleInputChange}
                                placeholder="EmpresaX"
                                required 
                            />
                            {errors.empresa && <span className="text-danger">{errors.empresa}</span>}
                        </Form.Group>

                        <Form.Group controlId="telefono">
                            <Form.Label>Telefono:</Form.Label>
                            <Form.Control
                                type="text"
                                name="telefono"
                                value={input.telefono}
                                onChange={handleInputChange}
                                placeholder="+569 1234 5678"
                                required 
                            />
                            {errors.telefono && <span className="text-danger">{errors.telefono}</span>}
                        </Form.Group>

                        <Form.Group controlId="correo">
                            <Form.Label>Correo:</Form.Label>
                            <Form.Control 
                                type="email" 
                                name="correo" 
                                value={input.correo} 
                                onChange={handleInputChange} 
                                placeholder="correfake@gmail.com"
                                required 
                            />
                            {errors.correo && <span className="text-danger">{errors.correo}</span>}
                        </Form.Group>
                        <Button className="boton" type="submit">
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
        font-size: 18px; /* Incrementa el tamaño de la letra */
        font-weight: bold; /* Hace la letra negrita */
    }

    input[type="text"], input[type="email"] {
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

    .text-danger {
        color: red;
        margin-left: 15px;
    }
`;
