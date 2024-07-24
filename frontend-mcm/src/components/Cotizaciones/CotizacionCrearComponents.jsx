import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import atras from "../../images/atras.png";

import HeaderComponents from "../Headers/HeaderComponents";
import CotizacionService from "../../services/CotizacionService";
import ClienteService from "../../services/ClienteService";
import ProductoService from "../../services/ProductoService";

function CotizacionCrearComponent() {
   const obtenerFechaHoy = () => {
      const hoy = new Date();
      const dia = String(hoy.getDate()).padStart(2, "0");
      const mes = String(hoy.getMonth() + 1).padStart(2, "0");
      const año = hoy.getFullYear();
      return `${año}-${mes}-${dia}`; // Formato yyyy-mm-dd para ser compatible con el tipo de input date
   };
   const navigate = useNavigate();

   const initialState = {
      pedido: "",
      fecha: obtenerFechaHoy(),
      estado: "En espera",
      nombreCliente: "",
   };
   const [input, setInput] = useState(initialState);

   const [id, setID] = useState();
   useEffect(() => {
      CotizacionService.getCotizaciones().then((res) => {
         setID(res.data.length + 1);
      });
   }, []);

   const [ListProducto, setListProducto] = useState([{ nombre: "", cantidad: "" }]);
   const handleAddRow = () => {
      setListProducto([...ListProducto, { nombre: "", cantidad: "" }]);
   };
   const handleMinusRow = () => {
      if (ListProducto.length > 1) {
         setListProducto(ListProducto.slice(0, -1));
      }
   };

   const handleInputChange = (event, index = null) => {
      const { name, value } = event.target;

      if (index !== null) {
         const newListProducto = [...ListProducto];
         newListProducto[index][name] = value;
         setListProducto(newListProducto);
      } else {
         setInput({ ...input, [name]: value });
      }
   };

   const validateForm = () => {
      const requiredFields = ["pedido", "fecha", "estado", "nombreCliente"];
      for (let field of requiredFields) {
         if (!input[field]) {
            return false;
         }
      }
      for (let producto of ListProducto) {
         if (!producto.nombre || !producto.cantidad) {
            return false;
         }
      }
      return true;
   };
   const handleSubmit = (event) => {
      event.preventDefault();
      if (validateForm()) {
         ingresarCotizacion();
      } else {
         Swal.fire({
            title: "Error",
            text: "Por favor, complete todos los campos requeridos.",
            icon: "error",
            confirmButtonText: "OK",
         });
      }
   };
   const ingresarCotizacion = (event) => {
      Swal.fire({
         title: "¿Desea registrar esta cotización?",
         text: "Luego podrá modificar los valores, pero no todos. Recomiendo revisar el contenido de este",
         icon: "question",
         showDenyButton: true,
         confirmButtonText: "Confirmar",
         confirmButtonColor: "rgb(68, 194, 68)",
         denyButtonText: "Cancelar",
         denyButtonColor: "rgb(190, 54, 54)",
      }).then((result) => {
         if (result.isConfirmed) {
            ClienteService.getClienteByNombreTextual(input.nombreCliente).then((res) => {
               if (res.data === null || res.data === "") {
                  Swal.fire({
                     title: "Cliente no encontrado",
                     timer: 2000,
                     icon: "warning",
                     timerProgressBar: true,
                     didOpen: () => {
                        Swal.showLoading();
                     },
                  });
               } else {
                  let newCotizacion = {
                     pedido: input.pedido,
                     fecha: input.fecha,
                     estado: input.estado,
                     rutCliente: res.data.rut,
                  };
                  CotizacionService.createCotizacion(newCotizacion).then((res2) => {
                     ListProducto.map((productos) => {
                        ProductoService.getProductosByNombreTextual(productos.nombre).then((res3) => {
                           let newListP = {
                              id_cotizacion: res2.data.idCotizacion,
                              id_producto: res3.data.id,
                              cantidad: productos.cantidad,
                              valor_pago: res3.data.valor_final * parseInt(productos.cantidad),
                           };
                           CotizacionService.createListProductos(newListP);
                        });
                        return null;
                     });
                  });
                  Swal.fire({
                     title: "Enviado",
                     timer: 2000,
                     icon: "success",
                     timerProgressBar: true,
                     didOpen: () => {
                        Swal.showLoading();
                     },
                     willClose: () => {
                        navigate("/cotizaciones");
                     },
                  });
               }
            });
         }
      });
   };
   const regresar = () => {
      navigate(`/cotizaciones`);
   };
   return (
      <div className="general">
         <HeaderComponents></HeaderComponents>
         <NavStyle>
         <div className="container-create">
               <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} style={{ width: "35px" }} />
            </div>
            <div className="container">
               <div style={{ marginLeft: "1%", fontFamily:"Pacifico, serif", fontSize:"25px" }}>
                  <b>N° Cotizacion: {id}</b>
               </div>
               <Form onSubmit={handleSubmit}>
                  <div className="container-2">
                     <h1>
                        <b> Ordenes de Compra</b>
                     </h1>
                     <table border="1" className="content-table">
                        <thead>
                           <tr>
                              <th>Pedido</th>
                              <th>Fecha de la Solicitud</th>
                              <th>Estado</th>
                              <th>Nombre del Cliente</th>
                           </tr>
                        </thead>
                        <tbody>
                           <tr>
                              <td>
                                 <Form.Group className="mb-3" controlId="pedido">
                                    <Form.Control className="agregar" type="text" name="pedido" value={input.pedido} onChange={handleInputChange} />
                                 </Form.Group>
                              </td>
                              <td>
                                 <Form.Group className="mb-3" controlId="fecha">
                                    <Form.Control className="agregar" type="date" name="fecha" value={input.fecha} onChange={handleInputChange} />
                                 </Form.Group>
                              </td>
                              <td>
                                 <Form.Group className="mb-3" controlId="estado">
                                    <Form.Select className="agregar" name="estado" value={input.estado} onChange={handleInputChange}>
                                       <option value="En espera">En espera</option>
                                       <option value="Listo">Listo</option>
                                       <option value="Rechazado">Rechazado</option>
                                    </Form.Select>
                                 </Form.Group>
                              </td>
                              <td>
                                 <Form.Group className="mb-3" controlId="nombreCliente">
                                    <Form.Control className="agregar" type="text" name="nombreCliente" value={input.nombreCliente} onChange={handleInputChange} />
                                 </Form.Group>
                              </td>
                           </tr>
                        </tbody>
                     </table>
                     <h1>
                        <b> Lista de Productos</b>
                     </h1>
                     <div>
                        <table border="1" className="content-table">
                           <thead>
                              <tr>
                                 <th>* Nombre</th>
                                 <th>* Cantidad</th>
                              </tr>
                           </thead>
                           <tbody>
                              {ListProducto.map((row, index) => (
                                 <tr key={index}>
                                    <td>
                                       <Form.Group controlId={`nombre-${index}`}>
                                          <Form.Control type="text" value={row.nombre} onChange={(event) => handleInputChange(event, index)} name="nombre" />
                                       </Form.Group>
                                    </td>
                                    <td>
                                       <Form.Group controlId={`cantidad-${index}`}>
                                          <Form.Control type="number" value={row.cantidad} onChange={(event) => handleInputChange(event, index)} name="cantidad" />
                                       </Form.Group>
                                    </td>
                                 </tr>
                              ))}
                           </tbody>
                        </table>
                        <Button className="Aumentar" onClick={handleAddRow}>
                           +
                        </Button>
                        <Button className="Disminuir" onClick={handleMinusRow}>
                           -
                        </Button>
                     </div>
                  </div>
                  <div className="button-container">
                     <Button className="boton" type="submit">
                        Registrar Cotización
                     </Button>
                  </div>
               </Form>
            </div>
         </NavStyle>
      </div>
   );
}
export default CotizacionCrearComponent;

const NavStyle = styled.nav`
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
      gap: 20px;
      height: 100%;
      padding-top: 20px;
   }
   .container-2 {
      background-color: #f0f0f0;
      flex-grow: 1; /* El lado derecho es flexible y ocupará todo el espacio restante */
      overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
      padding: 1%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
      padding-top: 0;
      max-height: calc(0px + 55.3vh); /* Asegura que el contenedor no exceda la altura de la ventana */
   }

   /* Tabla */

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

   h1 {
      text-align: left;
   }
   label {
      display: block;
      margin-bottom: 10px;
      margin-left: 15px;
      margin-top: 10px;
   }
   input[type="text"],
   input[type="date"],
   input[type="number"],
   option,
   select {
      background-color: rgb(201, 201, 201);
      width: 100%;
      padding: 10px;
      font-size: 16px;
      border-radius: 30px;
      border: 1px solid #ccc;
      box-sizing: border-box; /* Asegura que los inputs tengan el mismo ancho */
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

   .Aumentar,
   .Disminuir {
      padding: 10px;
      background-color: #d2712b;
      margin-left: 1%;
      width: 30px;
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
      margin: 2%;
   }

   .button-container {
      display: flex;
      justify-content: center;
      margin-top: 20px;
   }

   td,
   th,
   h1,
   Label,
   Control,
   Button {
      font-family: "Pacifico", serif;
   }
`;
