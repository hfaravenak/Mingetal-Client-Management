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

    @PostMapping()
    public ResponseEntity<ProductosEntity> saveProducto(@RequestBody ProductosEntity productosEntity) {
        productosService.save(productosEntity);
        return ResponseEntity.ok(productosEntity);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductosEntity>> getAll(){
        return ResponseEntity.ok(productosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosEntity> getProductoById(@PathVariable("id") int id){
        ProductosEntity productosEntity = productosService.findById(id);
        System.out.println(productosEntity);
        if(productosEntity != null){
            return ResponseEntity.ok(productosEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ProductosEntity>> getProductoByTipo(@PathVariable("tipo") String tipo){
        List<ProductosEntity> productosEntity = productosService.findByTipo(tipo);
        System.out.println(productosEntity);
        if(productosEntity != null){
            return ResponseEntity.ok(productosEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ProductosEntity> getProductoByNombre(@PathVariable("nombre") String nombre){
        ProductosEntity productosEntity = productosService.findByNombre(nombre);
        System.out.println(productosEntity);
        if(productosEntity != null){
            return ResponseEntity.ok(productosEntity);
        }
        return ResponseEntity.notFound().build();
    }
}
