import axios from 'axios';

const PRODUCTOS_API_URL = "http://147.182.163.81:8080/productos/";

class ProductoService{

    crearProducto(producto){
        return axios.post(PRODUCTOS_API_URL, producto, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
    }

    getProductos(){
        return axios.get(PRODUCTOS_API_URL);
    }
    getProductoByTipo(tipo){
        if(tipo===""){
            return this.getProductos();
        }
        return axios.get(PRODUCTOS_API_URL + "tipo/" + tipo);
    }
    getProductoByNombre(nombre){
        if(nombre===""){
            return this.getClientes();
        }
        return axios.get(PRODUCTOS_API_URL + "nombre/" + nombre);
    }
    getProductosByNombreTextual(nombre){
        if(nombre===""){
            return this.getClientes();
        }
        return axios.get(PRODUCTOS_API_URL + "fullNombre/" + nombre);
    }
    getListByOCCliente(id){
        return axios.get(PRODUCTOS_API_URL+"OCCliente/"+id);
    }
    getListByOCProveedor(id){
        return axios.get(PRODUCTOS_API_URL+"OCProveedor/"+id);
    }
    getListByCotizacion(id){
        return axios.get(PRODUCTOS_API_URL+"cotizacion/"+id);
    }
    getPocoProductos(){
        return axios.get(PRODUCTOS_API_URL+"pocos_productos/");
    }
    updateProducto(producto){
        return axios.put(PRODUCTOS_API_URL+"update", producto, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
    }
    uploadFile(formData) {
        const uploadUrl = PRODUCTOS_API_URL + "upload"; // Asegúrate de cambiar "upload" por el endpoint correcto del backend
        return axios.post(uploadUrl, formData, {
            headers: {
                'Content-Type': 'multipart/form-data' // Esto es opcional, axios lo establecerá automáticamente
            }
        });
    }
}
const productoService = new ProductoService();
export default productoService;