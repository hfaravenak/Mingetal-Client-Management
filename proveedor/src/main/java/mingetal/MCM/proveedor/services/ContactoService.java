package mingetal.MCM.proveedor.services;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.repositories.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoService {
    @Autowired
    private ContactoRepository contactoRepository;

    // Create
    public ContactoEntity createContacto(ContactoEntity contacto) {
        if(findContactoById(contacto.getId_contacto()) != null){
            return null;
        }
        return contactoRepository.save(contacto);
    }

    // Read
    public List<ContactoEntity> findAllContactos() {
        return contactoRepository.findAll();
    }

    public ContactoEntity findContactoById(int id) {
        return contactoRepository.findById(id);
    }

    // find by nombre del contacto
    public List<ContactoEntity> findContactoByNombre(String nombre) {
        return contactoRepository.findByNombreContacto(nombre);
    }      

    // Update
    public ContactoEntity updateContacto(ContactoEntity updatedContacto) {
        if(findContactoById(updatedContacto.getId_contacto())==null){
            return null;
        }
        return contactoRepository.save(updatedContacto);
    }

    // Delete
    public ContactoEntity deleteContacto(int id) {
        if(findContactoById(id)==null){
            return null;
        }
        ContactoEntity contacto= findContactoById(id);
        contactoRepository.deleteById(id);
        return contacto;
    }

}
