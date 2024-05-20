import axios from 'axios';

const OC_API_URL = "http://localhost:8080/ordenes_de_compra/cliente/";

class OrdenesDeCompraClienteService {

    createOCCliente(newOC){
        return axios.post(OC_API_URL, newOC);
    }

    getOCByCliente(rut){
        return axios.get(OC_API_URL+"id/"+rut);
    }

    getOCCliente(){
        return axios.get(OC_API_URL);
    }
    getOCClienteById(id){
        if(id===""){
            return this.getOCCliente()
        }
        return axios.get(OC_API_URL+id);
    }
    getOCClienteByNombre(nombre){
        if(nombre===""){
            return this.getOCCliente()
        }
        return axios.get(OC_API_URL+"nombre/"+nombre);
    }

    getOCClienteByEmpresa(empresa){
        if(empresa===""){
            return this.getOCCliente()
        }
        return axios.get(OC_API_URL+"empresa/"+empresa);
    }
    putOCCliente(OCCliente){
        return axios.put(OC_API_URL+"update", OCCliente);
    }

    deleteOCCliente(id){
        return axios.delete(OC_API_URL+"delete/"+id);
    }
    
}

export default new OrdenesDeCompraClienteService()