import axios from 'axios';

const CLIENTE_API_URL = "http://localhost:8080/cliente/";

class ClienteService {

    createCliente(cliente){
        console.log(cliente);
        return axios.post(CLIENTE_API_URL+"guardar-cliente", cliente);
    }

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

    putCliente(cliente){
        return axios.put(CLIENTE_API_URL +"update", cliente);
    }

    deleteCliente(rut){
        console.log("la entrada del serivce: "+rut);
        return axios.delete(CLIENTE_API_URL + "delete-rut/"+rut);
    }

    
}

export default new ClienteService()