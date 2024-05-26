import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import editar from "../../images/editar.png";

import HeaderComponents from "../Headers/HeaderComponents";
import ProveedorService from "../../services/ProveedorService";
import ContactoService from "../../services/ContactoService";

function ListProveedorComponents() {
    const navigate = useNavigate();

    const initialState = {
        rut: "",
        nombre: "",
        empresa: "",
        rubro: "",
    };
    const [input, setInput] = useState(initialState);

    const [ProveedorEntity, setProveedorEntity] = useState([]);
    const [ContactosEntity, setContactosEntity] = useState([]);
    const [RubroEntity, setRubroEntity] = useState([]);
    useEffect(() => {
        ProveedorService.getProveedores().then((res) => {
            setProveedorEntity(res.data);
        });
        ContactoService.getContactos().then((res) => {
            setContactosEntity(res.data);
        });
        ProveedorService.getRubroProveedores().then((res) => {
            setRubroEntity(res.data);
        });
    }, []);

    const busquedaContacto = (id_contacto) => {
        let variable=null;
        ContactosEntity.forEach(contacto => {
            if(contacto.rut===id_contacto){
                variable=contacto;
            }
        })
        return variable;
    }
   
    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setInput({ ...input, [name]: value });
    };


    const buscarRut = () => {
        ProveedorService.getProveedorByRut(input.rut).then((res) => {
            if (Array.isArray(res.data)) {
                setProveedorEntity(res.data);
            } else if(res.data===""){
                setProveedorEntity([]);
            }else{
                setProveedorEntity([res.data]);
            }
        });
    }
    const buscarNombre = () => {
        ProveedorService.getProveedorByNombre(input.nombre).then((res) => {
            setProveedorEntity(res.data);
        });
    }
    const buscarEmpresa = () => {
        ProveedorService.getProveedorByEmpresa(input.empresa).then((res) => {
            if(Array.isArray(res.data)){
                setProveedorEntity(res.data);
            }else if(res.data===""){
                setProveedorEntity([]);
            }else{
                setProveedorEntity([res.data]);
            }
        });
    }
    const buscarRubro = () => {
        ProveedorService.getProveedorByRubro(input.rubro).then((res) => {
            setProveedorEntity(res.data);
        });
    }
    const handleKeyPressRut = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarRut(); // Llama a la función que deseas ejecutar
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

    const crearProveedor = () => {
        navigate("crear");
    }

    const ChangeViendoCliente = (proveedor) => {
        const datos = {
            id_proveedor: proveedor.id_proveedor,
            empresa: proveedor.empresa,
            rubro: proveedor.rubro,
            id_contacto: busquedaContacto(proveedor.id_contacto),
            id_contacto2: busquedaContacto(proveedor.id_contacto2),
            id_contacto3: busquedaContacto(proveedor.id_contacto3),
            comentario: proveedor.comentario
        };
        const datosComoTexto = JSON.stringify(datos);
        navigate(`/proveedores/mas info/${encodeURIComponent(datosComoTexto)}`);
    };
    return (
        <div>
            <NavStyle>
                <HeaderComponents />
                <div className="container">
                    <div className="container-1">
                        <div className="inline-forms-container">
                            <Form className="inline-form">
                                <Form.Group name="rut" controlId="rut">
                                    <Form.Label className="agregar">Rut del Proveedor:</Form.Label>
                                    <Form.Control
                                        className="agregar"
                                        type="text"
                                        name="rut"
                                        placeholder="12.345.678-9"
                                        onKeyPress={handleKeyPressRut}
                                        value={input.rut}
                                        onChange={handleInputChange}
                                    />
                                </Form.Group>
                                <Button className="boton" onClick={buscarRut}>
                                    Buscar
                                </Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group name="nombre" controlId="nombre">
                                    <Form.Label className="agregar">Nombre Proveedor:</Form.Label>
                                    <Form.Control
                                        className="agregar"
                                        type="text"
                                        name="nombre"
                                        placeholder="Juan Perez"
                                        onKeyPress={handleKeyPressNombre}
                                        value={input.nombre}
                                        onChange={handleInputChange}
                                    />
                                </Form.Group>
                                <Button className="boton" onClick={buscarNombre}>
                                    Buscar
                                </Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group name="empresa" controlId="empresa">
                                    <Form.Label className="agregar">Empresa del Proveedor:</Form.Label>
                                    <Form.Control
                                        className="agregar"
                                        type="text"
                                        name="empresa"
                                        placeholder="Nombre Generico"
                                        onKeyPress={handleKeyPressEmpresa}
                                        value={input.empresa}
                                        onChange={handleInputChange}
                                    />
                                </Form.Group>
                                <Button className="boton" onClick={buscarEmpresa}>
                                    Buscar
                                </Button>
                            </Form>
                            <Form className="inline-form">
                                <Form.Group name="rubro" controlId="rubro">
                                    <Form.Label className="agregar">Rubro:</Form.Label>
                                    <Form.Select
                                        style={{ width: "100%" }}
                                        className="font-h2 no-border"
                                        name="rubro"
                                        value={input.rubro}
                                        onChange={handleInputChange}
                                    >
                                        <option value="" style={{ color: "gray" }}>
                                            Rubros
                                        </option>
                                        {RubroEntity.map((rubro) => (
                                            <option key={rubro} value={rubro}>
                                                {rubro}
                                            </option>
                                        ))}
                                    </Form.Select>
                                </Form.Group>
                                <Button className="boton" onClick={buscarRubro}>
                                    Buscar
                                </Button>
                            </Form>
                        </div>
                        <div className="btn-inf">
                            <Button className="boton" onClick={crearProveedor}>
                                Ingresar Proveedor
                            </Button>
                        </div>
                    </div>
                    <div align="center" className="container-2">
                        <h1>
                            <b> Listado de Proveedores</b>
                        </h1>
                        <table border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>Nombre Contacto</th>
                                    <th>Empresa</th>
                                    <th>Rubro</th>
                                    <th>Número Celular</th>
                                    <th>Correo</th>
                                    <th>Más información</th>
                                </tr>
                            </thead>
                            <tbody>
                                {ProveedorEntity.map((proveedor) => {
                                    const contacto = busquedaContacto(proveedor.id_contacto);
                                    return (
                                        <tr key={proveedor.id_proveedor}>
                                            <td> {contacto ? contacto.nombre : ""} </td>
                                            <td> {proveedor.empresa} </td>
                                            <td> {proveedor.rubro} </td>
                                            <td> {contacto ? contacto.fono_cel : ""} </td>
                                            <td> {contacto ? contacto.correo : ""} </td>
                                            <td style={{ textAlign: "center", verticalAlign: "middle", width: "1%" }}>
                                                <img
                                                    id="editar"
                                                    src={editar}
                                                    alt="editar"
                                                    onClick={() => ChangeViendoCliente(proveedor)}
                                                />
                                            </td>
                                        </tr>
                                    );
                                })}
                            </tbody>
                        </table>
                    </div>
                </div>
            </NavStyle>
        </div>
    );   
}

export default ListProveedorComponents;

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
input[type="text"], option, select{
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