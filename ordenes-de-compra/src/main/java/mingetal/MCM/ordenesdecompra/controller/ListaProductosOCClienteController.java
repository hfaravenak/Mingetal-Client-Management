package mingetal.MCM.ordenesdecompra.controller;


import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.service.ListaProductosOCClienteService;
import mingetal.MCM.ordenesdecompra.service.ListaProductosOCProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes_de_compra/cliente/productos")
public class ListaProductosOCClienteController {

    @Autowired
    ListaProductosOCClienteService listaProductosOCClienteService;

    @PostMapping(("/"))
    public ResponseEntity<ListaProductosOCClienteEntity> saveListProductos(@RequestBody ListaProductosOCClienteEntity listaProductosOCClienteEntities) {
        return ResponseEntity.ok(listaProductosOCClienteService.save(listaProductosOCClienteEntities));
    }

    @GetMapping("/")
    public ResponseEntity<List<ListaProductosOCClienteEntity>> getListProductos(){
        return ResponseEntity.ok(listaProductosOCClienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ListaProductosOCClienteEntity>> getListProductosByIdOCCliente(@PathVariable("id") int id){
        return ResponseEntity.ok(listaProductosOCClienteService.findByIdOCCliente(id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ListaProductosOCClienteEntity> deleteListProductos(@PathVariable("id") int id){
        return ResponseEntity.ok(listaProductosOCClienteService.delete(id));
    }
}
