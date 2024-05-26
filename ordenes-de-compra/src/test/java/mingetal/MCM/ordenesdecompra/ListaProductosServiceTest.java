package mingetal.MCM.ordenesdecompra;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.service.ListaProductosService;
import mingetal.MCM.ordenesdecompra.service.OrdenesDeCompraProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ListaProductosServiceTest {
    @Autowired
    private ListaProductosService listaProductosService;

    @Test
    void guardarListProductosTestTrue(){
        ListaProductosEntity listaProductosEntity = new ListaProductosEntity(
                -3,
                -1,
                -2,
                5,
                300,
                -4
        );

        assertEquals(listaProductosEntity, listaProductosService.save(listaProductosEntity));
        listaProductosService.delete(listaProductosEntity.getId());
    }
    @Test
    void guardarListProductosTestFalse(){
        ListaProductosEntity listaProductosEntity = new ListaProductosEntity(
                -3,
                -1,
                -2,
                5,
                300,
                -4
        );
        listaProductosService.save(listaProductosEntity);
        assertNull(listaProductosService.save(listaProductosEntity));
        listaProductosService.delete(listaProductosEntity.getId());
    }

    @Test
    void deleteListProductosTestTrue(){
        ListaProductosEntity listaProductosEntity = new ListaProductosEntity(
                -3,
                -1,
                -2,
                5,
                300,
                -4
        );
        listaProductosService.save(listaProductosEntity);
        assertEquals(listaProductosEntity, listaProductosService.delete(listaProductosEntity.getId()));
        assertNull(listaProductosService.findById(listaProductosEntity.getId()));
    }
    @Test
    void deleteListProductosTestFalse(){
        assertNull(listaProductosService.delete(101));
    }

    @Test
    void findByIdTestTrue(){
        ListaProductosEntity listaProductosEntity = new ListaProductosEntity(
                -3,
                -1,
                -2,
                5,
                300,
                -4
        );
        listaProductosService.save(listaProductosEntity);

        ListaProductosEntity listaProductosEntityGetId = listaProductosService.findById(listaProductosEntity.getId());
        assertEquals(listaProductosEntityGetId.getId(), listaProductosEntity.getId());
        listaProductosService.delete(listaProductosEntity.getId());
    }
    @Test
    void findByIdTestFalse(){
        assertNull(listaProductosService.findById(101));
    }

    @Test
    void findByIdClienteTestTrue(){
        ListaProductosEntity listaProductosEntity = new ListaProductosEntity(
                -3,
                -1,
                -2,
                5,
                300,
                -4
        );
        listaProductosService.save(listaProductosEntity);

        assertFalse(listaProductosService.findByIdOCCliente(listaProductosEntity.getId_OC_cliente()).isEmpty());

        listaProductosService.delete(listaProductosEntity.getId());
    }
    @Test
    void findByIdClienteTestFalse(){
        assertTrue(listaProductosService.findByIdOCCliente(-1).isEmpty());
    }

    @Test
    void findByIdProductoTestTrue(){
        ListaProductosEntity listaProductosEntity = new ListaProductosEntity(
                -3,
                -1,
                -2,
                5,
                300,
                -4
        );
        listaProductosService.save(listaProductosEntity);

        assertFalse(listaProductosService.findByIdProducto(listaProductosEntity.getId_producto()).isEmpty());

        listaProductosService.delete(listaProductosEntity.getId());
    }
    @Test
    void findByIdProductoTestFalse(){
        assertTrue(listaProductosService.findByIdProducto(-1).isEmpty());
    }

    @Test
    void findByIdProveedorTestTrue(){
        ListaProductosEntity listaProductosEntity = new ListaProductosEntity(
                -3,
                -1,
                -2,
                5,
                300,
                -4
        );
        listaProductosService.save(listaProductosEntity);

        assertFalse(listaProductosService.findByIdOCProveedor(listaProductosEntity.getId_OC_proveedor()).isEmpty());

        listaProductosService.delete(listaProductosEntity.getId());
    }
    @Test
    void findByIdProveedorTestFalse(){
        assertTrue(listaProductosService.findByIdOCProveedor(-2).isEmpty());
    }

    @Test
    void updateOCProveedorByEstadoPagoTestTrue(){
        ListaProductosEntity listaProductosEntity = new ListaProductosEntity(
                -3,
                -1,
                -2,
                5,
                300,
                -4
        );
        listaProductosService.save(listaProductosEntity);

        assertNotNull(listaProductosService.updateCantidad(listaProductosEntity.getId(), 10));
        assertEquals(10, listaProductosService.findById(listaProductosEntity.getId()).getCantidad());

        listaProductosService.delete(listaProductosEntity.getId());
    }
    @Test
    void updateOCProveedorByEstadoPagoTestFalse(){
        assertNull(listaProductosService.updateCantidad(101,10));
    }

}
