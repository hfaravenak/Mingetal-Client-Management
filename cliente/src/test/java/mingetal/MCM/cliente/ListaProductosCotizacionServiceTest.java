package mingetal.MCM.cliente;

import mingetal.MCM.cliente.entities.ListaProductosCotizacionEntity;
import mingetal.MCM.cliente.services.ListaProductosCotizacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ListaProductosCotizacionServiceTest {
    @Autowired
    private ListaProductosCotizacionService listaProductosCotizacionService;

    //-------------------- save --------------------

    @Test
    void guardarListProductosTestTrue(){
        ListaProductosCotizacionEntity listaProductosCotizacionEntity = new ListaProductosCotizacionEntity(
                -2,
                -1,
                5,
                300
        );

        assertNotNull(listaProductosCotizacionService.save(listaProductosCotizacionEntity));
        assertEquals(listaProductosCotizacionEntity, listaProductosCotizacionService.findById(listaProductosCotizacionEntity.getId()));

        listaProductosCotizacionService.delete(listaProductosCotizacionEntity.getId());
    }
    @Test
    void guardarListProductosTestFalse(){
        ListaProductosCotizacionEntity listaProductosCotizacionEntity = new ListaProductosCotizacionEntity(
                -2,
                -1,
                5,
                300
        );
        listaProductosCotizacionService.save(listaProductosCotizacionEntity);
        assertNull(listaProductosCotizacionService.save(listaProductosCotizacionEntity));

        listaProductosCotizacionService.delete(listaProductosCotizacionEntity.getId());
    }

    //-------------------- findById --------------------

    @Test
    void findByIdTestTrue(){
        ListaProductosCotizacionEntity listaProductosCotizacionEntity = new ListaProductosCotizacionEntity(
                -2,
                -1,
                5,
                300
        );
        listaProductosCotizacionService.save(listaProductosCotizacionEntity);

        ListaProductosCotizacionEntity listaProductosOCProveedorEntityGetId = listaProductosCotizacionService.findById(listaProductosCotizacionEntity.getId());
        assertEquals(listaProductosOCProveedorEntityGetId.getId(), listaProductosCotizacionEntity.getId());

        listaProductosCotizacionService.delete(listaProductosCotizacionEntity.getId());
    }
    @Test
    void findByIdTestFalse(){
        assertNull(listaProductosCotizacionService.findById(-1));
    }

    //-------------------- findByIdCotizacion --------------------

    @Test
    void findByIdProveedorTestTrue(){
        ListaProductosCotizacionEntity listaProductosCotizacionEntity = new ListaProductosCotizacionEntity(
                -2,
                -1,
                5,
                300
        );
        listaProductosCotizacionService.save(listaProductosCotizacionEntity);

        assertFalse(listaProductosCotizacionService.findByIdCotizacion(listaProductosCotizacionEntity.getId_cotizacion()).isEmpty());

        listaProductosCotizacionService.delete(listaProductosCotizacionEntity.getId());
    }
    @Test
    void findByIdProveedorTestFalse(){
        assertTrue(listaProductosCotizacionService.findByIdCotizacion(-2).isEmpty());
    }

    //-------------------- delete --------------------

    @Test
    void deleteListProductosTestTrue(){
        ListaProductosCotizacionEntity listaProductosCotizacionEntity = new ListaProductosCotizacionEntity(
                -2,
                -1,
                5,
                300
        );
        listaProductosCotizacionService.save(listaProductosCotizacionEntity);

        assertEquals(listaProductosCotizacionEntity, listaProductosCotizacionService.delete(listaProductosCotizacionEntity.getId()));
        assertNull(listaProductosCotizacionService.findById(listaProductosCotizacionEntity.getId()));
    }
    @Test
    void deleteListProductosTestFalse(){
        assertNull(listaProductosCotizacionService.delete(-1));
    }
}
