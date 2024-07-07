import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Swal from "sweetalert2";

import clientes from "../../images/cliente.png";

import ContactoService from "../../services/ContactoService";
import ProveedorService from "../../services/ProveedorService";

const CarruselContactos = ({ datos, onMostrarCard }) => {
   const navigate = useNavigate();

   const contactos = [datos.id_contacto, datos.id_contacto2, datos.id_contacto3];
   const verLargo = () => {
      let largoEntrada = 3;
      contactos.forEach((contacto) => {
         if (contacto == null) {
            largoEntrada -= 1;
         }
      });
      return largoEntrada;
   };
   const [largo] = useState(verLargo);

   const nextSlide = () => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % largo);
   };
   const prevSlide = () => {
      setCurrentIndex((prevIndex) => (prevIndex - 1 + largo) % largo);
   };

   const [currentIndex, setCurrentIndex] = useState(0);

   const changeMostrarCard = () => {
      onMostrarCard(true);
   };
   const EliminarCliente = () => {
      Swal.fire({
         title: "¿Seguro de que desea eliminar este cliente?",
         text: "Esta acción es irreversible y no podrá recuperar al cliente perdido.",
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
            if (currentIndex === 0) {
               let rut = datos.id_contacto.rut;
               ContactoService.deleteContacto(rut);
               datos.id_contacto = datos.id_contacto2;
               datos.id_contacto2 = datos.id_contacto3;
               datos.id_contacto3 = null;
            } else if (currentIndex === 1) {
               let rut = datos.id_contacto2.rut;
               ContactoService.deleteContacto(rut);
               datos.id_contacto2 = datos.id_contacto3;
               datos.id_contacto3 = null;
            } else {
               let rut = datos.id_contacto3.rut;
               ContactoService.deleteContacto(rut);
               datos.id_contacto3 = null;
            }

            if (datos.id_contacto === null) {
               ProveedorService.deleteProveedor(datos.id_proveedor);
            } else {
               let rut_contacto = null;
               if (datos.id_contacto != null) {
                  rut_contacto = datos.id_contacto.rut;
               }
               let rut_contacto2 = null;
               if (datos.id_contacto2 != null) {
                  rut_contacto2 = datos.id_contacto2.rut;
               }
               let rut_contacto3 = null;
               if (datos.id_contacto3 != null) {
                  rut_contacto3 = datos.id_contacto3.rut;
               }
               let updateProveedor = {
                  id_proveedor: datos.id_proveedor,
                  empresa: datos.empresa,
                  rubro: datos.rubro,
                  id_contacto: rut_contacto,
                  id_contacto2: rut_contacto2,
                  id_contacto3: rut_contacto3,
                  comentario: datos.comentario,
               };
               ProveedorService.updateProveedor(updateProveedor);
            }

            Swal.fire({
               title: "Eliminando...",
               text: "Por favor espera",
               timer: 2000, // 3 segundos
               didOpen: () => {
                  Swal.showLoading();
               },
               willClose: () => {
                  if (datos.id_contacto === null) {
                     ProveedorService.deleteProveedor(datos.id_proveedor);
                     navigate("/proveedores");
                  } else {
                     let rut_contacto = null;
                     if (datos.id_contacto != null) {
                        rut_contacto = datos.id_contacto.rut;
                     }
                     let rut_contacto2 = null;
                     if (datos.id_contacto2 != null) {
                        rut_contacto2 = datos.id_contacto2.rut;
                     }
                     let rut_contacto3 = null;
                     if (datos.id_contacto3 != null) {
                        rut_contacto3 = datos.id_contacto3.rut;
                     }
                     let updateProveedor = {
                        id_proveedor: datos.id_proveedor,
                        empresa: datos.empresa,
                        rubro: datos.rubro,
                        id_contacto: rut_contacto,
                        id_contacto2: rut_contacto2,
                        id_contacto3: rut_contacto3,
                        comentario: datos.comentario,
                     };
                     const datosComoTexto = JSON.stringify(updateProveedor);
                     navigate(`/proveedores/mas info/${encodeURIComponent(datosComoTexto)}`);
                  }
               },
            });
         }
      });
   };

   return (
      <NavStyle>
         <div>
            <div className="carrusel">
               <div
                  className="carrusel-contenido"
                  style={
                     largo === 3
                        ? { transform: `translateX(-${currentIndex * 33.3}%)`, width: "300%" }
                        : largo === 2
                        ? { transform: `translateX(-${currentIndex * 50}%)`, width: "200%" }
                        : { width: "100%" }
                  }
               >
                  {contactos.map((contacto, index) => {
                     if (contacto != null) {
                        return (
                           <div className="card" key={index}>
                              <div className="contenedor-img">
                                 <img id="clientes" src={clientes} alt="clientes" />
                              </div>
                              <div className="contenedor-informacion">
                                 <h2>{contacto.nombre}</h2>
                                 <h3>Empresa: {datos.empresa}</h3>
                                 <h3>Rubro: {datos.rubro}</h3>
                                 <h3>Rut: {contacto.rut}</h3>
                                 <h3>Correo: {contacto.correo}</h3>
                                 <h3>Telefono Celular: {contacto.fono_cel}</h3>
                                 <h3>Telefono Fijo: {contacto.fono_fijo}</h3>
                                 <h3>
                                    Comentario: <p></p> {datos.comentario}
                                 </h3>
                              </div>
                           </div>
                        );
                     } else {
                        return null;
                     }
                  })}
               </div>
               <button onClick={prevSlide} className="prev">
                  Anterior
               </button>
               <button onClick={nextSlide} className="next">
                  Siguiente
               </button>
            </div>
            <Button className="editar" onClick={changeMostrarCard}>
               Editar
            </Button>
            <Button className="eliminar" onClick={EliminarCliente}>
               Eliminar
            </Button>
         </div>
      </NavStyle>
   );
};

export default CarruselContactos;

const NavStyle = styled.nav`
   .carrusel {
      position: relative;
      width: 100%;
      overflow: hidden;
   }

   .carrusel-contenido {
      display: flex;
      transition: transform 0.5s ease-in-out;
   }

   /* Por el lado de la información del cliente*/

   .card {
      border: 1px solid black;
      border-radius: 10px;
      background-color: white;
      width: 100%;
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

   .prev,
   .next {
      position: absolute;
      top: 10%;
      transform: translateY(-50%);
      background-color: rgba(0, 0, 0, 0.5);
      color: white;
      border: none;
      padding: 10px;
      cursor: pointer;
   }

   .prev {
      left: 10px;
   }

   .next {
      right: 10px;
   }
`;
