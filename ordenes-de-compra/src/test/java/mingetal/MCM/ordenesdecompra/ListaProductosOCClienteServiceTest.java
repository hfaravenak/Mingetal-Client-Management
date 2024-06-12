package mingetal.MCM.ordenesdecompra;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.service.ListaProductosOCClienteService;
import mingetal.MCM.ordenesdecompra.service.ListaProductosOCProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ListaProductosOCClienteServiceTest {
    @Autowired
    private ListaProductosOCClienteService listaProductosOCClienteService;

    //-------------------- save --------------------

    @Test
    void guardarListProductosTestTrue(){
        ListaProductosOCClienteEntity listaProductosOCClienteEntity = new ListaProductosOCClienteEntity(
                -2,
                -1,
                5,
                300
        );

        assertNotNull(listaProductosOCClienteService.save(listaProductosOCClienteEntity));
        assertEquals(listaProductosOCClienteEntity, listaProductosOCClienteService.findById(listaProductosOCClienteEntity.getId()));

        listaProductosOCClienteService.delete(listaProductosOCClienteEntity.getId());
    }
    @Test
    void guardarListProductosTestFalse(){
        ListaProductosOCClienteEntity listaProductosOCClienteEntity = new ListaProductosOCClienteEntity(
                -2,
                -1,
                5,
                300
        );
        listaProductosOCClienteService.save(listaProductosOCClienteEntity);
        assertNull(listaProductosOCClienteService.save(listaProductosOCClienteEntity));

        listaProductosOCClienteService.delete(listaProductosOCClienteEntity.getId());
    }

    //-------------------- findById --------------------

    @Test
    void findByIdTestTrue(){
        ListaProductosOCClienteEntity listaProductosOCClienteEntity = new ListaProductosOCClienteEntity(
                -2,
                -1,
                5,
                300
        );
        listaProductosOCClienteService.save(listaProductosOCClienteEntity);
        assertEquals(listaProductosOCClienteEntity, listaProductosOCClienteService.findById(listaProductosOCClienteEntity.getId()));

        listaProductosOCClienteService.delete(listaProductosOCClienteEntity.getId());
    }
    @Test
    void findByIdTestFalse(){
        assertNull(listaProductosOCClienteService.findById(-1));
    }

    //-------------------- findByIdOCCliente --------------------

    @Test
    void findByIdProveedorTestTrue(){
        ListaProductosOCClienteEntity listaProductosOCClienteEntity = new ListaProductosOCClienteEntity(
                -2,
                -1,
                5,
                300
        );
        listaProductosOCClienteService.save(listaProductosOCClienteEntity);

        assertFalse(listaProductosOCClienteService.findByIdOCCliente(listaProductosOCClienteEntity.getId_OC_cliente()).isEmpty());

        listaProductosOCClienteService.delete(listaProductosOCClienteEntity.getId());
    }
    @Test
    void findByIdProveedorTestFalse(){
        assertTrue(listaProductosOCClienteService.findByIdOCCliente(-2).isEmpty());
    }

    //-------------------- delete --------------------

    @Test
    void deleteListProductosTestTrue(){
        ListaProductosOCClienteEntity listaProductosOCClienteEntity = new ListaProductosOCClienteEntity(
                -2,
                -1,
                5,
                300
        );
        listaProductosOCClienteService.save(listaProductosOCClienteEntity);

        assertEquals(listaProductosOCClienteEntity, listaProductosOCClienteService.delete(listaProductosOCClienteEntity.getId()));
        assertNull(listaProductosOCClienteService.findById(listaProductosOCClienteEntity.getId()));
    }
    @Test
    void deleteListProductosTestFalse(){
        assertNull(listaProductosOCClienteService.delete(-1));
    }
}
