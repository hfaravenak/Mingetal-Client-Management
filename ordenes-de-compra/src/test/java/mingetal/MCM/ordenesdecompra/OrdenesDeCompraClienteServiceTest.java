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
        ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity.getId());
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

        assertFalse(ordenesDeCompraClienteService.findAll().isEmpty());
        assertTrue(ordenesDeCompraClienteService.findAll().contains(ordenesDeCompraClienteEntity1));
        assertTrue(ordenesDeCompraClienteService.findAll().contains(ordenesDeCompraClienteEntity2));

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

        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntityGetId = ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId());
        assertEquals(ordenesDeCompraClienteEntityGetId.getId(), ordenesDeCompraClienteEntity.getId());
        ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity.getId());
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
        ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity.getId());
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
        assertEquals(ordenesDeCompraClienteEntity, ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity.getId()));
        assertNull(ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId()));
    }
    @Test
    void deleteOCClienteTestFalse(){
        assertNull(ordenesDeCompraClienteService.delete(-1));
    }

    //-------------------- updateOCCliente --------------------

    @Test
    void updateOCClienteTestTrue(){
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

        ordenesDeCompraClienteEntity.setEstado_entrega("Pagado");
        ordenesDeCompraClienteEntity.setValor_pago(20000);

        assertEquals(ordenesDeCompraClienteEntity, ordenesDeCompraClienteService.update(ordenesDeCompraClienteEntity));
        assertEquals(ordenesDeCompraClienteEntity, ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId()));

        ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void updateOCClienteTestFalse(){
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

        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntit2 = new OrdenesDeCompraClienteEntity(
                "-1",
                "Emitida",
                "Pagado",
                200000,
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-02"),
                "Entregado",
                "Efectivo",
                LocalDate.parse("2024-05-02"),
                30,
                "15888226",
                "12255",
                "SoyYoUnaPrueba",
                LocalDate.parse("2024-06-25")
        );

        assertNull(ordenesDeCompraClienteService.update(ordenesDeCompraClienteEntit2));

        ordenesDeCompraClienteService.delete(ordenesDeCompraClienteEntity.getId());
    }

    //-------------------- getProductByYear --------------------


    //-------------------- getProductByYearAndMonth --------------------


    //-------------------- getSalesByYear --------------------


    //-------------------- getSalesByYearAndMonth --------------------


    //-------------------- getClientsByYear --------------------



}
