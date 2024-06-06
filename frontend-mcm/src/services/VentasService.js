import axios from 'axios';

const VENTAS_GENERALES_API_URL = "http://localhost:8080/ordenes_de_compra/cliente/";

class VentasService{

    crearProducto(producto){
        return axios.post(VENTAS_GENERALES_API_URL, producto);
    }

    getVentasByYear(){
        return axios.get(VENTAS_GENERALES_API_URL + "salesbyyear");
    }
    getVentasByMonth(){
        return axios.get(VENTAS_GENERALES_API_URL + "salesbyyearandmonth");
    }
    getProductsByYear(){
        return axios.get(VENTAS_GENERALES_API_URL + "productsbyyear");
    }
    getProductsByYearAndMonth(){
        return axios.get(VENTAS_GENERALES_API_URL + "productsbyyearandmonth");
    }
}
const ventasService = new VentasService();
export default ventasService;