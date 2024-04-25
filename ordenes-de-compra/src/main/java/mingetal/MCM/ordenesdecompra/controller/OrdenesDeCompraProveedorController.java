package mingetal.MCM.ordenesdecompra.controller;


import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.service.OrdenesDeCompraProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes_de_compra/proveedor")
public class OrdenesDeCompraProveedorController {

    @Autowired
    OrdenesDeCompraProveedorService ordenesDeCompraProveedorService;

    @PostMapping()
    public ResponseEntity<OrdenesDeCompraProveedorEntity> saveProveedor(@RequestBody OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity) {
        ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntity);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getAllProveedor(){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities=ordenesDeCompraProveedorService.findAll();
        System.out.println(ordenesDeCompraProveedorEntities);
        if(ordenesDeCompraProveedorEntities != null){
            return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenesDeCompraProveedorEntity> getOrdenProveedorById(@PathVariable("id") int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findById(id);
        System.out.println(ordenesDeCompraProveedorEntities);
        if(ordenesDeCompraProveedorEntities != null){
            return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/id/{id_cliente}")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getOrdenProveedorByIdProveedor(@PathVariable("id_proveedor") int id_proveedor){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findByIdProveedor(id_proveedor);
        System.out.println(ordenesDeCompraProveedorEntities);
        if(ordenesDeCompraProveedorEntities != null){
            return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<OrdenesDeCompraProveedorEntity> deleteOCProveedorById(@PathVariable("id") int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = ordenesDeCompraProveedorService.deleteOCProveedor(id);
        System.out.println(ordenesDeCompraProveedorEntity);
        if(ordenesDeCompraProveedorEntity != null){
            return ResponseEntity.ok(ordenesDeCompraProveedorEntity);
        }
        return ResponseEntity.notFound().build();
    }
}
