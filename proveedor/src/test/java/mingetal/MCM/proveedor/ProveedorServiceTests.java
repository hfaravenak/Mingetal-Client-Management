package mingetal.MCM.proveedor;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.entities.ProveedorEntity;
import mingetal.MCM.proveedor.model.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.proveedor.services.ContactoService;
import mingetal.MCM.proveedor.services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProveedorServiceTests {
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private ContactoService contactoService;

    @Autowired
    RestTemplate restTemplate;

    //-------------------- save --------------------

    @Test
    void creatProveedorTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );

        assertEquals(proveedorEntity, proveedorService.save(proveedorEntity));
        assertNotNull(proveedorService.findById(proveedorEntity.getId_proveedor()));

        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void creatProveedorTestFalse(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);
        assertNull(proveedorService.save(proveedorEntity));

        proveedorService.delete(proveedorEntity.getId_proveedor());
    }

    //-------------------- findAll --------------------

    @Test
    void findAllTest() {
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        ProveedorEntity proveedorEntity2 = new ProveedorEntity(
                "Empresa UwU",
                "Algo X",
                "Algun que otro comentario aqui"
        );

        proveedorService.save(proveedorEntity);
        proveedorService.save(proveedorEntity2);

        assertFalse(proveedorService.findAll().isEmpty());

        proveedorService.delete(proveedorEntity.getId_proveedor());
        proveedorService.delete(proveedorEntity2.getId_proveedor());
    }

    //-------------------- findByListOC --------------------

    @Test
    void findProveedorByListOCTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                proveedorEntity.getId_proveedor(),
                "No Pagado",
                null,
                null,
                "Entregado",
                LocalDate.parse("2024-05-25"),
                "20021",
                20000
        );
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity2 = new OrdenesDeCompraProveedorEntity(
                proveedorEntity.getId_proveedor(),
                "No Pagado",
                null,
                null,
                "Entregado",
                LocalDate.parse("2024-05-25"),
                "20022",
                22000
        );

        HttpEntity<OrdenesDeCompraProveedorEntity> requestEntity = new HttpEntity<>(ordenesDeCompraProveedorEntity);
        HttpEntity<OrdenesDeCompraProveedorEntity> requestEntity2 = new HttpEntity<>(ordenesDeCompraProveedorEntity2);
        restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/proveedor/",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<OrdenesDeCompraProveedorEntity>() {}
        ).getBody();
        restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/proveedor/",
                HttpMethod.POST,
                requestEntity2,
                new ParameterizedTypeReference<OrdenesDeCompraProveedorEntity>() {}
        ).getBody();

        List<ProveedorEntity> proveedorEntities = proveedorService.findByListOC();

        assertTrue(proveedorEntities.contains(proveedorEntity));

        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/proveedor/id/"+proveedorEntity.getId_proveedor(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrdenesDeCompraProveedorEntity>>() {}
        ).getBody();

        restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/proveedor/delete/"+ordenesDeCompraProveedorEntities.get(0).getId(),
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<OrdenesDeCompraProveedorEntity>() {}
        ).getBody();
        restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/proveedor/delete/"+ordenesDeCompraProveedorEntities.get(1).getId(),
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<OrdenesDeCompraProveedorEntity>() {}
        ).getBody();
        proveedorService.delete(proveedorEntity.getId_proveedor());
    }

    @Test
    void findProveedorByListOCTestFalse(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        List<ProveedorEntity> proveedorEntities = proveedorService.findByListOC();

        assertFalse(proveedorEntities.contains(proveedorEntity));
    }

    //-------------------- findByEmpresa --------------------

    @Test
    void findProveedorByEmpresaTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        assertEquals(proveedorEntity, proveedorService.findByEmpresa("Empresa X"));

        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findProveedorByEmpresaTestFalse(){
        assertNull(proveedorService.findByEmpresa("Esto no existe ñññññññ"));
    }

    //-------------------- findByRut --------------------

    @Test
    void findProveedorByRutContacto1TestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        assertEquals(proveedorEntity, proveedorService.findByRut(proveedorEntity.getId_contacto()));

        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findProveedorByRutContacto2TestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        assertEquals(proveedorEntity, proveedorService.findByRut(proveedorEntity.getId_contacto2()));

        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findProveedorByRutContacto3TestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        assertEquals(proveedorEntity, proveedorService.findByRut(proveedorEntity.getId_contacto3()));

        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findProveedorByRutTestFalse(){
        assertNull(proveedorService.findByRut("11111111-1"));
    }


    //-------------------- findByNombre --------------------

    @Test
    void findProveedorByNombreContacto1TestTrue(){
        ContactoEntity contacto = new ContactoEntity(
                "20640480-9",
                "User 1",
                "user1@gmail.com",
                "+56934061014",
                "2234115548"
        );
        contactoService.save(contacto);
        ContactoEntity contacto2 = new ContactoEntity(
                "12345678-9",
                "User 2",
                "user2@gmail.com",
                "+56912345678",
                "2287654321"
        );
        contactoService.save(contacto2);
        ContactoEntity contacto3 = new ContactoEntity(
                "98654321-k",
                "User 3",
                "user3@gmail.com",
                "+56911223344",
                "2244332211"
        );
        contactoService.save(contacto3);
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        List<ProveedorEntity> proveedorEntities = proveedorService.findByNombre(contacto.getNombre());
        assertEquals(1, proveedorEntities.size());
        assertEquals(proveedorEntity, proveedorEntities.get(0));

        contactoService.delete(contacto.getRut());
        contactoService.delete(contacto2.getRut());
        contactoService.delete(contacto3.getRut());
        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findProveedorByNombreContacto2TestTrue(){
        ContactoEntity contacto = new ContactoEntity(
                "20640480-9",
                "User 1",
                "user1@gmail.com",
                "+56934061014",
                "2234115548"
        );
        contactoService.save(contacto);
        ContactoEntity contacto2 = new ContactoEntity(
                "12345678-9",
                "User 2",
                "user2@gmail.com",
                "+56912345678",
                "2287654321"
        );
        contactoService.save(contacto2);
        ContactoEntity contacto3 = new ContactoEntity(
                "98654321-k",
                "User 3",
                "user3@gmail.com",
                "+56911223344",
                "2244332211"
        );
        contactoService.save(contacto3);
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        List<ProveedorEntity> proveedorEntities = proveedorService.findByNombre(contacto2.getNombre());
        assertEquals(1, proveedorEntities.size());
        assertEquals(proveedorEntity, proveedorEntities.get(0));

        contactoService.delete(contacto.getRut());
        contactoService.delete(contacto2.getRut());
        contactoService.delete(contacto3.getRut());
        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findProveedorByNombreContacto3TestTrue(){
        ContactoEntity contacto = new ContactoEntity(
                "20640480-9",
                "User 1",
                "user1@gmail.com",
                "+56934061014",
                "2234115548"
        );
        contactoService.save(contacto);
        ContactoEntity contacto2 = new ContactoEntity(
                "12345678-9",
                "User 2",
                "user2@gmail.com",
                "+56912345678",
                "2287654321"
        );
        contactoService.save(contacto2);
        ContactoEntity contacto3 = new ContactoEntity(
                "98654321-k",
                "User 3",
                "user3@gmail.com",
                "+56911223344",
                "2244332211"
        );
        contactoService.save(contacto3);
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        List<ProveedorEntity> proveedorEntities = proveedorService.findByNombre(contacto3.getNombre());
        assertEquals(1, proveedorEntities.size());
        assertEquals(proveedorEntity, proveedorEntities.get(0));

        contactoService.delete(contacto.getRut());
        contactoService.delete(contacto2.getRut());
        contactoService.delete(contacto3.getRut());
        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findUniqProveedorByNombreContactoTestTrue(){
        ContactoEntity contacto = new ContactoEntity(
                "20640480-9",
                "User 1",
                "user1@gmail.com",
                "+56934061014",
                "2234115548"
        );
        contactoService.save(contacto);
        ContactoEntity contacto2 = new ContactoEntity(
                "12345678-9",
                "User 2",
                "user2@gmail.com",
                "+56912345678",
                "2287654321"
        );
        contactoService.save(contacto2);
        ContactoEntity contacto3 = new ContactoEntity(
                "98654321-k",
                "User 3",
                "user3@gmail.com",
                "+56911223344",
                "2244332211"
        );
        contactoService.save(contacto3);
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        List<ProveedorEntity> proveedorEntities = proveedorService.findByNombre("User");
        assertEquals(1, proveedorEntities.size());
        assertEquals(proveedorEntity, proveedorEntities.get(0));

        contactoService.delete(contacto.getRut());
        contactoService.delete(contacto2.getRut());
        contactoService.delete(contacto3.getRut());
        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findMultipleProveedorByNombreContactoTestTrue(){
        ContactoEntity contacto = new ContactoEntity(
                "20640480-9",
                "User 1",
                "user1@gmail.com",
                "+56934061014",
                "2234115548"
        );
        contactoService.save(contacto);
        ContactoEntity contacto2 = new ContactoEntity(
                "12345678-9",
                "User 2",
                "user2@gmail.com",
                "+56912345678",
                "2287654321"
        );
        contactoService.save(contacto2);
        ContactoEntity contacto3 = new ContactoEntity(
                "98654321-k",
                "User 3",
                "user3@gmail.com",
                "+56911223344",
                "2244332211"
        );
        contactoService.save(contacto3);
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        ContactoEntity contacto4 = new ContactoEntity(
                "11223344-5",
                "User 4",
                "user4@gmail.com",
                "+56934061014",
                "2234115548"
        );
        contactoService.save(contacto4);
        ContactoEntity contacto5 = new ContactoEntity(
                "55667788-9",
                "User 5",
                "user5@gmail.com",
                "+56912345678",
                "2287654321"
        );
        contactoService.save(contacto5);
        ContactoEntity contacto6 = new ContactoEntity(
                "159753456-k",
                "User 6",
                "user6@gmail.com",
                "+56911223344",
                "2244332211"
        );
        contactoService.save(contacto6);
        ProveedorEntity proveedorEntity2 = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "11223344-5",
                "55667788-9",
                "159753456-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity2);

        List<ProveedorEntity> proveedorEntities = proveedorService.findByNombre("User");
        assertEquals(2, proveedorEntities.size());
        assertEquals(proveedorEntity, proveedorEntities.get(0));
        assertEquals(proveedorEntity2, proveedorEntities.get(1));

        contactoService.delete(contacto.getRut());
        contactoService.delete(contacto2.getRut());
        contactoService.delete(contacto3.getRut());
        proveedorService.delete(proveedorEntity.getId_proveedor());
        contactoService.delete(contacto4.getRut());
        contactoService.delete(contacto5.getRut());
        contactoService.delete(contacto6.getRut());
        proveedorService.delete(proveedorEntity2.getId_proveedor());
    }
    @Test
    void findProveedorByNombreContactoTestFalse(){
        assertTrue(proveedorService.findByNombre("User 1").isEmpty());
    }

    //-------------------- findByRubro --------------------

    @Test
    void findProveedorByRubroTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        List<ProveedorEntity> proveedorEntities = proveedorService.findByRubro(proveedorEntity.getRubro());
        assertEquals(1, proveedorEntities.size());
        assertEquals(proveedorEntity, proveedorEntities.get(0));

        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findMultipleProveedorByRubroTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);
        ProveedorEntity proveedorEntity2 = new ProveedorEntity(
                "Empresa X2",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity2);

        List<ProveedorEntity> proveedorEntities = proveedorService.findByRubro(proveedorEntity.getRubro());
        assertEquals(2, proveedorEntities.size());
        assertEquals(proveedorEntity, proveedorEntities.get(0));
        assertEquals(proveedorEntity2, proveedorEntities.get(1));

        proveedorService.delete(proveedorEntity.getId_proveedor());
        proveedorService.delete(proveedorEntity2.getId_proveedor());
    }
    @Test
    void findProveedorByRubroTestFalse(){
        assertTrue(proveedorService.findByRubro("Algo X").isEmpty());
    }

    //-------------------- findRubros --------------------

    @Test
    void findRubroTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        List<String> rubros = proveedorService.findRubros();
        assertTrue(rubros.contains(proveedorEntity.getRubro()));

        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findMultipleRubroTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);
        ProveedorEntity proveedorEntity2 = new ProveedorEntity(
                "Empresa X2",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity2);

        List<String> rubros = proveedorService.findRubros();
        assertTrue(rubros.contains(proveedorEntity.getRubro()));
        assertTrue(rubros.contains(proveedorEntity2.getRubro()));

        proveedorService.delete(proveedorEntity.getId_proveedor());
        proveedorService.delete(proveedorEntity2.getId_proveedor());
    }

    //-------------------- findDespacho --------------------

    @Test
    void findDespachoTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "DESPACHOS",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);
        ProveedorEntity proveedorEntity2 = new ProveedorEntity(
                "Empresa X2",
                "DESPACHOS",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity2);

        List<ProveedorEntity> proveedorEntities = proveedorService.findDespacho();
        assertTrue(proveedorEntities.contains(proveedorEntity));
        assertTrue(proveedorEntities.contains(proveedorEntity2));

        proveedorService.delete(proveedorEntity.getId_proveedor());
        proveedorService.delete(proveedorEntity2.getId_proveedor());
    }

    //-------------------- findByNombreTextual --------------------

    @Test
    void findProveedorByNombreTextualContacto1TestTrue(){
        ContactoEntity contacto = new ContactoEntity(
                "20640480-9",
                "User 1",
                "user1@gmail.com",
                "+56934061014",
                "2234115548"
        );
        contactoService.save(contacto);
        ContactoEntity contacto2 = new ContactoEntity(
                "12345678-9",
                "User 2",
                "user2@gmail.com",
                "+56912345678",
                "2287654321"
        );
        contactoService.save(contacto2);
        ContactoEntity contacto3 = new ContactoEntity(
                "98654321-k",
                "User 3",
                "user3@gmail.com",
                "+56911223344",
                "2244332211"
        );
        contactoService.save(contacto3);
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        assertEquals(proveedorEntity, proveedorService.findByNombreTextual(contacto.getNombre()));

        contactoService.delete(contacto.getRut());
        contactoService.delete(contacto2.getRut());
        contactoService.delete(contacto3.getRut());
        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findProveedorByNombreTextualContacto2TestTrue(){
        ContactoEntity contacto = new ContactoEntity(
                "20640480-9",
                "User 1",
                "user1@gmail.com",
                "+56934061014",
                "2234115548"
        );
        contactoService.save(contacto);
        ContactoEntity contacto2 = new ContactoEntity(
                "12345678-9",
                "User 2",
                "user2@gmail.com",
                "+56912345678",
                "2287654321"
        );
        contactoService.save(contacto2);
        ContactoEntity contacto3 = new ContactoEntity(
                "98654321-k",
                "User 3",
                "user3@gmail.com",
                "+56911223344",
                "2244332211"
        );
        contactoService.save(contacto3);
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        assertEquals(proveedorEntity, proveedorService.findByNombreTextual(contacto2.getNombre()));

        contactoService.delete(contacto.getRut());
        contactoService.delete(contacto2.getRut());
        contactoService.delete(contacto3.getRut());
        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findProveedorByNombreTextualContacto3TestTrue(){
        ContactoEntity contacto = new ContactoEntity(
                "20640480-9",
                "User 1",
                "user1@gmail.com",
                "+56934061014",
                "2234115548"
        );
        contactoService.save(contacto);
        ContactoEntity contacto2 = new ContactoEntity(
                "12345678-9",
                "User 2",
                "user2@gmail.com",
                "+56912345678",
                "2287654321"
        );
        contactoService.save(contacto2);
        ContactoEntity contacto3 = new ContactoEntity(
                "98654321-k",
                "User 3",
                "user3@gmail.com",
                "+56911223344",
                "2244332211"
        );
        contactoService.save(contacto3);
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        assertEquals(proveedorEntity, proveedorService.findByNombreTextual(contacto3.getNombre()));

        contactoService.delete(contacto.getRut());
        contactoService.delete(contacto2.getRut());
        contactoService.delete(contacto3.getRut());
        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findMultipleProveedorByNombreTextualContactoTestTrue(){
        ContactoEntity contacto = new ContactoEntity(
                "20640480-9",
                "User 1",
                "user1@gmail.com",
                "+56934061014",
                "2234115548"
        );
        contactoService.save(contacto);
        ContactoEntity contacto2 = new ContactoEntity(
                "12345678-9",
                "User 2",
                "user2@gmail.com",
                "+56912345678",
                "2287654321"
        );
        contactoService.save(contacto2);
        ContactoEntity contacto3 = new ContactoEntity(
                "98654321-k",
                "User 3",
                "user3@gmail.com",
                "+56911223344",
                "2244332211"
        );
        contactoService.save(contacto3);
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        ContactoEntity contacto4 = new ContactoEntity(
                "11223344-5",
                "User 4",
                "user4@gmail.com",
                "+56934061014",
                "2234115548"
        );
        contactoService.save(contacto4);
        ContactoEntity contacto5 = new ContactoEntity(
                "55667788-9",
                "User 5",
                "user5@gmail.com",
                "+56912345678",
                "2287654321"
        );
        contactoService.save(contacto5);
        ContactoEntity contacto6 = new ContactoEntity(
                "159753456-k",
                "User 6",
                "user6@gmail.com",
                "+56911223344",
                "2244332211"
        );
        contactoService.save(contacto6);
        ProveedorEntity proveedorEntity2 = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "20640480-9",
                "12345678-9",
                "98654321-k",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity2);

        assertNull(proveedorService.findByNombreTextual("User"));

        contactoService.delete(contacto.getRut());
        contactoService.delete(contacto2.getRut());
        contactoService.delete(contacto3.getRut());
        proveedorService.delete(proveedorEntity.getId_proveedor());
        contactoService.delete(contacto4.getRut());
        contactoService.delete(contacto5.getRut());
        contactoService.delete(contacto6.getRut());
        proveedorService.delete(proveedorEntity2.getId_proveedor());
    }

    //-------------------- findById --------------------

    @Test
    void findProveedorByIdTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        assertEquals(proveedorEntity, proveedorService.findById(proveedorEntity.getId_proveedor()));

        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void findProveedorByIdTestFalse(){
        assertNull(proveedorService.findById(-1));
    }

    //-------------------- delete --------------------

    @Test
    void deleteContactoTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        assertEquals(proveedorEntity, proveedorService.delete(proveedorEntity.getId_proveedor()));
        assertNull(proveedorService.findById(proveedorEntity.getId_proveedor()));
    }
    @Test
    void deleteContactoTestFalse(){
        assertNull(proveedorService.delete(-1));
    }

    //-------------------- update --------------------

    @Test
    void updateTestTrue() {
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.save(proveedorEntity);

        proveedorEntity.setEmpresa("New Empresa Random");
        proveedorEntity.setRubro("New Rubro");
        proveedorEntity.setComentario("New Comentario");
        ProveedorEntity updatedCliente = proveedorService.update(proveedorEntity);

        assertEquals(proveedorEntity, updatedCliente);
        assertEquals(updatedCliente, proveedorService.findById(proveedorEntity.getId_proveedor()));

        proveedorService.delete(proveedorEntity.getId_proveedor());
    }
    @Test
    void updateTestFalse() {
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        assertNull(proveedorService.update(proveedorEntity));
    }
}
