import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import HeaderComponents from "../Headers/HeaderComponents";
import styled from "styled-components";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Swal from "sweetalert2";

import productos from "../../images/producto.png";
import ProductoService from "../../services/ProductoService";


function ProductoComponents() {

    const initialState = {
        id: 0,
        tipo: "",
        nombre: "",
        valor: 0,
        valor_final: 0,
        cantidad: 0,
    };

    const [input, setInput] = useState(initialState);
    const [mostrarCard, setMostrarCard] = useState(false);
    const { producto } = useParams();
    const datos = JSON.parse(decodeURIComponent(producto));
    const navigate = useNavigate();

    const changeNombreHandler = event => {
        setInput({ ...input, nombre: event.target.value });
    };

    const changeTipoHandler = event => {
        setInput({ ...input, tipo: event.target.value });
    };
    const changeValorHandler = event => {
        setInput({ ...input, valor: event.target.value });
    };
    const changeValorFinalHandler = event => {
        setInput({ ...input, valor_final: event.target.value });
    };
    const changeCantidadHandler = event => {
        setInput({ ...input, cantidad: event.target.value });
    };

    const changeMostrarCard = () => {
        setInput({
            id: datos.id,
            tipo: datos.tipo,
            nombre: datos.nombre,
            valor: datos.valor,
            valor_final: datos.valor_final,
            cantidad: datos.cantidad,
        });
        setMostrarCard(true);
    }


    const handleGuardarProducto = () => {
        Swal.fire({
            title: "¿Seguro de modificar este producto?",
            icon: "question",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        }).then((result) => {
            if (result.isConfirmed) {
                let updateProducto = {
                    id: datos.id,
                    tipo: input.tipo,
                    nombre: input.nombre,
                    valor: input.valor,
                    valor_final: input.valor_final,
                    cantidad: input.cantidad
                };
                ProductoService.updateProducto(updateProducto);
                    navigate(`/productos/mas-info/${encodeURIComponent(JSON.stringify(updateProducto))}`);
                    setMostrarCard(false);
                }
            }
        );
    }
       

    const handleEliminarProducto = () => {
        Swal.fire({
            title: "¿Seguro de que desea eliminar este producto?",
            text: "Esta acción es irreversible y no podrá recuperar el producto perdido.",
            icon: "warning",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
            input: "text",
            inputPlaceholder: "Confirmo",
            inputValidator: (value) => {
                return new Promise((resolve) => {
                    if (value === "Confirmo") {
                        resolve();
                    } else {
                        resolve('ERROR al introducir la palabra');
                    }
                });
            }
        }).then((result) => {
            if (result.isConfirmed) {
                ProductoService.deleteProducto(datos.id).then(() => {
                    Swal.fire({
                        title: 'Eliminando...',
                        text: 'Por favor espera',
                        timer: 2000, // 2 segundos
                        didOpen: () => {
                            Swal.showLoading();
                        },
                        willClose: () => {
                            navigate("/productos");
                        }
                    });
                });
            }
        });
    };

    const CancelarEdit = () => {
        setMostrarCard(false);
    }

    if (mostrarCard) {
        return (
            <div>
                <NavStyle>
                    <HeaderComponents />
                    <div className="container">
                        <div className="container-1">
                            <div className="card">
                                <div className="contenedor-img">
                                    <img id="productos" src={productos} alt="productos" />
                                </div>
                                <div className="inline-forms-container contenedor-informacion">
                                <Form className="inline-form">
                                        <Form.Group className="mb-3" controlId="nombre">
                                            <Form.Control value = {input.nombre} onChange={changeNombreHandler} className="font-h3 no-border" type="text" name="Nombre" placeholder="Nombre producto"/>
                                        </Form.Group>
                                    </Form>
                                    <Form className="inline-form">
                                        <Form.Group className="mb-3" controlId="tipo">
                                            <Form.Control value={input.tipo} onChange={changeTipoHandler} className="font-h3 no-border" type="text" name="tipo" placeholder="Tipo del Producto" />
                                        </Form.Group>
                                    </Form>
                                    <Form className="inline-form">
                                        <Form.Group className="mb-3" controlId="valor_final">
                                            <Form.Label className="font-h2">Valor Final:</Form.Label>
                                            <Form.Control value={input.valor} onChange={changeValorHandler} className="font-h2-control no-border" type="number" name="valor" placeholder="Valor del Producto" />
                                        </Form.Group>
                                    </Form>
                                    <Form className="inline-form">
                                        <Form.Group className="mb-3" controlId="valor_final">
                                            <Form.Label className="font-h2">Valor Final:</Form.Label>
                                            <Form.Control value={input.valor_final} onChange={changeValorFinalHandler} className="font-h2-control no-border" type="number" name="valor_final" placeholder="Valor Final del Producto" />
                                        </Form.Group>
                                    </Form>
                                    <Form className="inline-form">
                                        <Form.Group className="mb-3" controlId="cantidad">
                                            <Form.Label className="font-h2">Cantidad:</Form.Label>
                                            <Form.Control value={input.cantidad} onChange={changeCantidadHandler} className="font-h2-control no-border" type="number" name="cantidad" placeholder="Cantidad del Producto" />
                                        </Form.Group>
                                    </Form>
                                </div>
                            </div>
                            <Button className="aceptar" onClick={handleGuardarProducto}>Aceptar</Button>
                            <Button className="cancelar" onClick={CancelarEdit}>Cancelar</Button>
                        </div>
                    </div>
                </NavStyle>
            </div>
        );
    } else {
        return (
            <div>
                <NavStyle>
                    <HeaderComponents />
                    <div className="container">
                        <div className="container-1">
                            <div className="card">
                                <div className="contenedor-img">
                                    <img id="productos" src={productos} alt="productos" />
                                </div>
                                <div className="contenedor-informacion">
                                    <h2> {datos.id} || {datos.nombre}</h2> 
                                    <h3>Tipo: {datos.tipo}</h3>
                                    <h3>Valor: {datos.valor}</h3>
                                    <h3>Valor Final: {datos.valor_final}</h3>
                                    <h3>Cantidad: {datos.cantidad}</h3>
                                </div>
                            </div>
                            <Button className="editar" onClick={changeMostrarCard}>Editar</Button>
                            <Button className="eliminar" onClick={handleEliminarProducto}>Eliminar</Button>
                        </div>
                    </div>
                </NavStyle>
            </div>
        );
    }

    
}

export default ProductoComponents;

// Estilos con styled-components


const NavStyle = styled.nav`
/* Separacion de las partes */

.container{
    margin: 2%;
    border: 2px solid #D5D5D5;
    background-color: #F0F0F0;
    display: flex;
    flex-direction: row;
    gap: 20px;
    height: 100%;
}
.container-1{
    height: 80%;
    background-color: #F0F0F0;
    width: 20%;
    flex-shrink: 0; /* Hace que el contenedor no se encoja */
    overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
    padding: 5%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
}
.container-2{
    background-color: #F0F0F0;
    flex-grow: 1; /* El lado derecho es flexible y ocupará todo el espacio restante */
    overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
    padding: 1%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
    max-height: calc(0px + 74.3vh); /* Asegura que el contenedor no exceda la altura de la ventana */
}

/* Todo la parte de la tabla */

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

td img {
    width: 30%;
    object-fit: cover;
}

td img:hover{
    cursor: pointer;
}

th:hover, td:hover{
    cursor: default;
}

/* Por el lado de la información del producto*/

.card{
    border: 1px solid black;
    border-radius: 10px;
    background-color: white;
}
.card .contenedor-img{
    background-color: #F0F0F0;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.card .contenedor-informacion{
    background-color: white;
    height: 100%;
}

.card .contenedor-informacion h3,
.card .contenedor-informacion h2,
.font-h2, .font-h3{
    margin-left: 4%;
}

.card .contenedor-informacion h3,
.font-h2, .font-h2-control{
    font-size: 20px;
    font-weight: normal;
}

.font-h3{
    margin-top: 5%;
    font-size: 24px;
    font-weight: bold;
    width: 90%;
}

.font-h2, font-h3, .font-h2-control{
    padding-bottom: 3%;
    padding-top: 3%;
}

.no-border {
    border: none;
    box-shadow: none;
}

.editar, .eliminar, .cancelar, .aceptar {
    margin-left: 5px;
    margin-top: 10px;
    padding: 10px 20px;
    font-size: 16px;
    border-radius: 30px;
    border: none;
    cursor: pointer;
}

.eliminar, .cancelar{
    background-color: #550100;
    color: #fff;
}

.editar{
    background-color: #39BEAB;
    color: black;
}

.aceptar{
    background-color: #00A768;
    color: black;
}

.editar:hover, 
.eliminar:hover,
.aceptar:hover,
.cancelar:hover{
    border: 1px solid black;
}

/* Fuente de la letra*/

td, th, h1, h2, h3, Button, .font-h2, .font-h3, .font-h2-control{
    font-family: 'Pacifico',serif;
}
`