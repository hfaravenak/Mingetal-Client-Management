import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import HeaderComponents from "../Headers/HeaderComponents";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Swal from 'sweetalert2';
import ProductoService from "../../services/ProductoService";
import styled from "styled-components";

function ProductoCrearComponents(){

    const initialState = {
        tipo: "",
        nombre: "",
        valor: 0,
        valor_final: 0,
        cantidad: 0,
    };

    const [input, setInput] = useState(initialState);
    const navigate = useNavigate();


    const changeTipoHandler = event => {
        setInput({...input, tipo: event.target.value})
    }

    const changeNombreHandler = event => {
        setInput({...input, nombre: event.target.value})
    }

    const changeValorHandler = event => {
        setInput({...input, valor: parseInt(event.target.value)})
    }

    const changeValorFinalHandler = event => {
        setInput({...input, valor_final: parseInt(event.target.value)})
    }

    const changeCantidadHandler = event => {
        setInput({...input, cantidad: parseInt(event.target.value)})
    }


    //-- agregar producto + alarmas + confirmación
    const ingresarProducto = (event) => {
        Swal.fire({
            title: "¿Desea registrar el nuevo producto?",
            icon: "question",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        }).then((result) =>{
            if (result.isConfirmed) {
                let newProducto = {
                    tipo: input.tipo,
                    nombre: input.nombre,
                    valor: input.valor,
                    valor_final: input.valor_final,
                    cantidad: input.cantidad,
                };
                ProductoService.crearProducto(newProducto);
                Swal.fire({
                    title: "Enviado",
                    timer: 2000,
                    icon: "success",
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading()
                      },
                      willClose: () => {
                        navigate("/productos");
                    }
                })
            }
        });
    };

    //---

    return(
        <div className="general">
            <HeaderComponents></HeaderComponents>
            <NavStyle>
                <div className="container-create">
                    <Form>
                        <Form.Group className="mb-3" controlId="tipo" value = {input.tipo} onChange={changeTipoHandler}>
                            <Form.Label className="agregar">Tipo:</Form.Label>
                            <Form.Control className="agregar" type="text" name="tipo"/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="nombre" value = {input.nombre} onChange={changeNombreHandler}>
                            <Form.Label className="agregar">Nombre:</Form.Label>
                            <Form.Control className="agregar" type="text" name="nombre"/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="valor" value={input.valor} onChange={changeValorHandler}>
                            <Form.Label className="agregar">Valor:</Form.Label>
                            <Form.Control className="agregar" type="number" name="valor" min="0"/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="valor_final" value={input.valor_final} onChange={changeValorFinalHandler}>
                            <Form.Label className="agregar">Valor Final:</Form.Label>
                            <Form.Control className="agregar" type="number" name="valor_final" min="0"/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="cantidad" value={input.cantidad} onChange={changeCantidadHandler}>
                            <Form.Label className="agregar">Cantidad:</Form.Label>
                            <Form.Control className="agregar" type="number" name="cantidad" min="0"/>
                        </Form.Group>

                        
                        <Button className="boton" onClick={ingresarProducto}>Ingresar Producto</Button>
                    </Form>
                </div>
            </NavStyle>
        </div>
    )
}

export default ProductoCrearComponents;


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