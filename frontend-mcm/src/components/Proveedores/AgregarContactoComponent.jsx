import React, { useState } from "react";
import styled from "styled-components";
import Form from "react-bootstrap/Form";
import axios from "axios";
import { FaExclamationCircle } from "react-icons/fa"; // Icono de alerta

function AgregarContactoComponent({ onClose, onAgregarContacto }) {
   const initialState = {
      nombre: "",
      rut: "",
      correo: "",
      fono_cel: "",
      fono_fijo: "",
   };
   const [contacto, setContacto] = useState(initialState);
   const [isIconExpanded, setIsIconExpanded] = useState(false);
   const [error, setError] = useState("");

   const handleSendNewContacto = async (e) => {
      e.preventDefault();
      if (contacto.nombre !== "" && contacto.rut !== "" && contacto.correo !== "" && contacto.fono_fijo !== "" && contacto.fono_cel !== "") {
          setError("");
          console.log(contacto)
          try {
              const response = await axios.post("http://localhost:8080/proveedor/contactos", contacto);  
              onAgregarContacto(response.data); // Pasar el nuevo contacto al CarruselContactosEditar
              //onAgregarContacto(contacto)
              console.log("aqui?")
              onClose(); // Cerrar la ventana emergente
          } catch (error) {
              console.error("Error al agregar contacto:", error);
              setError("Hubo un problema al agregar el contacto");
          }
      } else {
          setError("Falta rellenar un formulario");
          setIsIconExpanded(true);
          setTimeout(() => setIsIconExpanded(false), 500);
      }
  };

   const handleInputChange = (event) => {
      const { name, value } = event.target;
      setContacto({ ...contacto, [name]: value });
   };

   return (
      <ModalBackground>
         <ModalContainer>
            <button className="close-button" onClick={onClose}>
               X
            </button>
            <>
               <h2>Nuevo Contacto</h2>
               {error && (
                  <p className="error-message">
                     <FaExclamationCircle className={`error-icon ${isIconExpanded ? "expanded" : ""}`} /> {error}
                  </p>
               )}
               <Form onSubmit={handleSendNewContacto}>
                  <div className="form-group">
                     <Form.Label>Nombre:</Form.Label>
                     <Form.Control name="nombre" type="text" placeholder="Juanito Perez" className="form-control" value={contacto.nombre} onChange={handleInputChange} required />
                  </div>
                  <div className="form-group">
                     <Form.Label>Rut:</Form.Label>
                     <Form.Control name="rut" type="text" placeholder="12.345.678-9" className="form-control" value={contacto.rut} onChange={handleInputChange} required />
                  </div>
                  <div className="form-group">
                     <Form.Label>Correo:</Form.Label>
                     <Form.Control name="correo" type="email" placeholder="ejemplo@mingetal.cl" className="form-control" value={contacto.correo} onChange={handleInputChange} required />
                  </div>
                  <div className="form-group">
                     <Form.Label>Numero Fijo:</Form.Label>
                     <Form.Control name="fono_fijo" type="number" placeholder="221234567" className="form-control" value={contacto.fono_fijo} onChange={handleInputChange} required />
                  </div>
                  <div className="form-group">
                     <Form.Label>Numero Celular:</Form.Label>
                     <Form.Control name="fono_cel" type="number" placeholder="+56912345678" className="form-control" value={contacto.fono_cel} onChange={handleInputChange} required />
                  </div>
                  <div className="btn-group">
                     <button type="submit" className="btn btn-primary">
                        Enviar
                     </button>
                     <button type="button" className="btn btn-secondary" onClick={onClose}>
                        Cancelar
                     </button>
                  </div>
               </Form>
            </>
         </ModalContainer>
      </ModalBackground>
   );
}

export default AgregarContactoComponent;

const ModalBackground = styled.div`
   position: fixed;
   top: 0;
   left: 0;
   width: 100%;
   height: 100%;
   background: rgba(0, 0, 0, 0.5);
   display: flex;
   justify-content: center;
   align-items: center;
`;

const ModalContainer = styled.div`
   background: white;
   padding: 40px; /* Aumentar el padding para hacer el contenedor más grande */
   border-radius: 10px;
   width: 450px; /* Aumentar el ancho del contenedor */
   position: relative;
   display: flex;
   flex-direction: column;
   align-items: center; /* Centrar el contenido */

   .close-button {
      position: absolute;
      top: 10px;
      right: 10px;
      background: transparent;
      border: none;
      font-size: 1.5em;
      cursor: pointer;
   }

   .form-group {
      margin-bottom: 15px;
      width: 100%; /* Asegurar que el formulario ocupe todo el ancho del contenedor */
      display: flex;
      align-items: center; /* Centrar el contenido verticalmente */
      justify-content: center; /* Centrar el contenido horizontalmente */
   }

   .form-group label {
      margin-right: 10px; /* Espacio entre el label y el input */
      font-size: 1.2em;
   }

   .form-control {
      margin-top: 0; /* Quitar el margen superior */
      font-size: 1.2em;
      padding: 10px;
      flex: 1; /* Hacer que el input ocupe el resto del espacio disponible */
   }

   .btn-group {
      display: flex;
      justify-content: center; /* Centrar los botones */
      width: 100%; /* Asegurar que el contenedor de los botones ocupe todo el ancho */
   }

   .btn {
      margin: 5px;
   }

   .btn-primary {
      background: #d2712b;
      color: #fff;
      border: none;
      font-size: 1.2em;
      padding: 10px 20px;
      cursor: pointer;
   }

   .btn-secondary {
      background: #ccc;
      color: #333;
      border: none;
      font-size: 1.2em;
      padding: 10px 20px;
      cursor: pointer;
   }

   .btn-link {
      color: #007bff;
      background-color: transparent;
      border: none;
      padding: 0;
      font: inherit;
      cursor: pointer;
      font-size: 1em;
      margin-top: 10px;
   }

   .error-message {
      color: red;
      font-size: 1.2em;
      display: flex;
      align-items: center;
   }
   .error-icon {
      margin-right: 10px;
      font-size: 1.5em;
      transition: transform 0.5s ease;
   }

   .error-icon.expanded {
      transform: scale(1.5); /* Expande el icono al 150% de su tamaño original */
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
`;
