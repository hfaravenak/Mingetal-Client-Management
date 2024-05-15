import axios from 'axios';

const OC_API_URL = "http://localhost:8080/ordenes_de_compra/";

class OrdenesDeCompraService {

    getOCByCliente(rut){
        return axios.get(OC_API_URL+"cliente/id/"+rut);
    }

    getOCCliente(){
        return axios.get(OC_API_URL+"cliente/");
    }
    getOCClienteById(id){
        return axios.get(OC_API_URL+"cliente/"+id);
    }

    
}

export default new OrdenesDeCompraService()