package mingetal.MCM.proveedor.services;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.repositories.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactoService {
    @Autowired
    private ContactoRepository contactoRepository;
    @Autowired
    RestTemplate restTemplate;

    //-------------------- Guardado --------------------

    public ContactoEntity save(ContactoEntity contacto) {
        if(findByRut(contacto.getRut()) != null){
            return null;
        }
        return contactoRepository.save(contacto);
    }

    //-------------------- Buscar --------------------

    public List<ContactoEntity> findAll() {
        return contactoRepository.findAll();
    }

    public ContactoEntity findByRut(String rut) {
        return contactoRepository.findById(rut);
    }

    public List<ContactoEntity> findByNombre(String nombre) {
        List<ContactoEntity> contactoEntities = findAll();
        List<ContactoEntity> resultados = new ArrayList<>();
        for (ContactoEntity nombreDeLista : contactoEntities) {
            if (nombreDeLista.getNombre().contains(nombre)) {
                resultados.add(nombreDeLista);
            }
        }

        resultados.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));
        return resultados;
    }
    public ContactoEntity findByNombreTextual(String nombre) {
        return contactoRepository.findByNombreContacto(nombre);
    }

    //-------------------- Eliminar --------------------

    public ContactoEntity delete(String id) {
        ContactoEntity contacto= findByRut(id);
        if(contacto==null){
            return null;
        }

        contactoRepository.delete(contacto);
        return contacto;
    }

    //-------------------- Editar --------------------

    public ContactoEntity update(ContactoEntity updatedContacto) {
        if(findByRut(updatedContacto.getRut())==null){
            return null;
        }
        return contactoRepository.save(updatedContacto);
    }
}
