package mingetal.MCM.productos;

import com.netflix.discovery.converters.Auto;
import mingetal.MCM.productos.entity.ProductosEntity;
import mingetal.MCM.productos.service.ProductosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductosApplicationTests {

	@Autowired
	private ProductosService productosService;


	//--------------------------- save -------------------------
	@Test
	void saveTestTrue() {
		ProductosEntity p1 = new ProductosEntity(
				500,
				"tipo",
				"producto",
				1,
				2,
				3
		);

		assertEquals(p1, productosService.save(p1));
		assertNotNull(productosService.findByNombre(p1.getNombre()));

		productosService.delete(p1.getId());
	}

	@Test
	void saveTestFalse() {
		ProductosEntity p1 = new ProductosEntity(
				501,
				"tipo",
				"producto",
				1,
				2,
				3
		);


		productosService.save(p1);
		assertNull(productosService.save(p1));

		productosService.delete(p1.getId());
	}

	//------------------------- find all -----------------------
	@Test
	void findAllTest() {
		ProductosEntity p1 = new ProductosEntity(
				502,
				"tipo1",
				"producto1",
				1,
				2,
				3
		);
		ProductosEntity p2 = new ProductosEntity(
				503,
				"tipo2",
				"producto2",
				1,
				2,
				3
		);


		productosService.save(p1);
		productosService.save(p2);

		assertFalse(productosService.findAll().isEmpty());

		productosService.delete(p1.getId());
		productosService.delete(p2.getId());
	}


	//--------------------------- find by id ------------------------
	@Test
	void findByIdTestTrue() {
		ProductosEntity p1 = new ProductosEntity(
				504,
				"tipo",
				"producto",
				1,
				2,
				3
		);

		productosService.save(p1);

		assertEquals(p1, productosService.findById(504));

		productosService.delete(p1.getId())   ;
	}
	@Test
	void findByIdTestFalse() {
		assertNull(productosService.findById(1000));
	}


	//------------------------------ find by tipo ------------------------
	@Test
	void findByTipoTestTrue() {
		ProductosEntity p1 = new ProductosEntity(
				505,
				"tipo",
				"producto",
				1,
				2,
				3
		);
		ProductosEntity p2 = new ProductosEntity(
				506,
				"tipo",
				"producto",
				1,
				2,
				3
		);

		productosService.save(p1);
		productosService.save(p2);

		List<ProductosEntity> productos = productosService.findByTipo("tipo");
		assertEquals(2,productos.size());
		assertEquals("tipo", productos.get(0).getTipo());
		assertEquals("tipo", productos.get(1).getTipo());

		productosService.delete(p1.getId());
		productosService.delete(p2.getId())   ;

	}
	@Test
	void findByTipoTestFalse() {
		assertTrue(productosService.findByTipo("NoExisteEsteTipo").isEmpty());
	}


	//-------------------------- find by nombre --------------------
	@Test
	void findByNombreTestTrue() {
		ProductosEntity p1 = new ProductosEntity(
				507,
				"tipo",
				"producto",
				1,
				2,
				3
		);

		productosService.save(p1);

		List<ProductosEntity> producto = productosService.findByNombre("producto");
		assertEquals(1, producto.size());
		assertEquals(p1, producto.get(0));

		productosService.delete(producto.get(0).getId());
	}
	@Test
	void findByNombreTestFalse() {
		assertTrue(productosService.findByNombre("NoExisteEsteNombre").isEmpty());
	}


	//------------------------------ find by nombre textual ------------------------------
	@Test
	void findByNombreTextualTestTrue() {
		ProductosEntity p1 = new ProductosEntity(
				508,
				"tipo",
				"producto 1",
				1,
				2,
				3
		);

		productosService.save(p1);

		assertEquals(p1, productosService.findByNombreTextual("producto 1"));

		productosService.delete(p1.getId());
	}

	@Test
	void findByNombreTextualTestFalseForBadTyping() {
		ProductosEntity p1 = new ProductosEntity(
				509,
				"tipo",
				"producto 1",
				1,
				2,
				3
		);

		productosService.save(p1);

		assertNull(productosService.findByNombreTextual("producto"));

		productosService.delete(p1.getId());
	}
	@Test
	void findByNombreTextualTestFalse() {
		assertNull(productosService.findByNombreTextual("NoExisteEsteNombre"));
	}


	//-------------------- delete ----------------------
	@Test
	void deleteTestTrue() {
		ProductosEntity p1 = new ProductosEntity(
				510,
				"tipo",
				"producto 1",
				1,
				2,
				3
		);

		productosService.save(p1);

		assertEquals(p1, productosService.delete(p1.getId()));
		assertNull((productosService.findById(p1.getId())));
	}
	@Test
	void deleteTestFalse() {
		assertNull(productosService.delete(-1));
	}

	//---------------------- Update ---------------------------
	//-------------------- update --------------------

	@Test
	void updateTestTrue() {
		ProductosEntity p1 = new ProductosEntity(
				511,
				"tipo",
				"producto",
				1,
				2,
				3
		);

		productosService.save(p1);

		p1.setNombre("new producto");
		p1.setCantidad(0);
		p1.setTipo("new tipo");
		p1.setValor(0);
		p1.setValor_final(0);
		ProductosEntity updateProducto = productosService.update(p1);

		assertEquals(p1, updateProducto);
		assertEquals(updateProducto, productosService.findById(p1.getId()));

		productosService.delete(p1.getId());
	}
	@Test
	void updateTestFalse() {
		ProductosEntity p1 = new ProductosEntity(
				512,
				"tipo",
				"producto",
				1,
				2,
				3
		);

		p1.setNombre("New producto Random");
		ProductosEntity updateProducto = productosService.update(p1);

		assertNull(updateProducto);
	}
	@Test
	void contextLoads() {
	}

}
