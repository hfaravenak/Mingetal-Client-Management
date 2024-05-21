import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import HeaderComponents from "../Headers/HeaderComponents";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Swal from 'sweetalert2';
import CotizacionService from "../../services/CotizacionService";
import styled from "styled-components";

function CotizacionCrearComponent(){

    const initialState = {
        pedido: "",
        fecha: "",
        estado: "",
        rutCliente: "",
    };

    const [input, setInput] = useState(initialState);
    const navigate = useNavigate();
    
    const changePedidoHandler = event => {
        setInput({ ...input, pedido: event.target.value });
    };
    const changeFechaHandler = event => {
        setInput({ ...input, fecha: event.target.value });
    };
    const changeEstadoHandler = event => {
        setInput({ ...input, estado: event.target.value });
    };
    const changeRutClienteHandler = event => {
        setInput({ ...input, rutCliente: event.target.value });
    };

    
    const ingresarCotizacion = (event) => {
        Swal.fire({
            title: "¿Desea crear una nueva Cotización?",
            icon: "question",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        }).then((result) => {
            if (result.isConfirmed) {
                let newCotizacion= {
                    pedido: input.pedido,
                    fecha: input.fecha,
                    estado: input.estado,
                    rutCliente: input.rutCliente,
                };
                CotizacionService.createCotizacion(newCotizacion);
                Swal.fire({
                    title: "Enviado",
                    timer: 2000,
                    icon: "success",
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading()
                      },
                      willClose: () => {
                        navigate("/cotizaciones");
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
                        <Form.Group className="mb-3" controlId="pedido" value = {input.pedido} onChange={changePedidoHandler}>
                            <Form.Label className="agregar">Pedido:</Form.Label>
                            <Form.Control className="agregar" type="text" name="pedido"/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="fecha" value = {input.fecha} onChange={changeFechaHandler}>
                            <Form.Label className="agregar">Fecha:</Form.Label>
                            <Form.Control
                                    className="agregar"
                                    type="date"
                                    name="fecha"
                                    value={input.fecha}
                                    onChange={changeFechaHandler}
                                    />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="estado" value = {input.estado} onChange={changeEstadoHandler}>
                            <Form.Label className="agregar">Estado:</Form.Label>
                            <Form.Select
                                className="agregar"
                                name="estado"
                                value={input.estado}
                                onChange={changeEstadoHandler}
                            >
                                <option value="">Seleccionar estado</option>
                                <option value="Listo">Listo</option>
                                <option value="En espera">En espera</option>
                            </Form.Select>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="rutCliente" value = {input.rutCliente} onChange={changeRutClienteHandler}>
                            <Form.Label className="agregar">Rut del Cliente:</Form.Label>
                            <Form.Control className="agregar" type="text" name="rutCliente"/>
                        </Form.Group>

                        <Button className="boton" onClick={ingresarCotizacion}>Crear Cotización</Button>
                    </Form>
                </div>
            </NavStyle>
        </div>
    )
}
export default CotizacionCrearComponent;


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