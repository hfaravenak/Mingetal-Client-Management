import axios from 'axios';

const CONTACTO_API_URL = "http://localhost:8080/proveedor/contactos/";

class ContactoService {
    getContacto1ByListOC(){
        return axios.get(CONTACTO_API_URL+"listProv/");
    }

    getContactos(){
        return axios.get(CONTACTO_API_URL);
    }

    putContacto(contacto){
        return axios.put(CONTACTO_API_URL +"update/", contacto);
    }

    deleteContacto(id){
        return axios.delete(CONTACTO_API_URL + "delete/"+id);
    }
}

export default new ContactoService()