package mingetal.MCM.ordenesdecompra.controller;


import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
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
    public ResponseEntity<OrdenesDeCompraClienteEntity> saveOCCliente(@RequestBody OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity) {
        return ResponseEntity.ok(ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity));
    }

    @GetMapping("/")
    public ResponseEntity<List<OrdenesDeCompraClienteEntity>> getAllOCCliente(){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities=ordenesDeCompraClienteService.findAll();
        return ResponseEntity.ok(ordenesDeCompraClienteEntities);
    }
    @GetMapping("/pagado/entregado/")
    public ResponseEntity<List<OrdenesDeCompraClienteEntity>> getOCClientePagadoEntregado(){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities=ordenesDeCompraClienteService.findPagadoEntregado();
        return ResponseEntity.ok(ordenesDeCompraClienteEntities);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrdenesDeCompraClienteEntity> getOCClienteById(@PathVariable("id") int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntities = ordenesDeCompraClienteService.findById(id);
        return ResponseEntity.ok(ordenesDeCompraClienteEntities);
    }
    @GetMapping("/id/{id_cliente}")
    public ResponseEntity<List<OrdenesDeCompraClienteEntity>> getOCClienteByIdCliente(@PathVariable("id_cliente") String id_cliente){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = ordenesDeCompraClienteService.findByIdCliente(id_cliente);
        return ResponseEntity.ok(ordenesDeCompraClienteEntities);
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<OrdenesDeCompraClienteEntity>> getOCClienteByNombreCliente(@PathVariable("nombre") String nombre){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = ordenesDeCompraClienteService.findByNameCliente(nombre);
        return ResponseEntity.ok(ordenesDeCompraClienteEntities);
    }
    @GetMapping("/empresa/{empresa}")
    public ResponseEntity<List<OrdenesDeCompraClienteEntity>> getOCClienteByEmpresaCliente(@PathVariable("empresa") String empresa){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = ordenesDeCompraClienteService.findByEmpresaCliente(empresa);
        return ResponseEntity.ok(ordenesDeCompraClienteEntities);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<OrdenesDeCompraClienteEntity> deleteOCClienteById(@PathVariable("id") int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteService.delete(id);
        return ResponseEntity.ok(ordenesDeCompraClienteEntity);
    }

    @PutMapping("/update")
    public ResponseEntity<OrdenesDeCompraClienteEntity> updateOCCliente(@RequestBody OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity){
        OrdenesDeCompraClienteEntity newOrdenesDeCompraClienteEntity = ordenesDeCompraClienteService.update(ordenesDeCompraClienteEntity);
        return ResponseEntity.ok(newOrdenesDeCompraClienteEntity);
    }

    @GetMapping("/productsbyyear")
    public ResponseEntity<List<Object[]>> getProductsByYear(){
        List<Object[]> peryear = ordenesDeCompraClienteService.getProductByYear();
        return ResponseEntity.ok(peryear);
    }

    @GetMapping("/productsbyyearandmonth")
    public ResponseEntity<List<Object[]>> getProductsByYearAndMonth(){
        List<Object[]> peryear = ordenesDeCompraClienteService.getProductByYearAndMonth();
        return ResponseEntity.ok(peryear);
    }

    @GetMapping("/salesbyyear")
    public ResponseEntity<List<Object[]>> getSalesByYear(){
        List<Object[]> peryear = ordenesDeCompraClienteService.getSalesByYear();
        return ResponseEntity.ok(peryear);
    }

    @GetMapping("/similarpreviousmonths")
    public ResponseEntity<List<Object[]>> getSimilarPreviousMonths(){
        List<Object[]> simil = ordenesDeCompraClienteService.similarPreviusMonths();
        return ResponseEntity.ok(simil);
    }

    @GetMapping("/salesbyyearandmonth")
    public ResponseEntity<List<Object[]>> getSalesByYearAndMonth(){
        List<Object[]> peryear = ordenesDeCompraClienteService.getSalesByYearAndMonth();
        return ResponseEntity.ok(peryear);
    }

    @GetMapping("/clientsbyyear")
    public ResponseEntity<List<Object[]>> getClientsByYear(){
        List<Object[]> peryear = ordenesDeCompraClienteService.getClientsByYear();
        return ResponseEntity.ok(peryear);
    }
}
