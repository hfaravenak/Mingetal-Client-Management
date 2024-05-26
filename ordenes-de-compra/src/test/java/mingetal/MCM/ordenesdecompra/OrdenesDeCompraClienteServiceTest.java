package mingetal.MCM.ordenesdecompra;

import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.service.OrdenesDeCompraClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrdenesDeCompraClienteServiceTest {
    @Autowired
    private OrdenesDeCompraClienteService ordenesDeCompraClienteService;

    //-------------------- save --------------------

    @Test
    void guardarOCClienteTestTrue(){

        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "No Emitida",
                "No Pagado",
                100000,
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-02"),
                "No Entregado",
                "Efectivo",
                LocalDate.parse("2024-05-02"),
                30,
                "15888226",
                "12255",
                "SoyYoUnaPrueba",
                LocalDate.parse("2024-06-25")
        );
        assertEquals(ordenesDeCompraClienteEntity, ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity));
        assertEquals(ordenesDeCompraClienteEntity, ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId()));
        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void guardarOCClienteTestFalse(){

        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "No Emitida",
                "No Pagado",
                100000,
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-02"),
                "No Entregado",
                "Efectivo",
                LocalDate.parse("2024-05-02"),
                30,
                "15888226",
                "12255",
                "SoyYoUnaPrueba",
                LocalDate.parse("2024-06-25")
        );
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);
        assertNull(ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity));
        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }

    //-------------------- findAll --------------------



    //-------------------- findById --------------------

    @Test
    void findByIdTestTrue(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "No Emitida",
                "No Pagado",
                100000,
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-02"),
                "No Entregado",
                "Efectivo",
                LocalDate.parse("2024-05-02"),
                30,
                "15888226",
                "12255",
                "SoyYoUnaPrueba",
                LocalDate.parse("2024-06-25")
        );
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);

        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntityGetId = ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId());
        assertEquals(ordenesDeCompraClienteEntityGetId.getId(), ordenesDeCompraClienteEntity.getId());
        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void findByIdTestFalse(){
        assertNull(ordenesDeCompraClienteService.findById(101));
    }

    //-------------------- findByIdCliente --------------------

    @Test
    void findByIdClienteTestTrue(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "No Emitida",
                "No Pagado",
                100000,
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-02"),
                "No Entregado",
                "Efectivo",
                LocalDate.parse("2024-05-02"),
                30,
                "15888226",
                "12255",
                "SoyYoUnaPrueba",
                LocalDate.parse("2024-06-25")
        );
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);

        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = ordenesDeCompraClienteService.findByIdCliente(ordenesDeCompraClienteEntity.getId_cliente());
        assertFalse(ordenesDeCompraClienteEntities.isEmpty());
        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void findByIdClienteTestFalse(){
        assertTrue(ordenesDeCompraClienteService.findByIdCliente("-2").isEmpty());
    }

    //-------------------- findByNameCliente --------------------



    //-------------------- findByEmpresaCliente --------------------



    //-------------------- delete --------------------

    @Test
    void deleteOCClienteTestTrue(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "No Emitida",
                "No Pagado",
                100000,
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-02"),
                "No Entregado",
                "Efectivo",
                LocalDate.parse("2024-05-02"),
                30,
                "15888226",
                "12255",
                "SoyYoUnaPrueba",
                LocalDate.parse("2024-06-25")
        );
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);
        assertEquals(ordenesDeCompraClienteEntity, ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId()));
        assertNull(ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId()));
    }
    @Test
    void deleteOCClienteTestFalse(){
        assertNull(ordenesDeCompraClienteService.deleteOCCliente(-1));
    }

    //-------------------- updateOCCliente --------------------
}
