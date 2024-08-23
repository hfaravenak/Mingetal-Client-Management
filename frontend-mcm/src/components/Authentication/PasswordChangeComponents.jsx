import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import Form from "react-bootstrap/Form";
import axios from "axios";
import { FaExclamationCircle } from "react-icons/fa"; // Icono de alerta
import { useAuth } from "../../services/AuthProvider";

import HeaderLoginComponents from "../Headers/HeaderLoginComponents";

function LoginComponents() {
   const { email } = useParams();
   const [user, setUserData] = useState(null);
   const [confirmPassword, setConfirmPassword] = useState("");
   const [password, setPassword] = useState("");
   const [error, setError] = useState("");
   const navigate = useNavigate();
   const [isIconExpanded, setIsIconExpanded] = useState(false);
   const { setToken } = useAuth(); // Obtener setToken desde el contexto de autenticación

   useEffect(() => {
      const fetchUserData = async () => {
         if (email) {
            const response = await axios.get(`http://147.182.163.81:8080/user/${email}`);
            setUserData(response.data);
         }
      };

      fetchUserData();
   }, [email]); // Dependencia: vuelve a ejecutar cuando `email` cambia

   const handleCambiar = async (e) => {
      e.preventDefault();
      try {
         if (password === confirmPassword) {
            await axios.post("http://147.182.163.81:8080/user/cambio-contrasenia?correo=" + email + "&nuevaContrasenia=" + password);
         }

         const response = await axios.post("http://147.182.163.81:8080/user/token", {
            correo: email,
            password: password,
         });

         const token = response.data;
         setToken(token); // Actualizar el token en el contexto de autenticación

         // Redirigir al usuario a otra página
         navigate("/main");
      } catch (error) {
         setError("Usuario o contraseña incorrectos.");
         setIsIconExpanded(true);
         setTimeout(() => setIsIconExpanded(false), 500);
      }
   };
   const handleCancelar = async (e) => {
      e.preventDefault();
      try {
         if (password === confirmPassword) {
            await axios.post("http://147.182.163.81:8080/user/cambio-contrasenia/cancelar?correo=" + email);
         }
         navigate("/");
      } catch (error) {
         setError("Usuario o contraseña incorrectos.");
         setIsIconExpanded(true);
         setTimeout(() => setIsIconExpanded(false), 500);
      }
   };
   if (user !== null) {
      if (user.cambiando) {
         return (
            <div>
               <NavStyle>
                  <HeaderLoginComponents />
                  <div className="container-create" />
                  <div className="container_main">
                     <h1 className="main-title">¡Bienvenido a Mingetal Client Management!</h1>
                     <div className="card">
                        <h2>Por favor, modifique su contraseña</h2>
                        {error && (
                           <p className="error-message">
                              <FaExclamationCircle className={`error-icon ${isIconExpanded ? "expanded" : ""}`} /> {error}
                           </p>
                        )}
                        <Form onSubmit={handleCambiar}>
                           <Form.Group className="form-group" controlId="NewPassword">
                              <Form.Label>Nueva Contraseña:</Form.Label>
                              <Form.Control type="password" placeholder="******" className="form-control" value={password} onChange={(e) => setPassword(e.target.value)} />
                           </Form.Group>
                           <Form.Group className="form-group" controlId="ConfirmPassword">
                              <Form.Label>Confirmar Contraseña:</Form.Label>
                              <Form.Control type="password" placeholder="******" className="form-control" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} />
                           </Form.Group>
                           <div className="btn-group">
                              <button type="submit" className="btn btn-primary">
                                 Cambiar Contraseña
                              </button>
                              <button type="button" className="btn btn-secondary" onClick={handleCancelar}>
                                 Cancelar
                              </button>
                           </div>
                        </Form>
                     </div>
                  </div>
               </NavStyle>
            </div>
         );
      } else {
         return (
            <div>
               <NavStyle>
                  <HeaderLoginComponents />
                  <div className="container_main">
                     <p>No tienes permiso para cambiar la contraseña</p>
                  </div>
               </NavStyle>
            </div>
         );
      }
   } else {
   }
}

export default LoginComponents;

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
   .container_main {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      flex-wrap: wrap;
      margin: 2%;
      margin-top: 0;
      padding: 2%;
      padding-top: 0;
      border: 2px solid #d5d5d5;
      border-top: 0;
      background-color: #f0f0f0;
   }
   .main-title {
      font-size: 2.8em;
      color: #333;
      text-align: center;
      text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
      margin-bottom: 20px;
   }
   .card {
      background-color: #fff;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: space-around;
      margin: 10px;
      padding: 20px;
      width: 500px;
      height: auto;
      border: 1px solid black;
      border-radius: 20px;
      transition: transform 0.5s ease;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
   }
   .form-group {
      display: flex;
      flex-direction: column;
      width: 100%;
      margin-bottom: 15px;
   }
   .form-control {
      margin-top: 5px;
      font-size: 1.2em; /* Agrandar el tamaño del texto del campo de entrada */
      padding: 10px;
   }
   .form-group label {
      font-size: 1.5em; /* Agrandar el tamaño del texto de la etiqueta */
   }
   .btn-group {
      display: flex;
      flex-direction: column; /* Disposición en columna */
      justify-content: center; /* Centrar verticalmente los botones */
      align-items: center; /* Centrar horizontalmente los botones */
      width: 100%; /* Asegurar que el contenedor de los botones ocupe todo el ancho */
   }

   .btn-group .btn {
      margin-bottom: 10px; /* Espacio entre los botones */
   }

   /* Elimina el espacio extra en el último botón */
   .btn-group .btn:last-child {
      margin-bottom: 0;
   }

   .btn-primary {
      background: #d2712b;
      color: #fff;
      border: none;
      position: relative;
      height: 60px;
      font-size: 1.6em;
      padding: 0 2em;
      cursor: pointer;
      transition: 800ms ease all;
      outline: none;
   }
   .btn-secondary {
      background: #ccc;
      color: #333;
      border: none;
      position: relative;
      height: 60px;
      font-size: 1.6em;
      padding: 0 2em;
      cursor: pointer;
      transition: 800ms ease all;
      outline: none;
      width: 200px;
   }
   .btn-primary:hover,
   .btn-secondary:hover {
      background: #fff;
      color: #d2712b;
   }
   .btn-primary:before,
   .btn-primary:after,
   .btn-secondary:before,
   .btn-secondary:after {
      content: "";
      position: absolute;
      top: 0;
      right: 0;
      height: 2px;
      width: 0;
      background: #61c9f9;
      transition: 400ms ease all;
   }
   .btn-primary:after,
   .btn-secondary:after {
      right: inherit;
      top: inherit;
      left: 0;
      bottom: 0;
   }
   .btn-primary:hover:before,
   .btn-primary:hover:after,
   .btn-secondary:hover:before,
   .btn-secondary:hover:after {
      width: 100%;
      transition: 800ms ease all;
   }
   .forgot-password {
      margin-top: 10px;
   }
   .btn-link {
      color: #007bff;
      background-color: transparent;
      border: none;
      padding: 0;
      font: inherit;
      cursor: pointer;
      font-size: 1.5em;
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
