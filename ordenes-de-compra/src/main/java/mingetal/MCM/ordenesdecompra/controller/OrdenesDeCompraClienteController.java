package mingetal.MCM.ordenesdecompra.controller;


import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.service.OrdenesDeCompraClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ordenes_de_compra/cliente")
public class OrdenesDeCompraClienteController {

    @Autowired
    OrdenesDeCompraClienteService ordenesDeCompraClienteService;

    @PostMapping()
    public ResponseEntity<OrdenesDeCompraClienteEntity> saveCliente(@RequestBody OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity) {
        boolean bool = ordenesDeCompraClienteService.save(ordenesDeCompraClienteEntity);
        if(bool){
            return ResponseEntity.ok(ordenesDeCompraClienteEntity);
        }
        return null;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrdenesDeCompraClienteEntity>> getAllCliente(){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities=ordenesDeCompraClienteService.findAll();
        System.out.println(ordenesDeCompraClienteEntities);
        return ResponseEntity.ok(ordenesDeCompraClienteEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenesDeCompraClienteEntity> getOrdenClienteById(@PathVariable("id") int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntities = ordenesDeCompraClienteService.findById(id);
        System.out.println(ordenesDeCompraClienteEntities);
        return ResponseEntity.ok(ordenesDeCompraClienteEntities);
    }

    @GetMapping("/id/{id_cliente}")
    public ResponseEntity<List<OrdenesDeCompraClienteEntity>> getOrdenProveedorByIdCliente(@PathVariable("id_cliente") int id_cliente){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = ordenesDeCompraClienteService.findByIdCliente(id_cliente);
        System.out.println(ordenesDeCompraClienteEntities);
        return ResponseEntity.ok(ordenesDeCompraClienteEntities);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<OrdenesDeCompraClienteEntity> deleteOCClienteById(@PathVariable("id") int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteService.deleteOCCliente(id);
        System.out.println(ordenesDeCompraClienteEntity);
        return ResponseEntity.ok(ordenesDeCompraClienteEntity);
    }

    @PutMapping("/update/estado_factura/{id}")
    public ResponseEntity<OrdenesDeCompraClienteEntity> updateOCClienteByEstadoFactura(@PathVariable("id") int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteService.updateOCClienteByEstadoFactura(id);
        return ResponseEntity.ok(ordenesDeCompraClienteEntity);
    }
    @PutMapping("/update/estado_pago/{id}")
    public ResponseEntity<OrdenesDeCompraClienteEntity> updateOCClienteByEstadoPago(@PathVariable("id") int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteService.updateOCClienteByEstadoPago(id);
        return ResponseEntity.ok(ordenesDeCompraClienteEntity);
    }
    @PutMapping("/update/estado_entrega/{id}")
    public ResponseEntity<OrdenesDeCompraClienteEntity> updateOCClienteByEstadoEntrega(@PathVariable("id") int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteService.updateOCClienteByEstadoEntrega(id);
        return ResponseEntity.ok(ordenesDeCompraClienteEntity);
    }
    @PutMapping("/update/modo_pago/{id}")
    public ResponseEntity<OrdenesDeCompraClienteEntity> updateOCClienteByModoPago(@PathVariable("id") int id,
                                                                                  @RequestParam String modo_pago){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteService.updateOCClienteByModoPago(id, modo_pago);
        return ResponseEntity.ok(ordenesDeCompraClienteEntity);
    }
    @PutMapping("/update/fecha_pago/{id}")
    public ResponseEntity<OrdenesDeCompraClienteEntity> updateOCClienteByFechaPago(@PathVariable("id") int id,
                                                                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha_pago){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteService.updateOCClienteByFechaPago(id, fecha_pago);
        return ResponseEntity.ok(ordenesDeCompraClienteEntity);
    }
}
