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

    //-------------------- save --------------------

    @Test
    void guardarOCProveedorTestTrue(){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                -2,
                "No Pagado",
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-30"),
                LocalDate.parse("2024-05-10"),
                "b55c2214",
                500,
                "Entregado"
        );

        assertEquals(ordenesDeCompraProveedorEntity, ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity));
        assertEquals(ordenesDeCompraProveedorEntity, ordenesDeCompraProveedorService.findById(ordenesDeCompraProveedorEntity.getId()));
        ordenesDeCompraProveedorService.delete(ordenesDeCompraProveedorEntity.getId());
    }
    @Test
    void guardarOCProveedorTestFalse(){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                -2,
                "No Pagado",
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-30"),
                LocalDate.parse("2024-05-10"),
                "b55c2214",
                500,
                "Entregado"
        );
        ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);
        assertNull(ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity));
        ordenesDeCompraProveedorService.delete(ordenesDeCompraProveedorEntity.getId());
    }

    //-------------------- findAll --------------------

    //-------------------- findById --------------------

    @Test
    void findByIdTestTrue(){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                -2,
                "No Pagado",
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-30"),
                LocalDate.parse("2024-05-10"),
                "b55c2214",
                500,
                "Entregado"
        );
        ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);

        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntityGetId = ordenesDeCompraProveedorService.findById(ordenesDeCompraProveedorEntity.getId());
        assertEquals(ordenesDeCompraProveedorEntityGetId.getId(), ordenesDeCompraProveedorEntity.getId());
        ordenesDeCompraProveedorService.delete(ordenesDeCompraProveedorEntity.getId());
    }
    @Test
    void findByIdTestFalse(){
        assertNull(ordenesDeCompraProveedorService.findById(101));
    }

    //-------------------- findByNameProveedor --------------------

    //-------------------- findByRubro --------------------

    //-------------------- findByIdProveedor --------------------

    @Test
    void findByIdProveedorTestTrue(){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                -2,
                "No Pagado",
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-30"),
                LocalDate.parse("2024-05-10"),
                "b55c2214",
                500,
                "Entregado"
        );
        ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);

        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findByIdProveedor(-2);
        assertFalse(ordenesDeCompraProveedorEntities.isEmpty());
        ordenesDeCompraProveedorService.delete(ordenesDeCompraProveedorEntity.getId());
    }
    @Test
    void findByIdProveedorTestFalse(){
        assertTrue(ordenesDeCompraProveedorService.findByIdProveedor(-2).isEmpty());
    }

    //-------------------- delete --------------------

    @Test
    void deleteOCClienteTestTrue(){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = new OrdenesDeCompraProveedorEntity(
                -2,
                "No Pagado",
                LocalDate.parse("2024-05-25"),
                LocalDate.parse("2024-05-30"),
                LocalDate.parse("2024-05-10"),
                "b55c2214",
                500,
                "Entregado"
        );
        ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);
        assertEquals(ordenesDeCompraProveedorEntity, ordenesDeCompraProveedorService.delete(ordenesDeCompraProveedorEntity.getId()));
        assertNull(ordenesDeCompraProveedorService.findById(ordenesDeCompraProveedorEntity.getId()));
    }
    @Test
    void deleteOCClienteTestFalse(){
        assertNull(ordenesDeCompraProveedorService.delete(101));
    }

    //-------------------- update --------------------


}
