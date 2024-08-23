import axios from 'axios';

const OC_API_URL = "http://147.182.163.81:8080/ordenes_de_compra/cliente/";

class OrdenesDeCompraClienteService {

    createOCCliente(newOC){
        return axios.post(OC_API_URL, newOC);
    }
    createOCListProveedor(newListP){
        return axios.post(OC_API_URL+"productos/", newListP);
    }   

    getRankingCliente(){
        return axios.get(OC_API_URL+"clientsbyyear");
    }

    getOCCliente(){
        return axios.get(OC_API_URL);
    }
    getOCClientePagado(){
        return axios.get(OC_API_URL+"pagado/");
    }
    getOCClienteNoPagado(){
        return axios.get(OC_API_URL+"no_pagado/");
    }
    getOCListProductosCliente(){
        return axios.get(OC_API_URL+"productos/");
    }
    getOCClienteById(id){
        if(id===""){
            return this.getOCCliente()
        }
        return axios.get(OC_API_URL+id);
    }
    getOCByCliente(rut){
        return axios.get(OC_API_URL+"id/"+rut);
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

    deleteOCCliente(id){
        return axios.delete(OC_API_URL+"delete/"+id);
    }

    putOCCliente(OCCliente){
        return axios.put(OC_API_URL+"update", OCCliente);
    } 
    uploadFile(formData) {
        const uploadUrl = OC_API_URL + "upload"; // Asegúrate de cambiar "upload" por el endpoint correcto del backend
        return axios.post(uploadUrl, formData, {
            headers: {
                'Content-Type': 'multipart/form-data' // Esto es opcional, axios lo establecerá automáticamente
            }
        });
    }
}
const ordenesDeCompraClienteService = new OrdenesDeCompraClienteService();
export default ordenesDeCompraClienteService;