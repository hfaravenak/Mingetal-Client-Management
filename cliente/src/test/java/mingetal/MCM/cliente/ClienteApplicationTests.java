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
        assertNotNull(cliente.getRut());  // Asegura que el ID se establece al guardar
    }

    @Test
    void findAllTest() {
        List<ClienteEntity> clientes = clienteService.findAll();
        assertFalse(clientes.isEmpty());  // Suponiendo que hay datos en la base de datos
    }

    @Test
    void findByRutTest() {
        ClienteEntity cliente = clienteService.findByRut("12345678-9");
        assertNotNull(cliente);
        assertEquals("12345678-9", cliente.getRut());
    }

    @Test
    void findByNombreTest() {
        ClienteEntity cliente = clienteService.findByNombre("Juan Perez");
        assertNotNull(cliente);
        assertEquals("Juan Perez", cliente.getNombre());
    }

    @Test
    void findByEmpresaTest() {
        List<ClienteEntity> clientes = clienteService.findByEmpresa("EmpresaX");
        assertFalse(clientes.isEmpty());  // Verifica que se encuentren clientes para la empresa especificada
    }

    @Test
    void deleteTest() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setRut("12345678-9");
        clienteService.save(cliente);

        clienteService.delete(cliente.getRut());
        assertNull(clienteService.findByRut(cliente.getRut()));
    }

    @Test
    void updateTest() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setRut("12345678-9");
        cliente.setNombre("Juan Perez");
        clienteService.save(cliente);

        cliente.setNombre("Juan Pablo");
        ClienteEntity updatedCliente = clienteService.update(cliente);

        assertNotNull(updatedCliente);
        assertEquals("Juan Pablo", updatedCliente.getNombre());
    }
}

