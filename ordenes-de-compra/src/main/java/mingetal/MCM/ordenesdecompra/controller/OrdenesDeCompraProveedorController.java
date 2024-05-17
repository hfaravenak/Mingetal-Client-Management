package mingetal.MCM.ordenesdecompra.controller;


import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.service.OrdenesDeCompraProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ordenes_de_compra/proveedor")
public class OrdenesDeCompraProveedorController {

    @Autowired
    OrdenesDeCompraProveedorService ordenesDeCompraProveedorService;

    @PostMapping()
    public ResponseEntity<OrdenesDeCompraProveedorEntity> saveProveedor(@RequestBody OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity) {
        System.out.println(ordenesDeCompraProveedorEntity);
        boolean bool = ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity);
        if(bool){
            return ResponseEntity.ok(ordenesDeCompraProveedorEntity);
        }
        return null;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getAllProveedor(){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities=ordenesDeCompraProveedorService.findAll();
        System.out.println(ordenesDeCompraProveedorEntities);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrdenesDeCompraProveedorEntity> getOrdenProveedorById(@PathVariable("id") int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findById(id);
        System.out.println(ordenesDeCompraProveedorEntities);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
    }
    @GetMapping("/id/{id_proveedor}")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getOrdenProveedorByIdProveedor(@PathVariable("id_proveedor") int id_proveedor){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findByIdProveedor(id_proveedor);
        System.out.println(ordenesDeCompraProveedorEntities);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getOrdenProveedorByNombreProveedor(@PathVariable("nombre") String nombre){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findByNameProveedor(nombre);
        System.out.println(ordenesDeCompraProveedorEntities);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
    }
    @GetMapping("/empresa/{empresa}")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getOrdenProveedorByEmpresaProveedor(@PathVariable("empresa") String empresa){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findByEmpresa(empresa);
        System.out.println(ordenesDeCompraProveedorEntities);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
    }
    @GetMapping("/rubro/{rubro}")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getOrdenProveedorByRubroProveedor(@PathVariable("rubro") String rubro){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findByRubro(rubro);
        System.out.println(ordenesDeCompraProveedorEntities);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<OrdenesDeCompraProveedorEntity> deleteOCProveedorById(@PathVariable("id") int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = ordenesDeCompraProveedorService.deleteOCProveedor(id);
        System.out.println(ordenesDeCompraProveedorEntity);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntity);
    }
    @PutMapping("/update/estado_pago/{id}")
    public ResponseEntity<OrdenesDeCompraProveedorEntity> updateOCProveedorByEstadoPago(@PathVariable("id") int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = ordenesDeCompraProveedorService.updateOCProveedorByEstadoPago(id);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntity);
    }

    @PutMapping("/update")
    public ResponseEntity<OrdenesDeCompraProveedorEntity> updateOCProveedor(@RequestBody OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity) {
        OrdenesDeCompraProveedorEntity newOrdenesDeCompraProveedorEntity = ordenesDeCompraProveedorService.updateOCProveedor(ordenesDeCompraProveedorEntity);
        return ResponseEntity.ok(newOrdenesDeCompraProveedorEntity);
    }
}
