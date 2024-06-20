import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";


import editar from "../../images/editar.png";

import HeaderComponents from "../Headers/HeaderComponents";
import ClienteService from "../../services/ClienteService";
import OrdenesDeCompraClienteService from "../../services/OrdenesDeCompraClienteService";

function ListClienteComponents() {
    const navigate = useNavigate();

    const initialState = {
        rut: "",
        nombre: "",
        empresa: "",
    };
    const [input, setInput] = useState(initialState);

    const [rankingCard, setRankingCard] = useState(true);
    const [ClienteEntity, setClienteEntity] = useState([]);
    const [rankingData, setRankingData] = useState([]); // Estado para almacenar el ranking

    useEffect(() => {
        ClienteService.getClientes().then((res) => {
            setClienteEntity(res.data);
        });
    }, []);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setInput({ ...input, [name]: value });
    };

    const buscarRut = () => {
        ClienteService.getClienteByRut(input.rut).then((res) => {
            if (Array.isArray(res.data)) {
                setClienteEntity(res.data);
            } else if (res.data === "") {
                setClienteEntity([]);
            } else {
                setClienteEntity([res.data]);
            }
        });
    };

    const buscarNombre = () => {
        ClienteService.getClienteByNombre(input.nombre).then((res) => {
            setClienteEntity(res.data);
        });
    };

    const buscarEmpresa = () => {
        ClienteService.getClienteByEmpresa(input.empresa).then((res) => {
            setClienteEntity(res.data);
        });
    };

    const handleKeyPressRut = (event) => {
        if (event.key === "Enter") {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarRut(); // Llama a la función que deseas ejecutar
        }
    };

    const handleKeyPressNombre = (event) => {
        if (event.key === "Enter") {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarNombre(); // Llama a la función que deseas ejecutar
        }
    };

    const handleKeyPressEmpresa = (event) => {
        if (event.key === "Enter") {
            event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
            buscarEmpresa(); // Llama a la función que deseas ejecutar
        }
    };

    const crearCliente = () => {
        navigate("crear");
    };

    const ChangeViendoCliente = (todoElDato) => {
        const datos = {
            rut: todoElDato.rut,
            nombre: todoElDato.nombre,
            email: todoElDato.email,
            empresa: todoElDato.empresa,
            telefono: todoElDato.telefono,
        };
        const datosComoTexto = JSON.stringify(datos);
        navigate(`/clientes/mas info/${encodeURIComponent(datosComoTexto)}`);
    };

    const generateRanking = () => {
        ClienteService.getRankingCliente().then((res) => {
            setRankingData(res.data); // Almacena los datos del ranking en el estado
            setRankingCard(false);
            console.log(res.data);
        }).catch((error) => {
            console.error("Error fetching ranking data:", error);
        });
    };

    const backToList = () => {
        setRankingCard(true);
    };

    if (rankingCard) {
        return (
            <div>
                <NavStyle>
                    <HeaderComponents />
                    <div className="container">
                        <div className="container-1">
                            <div className="inline-forms-container">
                                <Form className="inline-form">
                                    <Form.Group controlId="rut">
                                        <Form.Label className="agregar">Rut del Cliente:</Form.Label>
                                        <Form.Control
                                            className="agregar"
                                            type="text"
                                            name="rut"
                                            placeholder="12.345.678-9"
                                            value={input.rut}
                                            onChange={handleInputChange}
                                            onKeyPress={handleKeyPressRut}
                                        />
                                    </Form.Group>
                                    <Button className="boton" onClick={buscarRut}>
                                        Buscar
                                    </Button>
                                </Form>
                                <Form className="inline-form">
                                    <Form.Group controlId="nombre">
                                        <Form.Label className="agregar">Nombre Cliente:</Form.Label>
                                        <Form.Control
                                            className="agregar"
                                            type="text"
                                            name="nombre"
                                            placeholder="Juan Perez"
                                            value={input.nombre}
                                            onChange={handleInputChange}
                                            onKeyPress={handleKeyPressNombre}
                                        />
                                    </Form.Group>
                                    <Button className="boton" onClick={buscarNombre}>
                                        Buscar
                                    </Button>
                                </Form>
                                <Form className="inline-form">
                                    <Form.Group controlId="empresa">
                                        <Form.Label className="agregar">Empresa del Cliente:</Form.Label>
                                        <Form.Control
                                            className="agregar"
                                            type="text"
                                            name="empresa"
                                            placeholder="Nombre Generico"
                                            value={input.empresa}
                                            onChange={handleInputChange}
                                            onKeyPress={handleKeyPressEmpresa}
                                        />
                                    </Form.Group>
                                    <Button className="boton" onClick={buscarEmpresa}>
                                        Buscar
                                    </Button>
                                </Form>
                            </div>
                            <div className="ranking-container">
                                <Button className="boton" onClick={generateRanking}>
                                    Generar ranking de clientes
                                </Button>
                            </div>
                            <div className="btn-inf">
                                <Button className="boton" onClick={crearCliente}>
                                    Ingresar Cliente
                                </Button>
                            </div>
                        </div>
                        <div align="center" className="container-2">
                            <h1>
                                <b> Listado de Cliente</b>
                            </h1>
                            <table border="1" className="content-table">
                                <thead>
                                    <tr>
                                        <th>RUT</th>
                                        <th>Nombre</th>
                                        <th>Correo</th>
                                        <th>Telefono</th>
                                        <th>Empresa</th>
                                        <th>Más información</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {ClienteEntity.map((cliente) => (
                                        <tr key={cliente.rut}>
                                            <td> {cliente.rut} </td>
                                            <td> {cliente.nombre} </td>
                                            <td> {cliente.email} </td>
                                            <td> {cliente.telefono} </td>
                                            <td> {cliente.empresa} </td>
                                            <td style={{ textAlign: "center", verticalAlign: "middle", width: "1%" }}>
                                                <img
                                                    id="editar"
                                                    src={editar}
                                                    alt="editar"
                                                    onClick={() => ChangeViendoCliente(cliente)}
                                                />
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
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
                            <div className="inline-forms-container">
                                <Form className="inline-form">


                                </Form>
                                <Form className="inline-form">
                                </Form>
                                <Form className="inline-form">
                                </Form>
                            </div>
                            <div className="ranking-container">
                                <Button className="boton" onClick={backToList}>
                                    Volver a lista de clientes
                                </Button>
                            </div>
                            <div className="btn-inf">
                            <Button className="boton" onClick={crearCliente}>
                                Ingresar Cliente
                            </Button>
                        </div>
                        </div>
                        <div align="center" className="container-2">
                            <h1>
                                <b>Ranking de Clientes</b>
                            </h1>
                            <table border="1" className="content-table-ranking">
                                <thead>
                                    <tr>                                        
                                        <th>RUT</th>
                                        <th>Nombre</th>
                                        <th>Correo</th>
                                        <th>Telefono</th>
                                        <th>Empresa</th>
                                        <th>Año</th>
                                        <th>Suma total de dinero</th>
                                        <th>Total Compras</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {rankingData.map((item, index) => (
                                        <tr key={index}>
                                            <td>{item[0]}</td>                                            
                                            <td>{item[4]}</td>
                                            <td>{item[6]}</td>
                                            <td>{item[7]}</td>
                                            <td>{item[5]}</td>
                                            <td>{item[3]}</td>
                                            <td>${item[1]}</td>
                                            <td>{item[2]}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>

                    </div>
                </NavStyle>
            </div>
        );
    }
}

export default ListClienteComponents;

const NavStyle = styled.nav`
    /* Separación de las partes */

    .container {
        margin: 2%;
        border: 2px solid #d5d5d5;
        background-color: #f0f0f0;
        display: flex;
        flex-direction: row;
        gap: 20px;
        height: 100%;
    }
    .container-1 {
        background-color: #f0f0f0;
        width: 10%;
        flex-shrink: 0; /* Hace que el contenedor no se encoja */
        overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
        padding: 5%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
        display: flex;
        flex-direction: column;
        height: 58.9vh;
    }
    .container-2 {
        background-color: #f0f0f0;
        flex-grow: 1; /* El lado derecho es flexible y ocupará todo el espacio restante */
        overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
        padding: 1%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
        max-height: calc(0px + 74.3vh); /* Asegura que el contenedor no exceda la altura de la ventana */
    }

    /* Parte de la tabla */

    .content-table {
        border-collapse: collapse;
        margin-left: 1;
        font-size: 0.9em;
        min-width: 100px;
        border-radius: 5px 5px 0 0;
        overflow: hidden;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    }
    .content-table thead tr {
        background-color: #d2712b;
        color: #ffffff;
        text-align: left;
        font-weight: bold;
    }
    .content-table th,
    .content-table td {
        padding: 12px 15px;
    }
    .content-table td {
        font-size: 18px;
    }
    .content-table tbody tr {
        border-bottom: 1px solid #dddddd;
    }
    .content-table tbody tr:nth-of-type(even) {
        background-color: #f3f3f3;
    }
    .content-table tbody tr:last-of-type {
        border-bottom: 2px solid #009879;
    }
    .content-table tbody tr.active-row {
        font-weight: bold;
        color: #009879;
    }

    img {
        width: 50%;
        object-fit: cover;
    }
    img:hover {
        cursor: pointer;
    }
    th:hover,
    td:hover {
        cursor: default;
    }

    .content-table-ranking {
        border-collapse: collapse;
        margin-left: 1;
        font-size: 0.9em;
        min-width: 100px;
        border-radius: 5px 5px 0 0;
        overflow: hidden;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    }
    .content-table-ranking thead tr {
        background-color: #d2712b;
        color: #ffffff;
        text-align: left;
        font-weight: bold;
    }
    .content-table-ranking th,
    .content-table-ranking td {
        padding: 12px 15px;
    }
    .content-table-ranking td {
        font-size: 18px;
    }
    .content-table-ranking tbody tr {
        border-bottom: 1px solid #dddddd;
    }
    .content-table-ranking tbody tr:nth-of-type(even) {
        background-color: #f3f3f3;
    }
    .content-table-ranking tbody tr:last-of-type {
        border-bottom: 2px solid #009879;
    }
    .content-table-ranking tbody tr.active-row {
        font-weight: bold;
        color: #009879;
    }


    /* Formulario de filtro */

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

    /* Botón de crear */

    .btn-inf .boton {
        font-size: 20px;
    }
    .boton:hover {
        border: 1px solid black;
    }

    /* Ranking */
    .ranking-container {
        display: flex;
        justify-content: center;
        margin-top: 10px;
    }
    .btn-inf .boton {
        font-size: 20px;
    }
    .boton:hover {
        border: 1px solid black;
    }    

    /* Fuente de la letra */

    td,
    th,
    h1,
    Label,
    Control,
    Button {
        font-family: "Pacifico", serif;
    }
`;
