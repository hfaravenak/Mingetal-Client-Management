import axios from 'axios';

const PROVEEDOR_API_URL = "http://localhost:8080/proveedor/";

class ProveedorService {

    createProveedor(newProveedor){
        return axios.post(PROVEEDOR_API_URL, newProveedor);
    }

    getProveedores(){
        return axios.get(PROVEEDOR_API_URL);
    }
    getProveedorByListOC(){
        return axios.get(PROVEEDOR_API_URL+"listOC/");
    }
    getProveedorByEmpresa(empresa){
        if(empresa===""){
            return this.getProveedores();
        }
        return axios.get(PROVEEDOR_API_URL+"empresa/"+empresa);
    }
    getProveedorByRut(rut){
        if(rut===""){
            return this.getProveedores();
        }
        return axios.get(PROVEEDOR_API_URL+"rut/"+rut);
    }
    getProveedorByNombre(nombre){
        if(nombre===""){
            return this.getProveedores();
        }
        return axios.get(PROVEEDOR_API_URL+"nombre/"+nombre);
    }
    getProveedorByRubro(rubro){
        if(rubro===""){
            return this.getProveedores();
        }
        return axios.get(PROVEEDOR_API_URL+"rubro/"+rubro);
    }
    getRubroProveedores(){
        return axios.get(PROVEEDOR_API_URL+"rubro/");
    }
    getDespacho(){
        return axios.get(PROVEEDOR_API_URL+"despacho/");
    }
    getProveedorByNombreTextual(nombre){
        if(nombre===""){
            return this.getClientes();
        }
        return axios.get(PROVEEDOR_API_URL+"fullNombre/"+nombre);
    }
    
    deleteProveedor(id){
        return axios.delete(PROVEEDOR_API_URL + "delete/"+id);
    }

    updateProveedor(proveedor){
        return axios.put(PROVEEDOR_API_URL +"update/", proveedor);
    }

    
}
const proveedorService = new ProveedorService();
export default proveedorService;