import axios from 'axios';

const OC_API_URL = "http://localhost:8080/ordenes_de_compra/";

class OrdenesDeCompraService {

    getOCByCliente(rut){
        return axios.get(OC_API_URL+"cliente/id/"+rut);
    }

    
}

export default new OrdenesDeCompraService()