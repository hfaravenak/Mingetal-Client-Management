import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import atras from "../../images/atras.png";

import HeaderComponents from "../Headers/HeaderComponents";
import ProveedorService from "../../services/ProveedorService";
import ContactoService from "../../services/ContactoService";

function ProveedorCrearComponent() {
   const navigate = useNavigate();

   const initialState = {
      empresa: "",
      rubro: "",
      contacto: {
         nombre: "",
         rut: "",
         correo: "",
         fono_cel: "",
         fono_fijo: "",
      },
      contacto2: {
         nombre: "",
         rut: "",
         correo: "",
         fono_cel: "",
         fono_fijo: "",
      },
      contacto3: {
         nombre: "",
         rut: "",
         correo: "",
         fono_cel: "",
         fono_fijo: "",
      },
      comentario: "",
   };
   const [input, setInput] = useState(initialState);

   const handleInputChange = (event) => {
      const { name, value } = event.target;
      const keys = name.split(".");
      setInput((prevState) => {
         let newState = { ...prevState };
         let current = newState;

         for (let i = 0; i < keys.length - 1; i++) {
            current = current[keys[i]];
         }

         current[keys[keys.length - 1]] = value;
         return newState;
      });
   };

   const validateFormContacto2 = () => {
      const contacto2 = input.contacto2;
      const allFieldsEmpty = Object.values(contacto2).every((field) => field === "");
      const allFieldsNotEmpty = Object.values(contacto2).every((field) => field !== "");
      if (allFieldsEmpty) {
         return true;
      } else if (allFieldsNotEmpty) {
         return true;
      } else {
         return false;
      }
   };
   const validateFormContacto3 = () => {
      const contacto3 = input.contacto3;
      const allFieldsEmpty = Object.values(contacto3).every((field) => field === "");
      const allFieldsNotEmpty = Object.values(contacto3).every((field) => field !== "");
      if (allFieldsEmpty) {
         return true;
      } else if (allFieldsNotEmpty) {
         return true;
      } else {
         return false;
      }
   };
   const handleSubmit = (event) => {
      event.preventDefault();
      if (validateFormContacto2() && validateFormContacto3()) {
         ingresarProveedor();
      } else {
         Swal.fire({
            title: "Error",
            text: "Por favor, complete todos los campos requeridos.",
            icon: "error",
            confirmButtonText: "OK",
         });
      }
   };

   const ingresarProveedor = () => {
      Swal.fire({
         title: "¿Desea ingresar un nuevo Proveedor?",
         icon: "question",
         showDenyButton: true,
         confirmButtonText: "Confirmar",
         confirmButtonColor: "rgb(68, 194, 68)",
         denyButtonText: "Cancelar",
         denyButtonColor: "rgb(190, 54, 54)",
      }).then((result) => {
         if (result.isConfirmed) {
            let newContacto1 = {
               nombre: input.contacto.nombre,
               rut: input.contacto.rut,
               correo: input.contacto.correo,
               fono_cel: input.contacto.fono_cel,
               fono_fijo: input.contacto.fono_fijo,
            };
            ContactoService.createContacto(newContacto1);

            if (input.contacto2.rut !== "" && input.contacto2.rut !== null) {
               let newContacto2 = {
                  nombre: input.contacto2.nombre,
                  rut: input.contacto2.rut,
                  correo: input.contacto2.correo,
                  fono_cel: input.contacto2.fono_cel,
                  fono_fijo: input.contacto2.fono_fijo,
               };
               ContactoService.createContacto(newContacto2);
            }

            if (input.contacto3.rut !== "" && input.contacto3.rut !== null) {
               let newContacto3 = {
                  nombre: input.contacto3.nombre,
                  rut: input.contacto3.rut,
                  correo: input.contacto3.correo,
                  fono_cel: input.contacto3.fono_cel,
                  fono_fijo: input.contacto3.fono_fijo,
               };
               ContactoService.createContacto(newContacto3);
            }

            let newProveedor = {
               empresa: input.empresa,
               rubro: input.rubro,
               id_contacto: input.contacto.rut,
               id_contacto2: input.contacto2.rut === "" || input.contacto2.rut === null ? null : input.contacto2.rut,
               id_contacto3: input.contacto3.rut === "" || input.contacto3.rut === null ? null : input.contacto3.rut,
               comentario: input.comentario,
            };
            ProveedorService.createProveedor(newProveedor);
            Swal.fire({
               title: "Enviado",
               timer: 2000,
               icon: "success",
               timerProgressBar: true,
               didOpen: () => {
                  Swal.showLoading();
               },
               willClose: () => {
                  navigate("/proveedores");
               },
            });
         }
      });
   };

   const [isTableVisibleContacto2, setisTableVisibleContacto2] = useState(false);
   const [isTableVisibleContacto3, setisTableVisibleContacto3] = useState(false);
   const toggleTableVisibilityContacto2 = () => {
      setisTableVisibleContacto2(!isTableVisibleContacto2);
   };
   const toggleTableVisibilityContacto3 = () => {
      setisTableVisibleContacto3(!isTableVisibleContacto3);
   };

   const regresar = () => {
      navigate(`/proveedores`);
   };

   return (
      <div className="general">
         <HeaderComponents></HeaderComponents>
         <NavStyle>
            <div className="container-create">
               <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} style={{ width: "35px" }} />
            </div>
            <div className="container">
               <Form onSubmit={handleSubmit}>
                  <div className="container-2">
                     <h1>Datos de la Empresa</h1>
                     <table border="1" className="content-table">
                        <thead>
                           <tr>
                              <th>* Empresa</th>
                              <th>* Rubro</th>
                              <th>Comentario</th>
                           </tr>
                        </thead>
                        <tbody>
                           <tr>
                              <td>
                                 <Form.Group controlId="empresa">
                                    <Form.Control style={{ width: "100%" }} className="font-h2 no-border" type="text" value={input.empresa} onChange={handleInputChange} name="empresa" required />
                                 </Form.Group>
                              </td>
                              <td>
                                 <Form.Group controlId="rubro">
                                    <Form.Control style={{ width: "100%" }} className="font-h2 no-border" type="text" value={input.rubro} onChange={handleInputChange} name="rubro" required />
                                 </Form.Group>
                              </td>
                              <td>
                                 <Form.Group controlId="comentario">
                                    <Form.Control style={{ width: "100%" }} className="font-h2 no-border" type="text" value={input.comentario} onChange={handleInputChange} name="comentario" />
                                 </Form.Group>
                              </td>
                           </tr>
                        </tbody>
                     </table>

                     <h1>Datos del Contacto Principal</h1>
                     <table border="1" className="content-table">
                        <thead>
                           <tr>
                              <th>* Nombre</th>
                              <th>* Rut</th>
                              <th>* Correo</th>
                              <th>* Telefono Celular</th>
                              <th>* Telefono fijo</th>
                           </tr>
                        </thead>
                        <tbody>
                           <tr>
                              <td>
                                 <Form.Group controlId="nombre">
                                    <Form.Control
                                       style={{ width: "100%" }}
                                       className="font-h2 no-border"
                                       type="text"
                                       value={input.contacto.nombre}
                                       onChange={handleInputChange}
                                       name="contacto.nombre"
                                       required
                                    />
                                 </Form.Group>
                              </td>
                              <td>
                                 <Form.Group controlId="rut">
                                    <Form.Control
                                       style={{ width: "100%" }}
                                       className="font-h2 no-border"
                                       type="text"
                                       value={input.contacto.rut}
                                       onChange={handleInputChange}
                                       name="contacto.rut"
                                       required
                                    />
                                 </Form.Group>
                              </td>
                              <td>
                                 <Form.Group controlId="correo">
                                    <Form.Control
                                       style={{ width: "100%" }}
                                       className="font-h2 no-border"
                                       type="text"
                                       value={input.contacto.correo}
                                       onChange={handleInputChange}
                                       name="contacto.correo"
                                       required
                                    />
                                 </Form.Group>
                              </td>
                              <td>
                                 <Form.Group controlId="fono_cel">
                                    <Form.Control
                                       style={{ width: "100%" }}
                                       className="font-h2 no-border"
                                       type="number"
                                       value={input.contacto.fono_cel}
                                       onChange={handleInputChange}
                                       name="contacto.fono_cel"
                                       required
                                    />
                                 </Form.Group>
                              </td>
                              <td>
                                 <Form.Group controlId="fono_fijo">
                                    <Form.Control
                                       style={{ width: "100%" }}
                                       className="font-h2 no-border"
                                       type="number"
                                       value={input.contacto.fono_fijo}
                                       onChange={handleInputChange}
                                       name="contacto.fono_fijo"
                                       required
                                    />
                                 </Form.Group>
                              </td>
                           </tr>
                        </tbody>
                     </table>

                     <h1 onClick={toggleTableVisibilityContacto2} style={{ cursor: "pointer" }}>
                        <b> Contacto de Emergencia</b>
                        <span style={{ marginLeft: "10px" }}>{isTableVisibleContacto2 ? "−" : "+"}</span>
                     </h1>
                     {isTableVisibleContacto2 && (
                        <table border="1" className="content-table">
                           <thead>
                              <tr>
                                 <th>* Nombre</th>
                                 <th>* Rut</th>
                                 <th>* Correo</th>
                                 <th>* Telefono Celular</th>
                                 <th>* Telefono fijo</th>
                              </tr>
                           </thead>
                           <tbody>
                              <tr>
                                 <td>
                                    <Form.Group controlId="nombre">
                                       <Form.Control
                                          style={{ width: "100%" }}
                                          className="font-h2 no-border"
                                          type="text"
                                          value={input.contacto2.nombre}
                                          onChange={handleInputChange}
                                          name="contacto2.nombre"
                                       />
                                    </Form.Group>
                                 </td>
                                 <td>
                                    <Form.Group controlId="rut">
                                       <Form.Control
                                          style={{ width: "100%" }}
                                          className="font-h2 no-border"
                                          type="text"
                                          value={input.contacto2.rut}
                                          onChange={handleInputChange}
                                          name="contacto2.rut"
                                       />
                                    </Form.Group>
                                 </td>
                                 <td>
                                    <Form.Group controlId="correo">
                                       <Form.Control
                                          style={{ width: "100%" }}
                                          className="font-h2 no-border"
                                          type="text"
                                          value={input.contacto2.correo}
                                          onChange={handleInputChange}
                                          name="contacto2.correo"
                                       />
                                    </Form.Group>
                                 </td>
                                 <td>
                                    <Form.Group controlId="fono_cel">
                                       <Form.Control
                                          style={{ width: "100%" }}
                                          className="font-h2 no-border"
                                          type="number"
                                          value={input.contacto2.fono_cel}
                                          onChange={handleInputChange}
                                          name="contacto2.fono_cel"
                                       />
                                    </Form.Group>
                                 </td>
                                 <td>
                                    <Form.Group controlId="fono_fijo">
                                       <Form.Control
                                          style={{ width: "100%" }}
                                          className="font-h2 no-border"
                                          type="number"
                                          value={input.contacto2.fono_fijo}
                                          onChange={handleInputChange}
                                          name="contacto2.fono_fijo"
                                       />
                                    </Form.Group>
                                 </td>
                              </tr>
                           </tbody>
                        </table>
                     )}

                     <h1 onClick={toggleTableVisibilityContacto3} style={{ cursor: "pointer" }}>
                        <b> Segundo Contacto de Emergencia</b>
                        <span style={{ marginLeft: "10px" }}>{isTableVisibleContacto3 ? "−" : "+"}</span>
                     </h1>
                     {isTableVisibleContacto3 && (
                        <table border="1" className="content-table">
                           <thead>
                              <tr>
                                 <th>* Nombre</th>
                                 <th>* Rut</th>
                                 <th>* Correo</th>
                                 <th>* Telefono Celular</th>
                                 <th>* Telefono fijo</th>
                              </tr>
                           </thead>
                           <tbody>
                              <tr>
                                 <td>
                                    <Form.Group controlId="nombre">
                                       <Form.Control
                                          style={{ width: "100%" }}
                                          className="font-h2 no-border"
                                          type="text"
                                          value={input.contacto3.nombre}
                                          onChange={handleInputChange}
                                          name="contacto3.nombre"
                                       />
                                    </Form.Group>
                                 </td>
                                 <td>
                                    <Form.Group controlId="rut">
                                       <Form.Control
                                          style={{ width: "100%" }}
                                          className="font-h2 no-border"
                                          type="text"
                                          value={input.contacto3.rut}
                                          onChange={handleInputChange}
                                          name="contacto3.rut"
                                       />
                                    </Form.Group>
                                 </td>
                                 <td>
                                    <Form.Group controlId="correo">
                                       <Form.Control
                                          style={{ width: "100%" }}
                                          className="font-h2 no-border"
                                          type="text"
                                          value={input.contacto3.correo}
                                          onChange={handleInputChange}
                                          name="contacto3.correo"
                                       />
                                    </Form.Group>
                                 </td>
                                 <td>
                                    <Form.Group controlId="fono_cel">
                                       <Form.Control
                                          style={{ width: "100%" }}
                                          className="font-h2 no-border"
                                          type="number"
                                          value={input.contacto3.fono_cel}
                                          onChange={handleInputChange}
                                          name="contacto3.fono_cel"
                                       />
                                    </Form.Group>
                                 </td>
                                 <td>
                                    <Form.Group controlId="fono_fijo">
                                       <Form.Control
                                          style={{ width: "100%" }}
                                          className="font-h2 no-border"
                                          type="number"
                                          value={input.contacto3.fono_fijo}
                                          onChange={handleInputChange}
                                          name="contacto3.fono_fijo"
                                       />
                                    </Form.Group>
                                 </td>
                              </tr>
                           </tbody>
                        </table>
                     )}
                  </div>
                  <Button className="boton" type="submit">
                     Crear Proveedor
                  </Button>
               </Form>
            </div>
         </NavStyle>
      </div>
   );
}
export default ProveedorCrearComponent;

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
   }
   .container-2 {
      background-color: #f0f0f0;
      flex-grow: 1; /* El lado derecho es flexible y ocupará todo el espacio restante */
      overflow-y: auto; /* Aparecerá una barra de desplazamiento vertical si el contenido es demasiado largo */
      padding: 1%; /* Espacio interno para evitar que el contenido se pegue a los bordes */
      max-height: calc(0px + 55.3vh); /* Asegura que el contenedor no exceda la altura de la ventana */
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
