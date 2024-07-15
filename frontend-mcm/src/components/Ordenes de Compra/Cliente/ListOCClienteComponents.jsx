import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import axios from "axios";

import editar from "../../../images/editar.png";
import excel from "../../../images/excel.png";
import atras from "../../../images/atras.png";

import HeaderComponents from "../../Headers/HeaderComponents";
import OrdenesDeCompraClienteService from "../../../services/OrdenesDeCompraClienteService";
import ClienteService from "../../../services/ClienteService";

function ListOCClienteComponents() {
   const formatFecha = (fecha) => {
      if (fecha === null) {
         return "-";
      }
      const [year, month, day] = fecha.split("-");
      return `${day}-${month}-${year}`;
   };
   const navigate = useNavigate();

   const initialState = {
      id: "",
      nombre: "",
      empresa: "",
   };
   const [input, setInput] = useState(initialState);

   const [ClienteEntity, setClienteEntity] = useState([]);
   const [OCClienteEntity, setOCClienteEntity] = useState([]);
   useEffect(() => {
      OrdenesDeCompraClienteService.getOCCliente().then((res) => {
         setOCClienteEntity(res.data);
      });
      ClienteService.getClientes().then((res) => {
         setClienteEntity(res.data);
      });
   }, []);

   const handleInputChange = (event) => {
      const { name, value } = event.target;
      setInput({ ...input, [name]: value });
   };

   const busquedaCliente = (rut) => {
      let variable = "";
      ClienteEntity.forEach((cliente) => {
         if (cliente.rut === rut) {
            variable = cliente;
         }
      });
      return variable;
   };

   const buscarId = () => {
      OrdenesDeCompraClienteService.getOCClienteById(input.id).then((res) => {
         if (Array.isArray(res.data)) {
            setOCClienteEntity(res.data);
         } else if (res.data === "") {
            setOCClienteEntity([]);
         } else {
            setOCClienteEntity([res.data]);
         }
      });
   };
   const buscarNombre = () => {
      OrdenesDeCompraClienteService.getOCClienteByNombre(input.nombre).then((res) => {
         setOCClienteEntity(res.data);
      });
   };
   const buscarEmpresa = () => {
      OrdenesDeCompraClienteService.getOCClienteByEmpresa(input.empresa).then((res) => {
         setOCClienteEntity(res.data);
      });
   };
   const handleKeyPressId = (event) => {
      if (event.key === "Enter") {
         event.preventDefault(); // Evita que el formulario se envíe si está dentro de un <form>
         buscarId(); // Llama a la función que deseas ejecutar
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

   const ChangeViendoCliente = (todoElDato) => {
      let datos = {
         id: todoElDato.id,
         id_cliente: todoElDato.id_cliente,
         estado_factura: todoElDato.estado_factura,
         estado_pago: todoElDato.estado_pago,
         valor_pago: todoElDato.valor_pago,
         fecha_pago: todoElDato.fecha_pago,
         fecha_solicitud: todoElDato.fecha_solicitud,
         estado_entrega: todoElDato.estado_entrega,
         modo_pago: todoElDato.modo_pago,
         fecha_inicio_pago: todoElDato.fecha_inicio_pago,
         tiempo_de_pago: todoElDato.tiempo_de_pago,
         numero_cheque: todoElDato.numero_cheque,
         numero_factura: todoElDato.numero_factura,
         empresa_despacho: todoElDato.empresa_despacho,
         cliente: busquedaCliente(todoElDato.id_cliente),
      };
      const datosComoTexto = JSON.stringify(datos);
      navigate(`/oc/cliente/mas info/${encodeURIComponent(datosComoTexto)}`);
   };

   const crearOCCliente = () => {
      navigate("crear");
   };

   const descargarExcel = async () => {
      try {
         const response = await axios.get("http://localhost:8080/ordenes_de_compra/archive/download/excel", {
            responseType: "blob", // importante para manejar el blob de la respuesta
         });

         const url = window.URL.createObjectURL(new Blob([response.data]));
         const link = document.createElement("a");
         link.href = url;
         link.setAttribute("download", "Ordenes de Compra.xlsx"); // nombre del archivo
         document.body.appendChild(link);
         link.click();
      } catch (error) {
         console.error("Error al descargar el archivo", error);
      }
   };

   const regresar = () => {
      navigate(`/oc`);
   };

   return (
      <div>
         <HeaderComponents />
         <NavStyle>
            <div className="container-create">
               <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} style={{ width: "35px" }} />
            </div>
            <div className="container">
               <div className="container-1">
                  <div className="inline-forms-container">
                     <Form className="inline-form">
                        <Form.Group controlId="id">
                           <Form.Label className="agregar">Id de OC:</Form.Label>
                           <Form.Control className="agregar" type="number" name="id" placeholder="2044111" onKeyPress={handleKeyPressId} value={input.id} onChange={handleInputChange} />
                        </Form.Group>
                        <Button className="boton" onClick={buscarId}>
                           Buscar
                        </Button>
                     </Form>
                     <Form className="inline-form">
                        <Form.Group controlId="nombre">
                           <Form.Label className="agregar">Nombre Cliente:</Form.Label>
                           <Form.Control className="agregar" type="text" name="nombre" placeholder="Juan Perez" onKeyPress={handleKeyPressNombre} value={input.nombre} onChange={handleInputChange} />
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
                              onKeyPress={handleKeyPressEmpresa}
                              value={input.empresa}
                              onChange={handleInputChange}
                           />
                        </Form.Group>
                        <Button className="boton" onClick={buscarEmpresa}>
                           Buscar
                        </Button>
                     </Form>
                  </div>
               </div>
               <div align="center" className="container-2">
                  <div className="TituloSuperior">
                     <h1>
                        <b> Ordenes de Compra de Clientes</b>
                     </h1>
                     <div className="Izquierda">
                        <img id="excel" src={excel} alt="excel" className="img-card" onClick={descargarExcel} />
                     </div>
                     <div className="Derecha">
                        <div className="btn-inf">
                           <Button className="boton" onClick={crearOCCliente}>
                              Ingresar nueva OC
                           </Button>
                        </div>
                     </div>
                  </div>

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
                        {OCClienteEntity.map((OCCliente) => (
                           <tr key={OCCliente.id}>
                              <td> #{OCCliente.id} </td>
                              <td>{busquedaCliente(OCCliente.id_cliente).nombre}</td>
                              <td> {OCCliente.estado_pago} </td>
                              <td> {formatFecha(OCCliente.fecha_pago)} </td>
                              <td> {formatFecha(OCCliente.fecha_solicitud)} </td>
                              <td> {OCCliente.valor_pago} </td>
                              <td> {OCCliente.estado_entrega} </td>
                              <td style={{ textAlign: "center", verticalAlign: "middle", width: "1%" }}>
                                 <img id="editar" src={editar} alt="editar" onClick={() => ChangeViendoCliente(OCCliente)} />
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

export default ListOCClienteComponents;

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

   /* Todo la parte de la tabla */

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

   .TituloSuperior .Derecha .btn-inf .boton {
      margin-top: 0;
   }

   .TituloSuperior .Derecha .img-card {
      width: 5%;
      height: 5%;
   }

   .TituloSuperior .Izquierda {
      position: absolute;
      left: 0;
   }

   .TituloSuperior .Izquierda .btn-inf .boton {
      margin-top: 0;
   }

   .TituloSuperior .Izquierda .img-card {
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
   input[type="text"],
   input[type="number"] {
      background-color: rgb(201, 201, 201);
      width: 100%;
      padding: 10px;
      font-size: 16px;
      border-radius: 30px;
      border: 1px solid #ccc;
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

   /* Apartado del boton de crear */

   .btn-inf .boton {
      font-size: 20px;
   }

   .boton:hover {
      border: 1px solid black;
   }

   /* Fuente de la letra*/

   td,
   th,
   h1,
   Label,
   Control,
   Button {
      font-family: "Pacifico", serif;
   }
`;
