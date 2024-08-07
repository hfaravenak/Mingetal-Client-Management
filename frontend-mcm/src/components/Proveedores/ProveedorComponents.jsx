import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import HeaderComponents from "../Headers/HeaderComponents";
import styled from "styled-components";
import Swal from "sweetalert2";

import editar from "../../images/editar.png";
import atras from "../../images/atras.png";

import CarruselContactos from "./CarruselContactos";
import CarruselContactosEditar from "./CarruselContactosEditar";
import OrdenesDeCompraProveedorService from "../../services/OrdenesDeCompraProveedorService";

function ProveedorComponents() {
   const formatFecha = (fecha) => {
      if (fecha === null) {
         return "-";
      }
      const [year, month, day] = fecha.split("-");
      return `${day}-${month}-${year}`;
   };

   const { proveedor } = useParams();
   const datos = JSON.parse(decodeURIComponent(proveedor));

   const navigate = useNavigate();

   const [OC_ProveedorEntity, setOC_ProveedorEntity] = useState([]);
   useEffect(() => {
      OrdenesDeCompraProveedorService.getOCByProveedor(datos.id_proveedor).then((res) => {
         setOC_ProveedorEntity(res.data);
      });
   }, [datos.id_proveedor]);

   const verOrdenDeCompra = (OC_Proveedor) => {
      let datosEnvio = {
         id: OC_Proveedor.id,
         id_proveedor: OC_Proveedor.id_proveedor,
         estado_pago: OC_Proveedor.estado_pago,
         fecha_pago: OC_Proveedor.fecha_pago,
         fecha_entrega: OC_Proveedor.fecha_entrega,
         estado_entrega: OC_Proveedor.estado_entrega,
         fecha_solicitud: OC_Proveedor.fecha_solicitud,
         factura: OC_Proveedor.factura,
         valor_pago: OC_Proveedor.valor_pago,
         proveedor: datos,
      };
      const datosComoTexto = JSON.stringify(datosEnvio);
      navigate(`/oc/proveedor/mas info/${encodeURIComponent(datosComoTexto)}`);
   };
   const verOrdenDeCompraEditar = (OC_Proveedor) => {
      Swal.fire({
         title: "¿Desea cancelar la edición del cliente?",
         icon: "question",
         showDenyButton: true,
         confirmButtonText: "Confirmar",
         confirmButtonColor: "rgb(68, 194, 68)",
         denyButtonText: "Cancelar",
         denyButtonColor: "rgb(190, 54, 54)",
      }).then((result) => {
         if (result.isConfirmed) {
            let datosEnvio = {
               id: OC_Proveedor.id,
               id_proveedor: OC_Proveedor.id_proveedor,
               estado_pago: OC_Proveedor.estado_pago,
               fecha_pago: OC_Proveedor.fecha_pago,
               fecha_entrega: OC_Proveedor.fecha_entrega,
               estado_entrega: OC_Proveedor.estado_entrega,
               fecha_solicitud: OC_Proveedor.fecha_solicitud,
               factura: OC_Proveedor.factura,
               valor_pago: OC_Proveedor.valor_pago,
               proveedor: datos,
            };
            const datosComoTexto = JSON.stringify(datosEnvio);
            navigate(`/oc/proveedor/mas info/${encodeURIComponent(datosComoTexto)}`);
         }
      });
   };

   const [mostrarCard, setMostrarCard] = useState(false);
   const handleMostrarCard = (boolean) => {
      setMostrarCard(boolean);
   };

   const regresar = () => {
      navigate(`/proveedores`);
   };

   if (mostrarCard) {
      return (
         <div>
            <HeaderComponents></HeaderComponents>
            <NavStyle>
               <div className="container-create">
                  <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} style={{ width: "35px" }} />
               </div>
               <div className="container">
                  <div className="container-1">
                     <CarruselContactosEditar datos={datos} onMostrarCard={handleMostrarCard} />
                  </div>
                  <div className="container-2">
                     <h1>
                        <b> Ordenes de Compra</b>
                     </h1>
                     <table border="1" className="content-table">
                        <thead>
                           <tr>
                              <th>Ref #</th>
                              <th>Estado del Pago</th>
                              <th>Fecha del Pago</th>
                              <th>Fecha de la Solicitud</th>
                              <th>Valor del Pago</th>
                              <th>Estado de la Entrega</th>
                              <th>Más información</th>
                           </tr>
                        </thead>
                        <tbody>
                           {OC_ProveedorEntity.map((OCProveedor) => (
                              <tr key={OCProveedor.id}>
                                 <td> #{OCProveedor.id} </td>
                                 <td> {OCProveedor.estado_pago} </td>
                                 <td> {formatFecha(OCProveedor.fecha_pago)} </td>
                                 <td> {formatFecha(OCProveedor.fecha_solicitud)} </td>
                                 <td> {OCProveedor.valor_pago} </td>
                                 <td> {OCProveedor.estado_entrega} </td>
                                 <td style={{ textAlign: "center", verticalAlign: "middle", width: "1%" }}>
                                    <img id="editar" src={editar} alt="editar" onClick={() => verOrdenDeCompraEditar(OCProveedor)} />
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
            <HeaderComponents />
            <NavStyle>
               <div className="container-create">
                  <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} style={{ width: "35px" }} />
               </div>
               <div className="container">
                  <div className="container-1">
                     <CarruselContactos datos={datos} onMostrarCard={handleMostrarCard} />
                  </div>
                  <div className="container-2">
                     <h1>
                        <b> Ordenes de Compra</b>
                     </h1>
                     <table border="1" className="content-table">
                        <thead>
                           <tr>
                              <th>Ref #</th>
                              <th>Estado del Pago</th>
                              <th>Fecha del Pago</th>
                              <th>Fecha de la Solicitud</th>
                              <th>Valor del Pago</th>
                              <th>Estado de la Entrega</th>
                              <th>Más información</th>
                           </tr>
                        </thead>
                        <tbody>
                           {OC_ProveedorEntity.map((OCProveedor) => (
                              <tr key={OCProveedor.id}>
                                 <td> #{OCProveedor.id} </td>
                                 <td> {OCProveedor.estado_pago} </td>
                                 <td> {formatFecha(OCProveedor.fecha_pago)} </td>
                                 <td> {formatFecha(OCProveedor.fecha_solicitud)} </td>
                                 <td> {OCProveedor.valor_pago} </td>
                                 <td> {OCProveedor.estado_entrega} </td>
                                 <td style={{ textAlign: "center", verticalAlign: "middle", width: "1%" }}>
                                    <img id="editar" src={editar} alt="editar" onClick={() => verOrdenDeCompra(OCProveedor)} />
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
   }
}
export default ProveedorComponents;

const NavStyle = styled.nav`
   /* Separacion de las partes */

   .container-create {
      margin: 2%;
      margin-bottom: 0;
      padding: 1%;
      padding-bottom: 0;
      border: 2px solid #d5d5d5;
      border-bottom: 0;
      background-color: #f0f0f0;
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
      height: 80%;
      background-color: #f0f0f0;
      width: 20%;
      flex-shrink: 0; /* Hace que el contenedor no se encoja */
      overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
      padding: 5%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
      padding-top: 1%;
   }
   .container-2 {
      background-color: #f0f0f0;
      flex-grow: 1; /* El lado derecho es flexible y ocupará todo el espacio restante */
      overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
      padding: 1%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
      padding-top: 0;
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

   .font-h3 {
      margin-top: 5%;
      font-size: 24px;
      font-weight: bold;
      width: 90%;
   }

   .font-h2,
   font-h3,
   .font-h2-control {
      padding-bottom: 3%;
      padding-top: 3%;
   }

   .no-border {
      border: none;
      box-shadow: none;
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
   .img-back:hover {
      cursor: pointer;
   }
`;
