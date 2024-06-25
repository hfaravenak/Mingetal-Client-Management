


import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import HeaderComponents from "../Headers/HeaderComponents";

import ProductoService from "../../services/ProductoService";

function ProductoCrearComponents() {
    const navigate = useNavigate();

    const initialState = {
        tipo: "",
        nombre: "",
        valor: 0,
        valor_final: 0,
        cantidad: 0,
        imagen: null,
        tipo_imagen: "",
    };
    const [input, setInput] = useState(initialState);
    const [previewImage, setPreviewImage] = useState(null);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setInput({ ...input, [name]: value });
    };

    const handleImageChange = (event) => {
        const file = event.target.files[0];
        setInput({ ...input, imagen: file });
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setPreviewImage(reader.result);
            };
            reader.readAsDataURL(file);
        } else {
            setPreviewImage(null);
        }
    };

    //-- agregar producto + alarmas + confirmación
    const ingresarProducto = () => {
        Swal.fire({
            title: "¿Desea registrar el nuevo producto?",
            icon: "question",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        }).then((result) => {
            if (result.isConfirmed) {
                const formData = new FormData();
                formData.append("tipo", input.tipo);
                formData.append("nombre", input.nombre);
                formData.append("valor", input.valor);
                formData.append("valor_final", input.valor_final);
                formData.append("cantidad", input.cantidad);
                if (input.imagen) {
                    formData.append("imagen", input.imagen);
                }
                ProductoService.crearProducto(formData)
                .then(() => {
                    Swal.fire({
                        title: "Enviado",
                        timer: 2000,
                        icon: "success",
                        timerProgressBar: true,
                        didOpen: () => {
                            Swal.showLoading();
                        },
                        willClose: () => {
                            navigate("/productos");
                        },
                    });
                })
                .catch((error) => {
                    Swal.fire({
                        title: "Error",
                        text: error.message,
                        icon: "error",
                    });
                });
                
            }
        });
    };

    //---

    return (
        <div className="general">
            <HeaderComponents></HeaderComponents>
            <NavStyle>
                <div className="container-create">
                    <Form>
                        <Form.Group controlId="tipo">
                            <Form.Label className="agregar">Tipo:</Form.Label>
                            <Form.Control
                                className="agregar"
                                type="text"
                                name="tipo"
                                value={input.tipo}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group controlId="nombre">
                            <Form.Label className="agregar">Nombre:</Form.Label>
                            <Form.Control
                                className="agregar"
                                type="text"
                                name="nombre"
                                value={input.nombre}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group controlId="valor">
                            <Form.Label className="agregar">Valor:</Form.Label>
                            <Form.Control
                                className="agregar"
                                type="number"
                                name="valor"
                                min="0"
                                value={input.valor}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group controlId="valor_final">
                            <Form.Label className="agregar">Valor Final:</Form.Label>
                            <Form.Control
                                className="agregar"
                                type="number"
                                name="valor_final"
                                min="0"
                                value={input.valor_final}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group controlId="cantidad">
                            <Form.Label className="agregar">Cantidad:</Form.Label>
                            <Form.Control
                                className="agregar"
                                type="number"
                                name="cantidad"
                                min="0"
                                value={input.cantidad}
                                onChange={handleInputChange}
                            />
                        </Form.Group>

                        <Form.Group controlId="imagen">
                            <Form.Label className="agregar">Imagen:</Form.Label>
                            <Form.Control
                                className="agregar"
                                type="file"
                                accept="image/jpeg, image/png"
                                onChange={handleImageChange}
                            />
                        </Form.Group>

                        {previewImage && (
                            <div className="image-preview">
                                <img src={previewImage} alt="Vista previa" />
                            </div>
                        )}

                        <Button className="boton" onClick={ingresarProducto}>
                            Ingresar Producto
                        </Button>
                    </Form>
                </div>
            </NavStyle>
        </div>
    );
}

export default ProductoCrearComponents;

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