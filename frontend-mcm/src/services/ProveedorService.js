import axios from 'axios';

const PROVEEDOR_API_URL = "http://localhost:8080/proveedor/";

class ProveedorService {
    getProveedores(){
        return axios.get(PROVEEDOR_API_URL);
    }

    getProveedorByListOC(){
        return axios.get(PROVEEDOR_API_URL+"listOC/");
    }

    getContacto1ByListOC(){
        return axios.get(PROVEEDOR_API_URL+"contactos/listProv/");
    }

    getContacto1ByProveedores(){
        return axios.get(PROVEEDOR_API_URL+"contactos/proveedores");
    }

    getDespacho(){
        return axios.get(PROVEEDOR_API_URL+"despacho/");
    }
    
    getProveedorByNombreTextual(nombre){
        return axios.get(PROVEEDOR_API_URL+"fullNombre/"+nombre);
    }
}

export default new ProveedorService()