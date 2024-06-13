package mingetal.MCM.ordenesdecompra;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.service.ListaProductosOCProveedorService;
import mingetal.MCM.ordenesdecompra.service.OrdenesDeCompraProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ListaProductosOCProveedorServiceTest {
    @Autowired
    private ListaProductosOCProveedorService listaProductosOCProveedorService;

    @Autowired
    private OrdenesDeCompraProveedorService ordenesDeCompraProveedorService;

    //-------------------- save --------------------

    @Test
    void guardarListProductosTestTrue(){
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

        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                ordenesDeCompraProveedorEntity.getId(),
                -1,
                5,
                300
        );

        assertNotNull(listaProductosOCProveedorService.save(listaProductosOCProveedorEntity));
        assertEquals(listaProductosOCProveedorEntity.getId(), listaProductosOCProveedorService.findById(listaProductosOCProveedorEntity.getId()).getId());

        listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId());
        ordenesDeCompraProveedorService.delete(ordenesDeCompraProveedorEntity.getId());
    }
    @Test
    void guardarListProductosTestFalse(){
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

        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                ordenesDeCompraProveedorEntity.getId(),
                -1,
                5,
                300
        );
        listaProductosOCProveedorService.save(listaProductosOCProveedorEntity);
        assertNull(listaProductosOCProveedorService.save(listaProductosOCProveedorEntity));

        listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId());
        ordenesDeCompraProveedorService.delete(ordenesDeCompraProveedorEntity.getId());
    }

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

        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                ordenesDeCompraProveedorEntity.getId(),
                -1,
                5,
                300
        );
        listaProductosOCProveedorService.save(listaProductosOCProveedorEntity);

        ListaProductosOCProveedorEntity listaProductosOCProveedorEntityGetId = listaProductosOCProveedorService.findById(listaProductosOCProveedorEntity.getId());
        assertEquals(listaProductosOCProveedorEntityGetId.getId(), listaProductosOCProveedorEntity.getId());

        listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId());
        ordenesDeCompraProveedorService.delete(ordenesDeCompraProveedorEntity.getId());
    }
    @Test
    void findByIdTestFalse(){
        assertNull(listaProductosOCProveedorService.findById(-1));
    }

    //-------------------- findByIdOCProveedor --------------------

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
        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                ordenesDeCompraProveedorEntity.getId(),
                -1,
                5,
                300
        );
        listaProductosOCProveedorService.save(listaProductosOCProveedorEntity);

        assertFalse(listaProductosOCProveedorService.findByIdOCProveedor(listaProductosOCProveedorEntity.getId_OC_proveedor()).isEmpty());

        listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId());
        ordenesDeCompraProveedorService.delete(ordenesDeCompraProveedorEntity.getId());
    }
    @Test
    void findByIdProveedorTestFalse(){
        assertTrue(listaProductosOCProveedorService.findByIdOCProveedor(-2).isEmpty());
    }

    //-------------------- delete --------------------

    @Test
    void deleteListProductosTestTrue(){
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

        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                ordenesDeCompraProveedorEntity.getId(),
                -1,
                5,
                300
        );
        listaProductosOCProveedorService.save(listaProductosOCProveedorEntity);

        assertEquals(listaProductosOCProveedorEntity.getId(), listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId()).getId());
        assertNull(listaProductosOCProveedorService.findById(listaProductosOCProveedorEntity.getId()));
        ordenesDeCompraProveedorService.delete(ordenesDeCompraProveedorEntity.getId());
    }
    @Test
    void deleteListProductosTestFalse(){
        assertNull(listaProductosOCProveedorService.delete(-1));
    }
}
