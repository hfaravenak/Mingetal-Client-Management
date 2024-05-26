package mingetal.MCM.cliente;

import mingetal.MCM.cliente.entities.ClienteEntity;
import mingetal.MCM.cliente.services.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteApplicationTests {
    @Autowired
    private ClienteService clienteService;

    //-------------------- save --------------------

    @Test
    void saveTestTrue() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");

        assertEquals(cliente1, clienteService.save(cliente1));
        assertNotNull(clienteService.findByRut(cliente1.getRut()));

        clienteService.delete(cliente1.getRut());
    }
    @Test
    void saveTestFalse() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");

        clienteService.save(cliente1);
        assertNull(clienteService.save(cliente1));

        clienteService.delete(cliente1.getRut());
    }

    //-------------------- findAll --------------------

    @Test
    void findAllTest() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");
        ClienteEntity cliente2 = new ClienteEntity(
                "98765432-1",
                "User 2",
                "user2@example.com",
                "987654321",
                "EmpresaX");

        clienteService.save(cliente1);
        clienteService.save(cliente2);

        assertFalse(clienteService.findAll().isEmpty());

        clienteService.delete(cliente1.getRut());
        clienteService.delete(cliente2.getRut());
    }

    //-------------------- findByRut --------------------

    @Test
    void findByRutTestTrue() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");

        clienteService.save(cliente1);

        assertEquals(cliente1, clienteService.findByRut("12345678-9"));

        clienteService.delete(cliente1.getRut());
    }
    @Test
    void findByRutTestFalse() {
        assertNull(clienteService.findByRut("-1"));
    }

    //-------------------- findByNombre --------------------

    @Test
    void findByNombreTestTrue() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");

        clienteService.save(cliente1);

        List<ClienteEntity> clienteEncontrado = clienteService.findByNombre("User 1");
        assertEquals(1, clienteEncontrado.size());
        assertEquals(cliente1, clienteEncontrado.get(0));

        clienteService.delete(clienteEncontrado.get(0).getRut());
    }
    @Test
    void findMultipleByNombreTestTrue() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");
        ClienteEntity cliente2 = new ClienteEntity(
                "98765432-1",
                "User 2",
                "user2@example.com",
                "987654321",
                "EmpresaX");

        clienteService.save(cliente1);
        clienteService.save(cliente2);

        List<ClienteEntity> clienteEncontrado = clienteService.findByNombre("User");
        assertEquals(2, clienteEncontrado.size());
        assertEquals("User 1", clienteEncontrado.get(0).getNombre());
        assertEquals("User 2", clienteEncontrado.get(1).getNombre());

        clienteService.delete(clienteEncontrado.get(0).getRut());
        clienteService.delete(clienteEncontrado.get(1).getRut());
    }
    @Test
    void findByNombreTestFalse() {
        assertTrue(clienteService.findByNombre("NoExisteEsteNombre").isEmpty());
    }

    //-------------------- findByNombreTextual --------------------

    @Test
    void findByNombreTextualTestTrue() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");

        clienteService.save(cliente1);

        assertEquals(cliente1, clienteService.findByNombreTextual("User 1"));

        clienteService.delete(cliente1.getRut());
    }
    @Test
    void findByNombreTextualTestFalseForBadTyping() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");

        clienteService.save(cliente1);

        assertNull(clienteService.findByNombreTextual("User"));

        clienteService.delete(cliente1.getRut());
    }
    @Test
    void findByNombreTextualTestFalse() {
        assertNull(clienteService.findByNombreTextual("NoExisteEsteNombre"));
    }

    //-------------------- findByEmpresa --------------------

    @Test
    void findByEmpresaTestTrue() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");
        ClienteEntity cliente2 = new ClienteEntity(
                "98765432-1",
                "User 2",
                "user2@example.com",
                "987654321",
                "EmpresaX");

        clienteService.save(cliente1);
        clienteService.save(cliente2);

        List<ClienteEntity> clientesEncontrados = clienteService.findByEmpresa("EmpresaX");

        assertEquals(2,clientesEncontrados.size());
        assertEquals("EmpresaX", clientesEncontrados.get(0).getEmpresa());
        assertEquals("EmpresaX", clientesEncontrados.get(1).getEmpresa());

        clienteService.delete(clientesEncontrados.get(0).getRut());
        clienteService.delete(clientesEncontrados.get(1).getRut());
    }
    @Test
    void findByEmpresaTestFalse() {
        assertTrue(clienteService.findByEmpresa("EmpresaX").isEmpty());
    }
    @Test
    void findByEmpresaTestFalseForBadTyping() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");
        ClienteEntity cliente2 = new ClienteEntity(
                "98765432-1",
                "User 2",
                "user2@example.com",
                "987654321",
                "EmpresaX");

        clienteService.save(cliente1);
        clienteService.save(cliente2);

        clienteService.save(cliente1);
        clienteService.save(cliente2);

        assertTrue(clienteService.findByEmpresa("Empresa").isEmpty());

        clienteService.delete(cliente1.getRut());
        clienteService.delete(cliente2.getRut());
    }

    //-------------------- delete --------------------

    @Test
    void deleteTestTrue() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");

        clienteService.save(cliente1);

        assertEquals(cliente1, clienteService.delete(cliente1.getRut()));
        assertNull((clienteService.findByRut(cliente1.getRut())));
    }
    @Test
    void deleteTestFalse() {
        assertNull(clienteService.delete("-1"));
    }

    //-------------------- update --------------------

    @Test
    void updateTestTrue() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");

        clienteService.save(cliente1);

        cliente1.setNombre("New User Random");
        cliente1.setEmail("newEmail@example.com");
        cliente1.setTelefono("11223344");
        cliente1.setEmpresa("New Empresa Random");
        ClienteEntity updatedCliente = clienteService.update(cliente1);

        assertEquals(cliente1, updatedCliente);
        assertEquals(updatedCliente, clienteService.findByRut(cliente1.getRut()));

        clienteService.delete(cliente1.getRut());
    }
    @Test
    void updateTestFalse() {
        ClienteEntity cliente1 = new ClienteEntity(
                "12345678-9",
                "User 1",
                "user1@example.com",
                "123456789",
                "EmpresaX");

        cliente1.setNombre("New User Random");
        ClienteEntity updatedCliente = clienteService.update(cliente1);

        assertNull(updatedCliente);
    }
}

