package mingetal.MCM.productos.controller;

import mingetal.MCM.productos.entity.ProductosEntity;
import mingetal.MCM.productos.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    ProductosService productosService;

    @PostMapping("")
    public ResponseEntity<ProductosEntity> saveProducto(@RequestBody ProductosEntity productosEntity) {
        productosService.save(productosEntity);
        return ResponseEntity.ok(productosEntity);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductosEntity>> getAll(){
        List<ProductosEntity> productosEntities=productosService.findAll();
        return ResponseEntity.ok(productosEntities);
    }
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ProductosEntity>> getProductoByTipo(@PathVariable("tipo") String tipo){
        List<ProductosEntity> productosEntities = productosService.findByTipo(tipo);
        return ResponseEntity.ok(productosEntities);
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ProductosEntity>> getProductoByNombre(@PathVariable("nombre") String nombre){
        List<ProductosEntity> productosEntity = productosService.findByNombre(nombre);
        return ResponseEntity.ok(productosEntity);
    }
    @GetMapping("/fullNombre/{nombre}")
    public ResponseEntity<ProductosEntity>getClienteByNombreTextual(@PathVariable("nombre") String nombre){
        ProductosEntity productosEntity = productosService.findByNombreTextual(nombre);
        System.out.println(productosEntity);
        return ResponseEntity.ok(productosEntity);
    }
    @GetMapping("/OCCliente/{id}")
    public ResponseEntity<List<ProductosEntity>>getProductosByOCCliente(@PathVariable("id") int id){
        List<ProductosEntity> productosEntity = productosService.findByOCCliente(id);
        return ResponseEntity.ok(productosEntity);
    }
    @GetMapping("/OCProveedor/{id}")
    public ResponseEntity<List<ProductosEntity>>getProductosByOCProveedor(@PathVariable("id") int id){
        List<ProductosEntity> productosEntity = productosService.findByOCProveedor(id);
        return ResponseEntity.ok(productosEntity);
    }
    @GetMapping("/cotizacion/{id}")
    public ResponseEntity<List<ProductosEntity>>getProductosByCotizacion(@PathVariable("id") int id){
        List<ProductosEntity> productosEntity = productosService.findByCotizacion(id);
        return ResponseEntity.ok(productosEntity);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductosEntity> deleteProductoById(@PathVariable("id") int id){
        ProductosEntity productosEntity = productosService.delete(id);
        return ResponseEntity.ok(productosEntity);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductosEntity> updateProducto(@RequestBody ProductosEntity productosEntity){
        ProductosEntity producto_1 = productosService.update(productosEntity);
        System.out.println(producto_1);
        return ResponseEntity.ok(producto_1);
    }
}
