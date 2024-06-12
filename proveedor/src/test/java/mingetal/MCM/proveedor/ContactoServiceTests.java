package mingetal.MCM.proveedor;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.services.ContactoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContactoServiceTests {
    @Autowired
    private ContactoService contactoService;

    //-------------------- save --------------------

    @Test
    void creatContactoTestTrue(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "20158268-k",
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        assertEquals(contactoService.save(contactoEntity),  contactoEntity);
        assertNotNull(contactoService.findByRut(contactoEntity.getRut()));
        contactoService.delete(contactoEntity.getRut());
    }
    @Test
    void creatContactoTestFalse(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "20158268-k",
                "User 1",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        contactoService.save(contactoEntity);
        assertNull(contactoService.save(contactoEntity));
        contactoService.delete(contactoEntity.getRut());
    }

    //-------------------- findAll --------------------

    @Test
    void findAllTest() {
        ContactoEntity contacto1 = new ContactoEntity(
                "20158268-k",
                "User 1",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        ContactoEntity contacto2 = new ContactoEntity(
                "98765432-1",
                "User 2",
                "user2@example.com",
                "987654321",
                "22555847");

        contactoService.save(contacto1);
        contactoService.save(contacto2);

        List<ContactoEntity> contactoEntities = contactoService.findAll();
        assertFalse(contactoEntities.isEmpty());

        contactoService.delete(contacto1.getRut());
        contactoService.delete(contacto2.getRut());
    }

    //-------------------- findByRut --------------------

    @Test
    void findContactByRutTestTrue(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "20158268-k",
                "User 1",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        contactoService.save(contactoEntity);

        assertEquals(contactoEntity, contactoService.findByRut(contactoEntity.getRut()));

        contactoService.delete(contactoEntity.getRut());
    }
    @Test
    void findContactByRutTestFalse(){
        assertNull(contactoService.findByRut("-1"));
    }

    //-------------------- findByNombre --------------------

    @Test
    void findContactByNombreTestTrue(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "20158268-k",
                "User 1",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        contactoService.save(contactoEntity);

        List<ContactoEntity> contactoEntities = contactoService.findByNombre("User 1");
        assertEquals(1, contactoEntities.size());
        assertEquals(contactoEntity, contactoEntities.get(0));

        contactoService.delete(contactoEntity.getRut());
    }
    @Test
    void findMultipleContactByNombreTestTrue(){
        ContactoEntity contacto1 = new ContactoEntity(
                "20158268-k",
                "User 1",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        ContactoEntity contacto2 = new ContactoEntity(
                "98765432-1",
                "User 2",
                "user2@example.com",
                "987654321",
                "22555847");

        contactoService.save(contacto1);
        contactoService.save(contacto2);

        List<ContactoEntity> contactoEntities = contactoService.findByNombre("User");
        assertEquals(2, contactoEntities.size());
        assertEquals(contacto1, contactoEntities.get(0));
        assertEquals(contacto2, contactoEntities.get(1));

        contactoService.delete(contacto1.getRut());
        contactoService.delete(contacto2.getRut());
    }
    @Test
    void findContactByNombreTestFalse(){
        assertTrue(contactoService.findByNombre("NoExisteEsteUsuario").isEmpty());
    }

    //-------------------- findByNombreTextual --------------------

    @Test
    void findContactByNombreTextualTestTrue(){
        ContactoEntity contacto = new ContactoEntity(
                "20158268-k",
                "User 1",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        contactoService.save(contacto);

        assertEquals(contacto, contactoService.findByNombreTextual("User 1"));

        contactoService.delete(contacto.getRut());
    }
    @Test
    void findMultipleContactByNombreTextualTestFalse(){
        ContactoEntity contacto1 = new ContactoEntity(
                "20158268-k",
                "User 1",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        ContactoEntity contacto2 = new ContactoEntity(
                "98765432-1",
                "User 2",
                "user2@example.com",
                "987654321",
                "22555847");

        contactoService.save(contacto1);
        contactoService.save(contacto2);

        assertNull(contactoService.findByNombreTextual("User"));

        contactoService.delete(contacto1.getRut());
        contactoService.delete(contacto2.getRut());
    }
    @Test
    void findContactByNombreTextualTestFalse(){
        assertNull(contactoService.findByNombreTextual("NoExisteEsteUsuario"));
    }

    //-------------------- delete --------------------

    @Test
    void deleteContactoTestTrue(){
        ContactoEntity contacto1 = new ContactoEntity(
                "20158268-k",
                "User 1",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        contactoService.save(contacto1);
        assertEquals(contacto1, contactoService.delete(contacto1.getRut()));
        assertNull(contactoService.findByRut(contacto1.getRut()));
    }
    @Test
    void deleteContactoTestFalse(){
        assertNull(contactoService.delete("-1"));
    }

    //-------------------- update --------------------

    @Test
    void updateContactoTestTrue(){
        ContactoEntity contacto1 = new ContactoEntity(
                "20158268-k",
                "User 1",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        contactoService.save(contacto1);

        contacto1.setNombre("Cambiado");
        contacto1.setCorreo("Cambiado@gmail.com");
        contacto1.setFono_cel("+569123456789");
        contacto1.setFono_fijo("22123456789");

        contactoService.update(contacto1);

        ContactoEntity newContact = contactoService.findByRut(contacto1.getRut());

        assertEquals(contacto1, newContact);
        assertEquals(newContact, contactoService.findByRut(contacto1.getRut()));

        contactoService.delete(contacto1.getRut());
    }
    @Test
    void updateContactoTestFalse(){
        ContactoEntity contactoEntity = new ContactoEntity(
                "20158268-k",
                "Soy Yo",
                "correo_prueba@gmail.com",
                "+56912345684",
                "221548544"
        );
        assertNull(contactoService.update(contactoEntity));
    }


}
