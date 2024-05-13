import axios from 'axios';

const CLIENTE_API_URL = "http://localhost:8080/cliente/";

class ClienteService {

    getClientes(){
        return axios.get(CLIENTE_API_URL);
    }
}

export default new ClienteService()