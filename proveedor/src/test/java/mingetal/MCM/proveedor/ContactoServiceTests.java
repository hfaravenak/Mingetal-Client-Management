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
                "20.158.268-k",
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        assertEquals(contactoService.createContacto(contactoEntity),  contactoEntity);
        assertNotNull(contactoService.findContactoById(contactoEntity.getRut()));
        contactoService.deleteContacto(contactoEntity.getRut());
    }
    @Test
    void creatContactoTestFalse(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "20.158.268-k",
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        contactoService.createContacto(contactoEntity);
        assertNull(contactoService.createContacto(contactoEntity));
        contactoService.deleteContacto(contactoEntity.getRut());
    }

    @Test
    void findContactByIdTestTrue(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "20.158.268-k",
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        contactoService.createContacto(contactoEntity);

        assertEquals(contactoEntity, contactoService.findContactoById(contactoEntity.getRut()));
        contactoService.deleteContacto(contactoEntity.getRut());
    }
    @Test
    void findContactByIdTestFalse(){
        assertNull(contactoService.findContactoById("-1"));
    }

    @Test
    void updateContactoTestTrue(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "20.158.268-k",
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        contactoService.createContacto(contactoEntity);

        ContactoEntity contactoEntityUpdate = contactoService.findContactoById(contactoEntity.getRut());
        contactoEntityUpdate.setNombre("Cambiado");
        contactoEntityUpdate.setCorreo("Cambiado@gmail.com");
        contactoEntityUpdate.setFono_cel("+569123456789");
        contactoEntityUpdate.setFono_fijo("22123456789");

        contactoService.updateContacto(contactoEntityUpdate);

        ContactoEntity newContact = contactoService.findContactoById(contactoEntity.getRut());

        assertEquals("Cambiado", newContact.getNombre());
        assertEquals("Cambiado@gmail.com", newContact.getCorreo());
        assertEquals("+569123456789", newContact.getFono_cel());
        assertEquals("22123456789", newContact.getFono_fijo());
        assertEquals(contactoEntityUpdate.getRut(), newContact.getRut());

        contactoService.deleteContacto(contactoEntity.getRut());
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
                "20.158.268-k",
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        contactoService.createContacto(contactoEntity);
        ContactoEntity contactoDelete = contactoService.deleteContacto(contactoEntity.getRut());
        assertEquals(contactoEntity, contactoDelete);
        assertNull(contactoService.findContactoById(contactoEntity.getRut()));
    }
    @Test
    void deleteContactoTestFalse(){
        assertNull(contactoService.deleteContacto("-1"));
    }
}
