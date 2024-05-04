package mingetal.MCM.cliente;

import mingetal.MCM.cliente.Entities.ClienteEntity;
import mingetal.MCM.cliente.Services.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteApplicationTests {
    @Autowired
    private ClienteService clienteService;

    @Test
    void saveTest() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre("Juan Perez");
        cliente.setRut("12345678-9");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("123456789");
        cliente.setEmpresa("EmpresaX");

        clienteService.save(cliente);

        ClienteEntity clienteGuardado = clienteService.findByRut(cliente.getRut());
        assertNotNull(clienteGuardado);

        clienteService.delete(clienteGuardado.getRut());
    }

    @Test
    void findAllTest() {
        ClienteEntity cliente1 = new ClienteEntity();
        cliente1.setNombre("Juan Perez");
        cliente1.setRut("12345678-9");
        cliente1.setEmail("juan@example.com");
        cliente1.setTelefono("123456789");
        cliente1.setEmpresa("EmpresaX");

        ClienteEntity cliente2 = new ClienteEntity();
        cliente2.setNombre("María Gomez");
        cliente2.setRut("98765432-1");
        cliente2.setEmail("maria@example.com");
        cliente2.setTelefono("987654321");
        cliente2.setEmpresa("EmpresaX");

        clienteService.save(cliente1);
        clienteService.save(cliente2);

        List<ClienteEntity> clientes = clienteService.findAll();
        assertFalse(clientes.isEmpty());

        clienteService.delete(cliente1.getRut());
        clienteService.delete(cliente2.getRut());
    }

    @Test
    void findByRutTest() {

        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre("Juan Perez");
        cliente.setRut("12345678-9");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("123456789");
        cliente.setEmpresa("EmpresaX");

        clienteService.save(cliente);

        ClienteEntity clienteGuardado = clienteService.findByRut("12345678-9");
        assertNotNull(clienteGuardado);
        assertEquals("12345678-9", clienteGuardado.getRut());

        clienteService.delete(clienteGuardado.getRut());

    }

    @Test
    void findByNombreTest() {

        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre("Juan Perez");
        cliente.setRut("12345678-9");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("123456789");
        cliente.setEmpresa("EmpresaX");

        clienteService.save(cliente);

        ClienteEntity clienteEncontrado = clienteService.findByNombre("Juan Perez");
        assertNotNull(clienteEncontrado);
        assertEquals("Juan Perez", clienteEncontrado.getNombre());

        clienteService.deleteByNombre(clienteEncontrado.getNombre());

    }

    @Test
    void findByEmpresaTest() {
        ClienteEntity cliente1 = new ClienteEntity();
        cliente1.setNombre("Juan Perez");
        cliente1.setRut("12345678-9");
        cliente1.setEmail("juan@example.com");
        cliente1.setTelefono("123456789");
        cliente1.setEmpresa("EmpresaX");

        ClienteEntity cliente2 = new ClienteEntity();
        cliente2.setNombre("María Gomez");
        cliente2.setRut("98765432-1");
        cliente2.setEmail("maria@example.com");
        cliente2.setTelefono("987654321");
        cliente2.setEmpresa("EmpresaX");

        clienteService.save(cliente1);
        clienteService.save(cliente2);

        List<ClienteEntity> clientesEncontrados = clienteService.findByEmpresa("EmpresaX");

        assertNotNull(clientesEncontrados);

        assertTrue(!clientesEncontrados.isEmpty());

        for (ClienteEntity cliente : clientesEncontrados) {
            assertEquals("EmpresaX", cliente.getEmpresa());
        }

        for (ClienteEntity cliente : clientesEncontrados) {
            clienteService.delete(cliente.getRut());
        }

    }

    @Test
    void deleteTest() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre("Juan Perez");
        cliente.setRut("12345678-9");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("123456789");
        cliente.setEmpresa("EmpresaX");

        clienteService.save(cliente);
        assertNotNull(cliente);
        clienteService.delete(cliente.getRut());
    }

    @Test
    void deleteByNombreTest() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre("Juan Perez");
        cliente.setRut("12345678-9");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("123456789");
        cliente.setEmpresa("EmpresaX");

        clienteService.save(cliente);
        assertNotNull(cliente);
        clienteService.deleteByNombre(cliente.getNombre());
    }

    @Test
    void updateTest() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre("Juan Perez");
        cliente.setRut("12345678-9");
        cliente.setEmail("juan@example.com");
        cliente.setTelefono("123456789");
        cliente.setEmpresa("EmpresaX");

        clienteService.save(cliente);

        cliente.setNombre("Juan Pablo");
        ClienteEntity updatedCliente = clienteService.update(cliente);

        assertNotNull(updatedCliente);
        assertEquals("Juan Pablo", updatedCliente.getNombre());

        clienteService.delete(cliente.getRut());
    }
}

