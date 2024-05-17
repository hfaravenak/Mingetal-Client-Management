import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import styled from "styled-components";
import editar from "../../../images/editar.png"
import HeaderComponents from "../../Headers/HeaderComponents";
import OrdenesDeCompraProveedorService from "../../../services/OrdenesDeCompraProveedorService";
import ProveedorService from "../../../services/ProveedorService";

function ListOCProveedorComponents() {
    const initialState = {
        id: "",
        nombre: "",
        empresa: "",
    };
    const [input, setInput] = useState(initialState);

    const [ProveedorEntity, setProveedorEntity] = useState([]);
    const [ContactoEntity, setContactoEntity] = useState([]);
    const [OCProveedorEntity, setOCProveedorEntity] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        OrdenesDeCompraProveedorService.getOCProveedor().then((res) => {
            console.log(res.data);
            setOCProveedorEntity(res.data);
        });
        ProveedorService.getProveedorByListOC().then((res) => {
            console.log(res.data);
            setProveedorEntity(res.data);
        });

        ProveedorService.getContacto1ByListOC().then((res)=>{
            console.log(res.data);
            setContactoEntity(res.data);
        })
    }, []);
        
    const changeIdHandler = event => {
        setInput({ ...input, id: event.target.value });
    };
    const changeNombreHandler = event => {
        setInput({ ...input, nombre: event.target.value });
    };
    const changeEmpresaHandler = event => {
        setInput({ ...input, empresa: event.target.value });
    };

    const busquedaProveedor= (id_proveedor) => {
        let variable = "";
        
        ProveedorEntity.forEach(proveedor => {
            if(proveedor.id_proveedor===id_proveedor){
                ContactoEntity.forEach(contacto =>{
                    if(contacto.rut===proveedor.id_contacto){
                        variable=contacto;
                    }
                })
            }
        });
        return variable;
    };
    const modificacionFecha = (fechaOriginal) => {  
        const partesFecha = fechaOriginal.split("-");

        // Crea un nuevo objeto Date con el formato "yyyy-mm-dd"
        const fecha = new Date(partesFecha[0], partesFecha[1] - 1, partesFecha[2]);

        // Formatea la fecha en el formato deseado "dd-mm-yyyy"
        const dia = fecha.getDate().toString().padStart(2, "0");
        const mes = (fecha.getMonth() + 1).toString().padStart(2, "0");
        const anio = fecha.getFullYear().toString();

        const fechaSalida = `${dia}-${mes}-${anio}`;
        return fechaSalida;
    }

    const buscarId = () => {
        OrdenesDeCompraProveedorService.getOCProveedorById(input.id).then((res) => {
            if (Array.isArray(res.data)) {
                setOCProveedorEntity(res.data);
            } else if(res.data===""){
                setOCProveedorEntity([]);
            }else{
                setOCProveedorEntity([res.data]);
            }
        });
    }
    const buscarNombre = () => {
        OrdenesDeCompraProveedorService.getOCProveedorByNombre(input.nombre).then((res) => {
            setOCProveedorEntity(res.data);
        });
    }
    const buscarEmpresa = () => {
        OrdenesDeCompraProveedorService.getOCProveedorByEmpresa(input.empresa).then((res) => {
            console.log(res.data);
            setOCProveedorEntity(res.data);
        });
    }

    const crearOCProveedor = () => {
        navigate("crear");
    }

    const handleKeyPressId = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarId(); // Llama a la función que deseas ejecutar
        }
    };
    const handleKeyPressNombre = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarNombre(); // Llama a la función que deseas ejecutar
        }
    };
    const handleKeyPressEmpresa = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarEmpresa(); // Llama a la función que deseas ejecutar
        }
    };

    const ChangeViendoProveedor = (todoElDato) => {
        let datos = {
            id: todoElDato.id,
            id_proveedor: todoElDato.id_proveedor,
            estado_pago: todoElDato.estado_pago,
            fecha_pago: todoElDato.fecha_pago,
            fecha_entrega: todoElDato.fecha_entrega,
            estado_entrega: todoElDato.estado_entrega,
            fecha_solicitud: todoElDato.fecha_solicitud,
            factura: todoElDato.factura,
            valor_pago: todoElDato.valor_pago,
            modo_pago: todoElDato.modo_pago,
            proveedor: busquedaProveedor(todoElDato.id_proveedor),

        };
        const datosComoTexto = JSON.stringify(datos);
        console.log(datosComoTexto)
        console.log(datos)
        navigate(`/oc/proveedor/mas info/${encodeURIComponent(datosComoTexto)}`);
    };

    return(
        <div>
            <NavStyle>
                <HeaderComponents/>
                <div className="container">
                    <div className="container-1">
                        <div className="inline-forms-container">
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="id" value = {input.id} onChange={changeIdHandler}>
                                    <Form.Label className="agregar">Id de OC:</Form.Label>
                                    <Form.Control className="agregar" type="number" name="id" placeholder="2044111" onKeyPress={handleKeyPressId}/>
                                </Form.Group>
                                <Button className="boton" onClick={buscarId}>Buscar</Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="nombre" value = {input.nombre} onChange={changeNombreHandler}>
                                    <Form.Label className="agregar">Nombre Proveedor:</Form.Label>
                                    <Form.Control className="agregar" type="text" name="Nombre" placeholder="Juan Perez" onKeyPress={handleKeyPressNombre}/>
                                </Form.Group>
                                <Button className="boton" onClick={buscarNombre}>Buscar</Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group className="mb-3" controlId="empresa" value = {input.empresa} onChange={changeEmpresaHandler} >
                                    <Form.Label className="agregar">Empresa del Proveedor:</Form.Label>
                                    <Form.Control className="agregar" type="text" name="empresa" placeholder="Nombre Generico" onKeyPress={handleKeyPressEmpresa}/>
                                </Form.Group>
                                <Button className="boton" onClick={buscarEmpresa}>Buscar</Button>
                            </Form>
                        </div>
                        <div className="btn-inf">
                            <Button className="boton" onClick={crearOCProveedor}>Ingresar nueva OC</Button>
                        </div>
                    </div>
                    <div align="center" className="container-2">
                        <h1><b> Ordenes de Compra de Proveedor</b></h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>Ref #</th>
                                    <th>Nombre</th>
                                    <th>Estado del Pago</th>
                                    <th>Fecha del Pago</th>
                                    <th>Fecha de la Solicitud</th>
                                    <th>Valor del Pago</th>
                                    <th>Estado de la Entrega</th>
                                    <th>Más información</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    OCProveedorEntity.map((OCProveedor) => (
                                        <tr key= {OCProveedor.id}>
                                            <td> #{OCProveedor.id} </td>
                                            <td>{busquedaProveedor(OCProveedor.id_proveedor).nombre}</td>
                                            <td> {OCProveedor.estado_pago} </td>
                                            <td> {modificacionFecha(OCProveedor.fecha_pago)} </td>
                                            <td> {modificacionFecha(OCProveedor.fecha_solicitud)} </td>
                                            <td> {OCProveedor.valor_pago} </td>
                                            <td> {OCProveedor.estado_entrega} </td>
                                            <td style={{textAlign: 'center', verticalAlign: 'middle', width:'1%'}}>
                                            <img id="editar" src={editar} alt="editar" onClick={() => ChangeViendoProveedor(OCProveedor)}/>
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

export default ListOCProveedorComponents;

const NavStyle = styled.nav`

/* Separacion de las partes */

.container{
    margin: 2%;
    border: 1px solid gray;
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
    height: 59vh;
}
.container-2{
    background-color: #F0F0F0;
    flex-grow: 1; /* El lado derecho es flexible y ocupará todo el espacio restante */
    overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
    padding: 1%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
    max-height: calc(0px + 74.5vh); /* Asegura que el contenedor no exceda la altura de la ventana */
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
    padding: 10px;
    font-size: 16px;
    border-radius: 30px;
    border: 1px solid #ccc;
}

/* Para WebKit (Chrome, Safari, Edge) */
input[type=number]::-webkit-outer-spin-button,
input[type=number]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}
/* Para Firefox */
input[type=number] {
    -moz-appearance: textfield;
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

/* Apartado del boton de crear */

.btn-inf .boton{
    font-size: 20px;
}

.boton:hover{
    border: 1px solid black;
}

/* Fuente de la letra*/

td, th, h1, Label, Control, Button{
    font-family: 'Pacifico',serif;
}
`