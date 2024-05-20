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
        boolean bool = ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);
        assertTrue(bool);
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
        boolean bool = ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);
        assertFalse(bool);
        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }

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
        assertNull(ordenesDeCompraClienteService.deleteOCCliente(101));
    }

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

    @Test
    void findByNameClienteTestTrue(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "94.456.245-9",
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

        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = ordenesDeCompraClienteService.findByNameCliente("Jonathan Tilberry");
        assertFalse(ordenesDeCompraClienteEntities.isEmpty());
        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void findByNameClienteTestFalse(){
        assertTrue(ordenesDeCompraClienteService.findByNameCliente("Jonathan Tilberry").isEmpty());
    }


    @Test
    void updateOCClienteByEstadoFacturaTestTrue(){
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

        assertNotNull(ordenesDeCompraClienteService.updateOCClienteByEstadoFactura(ordenesDeCompraClienteEntity.getId()));

        assertEquals("Emitida", ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId()).getEstado_factura());

        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void updateOCClienteByEstadoFacturaTestFalseYaEmitida(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "Emitida",
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

        assertNull(ordenesDeCompraClienteService.updateOCClienteByEstadoFactura(ordenesDeCompraClienteEntity.getId()));

        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void updateOCClienteByEstadoFacturaTestFalse(){
        assertNull(ordenesDeCompraClienteService.updateOCClienteByEstadoFactura(101));
    }

    @Test
    void updateOCClienteByEstadoPagoTestTrue(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "Emitida",
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

        assertNotNull(ordenesDeCompraClienteService.updateOCClienteByEstadoPago(ordenesDeCompraClienteEntity.getId()));
        assertEquals("Pagado", ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId()).getEstado_pago());

        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void updateOCClienteByEstadoPagoTestFalseYaPagada(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "Emitida",
                "Pagado",
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

        assertNull(ordenesDeCompraClienteService.updateOCClienteByEstadoPago(ordenesDeCompraClienteEntity.getId()));

        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void updateOCClienteByEstadoPagoTestFalse(){
        assertNull(ordenesDeCompraClienteService.updateOCClienteByEstadoPago(101));
    }

    @Test
    void updateOCClienteByEstadoEntregaTestTrue(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "Emitida",
                "Pagado",
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

        assertNotNull(ordenesDeCompraClienteService.updateOCClienteByEstadoEntrega(ordenesDeCompraClienteEntity.getId()));

        assertEquals("Entregado", ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId()).getEstado_entrega());


        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void updateOCClienteByEstadoEntregaTestFalseYaEntregada(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "Emitida",
                "Pagado",
                100000,
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
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);

        assertNull(ordenesDeCompraClienteService.updateOCClienteByEstadoEntrega(ordenesDeCompraClienteEntity.getId()));

        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void updateOCClienteByEstadoEntregaTestFalse(){
        assertNull(ordenesDeCompraClienteService.updateOCClienteByEstadoEntrega(101));
    }

    @Test
    void updateOCClienteByModoPagoTestTrue_1(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "Emitida",
                "Pagado",
                100000,
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
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);

        assertNotNull(ordenesDeCompraClienteService.updateOCClienteByModoPago(ordenesDeCompraClienteEntity.getId(), 1));

        assertEquals("Transferencia", ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId()).getModo_pago());


        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void updateOCClienteByModoPagoTestTrue_2(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "Emitida",
                "Pagado",
                100000,
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
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);

        assertNotNull(ordenesDeCompraClienteService.updateOCClienteByModoPago(ordenesDeCompraClienteEntity.getId(), 2));

        assertEquals("Cheque", ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId()).getModo_pago());


        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void updateOCClienteByModoPagoTestTrue_3(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "Emitida",
                "Pagado",
                100000,
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
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);

        assertNotNull(ordenesDeCompraClienteService.updateOCClienteByModoPago(ordenesDeCompraClienteEntity.getId(), 3));

        assertEquals("Efectivo", ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId()).getModo_pago());


        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void updateOCClienteByModoPagoTestTrue_otro(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "Emitida",
                "Pagado",
                100000,
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
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);

        assertNull(ordenesDeCompraClienteService.updateOCClienteByModoPago(ordenesDeCompraClienteEntity.getId(), 4));

        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void updateOCClienteByModoPagoTestFalse(){
        assertNull(ordenesDeCompraClienteService.updateOCClienteByModoPago(101, 2));

    }

    @Test
    void updateOCClienteByFechaPagoTestTrue(){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = new OrdenesDeCompraClienteEntity(
                "-1",
                "Emitida",
                "Pagado",
                100000,
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
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);

        assertNotNull(ordenesDeCompraClienteService.updateOCClienteByFechaPago(ordenesDeCompraClienteEntity.getId(), LocalDate.parse("2024-05-05")));
        assertEquals(LocalDate.parse("2024-05-05"), ordenesDeCompraClienteService.findById(ordenesDeCompraClienteEntity.getId()).getFecha_pago());
        ordenesDeCompraClienteService.deleteOCCliente(ordenesDeCompraClienteEntity.getId());
    }
    @Test
    void updateOCClienteByFechaPagoTestFalse(){
        assertNull(ordenesDeCompraClienteService.updateOCClienteByFechaPago(101, LocalDate.parse("2024-05-05")));
    }

}
