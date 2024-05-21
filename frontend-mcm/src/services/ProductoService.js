import axios from 'axios';

const PRODUCTOS_API_URL = "http://localhost:8080/productos/";

class ProductoService{

    getProductos(){
        return axios.get(PRODUCTOS_API_URL);
    }
    getProductoByNombre(nombre){
        if(nombre===""){
            return this.getClientes();
        }
        return axios.get(PRODUCTOS_API_URL + "nombre/" + nombre);
    }

    getProductoByTipo(tipo){
        if(tipo===""){
            return this.getProductos();
        }
        return axios.get(PRODUCTOS_API_URL + "tipo/" + tipo);
    }
}

export default new ProductoService()