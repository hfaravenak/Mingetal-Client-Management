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
        return contactoRepository.save(contacto);
    }

    // Read
    public List<ContactoEntity> findAllContactos() {
        return contactoRepository.findAll();
    }

    public ContactoEntity findContactoById(int id) {
        return contactoRepository.findById(id).orElse(null);
    }

    // Update
    public ContactoEntity updateContacto(ContactoEntity updatedContacto) {
        return contactoRepository.save(updatedContacto);
    }

    // Delete
    public void deleteContacto(int id) {
        contactoRepository.deleteById(id);
    }

}
