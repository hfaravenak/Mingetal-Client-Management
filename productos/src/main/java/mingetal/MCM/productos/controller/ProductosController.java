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
        return ResponseEntity.ok(productosService.save(productosEntity));
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductosEntity>> getAll(){
        return ResponseEntity.ok(productosService.findAll());
    }
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ProductosEntity>> getProductoByTipo(@PathVariable("tipo") String tipo){
        return ResponseEntity.ok(productosService.findByTipo(tipo));
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ProductosEntity>> getProductoByNombre(@PathVariable("nombre") String nombre){
        return ResponseEntity.ok(productosService.findByNombre(nombre));
    }
    @GetMapping("/fullNombre/{nombre}")
    public ResponseEntity<ProductosEntity>getClienteByNombreTextual(@PathVariable("nombre") String nombre){
        return ResponseEntity.ok(productosService.findByNombreTextual(nombre));
    }
    @GetMapping("/OCCliente/{id}")
    public ResponseEntity<List<ProductosEntity>>getProductosByOCCliente(@PathVariable("id") int id){
        return ResponseEntity.ok(productosService.findByOCCliente(id));
    }
    @GetMapping("/OCProveedor/{id}")
    public ResponseEntity<List<ProductosEntity>>getProductosByOCProveedor(@PathVariable("id") int id){
        return ResponseEntity.ok(productosService.findByOCProveedor(id));
    }
    @GetMapping("/cotizacion/{id}")
    public ResponseEntity<List<ProductosEntity>>getProductosByCotizacion(@PathVariable("id") int id){
        return ResponseEntity.ok(productosService.findByCotizacion(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductosEntity> deleteProductoById(@PathVariable("id") int id){
        return ResponseEntity.ok(productosService.delete(id));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductosEntity> updateProducto(@RequestBody ProductosEntity productosEntity){
        return ResponseEntity.ok(productosService.update(productosEntity));
    }
}
