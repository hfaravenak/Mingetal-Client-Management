import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";

import clientes from "../../images/cliente.png";

import ContactoService from "../../services/ContactoService";
import ProveedorService from "../../services/ProveedorService";

const CarruselContactosEditar = ({ datos, onMostrarCard }) => {
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

   const initialState = {
      empresa: datos.empresa,
      rubro: datos.rubro,
      contactos: [datos.id_contacto, datos.id_contacto2, datos.id_contacto3],
      comentario: datos.comentario,
   };
   const [input, setInput] = useState(initialState);

   const [currentIndex, setCurrentIndex] = useState(0);

   const handleInputChange = (event, index = null) => {
      const { name, value } = event.target;

      if (index !== null) {
         setInput((prevInput) => {
            const updatedContactos = [...prevInput.contactos];
            updatedContactos[index] = {
               ...updatedContactos[index],
               [name]: value,
            };
            return {
               ...prevInput,
               contactos: updatedContactos,
            };
         });
      } else {
         setInput((prevInput) => ({
            ...prevInput,
            [name]: value,
         }));
      }
   };

   const CancelarEdit = () => {
      onMostrarCard(false);
   };
   const handleSubmit = (event) => {
      event.preventDefault(); // Previene el comportamiento predeterminado del formulario
      enviarDatos(); // Llama a la función para procesar el formulario
   };
   const enviarDatos = () => {
      Swal.fire({
         title: "¿Seguro de modificar este usuario?",
         icon: "question",
         showDenyButton: true,
         confirmButtonText: "Confirmar",
         confirmButtonColor: "rgb(68, 194, 68)",
         denyButtonText: "Cancelar",
         denyButtonColor: "rgb(190, 54, 54)",
      }).then((result) => {
         if (result.isConfirmed) {
            let updateAll = {
               id_proveedor: datos.id_proveedor,
               empresa: input.empresa,
               rubro: input.rubro,
               comentario: input.comentario,
            };
            let updateProveedor = {
               id_proveedor: datos.id_proveedor,
               empresa: input.empresa,
               rubro: input.rubro,
               comentario: input.comentario,
            };
            if (datos.id_contacto != null) {
               let updateContacto1 = {
                  rut: datos.id_contacto.rut,
                  nombre: input.contactos[0].nombre,
                  correo: input.contactos[0].correo,
                  fono_cel: input.contactos[0].fono_cel,
                  fono_fijo: input.contactos[0].fono_fijo,
               };
               updateAll.id_contacto = updateContacto1;
               updateProveedor.id_contacto = datos.id_contacto.rut;
               ContactoService.updateContacto(updateContacto1);
            } else {
               updateAll.id_contacto = null;
               updateProveedor.id_contacto = null;
            }
            if (datos.id_contacto2 != null) {
               let updateContacto2 = {
                  rut: datos.id_contacto2.rut,
                  nombre: input.contactos[1].nombre,
                  correo: input.contactos[1].correo,
                  fono_cel: input.contactos[1].fono_cel,
                  fono_fijo: input.contactos[1].fono_fijo,
               };
               updateAll.id_contacto2 = updateContacto2;
               updateProveedor.id_contacto2 = datos.id_contacto2.rut;
               ContactoService.updateContacto(updateContacto2);
            } else {
               updateAll.id_contacto2 = null;
               updateProveedor.id_contacto2 = null;
            }
            if (datos.id_contacto3 != null) {
               let updateContacto3 = {
                  rut: datos.id_contacto3.rut,
                  nombre: input.contactos[2].nombre,
                  correo: input.contactos[2].correo,
                  fono_cel: input.contactos[2].fono_cel,
                  fono_fijo: input.contactos[2].fono_fijo,
               };
               updateAll.id_contacto3 = updateContacto3;
               updateProveedor.id_contacto3 = datos.id_contacto3.rut;
               ContactoService.updateContacto(updateContacto3);
            } else {
               updateAll.id_contacto3 = null;
               updateProveedor.id_contacto3 = null;
            }
            ProveedorService.updateProveedor(updateProveedor);
            navigate(`/proveedores/mas info/${encodeURIComponent(JSON.stringify(updateAll))}`);
            onMostrarCard(false);
         }
      });
   };

   return (
      <NavStyle>
         <Form onSubmit={handleSubmit}>
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
                                 <Form.Group controlId="contactos">
                                    <Form.Control type="text" name="nombre" value={input.contactos[index].nombre} onChange={(event) => handleInputChange(event, index)} className="font-h3 no-border" />
                                 </Form.Group>
                                 <Form.Group controlId="empresa" style={{ marginTop: "2.2%" }}>
                                    <Form.Label className="font-h2">Empresa:</Form.Label>
                                    <Form.Control type="text" name="empresa" value={input.empresa} onChange={handleInputChange} className="font-h2-control no-border" />
                                 </Form.Group>
                                 <Form.Group controlId="empresa">
                                    <Form.Label className="font-h2">Rubro:</Form.Label>
                                    <Form.Control type="text" name="rubro" value={input.rubro} onChange={handleInputChange} className="font-h2-control no-border" />
                                 </Form.Group>
                                 <h3 style={{ color: "gray", marginTop: "2%", marginBottom: "2%" }}>Rut: {input.contactos[index].rut}</h3>
                                 <Form.Group controlId="correo">
                                    <Form.Label className="font-h2 eliminarMargen">Correo:</Form.Label>
                                    <Form.Control
                                       type="text"
                                       name="correo"
                                       value={input.contactos[index].correo}
                                       onChange={(event) => handleInputChange(event, index)}
                                       placeholder="abcedg@correo.com"
                                       className="font-h2-control no-border"
                                    />
                                 </Form.Group>
                                 <Form.Group controlId="telefono_celular" style={{ marginTop: "2.2%" }}>
                                    <Form.Label className="font-h2">Telefono Celular:</Form.Label>
                                    <Form.Control
                                       type="text"
                                       name="fono_cel"
                                       value={input.contactos[index].fono_cel}
                                       onChange={(event) => handleInputChange(event, index)}
                                       placeholder="+569 12345678"
                                       className="font-h2-control no-border"
                                       style={{ width: "150px" }}
                                    />
                                 </Form.Group>
                                 <Form.Group controlId="telefono_fijo">
                                    <Form.Label className="font-h2">Telefono Fijo:</Form.Label>
                                    <Form.Control
                                       type="text"
                                       name="fono_fijo"
                                       value={input.contactos[index].fono_fijo}
                                       onChange={(event) => handleInputChange(event, index)}
                                       placeholder="22 12345678"
                                       className="font-h2-control no-border"
                                       style={{ width: "150px" }}
                                    />
                                 </Form.Group>
                                 <Form.Group controlId="comentario" style={{ marginBottom: "2.2%" }}>
                                    <Form.Label className="font-h2">Comentario:</Form.Label>
                                    <p></p>
                                    <Form.Control
                                       rows={3}
                                       as="textarea"
                                       name="comentario"
                                       value={input.comentario}
                                       onChange={handleInputChange}
                                       className="font-h2-control no-border"
                                       style={{ width: "95%", marginLeft: "4%" }}
                                    />
                                 </Form.Group>
                              </div>
                           </div>
                        );
                     } else {
                        return null;
                     }
                  })}
               </div>
               <Button onClick={prevSlide} className="prev">
                  Anterior
               </Button>
               <Button onClick={nextSlide} className="next">
                  Siguiente
               </Button>
            </div>
            <Button className="aceptar" type="submit">
               Aceptar
            </Button>
            <Button className="cancelar" onClick={CancelarEdit}>
               Cancelar
            </Button>
         </Form>
      </NavStyle>
   );
};

export default CarruselContactosEditar;

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
