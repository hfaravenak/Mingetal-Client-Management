import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import HeaderComponents from "../Headers/HeaderComponents";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Swal from "sweetalert2";

import styled from "styled-components";
import editar from "../../images/editar.png"
import ProductoService from '../../services/ProductoService'

function ListProductosComponents(){
    
    const initialState = {
        tipo: "",
        nombre: "",
        cantidad: "",
    };

    const [ProductoEntity, setProductoEntity] = useState([]);
    const [input, setInput] = useState(initialState);
    const navigate = useNavigate();

    useEffect(() => {
        ProductoService.getProductos().then((res) => {
            setProductoEntity(res.data);
        });
    }, []);

    const changeTipoHandler = event => {
        setInput({ ...input, tipo: event.target.value });
    };
    const changeNombreHandler = event => {
        setInput({ ...input, nombre: event.target.value });
    };
    const changeCantidadHandler = event => {
        setInput({ ...input, cantidad: event.target.value });
    };

    const buscarTipo = () => {
        ProductoService.getProductoByTipo(input.tipo).then((res) => {
            setProductoEntity(res.data);
        });
    };
    const buscarNombre = () => {
        ProductoService.getProductoByNombre(input.nombre).then((res) => {
            setProductoEntity(res.data);
        });
    };

    const buscarCantidad = () => {
        ProductoService.getProductoByCantidad(input.cantidad).then((res) => {
            setProductoEntity(res.data);
        });
    };

    const crearProducto = () => {
        navigate("crear");
    };

    const handleKeyPressTipo = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            buscarTipo();
        }
    };
    const handleKeyPressNombre = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            buscarNombre();
        }
    };

    const handleKeyPressCantidad = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            buscarCantidad();
        }
    };

    const ChangeViendoProducto = (id, tipo, nombre, valor, valor_final, cantidad) => {
        const datos = {
            id: id,
            tipo: tipo,
            nombre: nombre,
            valor: valor,
            valor_final: valor_final,
            cantidad: cantidad
        };
        const datosComoTexto = JSON.stringify(datos);
        navigate(`/productos/mas-info/${encodeURIComponent(datosComoTexto)}`);
    };
    return (
        <div>
            <NavStyle>
                <HeaderComponents />
                <div className="container">
                    <div className="container-1">
                        <div className="inline-forms-container">
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="tipo" value={input.tipo} onChange={changeTipoHandler}>
                                    <Form.Label className="agregar">Tipo del Producto:</Form.Label>
                                    <Form.Control className="agregar" type="text" name="tipo" placeholder="Electrónica" onKeyPress={handleKeyPressTipo} />
                                </Form.Group>
                                <Button className="boton" onClick={buscarTipo}>Buscar</Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="nombre" value={input.nombre} onChange={changeNombreHandler}>
                                    <Form.Label className="agregar">Nombre del Producto:</Form.Label>
                                    <Form.Control className="agregar" type="text" name="nombre" placeholder="iPhone" onKeyPress={handleKeyPressNombre} />
                                </Form.Group>
                                <Button className="boton" onClick={buscarNombre}>Buscar</Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="cantidad" value={input.cantidad} onChange={changeCantidadHandler}>
                                    <Form.Label className="agregar">Cantidad del Producto:</Form.Label>
                                    <Form.Control className="agregar" type="number" name="cantidad" placeholder="10" onKeyPress={handleKeyPressCantidad} />
                                </Form.Group>
                                <Button className="boton" onClick={buscarCantidad}>Buscar</Button>
                            </Form>
                        </div>
                        <div className="btn-inf">
                            <Button className="boton" onClick={crearProducto}>Ingresar Producto</Button>
                        </div>
                    </div>
                    <div align="center" className="container-2">
                        <h1><b>Listado de Productos</b></h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Tipo</th>
                                    <th>Nombre</th>
                                    <th>Valor</th>
                                    <th>Valor Final</th>
                                    <th>Cantidad</th>
                                    <th>Más información</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    ProductoEntity.map((producto) => (
                                        <tr key={producto.id}>
                                            <td>{producto.id}</td>
                                            <td>{producto.tipo}</td>
                                            <td>{producto.nombre}</td>
                                            <td>{producto.valor}</td>
                                            <td>{producto.valor_final}</td>
                                            <td>{producto.cantidad}</td>
                                            <td style={{textAlign: 'center', verticalAlign: 'middle', width: '1%'}}>
                                                <img id="editar" src={editar} alt="editar" onClick={() => ChangeViendoProducto(producto.id, producto.tipo, producto.nombre, producto.valor, producto.valor_final, producto.cantidad)} />
                                            </td>
                                        </tr>
                                    ))
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            </NavStyle>
        </div>
    );
}

export default ListProductosComponents;

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
    background-color: #F0F0F0;
    width: 10%;
    flex-shrink: 0; /* Hace que el contenedor no se encoja */
    overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
    padding: 5%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
    display: flex;
    flex-direction: column;
    height: 58.9vh;
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

img {
    width: 50%;
    object-fit: cover;
}

img:hover{
    cursor: pointer;
}

th:hover, td:hover{
    cursor: default;
}

/* Todo lo relacionado al form del filtro */

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
input[type="text"], input[type="number"]{
    background-color: rgb(201, 201, 201);
    width: 100%;
    padding: 10}
    `