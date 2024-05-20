import axios from 'axios';

const PRODUCTOS_API_URL = "http://localhost:8080/productos/";

class ProductoService{

    getProductos(){
        return axios.get(PRODUCTOS_API_URL);
    }
}

export default new ProductoService()