package mingetal.MCM.ordenesdecompra.controller;


import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.service.OrdenesDeCompraClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes_de_compra/cliente")
public class OrdenesDeCompraClienteController {

    @Autowired
    OrdenesDeCompraClienteService ordenesDeCompraClienteService;

    @PostMapping()
    public ResponseEntity<OrdenesDeCompraClienteEntity> saveCliente(@RequestBody OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity) {
        ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);
        return ResponseEntity.ok(ordenesDeCompraClienteEntity);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrdenesDeCompraClienteEntity>> getAllCliente(){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities=ordenesDeCompraClienteService.findAll();
        System.out.println(ordenesDeCompraClienteEntities);
        if(ordenesDeCompraClienteEntities != null){
            return ResponseEntity.ok(ordenesDeCompraClienteEntities);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenesDeCompraClienteEntity> getOrdenClienteById(@PathVariable("id") int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntities = ordenesDeCompraClienteService.findById(id);
        System.out.println(ordenesDeCompraClienteEntities);
        if(ordenesDeCompraClienteEntities != null){
            return ResponseEntity.ok(ordenesDeCompraClienteEntities);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/id/{id_cliente}")
    public ResponseEntity<List<OrdenesDeCompraClienteEntity>> getOrdenProveedorByIdCliente(@PathVariable("id_cliente") int id_cliente){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = ordenesDeCompraClienteService.findByIdCliente(id_cliente);
        System.out.println(ordenesDeCompraClienteEntities);
        if(ordenesDeCompraClienteEntities != null){
            return ResponseEntity.ok(ordenesDeCompraClienteEntities);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<OrdenesDeCompraClienteEntity> deleteOCClienteById(@PathVariable("id") int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteService.deleteOCCliente(id);
        System.out.println(ordenesDeCompraClienteEntity);
        if(ordenesDeCompraClienteEntity != null){
            return ResponseEntity.ok(ordenesDeCompraClienteEntity);
        }
        return ResponseEntity.notFound().build();
    }
}
