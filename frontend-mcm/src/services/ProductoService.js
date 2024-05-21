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

    getClienteByNombreTextual(nombre){
        if(nombre===""){
            return this.getClientes();
        }
        return axios.get(PRODUCTOS_API_URL + "fullNombre/" + nombre);
    }
    
    getProductoByTipo(tipo){
        if(tipo===""){
            return this.getProductos();
        }
        return axios.get(PRODUCTOS_API_URL + "tipo/" + tipo);
    }
     
    crearProducto(producto){
        console.log(producto);
        return axios.post(PRODUCTOS_API_URL+"guardar-producto", producto);
    }
}

export default new ProductoService()