package mingetal.MCM.ordenesdecompra.controller;


import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.service.ListaProductosOCProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes_de_compra/proveedor/productos")
public class ListaProductosOCProveedorController {

    @Autowired
    ListaProductosOCProveedorService listaProductosOCProveedorService;

    @PostMapping(("/"))
    public ResponseEntity<ListaProductosOCProveedorEntity> saveListProductos(@RequestBody ListaProductosOCProveedorEntity listaProductosOCProveedorEntity) {
        System.out.println(listaProductosOCProveedorEntity);
        return ResponseEntity.ok(listaProductosOCProveedorService.save(listaProductosOCProveedorEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ListaProductosOCProveedorEntity>> getListProductosByIdOCProveedor(@PathVariable("id") int id){
        return ResponseEntity.ok(listaProductosOCProveedorService.findByIdOCProveedor(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ListaProductosOCProveedorEntity> deleteListProductos(@PathVariable("id") int id){
        return ResponseEntity.ok(listaProductosOCProveedorService.delete(id));
    }
}
