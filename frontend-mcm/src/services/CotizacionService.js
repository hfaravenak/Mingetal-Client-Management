import axios from 'axios';

const COTIZACION_API_URL = "http://localhost:8080/cliente/cotizacion/";

class CotizacionService {

    createCotizacion(cotizacion){
        return axios.post(COTIZACION_API_URL+"guardar-cotizacion", cotizacion);
    }
    createListProductos(newListP){
        console.log(newListP)
        return axios.post(COTIZACION_API_URL+"productos/", newListP);
    }

    getCotizaciones(){
        return axios.get(COTIZACION_API_URL);
    }
    getCotizacionesById(idCotizacion){
        if(idCotizacion===""){
            return this.getCotizaciones();
        }
        return axios.get(COTIZACION_API_URL + idCotizacion);
    }
    getCotizacionByPedido(pedido){
        if(pedido===""){
            return this.getCotizaciones();
        }
        return axios.get(COTIZACION_API_URL + "pedido/" + pedido);
    }
    getCotizacionByFecha(fecha){
        if(fecha===""){
            return this.getCotizaciones();
        }
        return axios.get(COTIZACION_API_URL + "fecha/" + fecha);
    }
    getCotizacionByEstado(estado){
        if(estado===""){
            return this.getCotizaciones();
        }
        return axios.get(COTIZACION_API_URL + "estado/" + estado);
    }
    getCotizacionByRutCliente(rutCliente){
        if(rutCliente===""){
            return this.getCotizaciones();
        }
        return axios.get(COTIZACION_API_URL + "rut/" + rutCliente);
    }

    putCotizacion(cotizacion){
        return axios.put(COTIZACION_API_URL +"update", cotizacion);
    }

    deleteCotizacion(idCotizacion){
        return axios.delete(COTIZACION_API_URL + "delete/"+idCotizacion);
    }

    
}

const cotizacionService = new CotizacionService();
export default cotizacionService;