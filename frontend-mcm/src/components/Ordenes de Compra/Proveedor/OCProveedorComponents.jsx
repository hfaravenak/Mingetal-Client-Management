import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import HeaderComponents from "../../Headers/HeaderComponents";
import OrdenesDeCompraProveedorService from "../../../services/OrdenesDeCompraProveedorService";
import ProductoService from "../../../services/ProductoService";

import clientes from "../../../images/cliente.png";
import editar from "../../../images/editar.png";
import atras from "../../../images/atras.png";

function OCProveedorComponents() {
   const formatFecha = (fecha) => {
      if (fecha === null) {
         return "-";
      }
      const [year, month, day] = fecha.split("-");
      return `${day}-${month}-${year}`;
   };
   const navigate = useNavigate();

   const { oc_proveedor } = useParams();
   const datos = JSON.parse(decodeURIComponent(oc_proveedor));

   const initialState = {
      estado_pago: "",
      estado_entrega: "",
      fecha_pago: "",
      factura: "",
      fecha_entrega: "",
   };
   const [input, setInput] = useState(initialState);

   const [ListProductos, setListProductos] = useState([]);
   useEffect(() => {
      ProductoService.getListByOCProveedor(datos.id).then((res) => {
         setListProductos(res.data);
      });
   }, [datos.id]);

   const handleInputChange = (event) => {
      const { name, value } = event.target;
      setInput({ ...input, [name]: value });
   };

   const [mostrarCard, setMostrarCard] = useState(false);
   const changeMostrarCard = () => {
      setInput({
         estado_pago: datos.estado_pago,
         estado_entrega: datos.estado_entrega,
         fecha_pago: datos.fecha_pago,
         factura: datos.factura,
         fecha_entrega: datos.fecha_entrega,
      });
      setMostrarCard(true);
   };

   const enviarDatos = () => {
      Swal.fire({
         title: "¿Seguro de modificar esta orden de compra con estos valores?",
         icon: "question",
         showDenyButton: true,
         confirmButtonText: "Confirmar",
         confirmButtonColor: "rgb(68, 194, 68)",
         denyButtonText: "Cancelar",
         denyButtonColor: "rgb(190, 54, 54)",
      }).then((result) => {
         if (result.isConfirmed) {
            let updateOC = {
               id: datos.id,
               id_proveedor: datos.id_proveedor,
               estado_pago: input.estado_pago,
               valor_pago: datos.valor_pago,
               fecha_pago: input.fecha_pago,
               fecha_solicitud: datos.fecha_solicitud,
               fecha_entrega: input.fecha_entrega,
               estado_entrega: input.estado_entrega,
               factura: input.factura,
            };
            OrdenesDeCompraProveedorService.putOCProveedor(updateOC);
            let OtherUpdateOC = {
               id: datos.id,
               id_proveedor: datos.id_proveedor,
               estado_pago: input.estado_pago,
               valor_pago: datos.valor_pago,
               fecha_pago: input.fecha_pago,
               fecha_solicitud: datos.fecha_solicitud,
               fecha_entrega: input.fecha_entrega,
               estado_entrega: input.estado_entrega,
               factura: input.factura,
               proveedor: datos.proveedor,
               contacto1: datos.contacto1,
            };
            Swal.fire({
               title: "Editando...",
               text: "Por favor espere",
               timer: 3000, // 3 segundos
               didOpen: () => {
                  Swal.showLoading();
               },
               willClose: () => {
                  navigate(`/oc/proveedor/mas info/${encodeURIComponent(JSON.stringify(OtherUpdateOC))}`);
                  setMostrarCard(false);
               },
            });
         }
      });
   };
   const CancelarEdit = () => {
      setMostrarCard(false);
   };

   const EliminarCliente = () => {
      Swal.fire({
         title: "¿Seguro de que desea eliminar esta orden de compra?",
         text: "Esta acción es irreversible y no podrá recuperar la Orden de compra eliminada. Además no se recomienda hacer esta acción",
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
            let id = datos.id;
            OrdenesDeCompraProveedorService.deleteOCProveedor(id);
            Swal.fire({
               title: "Eliminando...",
               text: "Por favor espera",
               timer: 3000, // 3 segundos
               didOpen: () => {
                  Swal.showLoading();
               },
               willClose: () => {
                  navigate("/oc/proveedor");
               },
            });
         }
      });
   };
   const ChangeViendoProducto = (todoElDato) => {
      const datos = {
         id: todoElDato.id,
         tipo: todoElDato.tipo,
         nombre: todoElDato.nombre,
         valor: todoElDato.valor,
         valor_final: todoElDato.valor_final,
         cantidad: todoElDato.cantidad,
      };
      const datosComoTexto = JSON.stringify(datos);
      navigate(`/productos/mas-info/${encodeURIComponent(datosComoTexto)}`);
   };

   const regresar = () => {
    navigate(`/oc/proveedor`);
 }

   if (mostrarCard) {
      return (
         <div>
            <NavStyle>
               <HeaderComponents></HeaderComponents>
               <div className="container-create">
                  <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} style={{ width: "35px" }} />
               </div>
               <div className="container">
                  <div className="container-1">
                     <div className="card">
                        <div className="contenedor-img">
                           <img id="clientes" src={clientes} alt="clientes" />
                        </div>
                        <div className="contenedor-informacion">
                           <h2>{datos.proveedor.id_contacto.nombre}</h2>
                           <h3>
                              <strong>Empresa: </strong>
                              {datos.proveedor.empresa}
                           </h3>
                           <h3>
                              <strong>Rubro: </strong>
                              {datos.proveedor.rubro}
                           </h3>
                           <h3>
                              <strong>Rut: </strong>
                              {datos.proveedor.id_contacto.rut}
                           </h3>
                           <h3>
                              <strong>Correo: </strong>
                              {datos.proveedor.id_contacto.correo}
                           </h3>
                           <h3>
                              <strong>Telefono Celular: </strong>
                              {datos.proveedor.id_contacto.fono_cel}
                           </h3>
                           <h3>
                              <strong>Telefono Fijo: </strong>
                              {datos.proveedor.id_contacto.fono_fijo}
                           </h3>
                           <h3>
                              <strong>Comentario: </strong> {datos.proveedor.comentario}
                           </h3>
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
                     <h1>
                        <b> Ordenes de Compra</b>
                     </h1>
                     <table border="1" className="content-table">
                        <thead>
                           <tr>
                              <th>Ref #</th>
                              <th>Fecha de la Solicitud</th>
                              <th>Estado de la Entrega</th>
                              <th>Fecha de la Entrega</th>
                           </tr>
                        </thead>
                        <tbody>
                           <tr key={datos.id}>
                              <td style={{ color: "gray" }}> #{datos.id} </td>
                              <td style={{ color: "gray" }}> {formatFecha(datos.fecha_solicitud)} </td>
                              <td>
                                 <div className="contenedor-informacion">
                                    <Form style={{ width: "150px" }}>
                                       <Form.Group controlId="estado_entrega">
                                          <Form.Select style={{ width: "100%" }} value={input.estado_entrega} onChange={handleInputChange} className="font-h2 no-border" name="estado_entrega">
                                             <option value="No Entregado">No Entregado</option>
                                             <option value="Entregado">Entregado</option>
                                          </Form.Select>
                                       </Form.Group>
                                    </Form>
                                 </div>
                              </td>
                              <td>
                                 <div className="contenedor-informacion">
                                    <Form style={{ width: "150px" }}>
                                       <Form.Group controlId="fecha_entrega">
                                          <Form.Control
                                             style={{ width: "100%" }}
                                             value={input.fecha_entrega}
                                             onChange={handleInputChange}
                                             className="font-h2 no-border"
                                             type="date"
                                             name="fecha_entrega"
                                          />
                                       </Form.Group>
                                    </Form>
                                 </div>
                              </td>
                           </tr>
                        </tbody>
                     </table>
                     <h1>
                        <b> Pago</b>
                     </h1>
                     <table border="1" className="content-table">
                        <thead>
                           <tr>
                              <th>Valor del Pago</th>
                              <th>Estado del Pago</th>
                              <th>Fecha del Pago</th>
                           </tr>
                        </thead>
                        <tbody>
                           <tr key={datos.id}>
                              <td style={{ color: "gray" }}> {datos.valor_pago} </td>
                              <td>
                                 <div className="contenedor-informacion">
                                    <Form style={{ width: "150px" }}>
                                       <Form.Group controlId="estado_pago">
                                          <Form.Select style={{ width: "100%" }} value={input.estado_pago} onChange={handleInputChange} className="font-h2 no-border" name="estado_pago">
                                             <option value="No Pagado">No Pagado</option>
                                             <option value="Pagado">Pagado</option>
                                          </Form.Select>
                                       </Form.Group>
                                    </Form>
                                 </div>
                              </td>
                              <td>
                                 <div className="contenedor-informacion">
                                    <Form style={{ width: "150px" }}>
                                       <Form.Group controlId="fecha_pago">
                                          <Form.Control style={{ width: "100%" }} value={input.fecha_pago} onChange={handleInputChange} className="font-h2 no-border" type="date" name="fecha_pago" />
                                       </Form.Group>
                                    </Form>
                                 </div>
                              </td>
                           </tr>
                        </tbody>
                     </table>
                     <h1>
                        <b> Factura</b>
                     </h1>
                     <table border="1" className="content-table">
                        <thead>
                           <tr>
                              <th>Numero de la Factura</th>
                           </tr>
                        </thead>
                        <tbody>
                           <tr key={datos.id}>
                              <td>
                                 <div className="contenedor-informacion">
                                    <Form style={{ width: "150px" }}>
                                       <Form.Group controlId="factura">
                                          <Form.Control
                                             style={{ width: "100%" }}
                                             value={input.factura}
                                             onChange={handleInputChange}
                                             className="font-h2 no-border"
                                             type="number"
                                             name="factura"
                                             placeholder="25"
                                          />
                                       </Form.Group>
                                    </Form>
                                 </div>
                              </td>
                           </tr>
                        </tbody>
                     </table>
                  </div>
               </div>
            </NavStyle>
         </div>
      );
   }
   return (
      <div>
         <NavStyle>
            <HeaderComponents></HeaderComponents>
            <div className="container-create">
               <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} style={{ width: "35px" }} />
            </div>
            <div className="container">
               <div className="container-1">
                  <div className="card">
                     <div className="contenedor-img">
                        <img id="clientes" src={clientes} alt="clientes" />
                     </div>
                     <div className="contenedor-informacion">
                        <h2>{datos.proveedor.id_contacto.nombre}</h2>
                        <h3>
                           <strong>Empresa: </strong>
                           {datos.proveedor.empresa}
                        </h3>
                        <h3>
                           <strong>Rubro: </strong>
                           {datos.proveedor.rubro}
                        </h3>
                        <h3>
                           <strong>Rut: </strong>
                           {datos.proveedor.id_contacto.rut}
                        </h3>
                        <h3>
                           <strong>Correo: </strong>
                           {datos.proveedor.id_contacto.correo}
                        </h3>
                        <h3>
                           <strong>Telefono Celular: </strong>
                           {datos.proveedor.id_contacto.fono_cel}
                        </h3>
                        <h3>
                           <strong>Telefono Fijo: </strong>
                           {datos.proveedor.id_contacto.fono_fijo}
                        </h3>
                        <h3>
                           <strong>Comentario: </strong> {datos.proveedor.comentario}
                        </h3>
                     </div>
                  </div>
                  <Button className="editar" onClick={changeMostrarCard}>
                     Editar
                  </Button>
                  <Button className="eliminar" onClick={EliminarCliente}>
                     Eliminar
                  </Button>
               </div>
               <div className="container-2">
                  <h1>
                     <b> Ordenes de Compra</b>
                  </h1>
                  <table border="1" className="content-table">
                     <thead>
                        <tr>
                           <th>Ref #</th>
                           <th>Fecha de la Solicitud</th>
                           <th>Estado de la Entrega</th>
                           <th>Fecha de la Entrega</th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr key={datos.id}>
                           <td> #{datos.id} </td>
                           <td> {formatFecha(datos.fecha_solicitud)} </td>
                           <td> {datos.estado_entrega} </td>
                           <td> {formatFecha(datos.fecha_entrega)} </td>
                        </tr>
                     </tbody>
                  </table>
                  <h1>
                     <b> Pago</b>
                  </h1>
                  <table border="1" className="content-table">
                     <thead>
                        <tr>
                           <th>Valor del Pago</th>
                           <th>Estado del Pago</th>
                           <th>Fecha del Pago</th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr key={datos.id}>
                           <td> {datos.valor_pago} </td>
                           <td> {datos.estado_pago} </td>
                           <td> {formatFecha(datos.fecha_pago)} </td>
                        </tr>
                     </tbody>
                  </table>
                  <h1>
                     <b> Factura</b>
                  </h1>
                  <table border="1" className="content-table">
                     <thead>
                        <tr>
                           <th>Numero de la Factura</th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr key={datos.id}>
                           <td> {datos.factura} </td>
                        </tr>
                     </tbody>
                  </table>
                  <h1>
                     <b> Lista de Productos</b>
                  </h1>
                  <table border="1" className="content-table">
                     <thead>
                        <tr>
                           <th>ID</th>
                           <th>Nombre</th>
                           <th>Tipo</th>
                           <th>Valor Final</th>
                           <th>Cantidad</th>
                           <th>Más información</th>
                        </tr>
                     </thead>
                     <tbody>
                        {ListProductos.map((productos) => (
                           <tr key={productos.id}>
                              <td> #{productos.id} </td>
                              <td> {productos.nombre}</td>
                              <td> {productos.tipo} </td>
                              <td> {productos.valor_final} </td>
                              <td> {productos.cantidad} </td>
                              <td style={{ textAlign: "center", verticalAlign: "middle", width: "1%" }}>
                                 <img id="editar" src={editar} alt="editar" onClick={() => ChangeViendoProducto(productos)} />
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
export default OCProveedorComponents;

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

   .img-back:hover {
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
