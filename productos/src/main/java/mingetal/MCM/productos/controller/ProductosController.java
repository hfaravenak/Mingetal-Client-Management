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

    @PostMapping("/guardar-producto")
    public ResponseEntity<ProductosEntity> saveProducto(@RequestBody ProductosEntity productosEntity) {
        productosService.save(productosEntity);
        return ResponseEntity.ok(productosEntity);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductosEntity>> getAll(){
        List<ProductosEntity> productosEntities=productosService.findAll();
        return ResponseEntity.ok(productosEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosEntity> getProductoById(@PathVariable("id") int id){
        ProductosEntity productosEntity = productosService.findById(id);
        return ResponseEntity.ok(productosEntity);
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


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductosEntity> deleteProductoById(@PathVariable("id") int id){
        ProductosEntity productosEntity = productosService.deleteProductos(id);
        return ResponseEntity.ok(productosEntity);
    }

    @PutMapping("/update/valor/{id}")
    public ResponseEntity<ProductosEntity> updateProductoByValorById(@PathVariable("id") int id,
                                                                     @RequestParam int newValor){
        ProductosEntity productosEntity = productosService.updateValor(id, newValor);
        return ResponseEntity.ok(productosEntity);
    }
    @PutMapping("/update/valor_final/{id}")
    public ResponseEntity<ProductosEntity> updateProductoByValorFinalById(@PathVariable("id") int id,
                                                                     @RequestParam int newValorFinal){
        ProductosEntity productosEntity = productosService.updateValorFinal(id, newValorFinal);
        return ResponseEntity.ok(productosEntity);
    }
    @PutMapping("/update/tipo/{id}")
    public ResponseEntity<ProductosEntity> updateProductoByTipoById(@PathVariable("id") int id,
                                                                     @RequestParam String newTipo){
        ProductosEntity productosEntity = productosService.updateTipo(id, newTipo);
        return ResponseEntity.ok(productosEntity);
    }
    @PutMapping("/update/nombre/{id}")
    public ResponseEntity<ProductosEntity> updateProductoByNombreById(@PathVariable("id") int id,
                                                                     @RequestParam String newNombre){
        ProductosEntity productosEntity = productosService.updateNombre(id, newNombre);
        return ResponseEntity.ok(productosEntity);
    }
    @PutMapping("/update/cantidad/{id}")
    public ResponseEntity<ProductosEntity> updateProductoByCantidadById(@PathVariable("id") int id,
                                                                     @RequestParam int newCantidad){
        ProductosEntity productosEntity = productosService.updateCantidad(id, newCantidad);
        return ResponseEntity.ok(productosEntity);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductosEntity> updateProducto(@RequestBody ProductosEntity productosEntity){
        ProductosEntity producto_1 = productosService.update(productosEntity);
        System.out.println(producto_1);
        return ResponseEntity.ok(producto_1);
    }
}
