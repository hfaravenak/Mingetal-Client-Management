package mingetal.MCM.ordenesdecompra;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.service.ListaProductosOCClienteService;
import mingetal.MCM.ordenesdecompra.service.ListaProductosOCProveedorService;
import mingetal.MCM.ordenesdecompra.service.OrdenesDeCompraClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ListaProductosOCClienteServiceTest {
    @Autowired
    private ListaProductosOCClienteService listaProductosOCClienteService;
    @Autowired
    private OrdenesDeCompraClienteService ordenesDeCompraClienteService;

    //-------------------- save --------------------

    @Test
    void guardarListProductosTestTrue(){
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
        ListaProductosOCClienteEntity listaProductosOCClienteEntity = new ListaProductosOCClienteEntity(
                ordenesDeCompraClienteEntity.getId(),
                -1,
                5,
                300
        );

        assertNotNull(listaProductosOCClienteService.save(listaProductosOCClienteEntity));
        assertEquals(listaProductosOCClienteEntity.getId(), listaProductosOCClienteService.findById(listaProductosOCClienteEntity.getId()).getId());

        listaProductosOCClienteService.delete(listaProductosOCClienteEntity.getId());
        ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void guardarListProductosTestFalse(){
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
        ListaProductosOCClienteEntity listaProductosOCClienteEntity = new ListaProductosOCClienteEntity(
                ordenesDeCompraClienteEntity.getId(),
                -1,
                5,
                300
        );
        listaProductosOCClienteService.save(listaProductosOCClienteEntity);
        assertNull(listaProductosOCClienteService.save(listaProductosOCClienteEntity));

        listaProductosOCClienteService.delete(listaProductosOCClienteEntity.getId());
        ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity.getId());
    }

    //-------------------- findAll --------------------

    @Test
    void findAllTest() {
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity1 = new OrdenesDeCompraClienteEntity(
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
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity2 = new OrdenesDeCompraClienteEntity(
                "-2",
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
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity1);
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity2);

        ListaProductosOCClienteEntity listaProductosOCClienteEntity1 = new ListaProductosOCClienteEntity(
                ordenesDeCompraClienteEntity1.getId(),
                -1,
                5,
                300
        );
        ListaProductosOCClienteEntity listaProductosOCClienteEntity2 = new ListaProductosOCClienteEntity(
                ordenesDeCompraClienteEntity2.getId(),
                -1,
                5,
                300
        );
        listaProductosOCClienteService.save(listaProductosOCClienteEntity1);
        listaProductosOCClienteService.save(listaProductosOCClienteEntity2);

        listaProductosOCClienteEntity1 = listaProductosOCClienteService.findById(listaProductosOCClienteEntity1.getId());
        listaProductosOCClienteEntity2 = listaProductosOCClienteService.findById(listaProductosOCClienteEntity2.getId());

        assertFalse(listaProductosOCClienteService.findAll().isEmpty());
        assertTrue(listaProductosOCClienteService.findAll().contains(listaProductosOCClienteEntity1));
        assertTrue(listaProductosOCClienteService.findAll().contains(listaProductosOCClienteEntity2));

        listaProductosOCClienteService.delete(listaProductosOCClienteEntity1.getId());
        listaProductosOCClienteService.delete(listaProductosOCClienteEntity2.getId());
        ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity1.getId());
        ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity2.getId());
    }

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
        ListaProductosOCClienteEntity listaProductosOCClienteEntity = new ListaProductosOCClienteEntity(
                ordenesDeCompraClienteEntity.getId(),
                -1,
                5,
                300
        );
        listaProductosOCClienteService.save(listaProductosOCClienteEntity);
        assertEquals(listaProductosOCClienteEntity.getId(), listaProductosOCClienteService.findById(listaProductosOCClienteEntity.getId()).getId());

        listaProductosOCClienteService.delete(listaProductosOCClienteEntity.getId());
        ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void findByIdTestFalse(){
        assertNull(listaProductosOCClienteService.findById(-1));
    }

    //-------------------- findByIdOCCliente --------------------

    @Test
    void findByIdProveedorTestTrue(){
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
        ListaProductosOCClienteEntity listaProductosOCClienteEntity = new ListaProductosOCClienteEntity(
                ordenesDeCompraClienteEntity.getId(),
                -1,
                5,
                300
        );
        listaProductosOCClienteService.save(listaProductosOCClienteEntity);

        assertFalse(listaProductosOCClienteService.findByIdOCCliente(listaProductosOCClienteEntity.getId_OC_cliente()).isEmpty());

        listaProductosOCClienteService.delete(listaProductosOCClienteEntity.getId());
        ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void findByIdProveedorTestFalse(){
        assertTrue(listaProductosOCClienteService.findByIdOCCliente(-2).isEmpty());
    }

    //-------------------- delete --------------------

    @Test
    void deleteListProductosTestTrue(){
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
        ListaProductosOCClienteEntity listaProductosOCClienteEntity = new ListaProductosOCClienteEntity(
                ordenesDeCompraClienteEntity.getId(),
                -1,
                5,
                300
        );
        listaProductosOCClienteService.save(listaProductosOCClienteEntity);

        assertEquals(listaProductosOCClienteEntity.getId(), listaProductosOCClienteService.delete(listaProductosOCClienteEntity.getId()).getId());
        assertNull(listaProductosOCClienteService.findById(listaProductosOCClienteEntity.getId()));
        ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void deleteListProductosTestFalse(){
        assertNull(listaProductosOCClienteService.delete(-1));
    }
}
