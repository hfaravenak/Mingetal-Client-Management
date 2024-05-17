package mingetal.MCM.proveedor.services;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.entities.ProveedorEntity;
import mingetal.MCM.proveedor.model.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.proveedor.repositories.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactoService {
    @Autowired
    private ContactoRepository contactoRepository;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    RestTemplate restTemplate;

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

    public List<ContactoEntity> findByProveedorContacto1(){
        List<ProveedorEntity> proveedorEntities = proveedorService.findByListOC();

        List<ContactoEntity> contactoEntities = new ArrayList<>();
        for(ProveedorEntity proveedor:proveedorEntities){
            contactoEntities.add(findContactoById(proveedor.getId_contacto()));
        }
        return contactoEntities;
    }

    public ContactoEntity findContactoById(String id) {
        return contactoRepository.findById(id);
    }

    // find by nombre del contacto
    public List<ContactoEntity> findContactoByNombre(String nombre) {
        List<ContactoEntity> contactoEntities = findAllContactos();
        List<ContactoEntity> resultados = new ArrayList<>();
        for (ContactoEntity nombreDeLista : contactoEntities) {
            if (nombreDeLista.getNombre().contains(nombre)) {
                resultados.add(nombreDeLista);
            }
        }

        resultados.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));

        return resultados;
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
