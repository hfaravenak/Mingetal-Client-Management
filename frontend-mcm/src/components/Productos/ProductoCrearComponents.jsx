import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import atras from "../../images/atras.png";
import HeaderComponents from "../Headers/HeaderComponents";

import ProductoService from "../../services/ProductoService";

function ProductoCrearComponents() {
   const navigate = useNavigate();

   const initialState = {
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
         const reader = new FileReader();
         reader.onloadend = () => {
            setPreviewImage(reader.result);
         };
         reader.readAsDataURL(file);
      } else {
         setPreviewImage(null);
      }
   };

   //-- agregar producto + alarmas + confirmación
   const ingresarProducto = () => {
      Swal.fire({
         title: "¿Desea registrar el nuevo producto?",
         icon: "question",
         showDenyButton: true,
         confirmButtonText: "Confirmar",
         confirmButtonColor: "rgb(68, 194, 68)",
         denyButtonText: "Cancelar",
         denyButtonColor: "rgb(190, 54, 54)",
      }).then((result) => {
         if (result.isConfirmed) {
            const formData = new FormData();
            formData.append("tipo", input.tipo);
            formData.append("nombre", input.nombre);
            formData.append("valor", input.valor);
            formData.append("valor_final", input.valor_final);
            formData.append("cantidad", input.cantidad);
            if (input.imagen) {
               formData.append("imagen", input.imagen);
            }
            ProductoService.crearProducto(formData)
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

   const regresar = () => {
      navigate(`/productos`);
   };

   //---

   return (
      <div className="general">
         <HeaderComponents></HeaderComponents>
         <NavStyle>
            <div className="container-create">
               <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} style={{ width: "35px" }} />
               <Form>
                  <Form.Group controlId="tipo">
                     <Form.Label className="agregar">Tipo:</Form.Label>
                     <Form.Control className="agregar" type="text" name="tipo" value={input.tipo} onChange={handleInputChange} />
                  </Form.Group>

                  <Form.Group controlId="nombre">
                     <Form.Label className="agregar">Nombre:</Form.Label>
                     <Form.Control className="agregar" type="text" name="nombre" value={input.nombre} onChange={handleInputChange} />
                  </Form.Group>

                  <Form.Group controlId="valor">
                     <Form.Label className="agregar">Valor:</Form.Label>
                     <Form.Control className="agregar" type="number" name="valor" min="0" value={input.valor} onChange={handleInputChange} />
                  </Form.Group>

                  <Form.Group controlId="valor_final">
                     <Form.Label className="agregar">Valor Final:</Form.Label>
                     <Form.Control className="agregar" type="number" name="valor_final" min="0" value={input.valor_final} onChange={handleInputChange} />
                  </Form.Group>

                  <Form.Group controlId="cantidad">
                     <Form.Label className="agregar">Cantidad:</Form.Label>
                     <Form.Control className="agregar" type="number" name="cantidad" min="0" value={input.cantidad} onChange={handleInputChange} />
                  </Form.Group>

                  <Form.Group className="file-custom" controlId="imagen">
                     <Form.Label className="agregar">Imagen:</Form.Label>
                     <Form.Control className="agregar" type="file" accept="image/jpeg, image/png" onChange={handleImageChange} />
                  </Form.Group>

                  {previewImage && (
                     <div className="image-preview">
                        <img style={{width:"80%"}} src={previewImage} alt="Vista previa" />
                     </div>
                  )}

                  <Button className="boton" onClick={ingresarProducto}>
                     Ingresar Producto
                  </Button>
               </Form>
            </div>
         </NavStyle>
      </div>
   );
}

export default ProductoCrearComponents;

const NavStyle = styled.nav`
   .container-create {
      margin: 2%;
      padding: 1%;
      border: 2px solid #d5d5d5;
      background-color: #f0f0f0;
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
      font-size: 18px; /* Incrementa el tamaño de la letra */
      font-weight: bold; /* Hace la letra negrita */
   }

   input[type="text"],
   input[type="email"],
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

   .text-danger {
      color: red;
      margin-left: 15px;
   }

   .file-custom input{
      display: none;
   }

   .file-custom label{
      margin-top: 3%;
      display: block;
      padding: 8px 21px;
      border: 1px solid #ccc;
      border-radius: 6px;
      background-color: #eee;
      cursor: pointer;
   }
   .img-back:hover{
      cursor: pointer;
   }
`;
