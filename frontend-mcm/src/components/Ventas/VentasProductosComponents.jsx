import React, { useState, useEffect } from "react";
//import { useNavigate } from "react-router-dom";
import styled from "styled-components";
//import Button from "react-bootstrap/Button";
//import Form from "react-bootstrap/Form";


//import editar from "../../images/editar.png";

import HeaderComponents from "../Headers/HeaderComponents";
import VentasService from "../../services/VentasService";
import ProductoService from "../../services/ProductoService";

function VentasProductosComponents () {

    
    
    const [datosOrganizados, setDatosOrganizados] = useState([]);
    const [mostrarCard, setMostrarCard] = useState(true);
    const [ventasEntity, setventasEntity] = useState([]);
    const [yearEntity, setYearEntity] = useState(0);
    const [productoEntity, setProductoEntity] = useState([]);
    useEffect(() => {
        const fetchData = async () => {
            const productosRespuesta = await ProductoService.getProductos();
            const productos = productosRespuesta.data;
            const ventasRespuesta = await VentasService.getProductsByYear();
            const ventas = ventasRespuesta.data;

            setProductoEntity(productos);
            organizarDatos(ventas, productos);
        };

        fetchData();
    }, []);

    const organizarDatos = (ventas, productos) => {
        // Crear un objeto para mapear año y productos vendidos
        let datosPorAnio = {};
        
        ventas.forEach(venta => {
            const [idProducto, cantidad, anio] = venta;
            if (!datosPorAnio[anio]) {
                datosPorAnio[anio] = Array(productos.length).fill(0);
            }
            const indexProducto = productos.findIndex(p => p.id === idProducto);
            datosPorAnio[anio][indexProducto] += cantidad;
        });

        // Convertir el objeto a una matriz para su uso en la vista
        const matrizDatos = Object.entries(datosPorAnio).map(([anio, cantidades]) => {
            return [parseInt(anio), ...cantidades];
        });

        setDatosOrganizados(matrizDatos);
    };

    const handleClickVerYear =(venta) => {
        
        console.log("Año:", venta[0]);        
        
        const yearAux = venta[0];

        console.log(yearAux);
        setYearEntity(yearAux);

        updateVentas(yearAux); // Función que maneja la actualización
    }
    const updateVentas = (mainYear) =>{
        
        //get de ventas x mes x año
        VentasService.getProductsByYearAndMonth().then((res) => {
            const ventas = res.data;
            
            ProductoService.getProductos().then((res_2) => {
                setProductoEntity(res_2.data);
            })
           

            console.log(ventas);

            console.log(productoEntity);
             // Crear un objeto para mapear año y productos vendidos 
            let datosPorMesYear = {};
            
            ventas.forEach(venta => {
                const [id_producto, cantidad, mes, anio] = venta;
                console.log("######");
                if(anio === mainYear){
                    console.log("A")
                    if (!datosPorMesYear[mes]) {
                        console.log("B")
                        datosPorMesYear[mes] = Array(productoEntity.length).fill(0);
                    }
                    const indexProducto = productoEntity.findIndex(p => p.id === id_producto);
                    datosPorMesYear[mes][indexProducto] += cantidad;
                }
            });
            console.log("--------------------------------------------------")
            console.log(datosPorMesYear);
           /* // Convertir el objeto a una matriz para su uso en la vista
            const matrizDatos = Object.entries(datosPorAnio).map(([anio, cantidades]) => {
                return [parseInt(anio), ...cantidades];
            });
            */
            setventasEntity(datosPorMesYear);   
            console.log(datosPorMesYear);
        });

                     
            //console.log('Datos de ventas:', ventasTemp);

        //filtrar datos por año

        //ordenar datos -> mostrar en tabla
        
        
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
                            <b>Ventas producto</b>
                        </h1>
                            <table border="1" className="content-table">
                                <thead>
                                    <tr>
                                        <th>Año</th>
                                        {productoEntity.map((p, index) => <th key={index}>{p.nombre}</th>)}
                                    </tr>
                                </thead>
                                <tbody>
                                    {datosOrganizados.map((fila, index) => (
                                        <tr key={index}>
    
    
                                            <td onClick={() => handleClickVerYear(fila)} style={{ cursor: 'pointer' }}>
                                            {fila[0]}
                                            </td>
                                            {fila.slice(1).map((cantidad, index) => (
                                                <td key={index}>{cantidad}</td>
                                            ))}
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
                            <b>Ventas producto {yearEntity}</b>
                        </h1>
                        
                    <table border="1" className="content-table">
                        <thead>
                            <tr>
                                <th>Meses</th>
                                {productoEntity.map((p, index) => (
                                    <th key={index}>{p.nombre}</th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            {meses.map((mes, indexMes) => (
                                <tr key={indexMes}>
                                    <td>{mes}</td>
                                    {productoEntity.map((producto, indexProd) => (
                                        <td key={indexProd}>
                                            {/* Asumiendo que datosPorMesYear es una matriz con datos por mes y producto */}
                                            {ventasEntity[mes] && ventasEntity[mes][indexProd]}
                                        </td>
                                    ))}
                                </tr>
                            ))}
                            <tr>
                                <th>Total</th>
                                {productoEntity.map((producto, indexProd) => {
                                    // Calcula el total por producto
                                    const total = meses.reduce((acc, mes) => {
                                        return acc + (ventasEntity[mes] ? ventasEntity[mes][indexProd] : 0);
                                    }, 0);
                                    return <td key={indexProd}>{total}</td>;
                                })}
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



export default VentasProductosComponents;

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

    