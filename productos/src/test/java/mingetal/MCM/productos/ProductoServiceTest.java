package mingetal.MCM.productos;

import mingetal.MCM.productos.entity.ProductosEntity;
import mingetal.MCM.productos.service.ProductosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductoServiceTest {
    @Autowired
    private ProductosService productosService;

    @Test
    void guardarProductoTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity("Quimico", "prueba", 30000, 50000, 5);
        boolean bool = productosService.save(productosEntity);
        assertTrue(bool);
        productosService.deleteProductos(productosEntity.getId());
    }

    @Test
    void guardarProductoTestFail(){
        ProductosEntity productosEntity = new ProductosEntity("Quimico", "prueba", 30000, 50000, 5);
        productosService.save(productosEntity);
        boolean bool = productosService.save(productosEntity);
        assertFalse(bool);
        productosService.deleteProductos(productosEntity.getId());
    }

    @Test
    void deleteProductoTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity("Quimico", "prueba", 30000, 50000, 5);
        productosService.save(productosEntity);
        assertEquals(productosEntity, productosService.deleteProductos(productosEntity.getId()));
        assertNull(productosService.findById(productosEntity.getId()));
    }

    @Test
    void deleteProductoTestFail(){
        assertNull(productosService.deleteProductos(101));
    }

    @Test
    void findByIdTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity("Quimico", "prueba", 30000, 50000, 5);
        productosService.save(productosEntity);
        ProductosEntity productosEntityGetId = productosService.findById(productosEntity.getId());
        assertEquals(productosEntityGetId.getId(), productosEntity.getId());
        productosService.deleteProductos(productosEntity.getId());
    }
    @Test
    void findByIdTestFalse(){
        assertNull(productosService.findById(101));
    }

    @Test
    void findByTipoTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity("Quimico", "prueba", 30000, 50000, 5);
        productosService.save(productosEntity);
        List<ProductosEntity> productosEntities = productosService.findByTipo("Quimico");
        assertFalse(productosEntities.isEmpty());
        productosService.deleteProductos(productosEntity.getId());
    }
    @Test
    void findByTipoTestFalse(){
        assertTrue(productosService.findByTipo("Quimico").isEmpty());
    }

    @Test
    void findByNombreTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity("Quimico", "prueba", 30000, 50000, 5);
        productosService.save(productosEntity);

        ProductosEntity productosEntityGetNombre = productosService.findByNombre("prueba");
        assertEquals(productosEntityGetNombre.getNombre(), "prueba");

        productosService.deleteProductos(productosEntity.getId());
    }
    @Test
    void findByNombreTestFalse(){
        assertNull(productosService.findByNombre("prueba_2"));
    }

    @Test
    void updateValorTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity("Quimico", "prueba", 30000, 50000, 5);
        productosService.save(productosEntity);

        ProductosEntity productosEntityModify = productosService.updateValor(productosEntity.getId(), 999);
        assertEquals(999, productosEntityModify.getValor());
        assertEquals(999, productosService.findById(productosEntity.getId()).getValor());

        productosService.deleteProductos(productosEntity.getId());
    }
    @Test
    void updateValorTestFalse(){
        assertNull(productosService.updateValorFinal(101, 999));
    }

    @Test
    void updateValorFinalTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity("Quimico", "prueba", 30000, 50000, 5);
        productosService.save(productosEntity);

        ProductosEntity productosEntityModify = productosService.updateValorFinal(productosEntity.getId(), 9999);
        assertEquals(9999, productosEntityModify.getValor_final());
        assertEquals(9999, productosService.findById(productosEntity.getId()).getValor_final());

        productosService.deleteProductos(productosEntity.getId());
    }
    @Test
    void updateValorFinalTestFalse(){
        assertNull(productosService.updateValorFinal(101, 9999));
    }

    @Test
    void updateTipoTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity("Quimico", "prueba", 30000, 50000, 5);
        productosService.save(productosEntity);

        ProductosEntity productosEntityModify = productosService.updateTipo(productosEntity.getId(), "De Tipo");
        assertEquals("De Tipo", productosEntityModify.getTipo());
        assertEquals("De Tipo", productosService.findById(productosEntity.getId()).getTipo());

        productosService.deleteProductos(productosEntity.getId());
    }
    @Test
    void updateTipoTestFalse(){
        assertNull(productosService.updateTipo(101, "De Tipo"));
    }

    @Test
    void updateNombreTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity("Quimico", "prueba", 30000, 50000, 5);
        productosService.save(productosEntity);

        ProductosEntity productosEntityModify = productosService.updateNombre(productosEntity.getId(), "Cambio");
        assertEquals("Cambio", productosEntityModify.getNombre());
        assertEquals("Cambio", productosService.findById(productosEntity.getId()).getNombre());

        productosService.deleteProductos(productosEntity.getId());
    }
    @Test
    void updateNombreTestFalse(){
        assertNull(productosService.updateNombre(101, "Cambio"));
    }

    @Test
    void updateCantidadTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity("Quimico", "prueba", 30000, 50000, 5);
        productosService.save(productosEntity);

        ProductosEntity productosEntityModify = productosService.updateCantidad(productosEntity.getId(), 50);
        assertEquals(50, productosEntityModify.getCantidad());
        assertEquals(50, productosService.findById(productosEntity.getId()).getCantidad());

        productosService.deleteProductos(productosEntity.getId());
    }
    @Test
    void updateCantidadTestFalse(){
        assertNull(productosService.updateCantidad(101, 50));
    }
}
