import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
//import Button from "react-bootstrap/Button";
//import Form from "react-bootstrap/Form";


//import editar from "../../images/editar.png";

import HeaderComponents from "../Headers/HeaderComponents";
import VentasService from "../../services/VentasService";

function VentasGeneralesComponents () {

    
    //const navigate = useNavigate();

    const initialState = {
        id: "",
        nombre: "",
        empresa: "",
    };
    const [input, setInput] = useState(initialState);

    
    const [mostrarCard, setMostrarCard] = useState(true);
    const [ventasEntity, setventasEntity] = useState([]);
    const [yearEntity, setYearEntity] = useState(0)
    useEffect(() => {
        VentasService.getVentasByYear().then((res) => {
            setventasEntity(res.data);
            console.log('Datos de ventas:', res.data); // Esto mostrará los datos obtenidos en la consola
        }).catch((error) => {
            console.error('Error al obtener los datos:', error);
        });
    }, []);
    
    const handleClickVerYear =(venta) => {
        
        console.log("Monto seleccionado:", venta[0], "Año:", venta[1]);        
        
        const yearAux = venta[1];

        console.log(yearAux);
        setYearEntity(yearAux);

        updateVentas(yearAux); // Función que maneja la actualización
    }
    const updateVentas = (mainYear) =>{
        
        VentasService.getVentasByMonth().then((res) => {
            const data = res.data;
            const ventasTemp = Array(12).fill(0);

            data.forEach(venta => {
                const [monto, mes, year] = venta;
                
                console.log("mes: " + mes)
                console.log("año: " + year)
                console.log("año analizado: " + mainYear)
                if(year === mainYear){
                    console.log("Años iguales")
                    ventasTemp[mes - 1] += monto; // Suma el monto al mes correspondiente (mes - 1 porque el array es base 0)
                    console.log("Entonces el mes "+meses[mes - 1]+" acumula "+monto+" pesos" )
                }
                else{
                    
                    console.log("Años distintos. No se acumula nada en el mes " + meses[mes - 1]);
                }
            });

            setventasEntity(ventasTemp);            
            console.log('Datos de ventas:', ventasTemp);
        });
        
        setMostrarCard(false);

    };

    const meses = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];

    
    if(mostrarCard){
        return(
            <div>
                <NavStyle>
                <HeaderComponents />
                <div className="container">
                    <div className="container-1">
                        <div className="inline-forms-container">
                           
                        </div>
                        <div className="btn-inf">

                        </div>
                    </div>
                    <div align="center" className="container-2">
                        <h1>
                            <b>Ventas generales</b>
                        </h1>
                            <table border="1" className="content-table">
                                <thead>
                                    <tr>
                                        <th>Monto Total de Ventas</th>
                                        <th>Año</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {ventasEntity.map((venta, index) => (
                                        <tr key={index}>
                                            
                                            <td>{venta[0]}</td> {/* Monto total de ventas */}
                                            <td onClick={() => handleClickVerYear(venta)} style={{ cursor: 'pointer' }}>
                                            {venta[1]}
                                            </td>

                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        
                    </div>
                </div>
                </NavStyle>
            </div>
        )
    }else{
        return(
            <div>
                 <NavStyle>
                <HeaderComponents />
                <div className="container">
                    <div className="container-1">
                        <div className="inline-forms-container">
                           
                        </div>
                        <div className="btn-inf">

                        </div>
                    </div>
                    <div align="center" className="container-2">
                        <h1>
                            <b>Ventas generales por mes</b>
                        </h1>
                        <table  border="1" className="content-table">
                            <thead>
                                <tr>
                                    <th>Año</th>
                                    {meses.map(mes => <th key={mes}>{mes}</th>)}
                                    <th>Total</th>
                                </tr>
                            </thead>
                                <tbody>
                                <tr>
                                    <th>{yearEntity}</th>
                                    {ventasEntity.map((venta, index) => (
                                        <td key={index}>{venta}</td>
                                    ))}
                                    <td>{ventasEntity.reduce((sum, current) => sum + current, 0)}</td>
                                </tr>
                                </tbody>
                            </table>
                        
                    </div>
                </div>
                </NavStyle>
            </div>
        )
    }
};



export default VentasGeneralesComponents;

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
