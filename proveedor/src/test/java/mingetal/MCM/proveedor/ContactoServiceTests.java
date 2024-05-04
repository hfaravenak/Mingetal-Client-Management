package mingetal.MCM.proveedor;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.services.ContactoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContactoServiceTests {
    @Autowired
    private ContactoService contactoService;

    @Test
    void creatContactoTestTrue(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544",
                "20.158.268-k"
        );
        assertEquals(contactoService.createContacto(contactoEntity),  contactoEntity);
        assertNotNull(contactoService.findContactoById(contactoEntity.getId_contacto()));
        contactoService.deleteContacto(contactoEntity.getId_contacto());
    }
    @Test
    void creatContactoTestFalse(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544",
                "20.158.268-k"
        );
        contactoService.createContacto(contactoEntity);
        assertNull(contactoService.createContacto(contactoEntity));
        contactoService.deleteContacto(contactoEntity.getId_contacto());
    }

    @Test
    void findContactByIdTestTrue(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544",
                "20.158.268-k"
        );
        contactoService.createContacto(contactoEntity);

        assertEquals(contactoEntity, contactoService.findContactoById(contactoEntity.getId_contacto()));
        contactoService.deleteContacto(contactoEntity.getId_contacto());
    }
    @Test
    void findContactByIdTestFalse(){
        assertNull(contactoService.findContactoById(-1));
    }

    @Test
    void updateContactoTestTrue(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544",
                "20.158.268-k"
        );
        contactoService.createContacto(contactoEntity);

        ContactoEntity contactoEntityUpdate = contactoService.findContactoById(contactoEntity.getId_contacto());
        contactoEntityUpdate.setNombre("Cambiado");
        contactoEntityUpdate.setCorreo("Cambiado@gmail.com");
        contactoEntityUpdate.setForo_cel("+569123456789");
        contactoEntityUpdate.setFono_fijo("22123456789");
        contactoEntityUpdate.setRut("12.345.678-9");

        contactoService.updateContacto(contactoEntityUpdate);

        ContactoEntity newContact = contactoService.findContactoById(contactoEntity.getId_contacto());

        assertEquals("Cambiado", newContact.getNombre());
        assertEquals("Cambiado@gmail.com", newContact.getCorreo());
        assertEquals("+569123456789", newContact.getForo_cel());
        assertEquals("22123456789", newContact.getFono_fijo());
        assertEquals("12.345.678-9", newContact.getRut());
        assertEquals(contactoEntityUpdate.getId_contacto(), newContact.getId_contacto());

        contactoService.deleteContacto(contactoEntity.getId_contacto());
    }
    @Test
    void updateContactoTestFalse(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544",
                "20.158.268-k"
        );
        assertNull(contactoService.updateContacto(contactoEntity));
    }

    @Test
    void deleteContactoTestTrue(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544",
                "20.158.268-k"
        );
        contactoService.createContacto(contactoEntity);
        ContactoEntity contactoDelete = contactoService.deleteContacto(contactoEntity.getId_contacto());
        assertEquals(contactoEntity, contactoDelete);
        assertNull(contactoService.findContactoById(contactoEntity.getId_contacto()));
    }
    @Test
    void deleteContactoTestFalse(){
        assertNull(contactoService.deleteContacto(-1));
    }
}
