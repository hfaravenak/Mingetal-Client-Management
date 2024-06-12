package mingetal.MCM.ordenesdecompra;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.service.ListaProductosOCProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ListaProductosOCProveedorServiceTest {
    @Autowired
    private ListaProductosOCProveedorService listaProductosOCProveedorService;

    //-------------------- save --------------------

    @Test
    void guardarListProductosTestTrue(){
        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                -2,
                -1,
                5,
                300
        );

        assertNotNull(listaProductosOCProveedorService.save(listaProductosOCProveedorEntity));
        assertEquals(listaProductosOCProveedorEntity, listaProductosOCProveedorService.findById(listaProductosOCProveedorEntity.getId()));

        listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId());
    }
    @Test
    void guardarListProductosTestFalse(){
        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                -2,
                -1,
                5,
                300
        );
        listaProductosOCProveedorService.save(listaProductosOCProveedorEntity);
        assertNull(listaProductosOCProveedorService.save(listaProductosOCProveedorEntity));

        listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId());
    }

    //-------------------- findById --------------------

    @Test
    void findByIdTestTrue(){
        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                -2,
                -1,
                5,
                300
        );
        listaProductosOCProveedorService.save(listaProductosOCProveedorEntity);

        ListaProductosOCProveedorEntity listaProductosOCProveedorEntityGetId = listaProductosOCProveedorService.findById(listaProductosOCProveedorEntity.getId());
        assertEquals(listaProductosOCProveedorEntityGetId.getId(), listaProductosOCProveedorEntity.getId());

        listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId());
    }
    @Test
    void findByIdTestFalse(){
        assertNull(listaProductosOCProveedorService.findById(-1));
    }

    //-------------------- findByIdOCProveedor --------------------

    @Test
    void findByIdProveedorTestTrue(){
        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                -2,
                -1,
                5,
                300
        );
        listaProductosOCProveedorService.save(listaProductosOCProveedorEntity);

        assertFalse(listaProductosOCProveedorService.findByIdOCProveedor(listaProductosOCProveedorEntity.getId_OC_proveedor()).isEmpty());

        listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId());
    }
    @Test
    void findByIdProveedorTestFalse(){
        assertTrue(listaProductosOCProveedorService.findByIdOCProveedor(-2).isEmpty());
    }

    //-------------------- delete --------------------

    @Test
    void deleteListProductosTestTrue(){
        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                -2,
                -1,
                5,
                300
        );
        listaProductosOCProveedorService.save(listaProductosOCProveedorEntity);

        assertEquals(listaProductosOCProveedorEntity, listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId()));
        assertNull(listaProductosOCProveedorService.findById(listaProductosOCProveedorEntity.getId()));
    }
    @Test
    void deleteListProductosTestFalse(){
        assertNull(listaProductosOCProveedorService.delete(-1));
    }
}
