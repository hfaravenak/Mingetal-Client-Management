import axios from 'axios';

const PROVEEDOR_API_URL = "http://localhost:8080/proveedor/";

class ProveedorService {
    getProveedorByListOC(){
        return axios.get(PROVEEDOR_API_URL+"listOC/");
    }

    getContacto1ByListOC(){
        return axios.get(PROVEEDOR_API_URL+"contactos/listProv/");
    }

    getDespacho(){
        return axios.get(PROVEEDOR_API_URL+"despacho/");
    }
    
    getProveedorByNombreTextual(nombre){
        return axios.get(PROVEEDOR_API_URL+"fullNombre/"+nombre);
    }
}

export default new ProveedorService()