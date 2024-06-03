import axios from 'axios';

const OC_API_URL = "http://localhost:8080/ordenes_de_compra/proveedor/";

class OrdenesDeCompraProveedorService {

    createOCProveedor(newOC){
        return axios.post(OC_API_URL, newOC);
    }
    createOCListProveedor(newListP){
        return axios.post(OC_API_URL+"productos/", newListP);
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
    getOCByProveedor(id_proveedor){
        if(id_proveedor===""){
            return this.getOCProveedor()
        }
        return axios.get(OC_API_URL+"id/"+id_proveedor);
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

    deleteOCProveedor(id){
        return axios.delete(OC_API_URL+"delete/"+id);
    }

    putOCProveedor(OCProveedor){
        return axios.put(OC_API_URL+"update", OCProveedor);
    }  
}
const ordenesDeCompraProveedorService = new OrdenesDeCompraProveedorService();
export default ordenesDeCompraProveedorService;