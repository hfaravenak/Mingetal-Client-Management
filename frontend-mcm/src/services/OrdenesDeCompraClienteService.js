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
        if(id===""){
            return this.getOCCliente()
        }
        return axios.get(OC_API_URL+"cliente/"+id);
    }
    getOCClienteByNombre(nombre){
        if(nombre===""){
            return this.getOCCliente()
        }
        return axios.get(OC_API_URL+"cliente/nombre/"+nombre);
    }

    getOCClienteByEmpresa(empresa){
        if(empresa===""){
            return this.getOCCliente()
        }
        return axios.get(OC_API_URL+"cliente/empresa/"+empresa);
    }
    putOCCliente(OCCliente){
        return axios.put(OC_API_URL+"cliente/update", OCCliente)
    }
    
}

export default new OrdenesDeCompraService()