package mingetal.MCM.proveedor.services;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.entities.ProveedorEntity;
import mingetal.MCM.proveedor.repositories.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactoService {
    @Autowired
    private ContactoRepository contactoRepository;

    // Create
    public ContactoEntity createContacto(ContactoEntity contacto) {
        if(findContactoById(contacto.getRut()) != null){
            return null;
        }
        return contactoRepository.save(contacto);
    }

    // Read
    public List<ContactoEntity> findAllContactos() {
        return contactoRepository.findAll();
    }

    public ContactoEntity findContactoById(String id) {
        return contactoRepository.findById(id);
    }

    // find by nombre del contacto
    public ContactoEntity findContactoByNombre(String nombre) {
        return contactoRepository.findByNombreContacto(nombre);
    }

    public List<ContactoEntity> findByContactosFromEmpresa(String empresa) {
        ProveedorService proveedorService = new ProveedorService();
        ProveedorEntity proveedorEntity = proveedorService.findByEmpresa(empresa);
        List<ContactoEntity> contactoEntities = new ArrayList<>();
        contactoEntities.add(findContactoById(proveedorEntity.getId_contacto()));
        contactoEntities.add(findContactoById(proveedorEntity.getId_contacto2()));
        contactoEntities.add(findContactoById(proveedorEntity.getId_contacto3()));
        return contactoEntities;
    }

    // Update
    public ContactoEntity updateContacto(ContactoEntity updatedContacto) {
        if(findContactoById(updatedContacto.getRut())==null){
            return null;
        }
        return contactoRepository.save(updatedContacto);
    }

    // Delete
    public ContactoEntity deleteContacto(String id) {
        if(findContactoById(id)==null){
            return null;
        }
        ContactoEntity contacto= findContactoById(id);
        contactoRepository.delete(contacto);
        return contacto;
    }

}
