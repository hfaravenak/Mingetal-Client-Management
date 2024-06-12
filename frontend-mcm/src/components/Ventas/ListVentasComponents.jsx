import React, { useState, useEffect } from "react";
import styled from "styled-components";

import HeaderComponents from "../Headers/HeaderComponents";
import ClienteService from "../../services/ClienteService";
import OrdenesDeCompraClienteService from "../../services/OrdenesDeCompraClienteService";
import ProductoService from "../../services/ProductoService";

function ListVentasComponents() {
    const formatFecha = (fecha) => {
        if(fecha===null){
            return "-";
        }
        const [year, month, day] = fecha.split("-");
        return `${day}-${month}-${year}`;
    };


    const [OCClienteEntity, setOCClienteEntity] = useState([]);
    const [ClienteEntity, setClienteEntity] = useState([]);
    const [ProductosEntity, setProductosEntity] = useState([]);
    const [ListOCProductosEntity, setListOCProductosEntity] = useState([]);
    const [expandedYears, setExpandedYears] = useState({});
    const [expandedMonths, setExpandedMonths] = useState({});

    useEffect(() => {
        OrdenesDeCompraClienteService.getOCCliente().then((res) => {
            setOCClienteEntity(res.data);
        });
        ClienteService.getClientes().then((res) => {
            setClienteEntity(res.data);
        });
        ProductoService.getProductos().then((res) => {
            setProductosEntity(res.data);
        });
        OrdenesDeCompraClienteService.getOCListProductosCliente().then((res) => {
            setListOCProductosEntity(res.data);
        });
    }, []);

    const busquedaCliente = (rut) => {
        return ClienteEntity.find((cliente) => cliente.rut === rut) || {};
    };

    const busquedaProductos = (id) => {
        return ListOCProductosEntity.filter((LOC) => LOC.id_OC_cliente === id).map((LOC) =>
            ProductosEntity.find((producto) => producto.id === LOC.id_producto)
        );
    };

    const ganancia = (valor_neto, id) => {
        const valorProductos = ListOCProductosEntity.filter((LOC) => LOC.id_OC_cliente === id).reduce((acc, LOC) => {
            const producto = ProductosEntity.find((producto) => producto.id === LOC.id_producto);
            return acc + (producto ? producto.valor : 0);
        }, 0);
        return valor_neto - valorProductos;
    };

    const handleYearClick = (year) => {
        setExpandedYears((prevExpandedYears) => ({
            ...prevExpandedYears,
            [year]: !prevExpandedYears[year],
        }));
    };

    const handleMonthClick = (year, month) => {
        setExpandedMonths((prevExpandedMonths) => ({
            ...prevExpandedMonths,
            [`${year}-${month}`]: !prevExpandedMonths[`${year}-${month}`],
        }));
    };
    const [expandedRows, setExpandedRows] = useState({});

    const handleRowClick = (id) => {
        setExpandedRows((prevExpandedRows) => ({
            ...prevExpandedRows,
            [id]: !prevExpandedRows[id],
        }));
    };

    const groupByYearAndMonth = (data) => {
        return data.reduce((acc, item) => {
            const date = new Date(item.fecha_solicitud);
            const year = date.getFullYear();
            const month = date.getMonth() + 1; // Month is zero-based
            if (!acc[year]) {
                acc[year] = {};
            }
            if (!acc[year][month]) {
                acc[year][month] = [];
            }
            acc[year][month].push(item);
            return acc;
        }, {});
    };

    const groupedData = groupByYearAndMonth(OCClienteEntity);
    const meses = [
        "Enero",
        "Febrero",
        "Marzo",
        "Abril",
        "Mayo",
        "Junio",
        "Julio",
        "Agosto",
        "Septiembre",
        "Octubre",
        "Noviembre",
        "Diciembre",
    ];

    return (
        <div>
            <NavStyle>
                <HeaderComponents />
                <div className="container">
                    <div align="center" className="container-2">
                        <h1>
                            <b>Historial de Ventas</b>
                        </h1>
                        {Object.keys(groupedData).map((year) => (
                            <div key={year} className="year-border">
                                <h2 onClick={() => handleYearClick(year)}>
                                    {expandedYears[year] ? "-" : "+"} {year}
                                </h2>
                                {expandedYears[year] && (
                                    <div>
                                        {Object.keys(groupedData[year]).map((month) => (
                                            <div key={month} className="month-border">
                                                <h3 onClick={() => handleMonthClick(year, month)}>
                                                    {expandedMonths[`${year}-${month}`] ? "-" : "+"} {meses[month]}
                                                </h3>
                                                {expandedMonths[`${year}-${month}`] && (
                                                    <table border="1" className="content-table">
                                                        <thead>
                                                            <tr>
                                                                <th></th>
                                                                <th>Empresa</th>
                                                                <th>Venta Neta</th>
                                                                <th>Ganancia</th>
                                                                <th>Fecha</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            {groupedData[year][month].map((OC) => (
                                                                <React.Fragment key={OC.id}>
                                                                    <tr onClick={() => handleRowClick(OC.id)}>
                                                                        <td>{expandedRows[OC.id]?'-':'+'}</td>
                                                                        <td>{busquedaCliente(OC.id_cliente).nombre}</td>
                                                                        <td>{OC.valor_pago}</td>
                                                                        <td>{ganancia(OC.valor_pago, OC.id)}</td>
                                                                        <td>{formatFecha(OC.fecha_solicitud)}</td>
                                                                    </tr>
                                                                    {expandedRows[OC.id] && (
                                                                        <tr>
                                                                            <td colSpan="4">
                                                                                <table
                                                                                    border="1"
                                                                                    className="content-table2"
                                                                                >
                                                                                    <thead>
                                                                                        <tr>
                                                                                            <th>Nombre</th>
                                                                                            <th>Valor</th>
                                                                                            <th>Valor Final</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                    <tbody>
                                                                                        {busquedaProductos(OC.id).map(
                                                                                            (producto) => (
                                                                                                <tr key={producto.id}>
                                                                                                    <td>
                                                                                                        {
                                                                                                            producto.nombre
                                                                                                        }
                                                                                                    </td>
                                                                                                    <td>
                                                                                                        {producto.valor}
                                                                                                    </td>
                                                                                                    <td>
                                                                                                        {
                                                                                                            producto.valor_final
                                                                                                        }
                                                                                                    </td>
                                                                                                </tr>
                                                                                            )
                                                                                        )}
                                                                                    </tbody>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                    )}
                                                                </React.Fragment>
                                                            ))}
                                                        </tbody>
                                                    </table>
                                                )}
                                            </div>
                                        ))}
                                    </div>
                                )}
                            </div>
                        ))}
                    </div>
                </div>
            </NavStyle>
        </div>
    );
}

export default ListVentasComponents;

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
        min-width: 1000px;
        max-width: 1000px;
        border-radius: 5px 5px 0 0;
        overflow: hidden;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    }
    .content-table2 {
        border-collapse: collapse;
        margin-left: 1;
        font-size: 0.9em;
        min-width: 900px;
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
    
    .year-border h2{
        background-color: #6199f9;
        padding: 10px;
        margin: 1%;
        border: 1px solid black;
        border-radius: 40px;
    }
    .month-border h3{
        background-color: #61c9f9;
        padding: 10px;
        margin: 1%;
        border: 1px solid black;
        border-radius: 40px;
        width: 90%;
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
