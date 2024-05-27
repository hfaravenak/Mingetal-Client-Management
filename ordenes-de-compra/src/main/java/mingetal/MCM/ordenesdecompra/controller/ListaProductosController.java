package mingetal.MCM.ordenesdecompra.controller;


import mingetal.MCM.ordenesdecompra.entity.ListaProductosEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.service.ListaProductosService;
import mingetal.MCM.ordenesdecompra.service.OrdenesDeCompraClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ordenes_de_compra/productos")
public class ListaProductosController {

    @Autowired
    ListaProductosService listaProductosService;

    @PostMapping(("{producto}/{cantidad}"))
    public ResponseEntity<List<ListaProductosEntity>> saveListProductos(@RequestBody List<ListaProductosEntity> listaProductosEntity) {
        return ResponseEntity.ok(listaProductosService.save(listaProductosEntity));

    }

    @GetMapping("/")
    public ResponseEntity<List<ListaProductosEntity>> getAllListProductos(){
        return ResponseEntity.ok(listaProductosService.findAll());
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<ListaProductosEntity>> getListProductosByIdOCCliente(@PathVariable("id") int id){
        return ResponseEntity.ok(listaProductosService.findByIdOCCliente(id));
    }
    @GetMapping("/proveedor/{id}")
    public ResponseEntity<List<ListaProductosEntity>> getListProductosByIdOCProveedor(@PathVariable("id") int id){
        return ResponseEntity.ok(listaProductosService.findByIdOCProveedor(id));
    }

    @GetMapping("/cotizacion/{id}")
    public ResponseEntity<List<ListaProductosEntity>> getListProductosByIdCotizacion(@PathVariable("id") int id){
        return ResponseEntity.ok(listaProductosService.findByIdCotizacion(id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ListaProductosEntity> deleteListProductos(@PathVariable("id") int id){
        return ResponseEntity.ok(listaProductosService.delete(id));
    }
}
