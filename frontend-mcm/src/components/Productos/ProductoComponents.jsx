import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import atras from "../../images/atras.png";

import HeaderComponents from "../Headers/HeaderComponents";
import ProductoService from "../../services/ProductoService";

function ProductoComponents() {
   const { producto } = useParams();
   const datos = JSON.parse(decodeURIComponent(producto));
   const navigate = useNavigate();
   const maxFileSize = 1048576;

   const initialState = {
      id: 0,
      tipo: "",
      nombre: "",
      valor: 0,
      valor_final: 0,
      cantidad: 0,
      imagen: null,
      tipo_imagen: "",
   };
   const [input, setInput] = useState(initialState);
   const [previewImage, setPreviewImage] = useState(null);

   const handleInputChange = (event) => {
      const { name, value } = event.target;
      setInput({ ...input, [name]: value });
   };

   const handleImageChange = (event) => {
      const file = event.target.files[0];
      setInput({ ...input, imagen: file });
      if (file) {
         if (file.size > maxFileSize) {
            alert("El archivo es demasiado grande. El tamaño máximo permitido es de 500 KB.");
            return;
         }
         const reader = new FileReader();
         reader.onloadend = () => {
            setPreviewImage(reader.result);
         };
         reader.readAsDataURL(file);
      } else {
         setPreviewImage(null);
      }
   };

   const [mostrarCard, setMostrarCard] = useState(false);
   const changeMostrarCard = () => {
      setInput({
         id: datos.id,
         tipo: datos.tipo,
         nombre: datos.nombre,
         valor: datos.valor,
         valor_final: datos.valor_final,
         cantidad: datos.cantidad,
         imagen: datos.imagen,
         tipo_imagen: datos.tipo_imagen,
      });
      setMostrarCard(true);
   };

   const handleGuardarProducto = () => {
      Swal.fire({
         title: "¿Seguro de modificar este producto?",
         icon: "question",
         showDenyButton: true,
         confirmButtonText: "Confirmar",
         confirmButtonColor: "rgb(68, 194, 68)",
         denyButtonText: "Cancelar",
         denyButtonColor: "rgb(190, 54, 54)",
      }).then((result) => {
         if (result.isConfirmed) {
            const formData = new FormData();
            formData.append("id", datos.id);
            formData.append("tipo", input.tipo);
            formData.append("nombre", input.nombre);
            formData.append("valor", input.valor);
            formData.append("valor_final", input.valor_final);
            formData.append("cantidad", input.cantidad);
            if (input.imagen) {
               formData.append("imagen", input.imagen);
            }
            ProductoService.updateProducto(formData)
               .then(() => {
                  Swal.fire({
                     title: "Enviado",
                     timer: 2000,
                     icon: "success",
                     timerProgressBar: true,
                     didOpen: () => {
                        Swal.showLoading();
                     },
                     willClose: () => {
                        navigate("/productos");
                     },
                  });
               })
               .catch((error) => {
                  Swal.fire({
                     title: "Error",
                     text: error.message,
                     icon: "error",
                  });
               });
         }
      });
   };
   const handleEliminarProducto = () => {
      Swal.fire({
         title: "¿Seguro de que desea eliminar este producto?",
         text: "Esta acción es irreversible y no podrá recuperar el producto perdido.",
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
            ProductoService.deleteProducto(id).then(() => {
               Swal.fire({
                  title: "Eliminando...",
                  text: "Por favor espera",
                  timer: 2000, // 2 segundos
                  didOpen: () => {
                     Swal.showLoading();
                  },
                  willClose: () => {
                     navigate("/productos");
                  },
               });
            });
         }
      });
   };
   const CancelarEdit = () => {
      setMostrarCard(false);
   };

   const regresar = () => {
      navigate(`/productos`);
   };

   if (mostrarCard) {
      return (
         <div>
            <HeaderComponents />
            <NavStyle>
               <div className="container-create">
                  <img id="atras" src={atras} alt="atras" className="img-card" onClick={regresar} style={{ width: "35px" }} />
               </div>
               <div className="container">
                  <div className="container-1">
                     <div className="card">
                        <div className="contenedor-img">
                           <img id="productos" src={`data:image/jpeg;base64,${datos.imagen}`} alt="productos" />
                        </div>
                        <div className="contenedor-informacion">
                           <Form>
                              <Form.Group controlId="nombre">
                                 <Form.Label className="font-h2">Nombre:</Form.Label>
                                 <Form.Control value={input.nombre} onChange={handleInputChange} className="font-h2-control no-border" type="text" name="nombre" placeholder="Nombre producto" />
                              </Form.Group>
                              <Form.Group controlId="tipo">
                                 <Form.Label className="font-h2">Tipo:</Form.Label>
                                 <Form.Control value={input.tipo} onChange={handleInputChange} className="font-h2-control no-border" type="text" name="tipo" placeholder="Tipo del Producto" />
                              </Form.Group>
                              <Form.Group controlId="valor">
                                 <Form.Label className="font-h2">Valor:</Form.Label>
                                 <Form.Control value={input.valor} onChange={handleInputChange} className="font-h2-control no-border" type="number" name="valor" placeholder="Valor del Producto" />
                              </Form.Group>
                              <Form.Group controlId="valor_final">
                                 <Form.Label className="font-h2">Valor Final:</Form.Label>
                                 <Form.Control
                                    value={input.valor_final}
                                    onChange={handleInputChange}
                                    className="font-h2-control no-border"
                                    type="number"
                                    name="valor_final"
                                    placeholder="Valor Final del Producto"
                                 />
                              </Form.Group>
                              <Form.Group controlId="cantidad">
                                 <Form.Label className="font-h2">Cantidad:</Form.Label>
                                 <Form.Control
                                    value={input.cantidad}
                                    onChange={handleInputChange}
                                    className="font-h2-control no-border"
                                    type="number"
                                    name="cantidad"
                                    placeholder="Cantidad del Producto"
                                 />
                              </Form.Group>
                              <Form.Group className="file-custom" controlId="imagen">
                                 <Form.Label className="agregar">Imagen:</Form.Label>
                                 <Form.Control className="agregar" type="file" accept="image/jpeg, image/png" onChange={handleImageChange} />
                              </Form.Group>

                              {previewImage && (
                                 <div className="image-preview">
                                    <img style={{ width: "80%" }} src={previewImage} alt="Vista previa" />
                                 </div>
                              )}
                           </Form>
                        </div>
                     </div>
                     <Button className="aceptar" onClick={handleGuardarProducto}>
                        Aceptar
                     </Button>
                     <Button className="cancelar" onClick={CancelarEdit}>
                        Cancelar
                     </Button>
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
                     <div className="card">
                        <div className="contenedor-img">
                           <img id="productos" src={`data:image/jpeg;base64,${datos.imagen}`} alt="productos" />
                        </div>
                        <div className="contenedor-informacion">
                           <h2>
                              {" "}
                              {datos.id} || {datos.nombre}
                           </h2>
                           <h3>Tipo: {datos.tipo}</h3>
                           <h3>Valor: {datos.valor}</h3>
                           <h3>Valor Final: {datos.valor_final}</h3>
                           <h3>Cantidad: {datos.cantidad}</h3>
                        </div>
                     </div>
                     <Button className="editar" onClick={changeMostrarCard}>
                        Editar
                     </Button>
                     <Button className="eliminar" onClick={handleEliminarProducto}>
                        Eliminar
                     </Button>
                  </div>
               </div>
            </NavStyle>
         </div>
      );
   }
}

export default ProductoComponents;

// Estilos con styled-components

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
      flex-direction: column;
      align-items: center;
      justify-content: center;
      gap: 20px;
      height: 100%;
   }

   .container-1 {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      width: 100%;
   }

   td img {
      width: 30%;
      object-fit: cover;
   }

   td img:hover {
      cursor: pointer;
   }

   th:hover,
   td:hover {
      cursor: default;
   }

   /* Por el lado de la información del producto*/

   .card {
      border: 2px solid black; /* Asegurarse de que los bordes sean visibles */
      border-radius: 10px;
      background-color: white;
      display: flex;
      flex-direction: column;
      align-items: center;
      width: 100%;
      max-width: 600px;
      padding: 20px; /* Añadir padding para una mejor presentación */
   }

   .card .contenedor-img {
      background-color: #f0f0f0;
      border-radius: 10px;
      display: flex;
      flex-direction: column;
      align-items: center;
      width: 100%; /* Asegurarse de que la imagen ocupe todo el ancho disponible */
      padding: 10px;
   }

   .card .contenedor-img img {
      width: 40%; /* Ajustar el tamaño de la imagen */
      object-fit: contain; /* Asegurar que la imagen mantenga su proporción */
   }

   .card .contenedor-informacion {
      background-color: white;
      width: 100%; /* Asegurarse de que la información ocupe todo el ancho disponible */
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

   .buttons {
      display: flex;
      gap: 10px;
      margin-top: 10px;
   }

   .editar,
   .eliminar,
   .cancelar,
   .aceptar {
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
   .text-danger {
      color: red;
      margin-left: 15px;
   }

   .file-custom input {
      display: none;
   }

   .file-custom label {
      margin-top: 3%;
      display: block;
      padding: 8px 21px;
      border: 1px solid #ccc;
      border-radius: 6px;
      background-color: #eee;
      cursor: pointer;
   }
   .img-back:hover {
      cursor: pointer;
   }
`;
