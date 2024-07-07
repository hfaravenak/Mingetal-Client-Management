import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import axios from "axios";

import editar from "../../images/editar.png";
import excel from "../../images/excel.png";

import HeaderComponents from "../Headers/HeaderComponents";
import ProductoService from "../../services/ProductoService";

function ListProductosComponents() {
   const navigate = useNavigate();

   const initialState = {
      tipo: "",
      nombre: "",
   };
   const [input, setInput] = useState(initialState);

   const [ProductoEntity, setProductoEntity] = useState([]);
   useEffect(() => {
      ProductoService.getProductos().then((res) => {
         setProductoEntity(res.data);
      });
   }, []);

   const handleInputChange = (event) => {
      const { name, value } = event.target;
      setInput({ ...input, [name]: value });
   };

   const buscarTipo = () => {
      ProductoService.getProductoByTipo(input.tipo).then((res) => {
         setProductoEntity(res.data);
      });
   };
   const buscarNombre = () => {
      ProductoService.getProductoByNombre(input.nombre).then((res) => {
         setProductoEntity(res.data);
      });
   };
   const handleKeyPressTipo = (event) => {
      if (event.key === "Enter") {
         event.preventDefault();
         buscarTipo();
      }
   };
   const handleKeyPressNombre = (event) => {
      if (event.key === "Enter") {
         event.preventDefault();
         buscarNombre();
      }
   };

   const crearProducto = () => {
      navigate("crear");
   };

   const ChangeViendoProducto = (todoElDato) => {
      const datos = {
         id: todoElDato.id,
         tipo: todoElDato.tipo,
         nombre: todoElDato.nombre,
         valor: todoElDato.valor,
         valor_final: todoElDato.valor_final,
         cantidad: todoElDato.cantidad,
         imagen: todoElDato.imagen,
         tipo_imagen: todoElDato.tipo_imagen,
      };
      const datosComoTexto = JSON.stringify(datos);
      navigate(`/productos/mas-info/${encodeURIComponent(datosComoTexto)}`);
   };

   const descargarExcel = async () => {
      try {
         const response = await axios.get("http://localhost:8080/productos/archive/download/excel", {
            responseType: "blob", // importante para manejar el blob de la respuesta
         });

         const url = window.URL.createObjectURL(new Blob([response.data]));
         const link = document.createElement("a");
         link.href = url;
         link.setAttribute("download", "Productos.xlsx"); // nombre del archivo
         document.body.appendChild(link);
         link.click();
      } catch (error) {
         console.error("Error al descargar el archivo", error);
      }
   };
   return (
      <div>
         <NavStyle>
            <HeaderComponents />
            <div className="container">
               <div className="container-1">
                  <div className="inline-forms-container">
                     <Form className="inline-form">
                        <Form.Group controlId="tipo">
                           <Form.Label className="agregar">Tipo del Producto:</Form.Label>
                           <Form.Control className="agregar" type="text" name="tipo" placeholder="Electrónica" onKeyPress={handleKeyPressTipo} value={input.tipo} onChange={handleInputChange} />
                        </Form.Group>
                        <Button className="boton" onClick={buscarTipo}>
                           Buscar
                        </Button>
                     </Form>
                     <Form className="inline-form">
                        <Form.Group controlId="nombre">
                           <Form.Label className="agregar">Nombre del Producto:</Form.Label>
                           <Form.Control className="agregar" type="text" name="nombre" placeholder="iPhone" onKeyPress={handleKeyPressNombre} value={input.nombre} onChange={handleInputChange} />
                        </Form.Group>
                        <Button className="boton" onClick={buscarNombre}>
                           Buscar
                        </Button>
                     </Form>
                  </div>
               </div>
               <div align="center" className="container-2">
                  <div className="TituloSuperior">
                     <h1>
                        <b>Listado de Productos</b>
                     </h1>
                     <div className="Derecha">
                        <img id="excel" src={excel} alt="excel" className="img-card" onClick={descargarExcel} />
                     </div>
                     <div className="Derecha">
                        <div className="btn-inf">
                           <Button className="boton" onClick={crearProducto}>
                              Ingresar Producto
                           </Button>
                        </div>
                     </div>
                  </div>

                  <table border="1" className="content-table">
                     <thead>
                        <tr>
                           <th>ID</th>
                           <th>Tipo</th>
                           <th>Nombre</th>
                           <th>Valor</th>
                           <th>Valor Final</th>
                           <th>Cantidad</th>
                           <th>Más información</th>
                        </tr>
                     </thead>
                     <tbody>
                        {ProductoEntity.map((producto) => (
                           <tr key={producto.id}>
                              <td>{producto.id}</td>
                              <td>{producto.tipo}</td>
                              <td>{producto.nombre}</td>
                              <td>{producto.valor}</td>
                              <td>{producto.valor_final}</td>
                              <td>{producto.cantidad}</td>
                              <td style={{ textAlign: "center", verticalAlign: "middle", width: "1%" }}>
                                 <img id="editar" src={editar} alt="editar" onClick={() => ChangeViendoProducto(producto)} />
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

export default ListProductosComponents;

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
