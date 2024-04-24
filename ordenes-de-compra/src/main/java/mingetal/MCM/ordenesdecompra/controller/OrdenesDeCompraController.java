package mingetal.MCM.ordenesdecompra.controller;


import mingetal.MCM.ordenesdecompra.entity.ListaProductosEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.service.ListaProductosService;
import mingetal.MCM.ordenesdecompra.service.OrdenesDeCompraClienteService;
import mingetal.MCM.ordenesdecompra.service.OrdenesDeCompraProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes_de_compra")
public class OrdenesDeCompraController {

    @Autowired
    OrdenesDeCompraClienteService ordenesDeCompraClienteService;
    @Autowired
    OrdenesDeCompraProveedorService ordenesDeCompraProveedorService;

    @PostMapping("/cliente")
    public ResponseEntity<OrdenesDeCompraClienteEntity> saveCliente(@RequestBody OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity) {
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);
        return ResponseEntity.ok(ordenesDeCompraClienteEntity);
    }
    @PostMapping("/proveedor")
    public ResponseEntity<OrdenesDeCompraProveedorEntity> saveProveedor(@RequestBody OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity) {
        ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntity);
    }

    @GetMapping("/cliente/")
    public ResponseEntity<List<OrdenesDeCompraClienteEntity>> getAllCliente(){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities=ordenesDeCompraClienteService.findAll();
        System.out.println(ordenesDeCompraClienteEntities);
        if(ordenesDeCompraClienteEntities != null){
            return ResponseEntity.ok(ordenesDeCompraClienteEntities);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/proveedor/")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getAllProveedor(){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities=ordenesDeCompraProveedorService.findAll();
        System.out.println(ordenesDeCompraProveedorEntities);
        if(ordenesDeCompraProveedorEntities != null){
            return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<OrdenesDeCompraClienteEntity> getOrdenClienteById(@PathVariable("id") int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntities = ordenesDeCompraClienteService.findById(id);
        System.out.println(ordenesDeCompraClienteEntities);
        if(ordenesDeCompraClienteEntities != null){
            return ResponseEntity.ok(ordenesDeCompraClienteEntities);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/proveedor/{id}")
    public ResponseEntity<OrdenesDeCompraProveedorEntity> getOrdenProveedorById(@PathVariable("id") int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findById(id);
        System.out.println(ordenesDeCompraProveedorEntities);
        if(ordenesDeCompraProveedorEntities != null){
            return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cliente/id/{id_cliente}")
    public ResponseEntity<List<OrdenesDeCompraClienteEntity>> getOrdenProveedorByIdCliente(@PathVariable("id_cliente") int id_cliente){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = ordenesDeCompraClienteService.findByIdCliente(id_cliente);
        System.out.println(ordenesDeCompraClienteEntities);
        if(ordenesDeCompraClienteEntities != null){
            return ResponseEntity.ok(ordenesDeCompraClienteEntities);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/proveedor/id/{id_cliente}")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getOrdenProveedorByIdProveedor(@PathVariable("id_proveedor") int id_proveedor){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findByIdProveedor(id_proveedor);
        System.out.println(ordenesDeCompraProveedorEntities);
        if(ordenesDeCompraProveedorEntities != null){
            return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
        }
        return ResponseEntity.notFound().build();
    }
}
