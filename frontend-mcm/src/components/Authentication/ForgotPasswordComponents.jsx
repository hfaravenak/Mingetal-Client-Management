import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Form from "react-bootstrap/Form";
import axios from "axios";
import { FaExclamationCircle } from "react-icons/fa"; // Icono de alerta

function ForgotPasswordComponents({ onClose }) {
   const [email, setEmail] = useState("");
   const [codeSent, setCodeSent] = useState(false);
   const [verificationCode, setVerificationCode] = useState("");
   const [error, setError] = useState("");
   const [isIconExpanded, setIsIconExpanded] = useState(false);
   const navigate = useNavigate();

   const handleSendEmail = async (e) => {
      e.preventDefault();
      if (email !== "") {
         // Llamada a la API para enviar el correo
         setCodeSent(true);
         setError("");
         await axios.post("http://147.182.163.81:8080/user/recuperar-contrasenia?correo=" + email);
      } else {
         setError("Debe escribir algún correo");
         setIsIconExpanded(true);
         setTimeout(() => setIsIconExpanded(false), 500);
      }
   };

   const handleVerifyCode = async (e) => {
      e.preventDefault();
      try {
         // Llamada a la API para verificar el código
         await axios.post("http://147.182.163.81:8080/user/codigo-reestablecimiento?correo=" + email + "&codigoReestablecimiento=" + verificationCode);
         setError("");
         navigate(`/changePass/${encodeURIComponent(email)}`);
         // Aquí puedes redirigir al usuario a una página para cambiar la contraseña
      } catch (error) {
         setError("Código incorrecto. Intente nuevamente.");
         setIsIconExpanded(true);
         setTimeout(() => setIsIconExpanded(false), 500);
      }
   };

   const handleBack = () => {
      setCodeSent(false);  // Volver a la vista de "Enviar correo"
      setVerificationCode(""); // Limpia el código de verificación
      setError(""); // Limpia los errores anteriores
   };

   return (
      <ModalBackground>
         <ModalContainer>
            <button className="close-button" onClick={onClose}>
               X
            </button>
            {!codeSent ? (
               // Esta es la vista de "Enviar correo"
               <>
                  <h2>Restablecer Contraseña</h2>
                  {error && (
                     <p className="error-message">
                        <FaExclamationCircle className={`error-icon ${isIconExpanded ? "expanded" : ""}`} /> {error}
                     </p>
                  )}
                  <Form onSubmit={handleSendEmail}>
                     <div className="form-group">
                        <Form.Label>Correo Electrónico:</Form.Label>
                        <Form.Control
                           type="email"
                           placeholder="ejemplo@mingetal.cl"
                           className="form-control"
                           value={email}
                           onChange={(e) => setEmail(e.target.value)}
                        />
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
                  <div style={{ color: "grey" }}>Recuerde revisar su correo no deseado y/o su carpeta de Spam</div>
               </>
            ) : (
               // Esta es la vista de "Verificar código"
               <>
                  <h2>Verificar Código</h2>
                  {error && (
                     <p className="error-message">
                        <FaExclamationCircle className={`error-icon ${isIconExpanded ? "expanded" : ""}`} /> {error}
                     </p>
                  )}
                  <Form onSubmit={handleVerifyCode}>
                     <div className="form-group">
                        <Form.Label>Código de Verificación:</Form.Label>
                        <Form.Control
                           type="text"
                           placeholder="Ingrese el código"
                           className="form-control"
                           value={verificationCode}
                           onChange={(e) => setVerificationCode(e.target.value)}
                        />
                     </div>
                     <div className="btn-group">
                        <button type="submit" className="btn btn-primary">Enviar</button>
                        <button type="button" className="btn btn-link" onClick={handleSendEmail}>
                           Reenviar Código
                        </button>
                        <button type="button" className="btn btn-secondary" onClick={handleBack}>
                           Volver
                        </button>
                     </div>
                  </Form>
               </>
            )}
         </ModalContainer>
      </ModalBackground>
   );   
}

export default ForgotPasswordComponents;

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
`;
