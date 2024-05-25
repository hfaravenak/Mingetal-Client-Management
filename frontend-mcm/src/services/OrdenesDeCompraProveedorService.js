import axios from 'axios';

const OC_API_URL = "http://localhost:8080/ordenes_de_compra/proveedor/";

class OrdenesDeCompraProveedorService {
    createOCProveedor(newOC){
        return axios.post(OC_API_URL, newOC);
    }
    getOCProveedor(){
        return axios.get(OC_API_URL);
    }
    getOCProveedorById(id){
        if(id===""){
            return this.getOCProveedor()
        }
        return axios.get(OC_API_URL+id);
    }
    getOCProveedorByNombre(nombre){
        if(nombre===""){
            return this.getOCProveedor()
        }
        return axios.get(OC_API_URL+"nombre/"+nombre);
    }

    getOCProveedorByEmpresa(empresa){
        if(empresa===""){
            return this.getOCProveedor()
        }
        return axios.get(OC_API_URL+"empresa/"+empresa);
    }
    getOCByProveedor(id_proveedor){
        if(id_proveedor===""){
            return this.getOCProveedor()
        }
        return axios.get(OC_API_URL+"id/"+id_proveedor);
    }

    putOCProveedor(OCProveedor){
        return axios.put(OC_API_URL+"update", OCProveedor);
    }

    deleteOCProveedor(id){
        return axios.delete(OC_API_URL+"delete/"+id);
    }
    
}

export default new OrdenesDeCompraProveedorService()