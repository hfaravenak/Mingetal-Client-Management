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

    @PostMapping()
    public ResponseEntity<ListaProductosEntity> saveListProductos(@RequestBody ListaProductosEntity listaProductosEntity) {
        boolean bool = listaProductosService.save(listaProductosEntity);
        if(bool){
            return ResponseEntity.ok(listaProductosEntity);
        }
        return null;
    }

    @GetMapping("/")
    public ResponseEntity<List<ListaProductosEntity>> getAllListProductos(){
        List<ListaProductosEntity> listaProductosEntities=listaProductosService.findAll();
        return ResponseEntity.ok(listaProductosEntities);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<ListaProductosEntity>> getListProductosByIdOCCliente(@PathVariable("id") int id){
        List<ListaProductosEntity> listaProductosEntities=listaProductosService.findByIdOCCliente(id);
        return ResponseEntity.ok(listaProductosEntities);
    }
    @GetMapping("/proveedor/{id}")
    public ResponseEntity<List<ListaProductosEntity>> getListProductosByIdOCProveedor(@PathVariable("id") int id){
        List<ListaProductosEntity> listaProductosEntities=listaProductosService.findByIdOCProveedor(id);
        return ResponseEntity.ok(listaProductosEntities);
    }
}
