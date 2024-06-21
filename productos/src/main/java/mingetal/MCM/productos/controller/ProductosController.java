package mingetal.MCM.productos.controller;

import mingetal.MCM.productos.entity.ProductosEntity;
import mingetal.MCM.productos.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    ProductosService productosService;

    @PostMapping("")
    public ResponseEntity<ProductosEntity> saveProducto(
            @RequestParam("tipo") String tipo,
            @RequestParam("nombre") String nombre,
            @RequestParam("valor") int valor,
            @RequestParam("valor_final") int valorFinal,
            @RequestParam("cantidad") int cantidad,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        System.out.println("##################################################################");
        try {
            ProductosEntity producto = new ProductosEntity(tipo, nombre, valor, valorFinal, cantidad, null, null);
            System.out.println("Tipo: " + tipo);
            System.out.println("Nombre: " + nombre);
            System.out.println("Valor: " + valor);
            System.out.println("Valor Final: " + valorFinal);
            System.out.println("Cantidad: " + cantidad);
            if (imagen != null) {
                System.out.println("Imagen: " + imagen.getOriginalFilename());
                System.out.println("Tipo de Imagen: " + imagen.getContentType());
            }

            if (imagen != null && !imagen.isEmpty()) {
                String tipoImagen = imagen.getContentType();
                if (!"image/jpeg".equals(tipoImagen) && !"image/png".equals(tipoImagen)) {
                    return ResponseEntity.badRequest().body(null);
                }
                producto.setImagen(imagen.getBytes());
                producto.setTipoImagen(tipoImagen);
            }
            return ResponseEntity.ok(productosService.save(producto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
    @GetMapping("/pocos_productos/")
    public ResponseEntity<List<ProductosEntity>>getPocoProductos(){
        return ResponseEntity.ok(productosService.findPocosProductos());
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
