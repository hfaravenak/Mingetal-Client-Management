package mingetal.MCM.ordenesdecompra;

import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.service.OrdenesDeCompraProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrdenesDeCompraProveedorServiceTest {
    @Autowired
    private OrdenesDeCompraProveedorService ordenesDeCompraProveedorService;

    @Test
    void guardarOCProveedorTestTrue(){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                -2,
                "No Pagado",
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-30"),
                "b55c2214"
        );

        boolean bool = ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);
        assertTrue(bool);
        ordenesDeCompraProveedorService.deleteOCProveedor(ordenesDeCompraProveedorEntity.getId());
    }
    @Test
    void guardarOCProveedorTestFalse(){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                -2,
                "No Pagado",
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-30"),
                "b55c2214"
        );
        ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);
        boolean bool = ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);
        assertFalse(bool);
        ordenesDeCompraProveedorService.deleteOCProveedor(ordenesDeCompraProveedorEntity.getId());
    }

    @Test
    void deleteOCClienteTestTrue(){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                -2,
                "No Pagado",
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-30"),
                "b55c2214"
        );
        ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);
        assertEquals(ordenesDeCompraProveedorEntity, ordenesDeCompraProveedorService.deleteOCProveedor(ordenesDeCompraProveedorEntity.getId()));
        assertNull(ordenesDeCompraProveedorService.findById(ordenesDeCompraProveedorEntity.getId()));
    }
    @Test
    void deleteOCClienteTestFalse(){
        assertNull(ordenesDeCompraProveedorService.deleteOCProveedor(101));
    }

    @Test
    void findByIdTestTrue(){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                -2,
                "No Pagado",
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-30"),
                "b55c2214"
        );
        ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);

        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntityGetId = ordenesDeCompraProveedorService.findById(ordenesDeCompraProveedorEntity.getId());
        assertEquals(ordenesDeCompraProveedorEntityGetId.getId(), ordenesDeCompraProveedorEntity.getId());
        ordenesDeCompraProveedorService.deleteOCProveedor(ordenesDeCompraProveedorEntity.getId());
    }
    @Test
    void findByIdTestFalse(){
        assertNull(ordenesDeCompraProveedorService.findById(101));
    }

    @Test
    void findByIdProveedorTestTrue(){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                -2,
                "No Pagado",
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-30"),
                "b55c2214"
        );
        ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);

        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findByIdProveedor(-2);
        assertFalse(ordenesDeCompraProveedorEntities.isEmpty());
        ordenesDeCompraProveedorService.deleteOCProveedor(ordenesDeCompraProveedorEntity.getId());
    }
    @Test
    void findByIdProveedorTestFalse(){
        assertTrue(ordenesDeCompraProveedorService.findByIdProveedor(-2).isEmpty());
    }

    @Test
    void updateOCProveedorByEstadoPagoTestTrue(){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                -2,
                "No Pagado",
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-30"),
                "b55c2214"
        );
        ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);

        assertNotNull(ordenesDeCompraProveedorService.updateOCProveedorByEstadoPago(ordenesDeCompraProveedorEntity.getId()));
        assertEquals("Pagado", ordenesDeCompraProveedorService.findById(ordenesDeCompraProveedorEntity.getId()).getEstado_pago());

        ordenesDeCompraProveedorService.deleteOCProveedor(ordenesDeCompraProveedorEntity.getId());
    }
    @Test
    void updateOCProveedorByEstadoPagoTestFalseYaPagada(){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                -2,
                "Pagado",
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-30"),
                "b55c2214"
        );
        ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);

        assertNull(ordenesDeCompraProveedorService.updateOCProveedorByEstadoPago(ordenesDeCompraProveedorEntity.getId()));

        ordenesDeCompraProveedorService.deleteOCProveedor(ordenesDeCompraProveedorEntity.getId());
    }
    @Test
    void updateOCProveedorByEstadoPagoTestFalse(){
        assertNull(ordenesDeCompraProveedorService.updateOCProveedorByEstadoPago(101));
    }

}
