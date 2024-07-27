import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Swal from "sweetalert2";
import HeaderComponents from "../Headers/HeaderComponents";
import ProductoService from "../../services/ProductoService";
import atras from "../../images/atras.png";
import imagen from "../../images/MC_productos.png";

function CargaMasivaProductos() {
  const navigate = useNavigate();
  const [file, setFile] = useState(null);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleSubmitFile = async (event) => {
    event.preventDefault();
    if (file) {
      const formData = new FormData();
      formData.append('file', file);
  
      try {
        await ProductoService.uploadFile(formData);
        Swal.fire("Éxito", "Archivo cargado con éxito", "success");
      } catch (error) {
        console.error("Error al cargar el archivo", error);
      }
    } else {
      Swal.fire("Error", "Por favor seleccione un archivo para cargar", "error");
    }
  };

  const regresar = () => {
    navigate(`/productos`);
  };

  return (
    <div>
        <HeaderComponents></HeaderComponents>
        <Container>        
        <img id="atras" src={atras} alt="atras" className="img-back" onClick={regresar} />
        <div className="card">
            <div className="img-container">
            <img src={imagen} alt="MC_clientes" className="img-card" />
            <div className="overlay">Formato del Documento de Carga Masiva de Productos</div>
            </div>
        </div>
        <Form onSubmit={handleSubmitFile}>
            <Form.Group controlId="file">
            <Form.Label>Cargar Documento Excel (.xlsx):</Form.Label>
            <Form.Control type="file" accept=".xlsx" onChange={handleFileChange} />
            </Form.Group>
            {file && <p>Archivo seleccionado: {file.name}</p>}
            <Button type="submit" className="boton">Cargar Archivo</Button>
        </Form>
        </Container>
    </div>
  );
}

export default CargaMasivaProductos;

const Container = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 2%;
  padding: 1%;
  border: 2px solid #d5d5d5;
  background-color: #f0f0f0;
  border-radius: 15px;

  .img-back {
    position: absolute;
    top: 20px;
    left: 20px;
    width: 35px;
    cursor: pointer;
  }

  .card {
    text-align: center;
    padding: 20px;
  }

  .img-container {
    position: relative;
    display: inline-block;
  }

  img.img-card {
    max-width: 90%;
    height: auto;
    border-radius: 15px;
  }

  .overlay {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: white;
    font-size: 20px;
    font-weight: bold;
    background-color: rgba(0, 0, 0, 0.5);
    padding: 10px;
    border-radius: 15px;
  }

  form {
    width: 100%;
    max-width: 500px;
    margin: 20px auto;
    padding: 20px;
    border-radius: 15px;
    background-color: #fff;
  }

  label {
    font-size: 18px;
    font-weight: bold;
  }

  input[type="file"] {
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border-radius: 30px;
  }

  .boton {
    background-color: #d2712b;
    color: #fff;
    padding: 10px 20px;
    font-size: 16px;
    border-radius: 30px;
    border: none;
    cursor: pointer;
    display: block;
    width: 100%;
    margin-top: 10px;
  }
`;
