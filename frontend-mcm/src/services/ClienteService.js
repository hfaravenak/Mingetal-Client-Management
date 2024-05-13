import axios from 'axios';

const CLIENTE_API_URL = "http://localhost:8080/cliente/";

class ClienteService {

    getClientes(){
        return axios.get(CLIENTE_API_URL);
    }

    getClienteByRut(rut){
        if(rut===""){
            return axios.get(CLIENTE_API_URL);
        }
        return axios.get(CLIENTE_API_URL + "rut/" + rut);
    }
    getClienteByNombre(nombre){
        if(nombre===""){
            return axios.get(CLIENTE_API_URL);
        }
        return axios.get(CLIENTE_API_URL + "nombre/" + nombre);
    }
    getClienteByEmpresa(empresa){
        if(empresa===""){
            return axios.get(CLIENTE_API_URL);
        }
        return axios.get(CLIENTE_API_URL + "empresa/" + empresa);
    }
}

export default new ClienteService()