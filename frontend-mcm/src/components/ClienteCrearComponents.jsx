import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import HeaderComponents from "./Headers/HeaderComponents";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Swal from 'sweetalert2';
import ClienteService from "../services/ClienteService";
import styled from "styled-components";

function ClienteCrearComponents(){

    const initialState = {
        rut: "",
        nombre: "",
        empresa: "",
        telefono: "",
        correo: "",
    };

    const [input, setInput] = useState(initialState);
    const navigate = useNavigate();
    
    const changeRutHandler = event => {
        setInput({ ...input, rut: event.target.value });
    };
    const changeNombreHandler = event => {
        setInput({ ...input, nombre: event.target.value });
    };
    const changeEmpresaHandler = event => {
        setInput({ ...input, empresa: event.target.value });
    };
    const changeTelefonoHandler = event => {
        setInput({ ...input, telefono: event.target.value });
    };
    const changeCorreoHandler = event => {
        setInput({ ...input, correo: event.target.value });
    };

    
    const ingresarEstudiante = (event) => {
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
                console.log(input.title);
                let newCliente= {
                    rut: input.rut,
                    nombre: input.nombre,
                    empresa: input.empresa,
                    email: input.correo,
                    telefono: input.telefono,
                };
                console.log(newCliente);
                ClienteService.createCliente(newCliente);
                Swal.fire({
                    title: "Enviado",
                    timer: 2000,
                    icon: "success",
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading()
                      },
                      willClose: () => {
                        navigate("/clientes");
                    }
                    })
            }
        });
    };

    return(
        <div className="general">
            <HeaderComponents></HeaderComponents>
            <NavStyle>
                <div className="container-create">
                    <Form>
                        <Form.Group className="mb-3" controlId="rut" value = {input.rut} onChange={changeRutHandler}>
                            <Form.Label className="agregar">Rut:</Form.Label>
                            <Form.Control className="agregar" type="text" name="rut"/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="nombre" value = {input.nombre} onChange={changeNombreHandler}>
                            <Form.Label className="agregar">Nombre:</Form.Label>
                            <Form.Control className="agregar" type="text" name="nombre"/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="empresa" value = {input.empresa} onChange={changeEmpresaHandler}>
                            <Form.Label className="agregar">Empresa:</Form.Label>
                            <Form.Control className="agregar" type="text" name="empresa"/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="telefono" value = {input.telefono} onChange={changeTelefonoHandler}>
                            <Form.Label className="agregar">Telefono:</Form.Label>
                            <Form.Control className="agregar" type="text" name="telefono"/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="correo" value = {input.correo} onChange={changeCorreoHandler}>
                            <Form.Label className="agregar">Correo:</Form.Label>
                            <Form.Control className="agregar" type="text" name="correo"/>
                        </Form.Group>
                        <Button className="boton" onClick={ingresarEstudiante}>Registrar Proveedor</Button>
                    </Form>
                </div>
            </NavStyle>
        </div>
    )
}
export default ClienteCrearComponents;


const NavStyle = styled.nav`
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
    font-size: 16px;
    border-radius: 30px;
    border: 1px solid #ccc;
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
`