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
        const dia = new Date().getDate();
        const mes = new Date().getMonth() + 1;
        const anio = new Date().getFullYear();

        const [year, month, day] = oc.fecha_pago.split("-");
        console.log(day, month, year)
        if (year < anio) {
            console.log("1y");
            return 0;
        }
        if (month < mes) {
            console.log("1m");
            return 0;
        }
        if (day < dia) {
            console.log("1d");
            return 0;
        }
        else{
            if (day - dia < 5) {
                console.log("1d");
                return 1;
            } else if (day - dia < 10) {
                console.log("2d");
                return 2;
            } else if (day - dia < 15) {
                console.log("3d");
                return 3;
            }
        }
    }

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
                                <div className='alerta' style={
                                    diferencia(oc) === 0 ? { background: "#920000", border: "2px solid #5E0005", color: "#FFE6D9" } :
                                    diferencia(oc) === 1 ? { background: "#E24747", border: "2px solid #840024", color: "#FFE4DE" } :
                                    diferencia(oc) === 2 ? { background: "#E28247", border: "2px solid #AC3141", color: "#54433A" } :
                                    { background: "#FFC848", border: "2px solid #AC3141", color: "#4E4637" }}>
                                    <div>El cliente <b>{busquedaCliente(oc.id_cliente)}</b> debe pagar la orden de compra <b>{oc.id}</b> </div>
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
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    padding: 20px;
    margin-top: 2%;
    margin-bottom: 2%;
    margin-left: 4%;
    margin-right: 4%;
    font-size: 30px;}
    
.swiper-button-next,
  .swiper-button-prev {
    color: #0059AA; // Cambia este color según tus necesidades
  }
`