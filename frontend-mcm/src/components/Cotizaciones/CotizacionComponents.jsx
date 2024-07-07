import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import clientes from "../../images/cliente.png";

import HeaderComponents from "../Headers/HeaderComponents";
import CotizacionService from "../../services/CotizacionService";
import ProductoService from "../../services/ProductoService";

function CotizacionComponent() {
   const formatFecha = (fecha) => {
      if (fecha === null) {
         return "-";
      }
      const [year, month, day] = fecha.split("-");
      return `${day}-${month}-${year}`;
   };

   const navigate = useNavigate();

   const { cotizacion } = useParams();
   const datos = JSON.parse(decodeURIComponent(cotizacion));

   const initialState = {
      idCotizacion: "",
      pedido: "",
      fecha: "",
      estado: "",
      rutCliente: "",
   };
   const [input, setInput] = useState(initialState);

   const [ProductoEntity, setProductoEntity] = useState([]);
   useEffect(() => {
      ProductoService.getListByCotizacion(datos.idCotizacion).then((res) => {
         setProductoEntity(res.data);
      });
   }, [datos.idCotizacion]);

   const handleInputChange = (event) => {
      const { name, value } = event.target;
      setInput({ ...input, [name]: value });
   };

   const [mostrarCard, setMostrarCard] = useState(false);
   const changeMostrarCard = () => {
      setInput({
         idCotizacion: datos.idCotizacion,
         pedido: datos.pedido,
         fecha: datos.fecha,
         estado: datos.estado,
         rutCliente: datos.rutCliente,
      });
      setMostrarCard(true);
   };

   const enviarDatos = () => {
      Swal.fire({
         title: "¿Seguro que desea modificar esta cotización?",
         icon: "question",
         showDenyButton: true,
         confirmButtonText: "Confirmar",
         confirmButtonColor: "rgb(68, 194, 68)",
         denyButtonText: "Cancelar",
         denyButtonColor: "rgb(190, 54, 54)",
      }).then((result) => {
         if (result.isConfirmed) {
            let updateCotizacion = {
               idCotizacion: input.idCotizacion,
               pedido: input.pedido,
               fecha: input.fecha,
               estado: input.estado,
               rutCliente: datos.rutCliente,
            };
            CotizacionService.putCotizacion(updateCotizacion);
            Swal.fire({
               title: "Actualizando...",
               text: "Por favor espera",
               timer: 1000,
               didOpen: () => {
                  Swal.showLoading();
               },
               willClose: () => {
                  let enviardatos = {
                     idCotizacion: input.idCotizacion,
                     pedido: input.pedido,
                     fecha: input.fecha,
                     estado: input.estado,
                     rutCliente: datos.rutCliente,
                     cliente: datos.cliente,
                  };
                  navigate(`/info-cotizacion/${encodeURIComponent(JSON.stringify(enviardatos))}`);
                  setMostrarCard(false);
               },
            });
         }
      });
   };
   const EliminarCotizacion = () => {
      Swal.fire({
         title: "¿Seguro que desea eliminar esta cotizacion?",
         text: "Esta acción es irreversible y no podrá recuperar la cotizacion perdida.",
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
                  resolve("ERROR al introducir la palabra");
               }
            });
         },
      }).then((result) => {
         if (result.isConfirmed) {
            let idCotizacion = datos.idCotizacion;
            CotizacionService.deleteCotizacion(idCotizacion);
            Swal.fire({
               title: "Eliminando...",
               text: "Por favor espera",
               timer: 1000, // 3 segundos
               didOpen: () => {
                  Swal.showLoading();
               },
               willClose: () => {
                  navigate("/cotizaciones");
               },
            });
         }
      });
   };
   const CancelarEdit = () => {
      setMostrarCard(false);
   };

   if (mostrarCard) {
      return (
         <div>
            <NavStyle>
               <HeaderComponents></HeaderComponents>
               <div className="container">
                  <div className="container-1">
                     <div className="card">
                        <div className="contenedor-img">
                           <img id="clientes" src={clientes} alt="clientes" />
                        </div>
                        <div className="contenedor-informacion">
                           <h2>{datos.cliente.nombre}</h2>
                           <h3>Empresa: {datos.cliente.empresa}</h3>
                           <h3>Rut: {datos.cliente.rut}</h3>
                           <h3>Correo: {datos.cliente.email}</h3>
                           <h3>Telefono: {datos.cliente.telefono}</h3>
                        </div>
                     </div>
                     <Button className="aceptar" onClick={enviarDatos}>
                        Aceptar
                     </Button>
                     <Button className="cancelar" onClick={CancelarEdit}>
                        Cancelar
                     </Button>
                  </div>
                  <div className="container-2">
                     <div align="center" className="container-2">
                        <h1>
                           <b> Cotizacion</b>
                        </h1>
                        <Form>
                           <table border="1" className="content-table">
                              <thead>
                                 <tr>
                                    <th>ID Cotizacion</th>
                                    <th>Pedido</th>
                                    <th>Fecha</th>
                                    <th>Estado</th>
                                 </tr>
                              </thead>
                              <tbody>
                                 <tr key={datos.id}>
                                    <td style={{ color: "gray" }}> {datos.idCotizacion} </td>
                                    <td>
                                       <div className=" contenedor-informacion">
                                          <Form.Group controlId="pedido">
                                             <Form.Control style={{ width: "100%" }} value={input.pedido} onChange={handleInputChange} className="font-h2 no-border" type="text" name="pedido" />
                                          </Form.Group>
                                       </div>
                                    </td>
                                    <td>
                                       <div className=" contenedor-informacion">
                                          <Form.Group controlId="fecha">
                                             <Form.Control style={{ width: "100%" }} value={input.fecha} onChange={handleInputChange} className="font-h2 no-border" type="date" name="fecha" />
                                          </Form.Group>
                                       </div>
                                    </td>
                                    <td>
                                       <div className=" contenedor-informacion">
                                          <Form.Group controlId="estado">
                                             <Form.Select style={{ width: "100%" }} value={input.estado} onChange={handleInputChange} className="font-h2 no-border" name="estado">
                                                <option value="En espera">En espera</option>
                                                <option value="Listo">Listo</option>
                                                <option value="Rechazado">Rechazado</option>
                                             </Form.Select>
                                          </Form.Group>
                                       </div>
                                    </td>
                                 </tr>
                              </tbody>
                           </table>
                        </Form>
                     </div>
                     <div align="center" className="container-3">
                        <h1>
                           <b> Lista de productos</b>
                        </h1>
                        <table border="1" className="content-table">
                           <thead>
                              <tr>
                                 <th>id Producto #</th>
                                 <th>Producto</th>
                                 <th>Cantidad</th>
                              </tr>
                           </thead>
                           <tbody>
                              {ProductoEntity.map((ListaProductosCotizacion) => (
                                 <tr key={ListaProductosCotizacion.id}>
                                    <td> #{ListaProductosCotizacion.id} </td>
                                    <td> {ListaProductosCotizacion.nombre} </td>
                                    <td> {ListaProductosCotizacion.cantidad} </td>
                                 </tr>
                              ))}
                           </tbody>
                        </table>
                     </div>
                  </div>
               </div>
            </NavStyle>
         </div>
      );
   } else {
      return (
         <div>
            <NavStyle>
               <HeaderComponents></HeaderComponents>
               <div className="container">
                  <div className="container-1">
                     <div className="card">
                        <div className="contenedor-img">
                           <img id="clientes" src={clientes} alt="clientes" />
                        </div>
                        <div className="contenedor-informacion">
                           <h2>{datos.cliente.nombre}</h2>
                           <h3>Empresa: {datos.cliente.empresa}</h3>
                           <h3>Rut: {datos.cliente.rut}</h3>
                           <h3>Correo: {datos.cliente.email}</h3>
                           <h3>Telefono: {datos.cliente.telefono}</h3>
                        </div>
                     </div>
                     <Button className="editar" onClick={changeMostrarCard}>
                        Editar
                     </Button>
                     <Button className="eliminar" onClick={EliminarCotizacion}>
                        Eliminar
                     </Button>
                  </div>
                  <div className="container-2">
                     <div align="center" className="container-2">
                        <h1>
                           <b> Cotizacion</b>
                        </h1>
                        <table border="1" className="content-table">
                           <thead>
                              <tr>
                                 <th>ID Cotizacion</th>
                                 <th>Pedido</th>
                                 <th>Fecha</th>
                                 <th>Estado</th>
                              </tr>
                           </thead>
                           <tbody>
                              <tr>
                                 <td>{datos.idCotizacion}</td>
                                 <td>{datos.pedido}</td>
                                 <td>{formatFecha(datos.fecha)}</td>
                                 <td>{datos.estado}</td>
                              </tr>
                           </tbody>
                        </table>
                     </div>
                     <div align="center" className="container-3">
                        <h1>
                           <b> Lista de productos</b>
                        </h1>
                        <table border="1" className="content-table">
                           <thead>
                              <tr>
                                 <th>id Producto #</th>
                                 <th>Producto</th>
                                 <th>Cantidad</th>
                              </tr>
                           </thead>
                           <tbody>
                              {ProductoEntity.map((ListaProductosCotizacion) => (
                                 <tr key={ListaProductosCotizacion.id}>
                                    <td> #{ListaProductosCotizacion.id} </td>
                                    <td> {ListaProductosCotizacion.nombre} </td>
                                    <td> {ListaProductosCotizacion.cantidad} </td>
                                 </tr>
                              ))}
                           </tbody>
                        </table>
                     </div>
                  </div>
               </div>
            </NavStyle>
         </div>
      );
   }
}
export default CotizacionComponent;

const NavStyle = styled.nav`
   /* Separacion de las partes */

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
      height: 80%;
      background-color: #f0f0f0;
      width: 20%;
      flex-shrink: 0; /* Hace que el contenedor no se encoja */
      overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
      padding: 5%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
   }
   .container-2 {
      background-color: #f0f0f0;
      flex-grow: 1; /* El lado derecho es flexible y ocupará todo el espacio restante */
      overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
      padding: 1%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
      max-height: calc(0px + 74.3vh); /* Asegura que el contenedor no exceda la altura de la ventana */
   }

   /* Todo la parte de la tabla */

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

   td img {
      width: 50%;
      object-fit: cover;
   }

   td img:hover {
      cursor: pointer;
   }

   th:hover,
   td:hover {
      cursor: default;
   }

   td,
   th {
      width: 150px;
   }

   /* Por el lado de la información del cliente*/

   .card {
      border: 1px solid black;
      border-radius: 10px;
      background-color: white;
   }
   .card .contenedor-img {
      background-color: #f0f0f0;
      border-radius: 10px;
      display: flex;
      flex-direction: column;
      align-items: center;
   }
   .card .contenedor-informacion {
      background-color: white;
      height: 100%;
   }

   .card .contenedor-informacion h3,
   .card .contenedor-informacion h2,
   .font-h2,
   .font-h3 {
      margin-left: 4%;
   }

   .card .contenedor-informacion h3,
   .font-h2,
   .font-h2-control {
      font-size: 20px;
      font-weight: normal;
   }

   .font-h2 {
      width: 100%;
      background-color: #f0f0f0f0;
   }

   .font-h3 {
      margin-top: 5%;
      font-size: 24px;
      font-weight: bold;
      width: 90%;
   }

   .no-border {
      border: none;
      box-shadow: none;
   }

   /* Para WebKit (Chrome, Safari, Edge) */
   input[type="number"]::-webkit-outer-spin-button,
   input[type="number"]::-webkit-inner-spin-button {
      -webkit-appearance: none;
      margin: 0;
   }
   /* Para Firefox */
   input[type="number"] {
      -moz-appearance: textfield;
   }

   .editar,
   .eliminar,
   .cancelar,
   .aceptar {
      margin-left: 5px;
      margin-top: 10px;
      padding: 10px 20px;
      font-size: 16px;
      border-radius: 30px;
      border: none;
      cursor: pointer;
   }

   .eliminar,
   .cancelar {
      background-color: #550100;
      color: #fff;
   }

   .editar {
      background-color: #39beab;
      color: black;
   }

   .aceptar {
      background-color: #00a768;
      color: black;
   }

   .editar:hover,
   .eliminar:hover,
   .aceptar:hover,
   .cancelar:hover {
      border: 1px solid black;
   }

   /* Fuente de la letra*/

   td,
   th,
   h1,
   h2,
   h3,
   Button,
   .font-h2,
   .font-h3,
   .font-h2-control {
      font-family: "Pacifico", serif;
   }
`;
