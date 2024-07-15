import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import HeaderComponents from "../Headers/HeaderComponents";
import VentasService from "../../services/VentasService";
import axios from "axios";

import excel from "../../images/excel.png";
import atras from "../../images/atras.png";

function EstadisticaGeneralesComponents() {
   const navigate = useNavigate();
   
   const [mostrarCard, setMostrarCard] = useState(true);
   const [ventasEntity, setventasEntity] = useState([]);
   const [ventasSecundariaEntity, setventasSecundariaEntity] = useState([]);
   const [cantidadEntity, setCantidadEntity] = useState([]);
   const [yearEntity, setYearEntity] = useState(0);
   useEffect(() => {
      VentasService.getVentasByYear()
         .then((res) => {
            setventasEntity(res.data);
         })
         .catch((error) => {
            console.error("Error al obtener los datos:", error);
         });
   }, []);

   const handleClickVerYear = (venta) => {
      const yearAux = venta[2];
      setYearEntity(yearAux);

      updateVentas(yearAux); // Función que maneja la actualización
   };
   const updateVentas = (mainYear) => {
      VentasService.getVentasByMonth().then((res) => {
         const data = res.data;
         const cantidadTemp = Array(12).fill(0);
         const ventasTemp = Array(12).fill(0);

         // monto vendido por mes de cada año
         // cantidad de ventas por mes de cada año
         // mes
         // Año de venta
         data.forEach((venta) => {
            const [monto, cantidad, mes, year] = venta;
            if (year === mainYear) {
               cantidadTemp[mes - 1] += cantidad; // Suma el monto al mes correspondiente (mes - 1 porque el array es base 0)
               ventasTemp[mes - 1] += monto;
            }
         });

         setCantidadEntity(cantidadTemp);

         setventasSecundariaEntity(ventasTemp);
      });

      setMostrarCard(false);
   };

   const descargarExcel = async () => {
      try {
         const response = await axios.get("http://localhost:8080/ordenes_de_compra/archive/download/excel/estadistica", {
            responseType: "blob", // importante para manejar el blob de la respuesta
         });

         const url = window.URL.createObjectURL(new Blob([response.data]));
         const link = document.createElement("a");
         link.href = url;
         link.setAttribute("download", "Estadistica.xlsx"); // nombre del archivo
         document.body.appendChild(link);
         link.click();
      } catch (error) {
         console.error("Error al descargar el archivo", error);
      }
   };

   const meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
   const regresar = () => {
      navigate(`/estadistica`);
   }
   const regresarCard = () => {
      setMostrarCard(true);
   }
   if (mostrarCard) {
      return (
         <div>
            <NavStyle>
               <HeaderComponents />
               <div className="container-create">
                  <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} style={{width:"35px"}}/>
               </div>
               <div className="container">
                  <div align="center" className="container-2">
                     <div className="TituloSuperior">
                        <h1>
                           <b>Ventas generales</b>
                        </h1>
                        <div className="Derecha">
                           <img id="excel" src={excel} alt="excel" className="img-card" onClick={descargarExcel} />
                        </div>
                     </div>

                     <table border="1" className="content-table">
                        <thead>
                           <tr>
                              <th>Monto Total de Ventas</th>
                              <th>Cantidad de ventas</th>
                              <th>Año</th>
                           </tr>
                        </thead>
                        <tbody>
                           {ventasEntity.map((venta, index) => (
                              <tr key={index}>
                                 <td>${venta[0]}</td> {/* Monto total de ventas */}
                                 <td>{venta[1]}</td>
                                 <td onClick={() => handleClickVerYear(venta)} style={{ cursor: "pointer" }}>
                                    {venta[2]}
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
               <div className="container-create">
                  <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresarCard} style={{width:"35px"}}/>
               </div>
               <div className="container">
                  <div align="center" className="container-2">
                     <div className="TituloSuperior">
                        <h1>
                           <b>Cantidad de ventas por mes || {yearEntity}</b>
                        </h1>
                        <div className="Derecha">
                           <img id="excel" src={excel} alt="excel" className="img-card" onClick={descargarExcel} />
                        </div>
                     </div>

                     <table border="1" className="content-table">
                        <thead>
                           <tr>
                              <th>Año</th>
                              {meses.map((mes) => (
                                 <th key={mes}>{mes}</th>
                              ))}
                              <th>Total</th>
                           </tr>
                        </thead>
                        <tbody>
                           <tr>
                              <th>{yearEntity}</th>
                              {cantidadEntity.map((venta, index) => (
                                 <td key={index}>{venta}</td>
                              ))}
                              <td>{cantidadEntity.reduce((sum, current) => sum + current, 0)}</td>
                           </tr>
                        </tbody>
                     </table>

                     <h1>
                        <b>Ventas por mes || {yearEntity}</b>
                     </h1>
                     <table border="1" className="content-table">
                        <thead>
                           <tr>
                              <th>Año</th>
                              {meses.map((mes) => (
                                 <th key={mes}>{mes}</th>
                              ))}
                              <th>Total</th>
                           </tr>
                        </thead>
                        <tbody>
                           <tr>
                              <th>{yearEntity}</th>
                              {ventasSecundariaEntity.map((venta, index) => (
                                 <td key={index}>${venta}</td>
                              ))}
                              <td>${ventasSecundariaEntity.reduce((sum, current) => sum + current, 0)}</td>
                           </tr>
                        </tbody>
                     </table>
                  </div>
               </div>
            </NavStyle>
         </div>
      );
   }
}

export default EstadisticaGeneralesComponents;

const NavStyle = styled.nav`
   /* Separación de las partes */

   .container-create {
      margin: 2%;
      margin-bottom: 0;
      padding: 1%;
      padding-bottom: 0;
      border: 2px solid #d5d5d5;
      border-bottom: 0;
      background-color: #f0f0f0;
   }
      .img-back:hover{
      cursor: pointer;
   }

   .container {
      margin: 2%;
      margin-top: 0;
      border: 2px solid #d5d5d5;
      border-top: 0;
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
      padding-top: 1%;
      display: flex;
      flex-direction: column;
      height: 58.9vh;
   }
   .container-2 {
      background-color: #f0f0f0;
      flex-grow: 1; /* El lado derecho es flexible y ocupará todo el espacio restante */
      overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
      padding: 1%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
      padding-top: 0;
      max-height: calc(0px + 74.3vh); /* Asegura que el contenedor no exceda la altura de la ventana */
   }

   /* Parte de la tabla */

   .TituloSuperior {
      display: flex;
      justify-content: center;
      align-items: center;
      white-space: nowrap;
      position: relative;
   }

   .TituloSuperior .Derecha {
      position: absolute;
      right: 0;
   }

   .TituloSuperior .Derecha .img-card {
      width: 5%;
      height: 5%;
   }

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
