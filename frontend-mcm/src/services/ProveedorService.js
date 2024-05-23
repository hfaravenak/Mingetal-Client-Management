import axios from 'axios';

const PROVEEDOR_API_URL = "http://localhost:8080/proveedor/";

class ProveedorService {

    createProveedor(newProveedor){
        console.log(newProveedor);
        return axios.post(PROVEEDOR_API_URL, newProveedor);
    }

    getProveedores(){
        return axios.get(PROVEEDOR_API_URL);
    }
    getProveedorByListOC(){
        return axios.get(PROVEEDOR_API_URL+"listOC/");
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
    getProveedorByNombre(nombre){
        if(nombre===""){
            return this.getProveedores();
        }
        return axios.get(PROVEEDOR_API_URL+"nombre/"+nombre);
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
    getRubroProveedores(){
        return axios.get(PROVEEDOR_API_URL+"rubro/");
    }
    getProveedorByRubro(rubro){
        if(rubro===""){
            return this.getProveedores();
        }
        return axios.get(PROVEEDOR_API_URL+"rubro/"+rubro);
    }

    putProveedor(proveedor){
        return axios.put(PROVEEDOR_API_URL +"update/", proveedor);
    }

    deleteProveedor(id){
        return axios.delete(PROVEEDOR_API_URL + "delete/"+id);
    }
}

export default new ProveedorService()