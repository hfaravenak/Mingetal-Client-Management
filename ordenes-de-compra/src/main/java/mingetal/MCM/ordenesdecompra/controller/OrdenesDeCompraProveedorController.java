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
    public ResponseEntity<OrdenesDeCompraProveedorEntity> saveOCProveedor(@RequestBody OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity) {
            return ResponseEntity.ok(ordenesDeCompraProveedorService.save(ordenesDeCompraProveedorEntity));
    }

    @GetMapping("/")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getAllOCProveedor(){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities=ordenesDeCompraProveedorService.findAll();
        return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrdenesDeCompraProveedorEntity> getOCProveedorById(@PathVariable("id") int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findById(id);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
    }
    @GetMapping("/id/{id_proveedor}")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getOCProveedorByIdProveedor(@PathVariable("id_proveedor") int id_proveedor){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findByIdProveedor(id_proveedor);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getOCProveedorByNombreProveedor(@PathVariable("nombre") String nombre){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findByNameProveedor(nombre);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
    }
    @GetMapping("/empresa/{empresa}")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getOCProveedorByEmpresaProveedor(@PathVariable("empresa") String empresa){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findByEmpresa(empresa);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
    }
    @GetMapping("/rubro/{rubro}")
    public ResponseEntity<List<OrdenesDeCompraProveedorEntity>> getOCProveedorByRubroProveedor(@PathVariable("rubro") String rubro){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorService.findByRubro(rubro);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntities);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<OrdenesDeCompraProveedorEntity> deleteOCProveedorById(@PathVariable("id") int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = ordenesDeCompraProveedorService.delete(id);
        return ResponseEntity.ok(ordenesDeCompraProveedorEntity);
    }

    @PutMapping("/update")
    public ResponseEntity<OrdenesDeCompraProveedorEntity> updateOCProveedor(@RequestBody OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity) {
        OrdenesDeCompraProveedorEntity newOrdenesDeCompraProveedorEntity = ordenesDeCompraProveedorService.update(ordenesDeCompraProveedorEntity);
        return ResponseEntity.ok(newOrdenesDeCompraProveedorEntity);
    }


    @GetMapping("/purchasesbyyear")
    public ResponseEntity<List<Object[]>> getProductsByYear(){
        List<Object[]> peryear = ordenesDeCompraProveedorService.getPurchasesByYear();
        return ResponseEntity.ok(peryear);
    }

    @GetMapping("/purchasesbyyearandmonth")
    public ResponseEntity<List<Object[]>> getProductsByYearAndMonth(){
        List<Object[]> peryear = ordenesDeCompraProveedorService.getPurchasesByYearAndMonth();
        return ResponseEntity.ok(peryear);
    }

    @GetMapping("/allpurchasesbyyear")
    public ResponseEntity<List<Object[]>> getSalesByYear(){
        List<Object[]> peryear = ordenesDeCompraProveedorService.getAllPurchasesByYear();
        return ResponseEntity.ok(peryear);
    }

    @GetMapping("/alpurchasesbyyearandmonth")
    public ResponseEntity<List<Object[]>> getSalesByYearAndMonth(){
        List<Object[]> peryear = ordenesDeCompraProveedorService.getAllPurchasesByYearAndMont();
        return ResponseEntity.ok(peryear);
    }
}
