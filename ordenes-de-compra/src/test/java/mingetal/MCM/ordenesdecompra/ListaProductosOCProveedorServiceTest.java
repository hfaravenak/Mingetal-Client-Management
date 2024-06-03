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

    @Test
    void guardarListProductosTestTrue(){
        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                -2,
                -1,
                5,
                300
        );
        List<ListaProductosOCProveedorEntity> listaProductosEntities = new ArrayList<>();
        listaProductosEntities.add(listaProductosOCProveedorEntity);

        List<ListaProductosOCProveedorEntity> listaProductosEntities1 = listaProductosOCProveedorService.save(listaProductosEntities);
        assertEquals(1, listaProductosEntities1.size());
        assertEquals(listaProductosOCProveedorEntity, listaProductosEntities1.get(0));
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
        List<ListaProductosOCProveedorEntity> listaProductosEntities = new ArrayList<>();
        listaProductosEntities.add(listaProductosOCProveedorEntity);
        listaProductosOCProveedorService.save(listaProductosEntities);
        assertTrue(listaProductosOCProveedorService.save(listaProductosEntities).isEmpty());
        listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId());
    }

    @Test
    void deleteListProductosTestTrue(){
        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                -2,
                -1,
                5,
                300
        );
        List<ListaProductosOCProveedorEntity> listaProductosEntities = new ArrayList<>();
        listaProductosEntities.add(listaProductosOCProveedorEntity);
        listaProductosOCProveedorService.save(listaProductosEntities);
        assertEquals(listaProductosOCProveedorEntity, listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId()));
        assertNull(listaProductosOCProveedorService.findById(listaProductosOCProveedorEntity.getId()));
    }
    @Test
    void deleteListProductosTestFalse(){
        assertNull(listaProductosOCProveedorService.delete(101));
    }

    @Test
    void findByIdTestTrue(){
        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                -2,
                -1,
                5,
                300
        );
        List<ListaProductosOCProveedorEntity> listaProductosEntities = new ArrayList<>();
        listaProductosEntities.add(listaProductosOCProveedorEntity);
        listaProductosOCProveedorService.save(listaProductosEntities);

        ListaProductosOCProveedorEntity listaProductosOCProveedorEntityGetId = listaProductosOCProveedorService.findById(listaProductosOCProveedorEntity.getId());
        assertEquals(listaProductosOCProveedorEntityGetId.getId(), listaProductosOCProveedorEntity.getId());
        listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId());
    }
    @Test
    void findByIdTestFalse(){
        assertNull(listaProductosOCProveedorService.findById(101));
    }

    @Test
    void findByIdProveedorTestTrue(){
        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = new ListaProductosOCProveedorEntity(
                -2,
                -1,
                5,
                300
        );
        List<ListaProductosOCProveedorEntity> listaProductosEntities = new ArrayList<>();
        listaProductosEntities.add(listaProductosOCProveedorEntity);
        listaProductosOCProveedorService.save(listaProductosEntities);

        assertFalse(listaProductosOCProveedorService.findByIdOCProveedor(listaProductosOCProveedorEntity.getId_OC_proveedor()).isEmpty());

        listaProductosOCProveedorService.delete(listaProductosOCProveedorEntity.getId());
    }
    @Test
    void findByIdProveedorTestFalse(){
        assertTrue(listaProductosOCProveedorService.findByIdOCProveedor(-2).isEmpty());
    }

}
