import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination, A11y } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';

import OrdenesDeCompraClienteService from "../../services/OrdenesDeCompraClienteService";
import ClienteService from "../../services/ClienteService";

function AlertaPagoComponents() {
    const [OCClienteEntity, setOCClienteEntity] = useState([]);
    const [ClienteEntity, setClienteEntity] = useState([]);
    useEffect(() => {
        OrdenesDeCompraClienteService.getOCClienteNoPagado().then((res) => {
            setOCClienteEntity(res.data);
        });
        ClienteService.getClientes().then((res) => {
            setClienteEntity(res.data);
        });
    }, []);
    const busquedaCliente = (rut) => {
        let variable = "";
        ClienteEntity.forEach((cliente) => {
            if (cliente.rut === rut) {
                variable = cliente;
            }
        });
        return variable.nombre;
    };

    const diferencia = (oc) => {
        const fechaInicial = new Date();
        const fechaFinal = new Date(oc.fecha_pago);
        
        // Obtener la diferencia en milisegundos
        const diferenciaMilisegundos = fechaFinal - fechaInicial;
        
        // Convertir la diferencia a días
        const milisegundosPorDia = 1000 * 60 * 60 * 24;
        const diferenciaDias = diferenciaMilisegundos / milisegundosPorDia;
        
        const diferencia = Math.floor(diferenciaDias);
        if (diferencia<= 0) {
            return 0;
        }
        else if (diferencia<=5) {
            return 1;
        }
        else if (diferencia<=10) {
            return 2;
        }
        else if(diferencia<=15){
            return 3;
        }
    }
    const diferenciaValor = (oc) => {
        const fechaInicial = new Date();
        const fechaFinal = new Date(oc.fecha_pago);
        
        // Obtener la diferencia en milisegundos
        const diferenciaMilisegundos = fechaFinal - fechaInicial;
        
        // Convertir la diferencia a días
        const milisegundosPorDia = 1000 * 60 * 60 * 24;
        const diferenciaDias = diferenciaMilisegundos / milisegundosPorDia;
        
        return Math.floor(diferenciaDias);
    }

    const [expandedId, setExpandedId] = useState({});
    const handleIdClick = (id) => {
        setExpandedId((prevExpandedId) => ({
            ...prevExpandedId,
            [id]: !prevExpandedId[id],
        }));
    };
    return (
        <div>
            <NavStyle>
                <Swiper
                    modules={[Navigation, Pagination, A11y]}
                    slidesPerView={1}
                    navigation
                    pagination={{ clickable: true, type: 'fraction' }}
                    loop={true}
                    style={{ marginLeft: "2%", marginRight: "2%" }}
                >
                    {OCClienteEntity.length > 0 ? (
                        OCClienteEntity.map((oc) => (
                            <SwiperSlide key={oc.id}>
                                <div onClick={() => handleIdClick(oc.id_cliente)} className='alerta' style={
                                    diferencia(oc) === 0 ? { background: "#920000", border: "2px solid #5E0005", color: "#FFE6D9" } :
                                    diferencia(oc) === 1 ? { background: "#E24747", border: "2px solid #840024", color: "#FFE4DE" } :
                                    diferencia(oc) === 2 ? { background: "#E28247", border: "2px solid #AC3141", color: "#54433A" } :
                                    { background: "#FFC848", border: "2px solid #AC3141", color: "#4E4637" }}>
                                    <div> {expandedId[oc.id_cliente] ? "-" : "+"} El cliente <b>{busquedaCliente(oc.id_cliente)}</b> debe pagar la orden de compra <b>{oc.id}</b> </div>
                                    {expandedId[oc.id_cliente]&&(
                                        <div className="expandible">
                                            {diferenciaValor(oc)<0?`Tiene un atraso de ${diferenciaValor(oc)*-1} días en el pago`:
                                            diferenciaValor(oc)>0?`Le quedan ${diferenciaValor(oc)} días para pagar`:
                                            "Debe pagar hoy"}
                                            
                                        </div>
                                    )}
                                </div>
                            </SwiperSlide>
                        ))
                    ) : (
                        <SwiperSlide>
                        </SwiperSlide>
                    )}
                </Swiper>
            </NavStyle>
        </div>
    )
}

export default AlertaPagoComponents;

const NavStyle = styled.nav`
.alerta{
    display: grid
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    padding: 20px;
    margin-top: 1%;
    margin-bottom: 30px;
    margin-left: 4%;
    margin-right: 4%;
    font-size: 30px;
    border-radius: 30px;    
}
    
.alerta .expandible{
    background-color: #f0f0f0;
    border: 2px solid black;
    color: black;
    padding: 1%;
    margin: 1% 1% 0px 1%;
    border-radius: 30px;
}
.swiper-button-next,
  .swiper-button-prev {
    color: #0059AA; // Cambia este color según tus necesidades
  }
`