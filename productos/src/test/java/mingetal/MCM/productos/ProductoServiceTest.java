package mingetal.MCM.productos;

import mingetal.MCM.productos.entity.ProductosEntity;
import mingetal.MCM.productos.service.ProductosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductoServiceTest {
    @Autowired
    private ProductosService productosService;

    //-------------------- save --------------------

    @Test
    void guardarProductoTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity(
                "Quimico",
                "prueba",
                30000,
                50000,
                5,null,null);

        assertNotNull(productosService.save(productosEntity));
        assertEquals(productosEntity, productosService.findById(productosEntity.getId()));

        productosService.delete(productosEntity.getId());
    }
    @Test
    void guardarProductoTestFail(){
        ProductosEntity productosEntity = new ProductosEntity(
                "Quimico",
                "prueba",
                30000,
                50000,
                5,null,null);

        productosService.save(productosEntity);
        assertNull(productosService.save(productosEntity));

        productosService.delete(productosEntity.getId());
    }

    //-------------------- findAll --------------------

    @Test
    void findAllTest() {
        ProductosEntity productosEntity = new ProductosEntity(
                "Quimico",
                "prueba",
                30000,
                50000,
                5,null,null);
        ProductosEntity productosEntity2 = new ProductosEntity(
                "Quimico2",
                "prueba2",
                30000,
                50000,
                5,null,null);

        productosService.save(productosEntity);
        productosService.save(productosEntity2);

        assertFalse(productosService.findAll().isEmpty());

        productosService.delete(productosEntity.getId());
        productosService.delete(productosEntity2.getId());
    }

    //-------------------- findById --------------------

    @Test
    void findByIdTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity(
                "Quimico",
                "prueba",
                30000,
                50000,
                5,null,null);
        productosService.save(productosEntity);

        assertEquals(productosEntity, productosService.findById(productosEntity.getId()));

        productosService.delete(productosEntity.getId());
    }
    @Test
    void findByIdTestFalse(){
        assertNull(productosService.findById(-1));
    }

    //-------------------- findByTipo --------------------

    @Test
    void findByTipoTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity(
                "Quimico",
                "prueba",
                30000,
                50000,
                5,null,null);
        productosService.save(productosEntity);
        List<ProductosEntity> productosEntities = productosService.findByTipo("Quimico");

        assertEquals(1, productosEntities.size());
        assertEquals(productosEntity, productosEntities.get(0));

        productosService.delete(productosEntity.getId());
    }
    @Test
    void findByTipoTestFalse(){
        assertTrue(productosService.findByTipo("Quimico").isEmpty());
    }

    //-------------------- findByNombre --------------------

    @Test
    void findByNombreTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity(
                "Quimico",
                "Prueba",
                30000,
                50000,
                5,null,null);
        productosService.save(productosEntity);

        List<ProductosEntity> productosEntities = productosService.findByNombre("Prueba");

        assertEquals(1, productosEntities.size());
        assertEquals(productosEntity, productosEntities.get(0));

        productosService.delete(productosEntity.getId());
    }
    @Test
    void findMultipleByNombreTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity(
                "Quimico1",
                "Prueba1",
                30000,
                50000,
                5,null,null);
        productosService.save(productosEntity);
        ProductosEntity productosEntity2 = new ProductosEntity(
                "Quimico2",
                "Prueba2",
                30000,
                50000,
                5,null,null);
        productosService.save(productosEntity2);

        List<ProductosEntity> productosEntities = productosService.findByNombre("Prueba");

        assertEquals(2, productosEntities.size());
        assertEquals(productosEntity, productosEntities.get(0));
        assertEquals(productosEntity2, productosEntities.get(1));

        productosService.delete(productosEntity.getId());
        productosService.delete(productosEntity2.getId());
    }
    @Test
    void findByNombreTestFalse(){
        assertTrue(productosService.findByNombre("Quimico").isEmpty());
    }

    //-------------------- findByNombreTextual --------------------

    @Test
    void findByNombreTextualTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity(
                "Quimico",
                "Prueba",
                30000,
                50000,
                5,null,null);
        productosService.save(productosEntity);

        assertEquals(productosEntity, productosService.findByNombreTextual("Prueba"));

        productosService.delete(productosEntity.getId());
    }
    @Test
    void findMultipleByNombreTextualTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity(
                "Quimico1",
                "Prueba1",
                30000,
                50000,
                5,null,null);
        productosService.save(productosEntity);
        ProductosEntity productosEntity2 = new ProductosEntity(
                "Quimico2",
                "Prueba2",
                30000,
                50000,
                5,null,null);
        productosService.save(productosEntity2);

        assertNull(productosService.findByNombreTextual("Prueba"));
        assertEquals(productosEntity, productosService.findByNombreTextual("Prueba1"));
        assertEquals(productosEntity2, productosService.findByNombreTextual("Prueba2"));

        productosService.delete(productosEntity.getId());
        productosService.delete(productosEntity2.getId());
    }

    //@Test
    //void findByNombreTextualTestFalse(){
    //    assertNull(productosService.findByNombreTextual("Quimico"));
    //}

    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-------------------- TERMINAR FUTURO PROGRAMADOR (Mi yo del futuro) --------------------
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-
    //-


    //-------------------- findByOCCliente --------------------


    //-------------------- findByOCProveedor --------------------


    //-------------------- findByCotizacion --------------------


    //-------------------- delete --------------------

    @Test
    void deleteProductoTestTrue(){
        ProductosEntity productosEntity = new ProductosEntity(
                "Quimico1",
                "prueba1",
                30000,
                50000,
                5,null,null);
        productosService.save(productosEntity);

        assertEquals(productosEntity, productosService.delete(productosEntity.getId()));
        assertNull(productosService.findById(productosEntity.getId()));
    }
    @Test
    void deleteProductoTestFail(){
        assertNull(productosService.delete(-1));
    }

    //-------------------- update --------------------

    @Test
    void updateTestTrue() {
        ProductosEntity productosEntity = new ProductosEntity(
                "Quimico1",
                "prueba1",
                30000,
                50000,
                5,null,null);
        productosService.save(productosEntity);

        productosEntity.setTipo("New Tipo");
        productosEntity.setNombre("New Nombre");
        productosEntity.setValor(1000);
        productosEntity.setValor_final(1500);
        productosEntity.setCantidad(3);
        ProductosEntity updateProducto = productosService.update(productosEntity);

        assertEquals(productosEntity, updateProducto);
        assertEquals(updateProducto, productosService.findById(productosEntity.getId()));

        productosService.delete(productosEntity.getId());
    }
    @Test
    void updateTestFalse() {
        ProductosEntity productosEntity = new ProductosEntity(
                "Quimico1",
                "prueba1",
                30000,
                50000,
                5,null,null);
        assertNull(productosService.update(productosEntity));
    }
}
