import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import HeaderComponents from "../Headers/HeaderComponents";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Swal from 'sweetalert2';
import ProveedorService from "../../services/ProveedorService";
import styled from "styled-components";

function ProveedorCrearComponent(){

    const initialState = {
        empresa: "",
        rubro: "",
        id_contacto: "",
        id_contacto2: "",
        id_contacto3: "",
        comentario: "",
    };

    const [input, setInput] = useState(initialState);
    const navigate = useNavigate();
    
    const changeEmpresaHandler = event => {
        setInput({ ...input, empresa: event.target.value });
    };
    const changeRubroHandler = event => {
        setInput({ ...input, rubro: event.target.value });
    };
    const changeId_contactoHandler = event => {
        setInput({ ...input, id_contacto: event.target.value });
    };
    const changeId_contacto2Handler = event => {
        setInput({ ...input, id_contacto2: event.target.value });
    };
    const changeId_contacto3Handler = event => {
        setInput({ ...input, id_contacto3: event.target.value });
    };
    const changeComentarioHandler = event => {
        setInput({ ...input, comentario: event.target.value });
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
                let newProveedor= {
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
                        Swal.showLoading()
                      },
                      willClose: () => {
                        navigate("/proveedores");
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
                        <Form.Group className="mb-3" controlId="empresa" value = {input.empresa} onChange={changeEmpresaHandler}>
                            <Form.Label className="agregar">Nombre de la empresa:</Form.Label>
                            <Form.Control className="agregar" type="text" name="pedido"/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="rubro" value = {input.rubro} onChange={changeRubroHandler}>
                            <Form.Label className="agregar">Ingrese el Rubro al que corresponde el proveedor:</Form.Label>
                            <Form.Control className="agregar" type="text" name="Rubro"/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="id_contacto" value = {input.id_contacto} onChange={changeId_contactoHandler}>
                            <Form.Label className="agregar">Contacto:</Form.Label>
                            <Form.Control className="agregar" type="text" name="id_contacto"/>
                        </Form.Group>

                        {/*
                        <Form.Group className="mb-3" controlId="id_contacto2" value = {input.id_contacto2} onChange={changeId_contacto2Handler}>
                            <Form.Label className="agregar">id contacto2:</Form.Label>
                            <Form.Control className="agregar" type="text" name="id_contacto2"/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="id_contacto3" value = {input.id_contacto3} onChange={changeId_contacto3Handler}>
                            <Form.Label className="agregar">idcontacto3:</Form.Label>
                            <Form.Control className="agregar" type="text" name="id_contacto3"/>
                        </Form.Group>
                        */}

                        <Form.Group className="mb-3" controlId="comentario" value = {input.comentario} onChange={changeComentarioHandler}>
                            <Form.Label className="agregar">Comentario:</Form.Label>
                            <Form.Control className="agregar" type="text" name="comentario"/>
                        </Form.Group>

                        <Button className="boton" onClick={ingresarProveedor}>Crear Proveedor</Button>
                    </Form>
                </div>
            </NavStyle>
        </div>
    )
}
export default ProveedorCrearComponent;


const NavStyle = styled.nav`

.container-create{
    margin: 2%;
    padding: 2%;
    border: 2px solid #D5D5D5;
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
input[type="text"], input[type="date"] {
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