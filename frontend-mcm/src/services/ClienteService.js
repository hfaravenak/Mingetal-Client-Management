import axios from 'axios';

const CLIENTE_API_URL = "http://localhost:8080/cliente/";

class ClienteService {

    createCliente(cliente){
        return axios.post(CLIENTE_API_URL, cliente);
    }

    getClientes(){
        return axios.get(CLIENTE_API_URL);
    }

    getClienteByRut(rut){
        if(rut===""){
            return this.getClientes();
        }
        return axios.get(CLIENTE_API_URL + "rut/" + rut);
    }
    getClienteByNombre(nombre){
        if(nombre===""){
            return this.getClientes();
        }
        return axios.get(CLIENTE_API_URL + "nombre/" + nombre);
    }
    getClienteByNombreTextual(nombre){
        if(nombre===""){
            return this.getClientes();
        }
        return axios.get(CLIENTE_API_URL + "fullNombre/" + nombre);
    }
    getClienteByEmpresa(empresa){
        if(empresa===""){
            return this.getClientes();
        }
        return axios.get(CLIENTE_API_URL + "empresa/" + empresa);
    }

    putCliente(cliente){
        return axios.put(CLIENTE_API_URL +"update", cliente);
    }

    deleteCliente(rut){
        return axios.delete(CLIENTE_API_URL + "delete/"+rut);
    }

    getRankingCliente(){
        return axios.get(CLIENTE_API_URL+"ranking/");
    }

    
}

const clienteService = new ClienteService();
export default clienteService;