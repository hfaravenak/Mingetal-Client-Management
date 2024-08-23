import axios from 'axios';

const CONTACTO_API_URL = "http://147.182.163.81:8080/proveedor/contactos/";

class ContactoService {
    createContacto(contacto){
        return axios.post(CONTACTO_API_URL, contacto);
    }

    getContactos(){
        return axios.get(CONTACTO_API_URL);
    }

    updateContacto(contacto){
        return axios.put(CONTACTO_API_URL +"update/", contacto);
    }

    deleteContacto(id){
        return axios.delete(CONTACTO_API_URL + "delete/"+id);
    }
}
const contactoService = new ContactoService();
export default contactoService;