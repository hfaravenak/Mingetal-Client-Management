import axios from 'axios';

const CLIENTE_API_URL = "http://147.182.163.81:8080/cliente/";

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

    uploadFile(formData) {
        const uploadUrl = CLIENTE_API_URL + "upload"; // Asegúrate de cambiar "upload" por el endpoint correcto del backend
        return axios.post(uploadUrl, formData, {
            headers: {
                'Content-Type': 'multipart/form-data' // Esto es opcional, axios lo establecerá automáticamente
            }
        });
    }
    
}

const clienteService = new ClienteService();
export default clienteService;
