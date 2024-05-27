package mingetal.MCM.cliente.controllers;

import mingetal.MCM.cliente.entities.CotizacionEntity;
import mingetal.MCM.cliente.services.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/cliente/cotizacion")
public class CotizacionController {
    @Autowired
    CotizacionService cotizacionService;

    @PostMapping("/guardar-cotizacion")
    public ResponseEntity<CotizacionEntity> saveCotizacion(@RequestBody CotizacionEntity cotizacionEntity) {
        System.out.println(cotizacionEntity);
        cotizacionService.save(cotizacionEntity);
        return ResponseEntity.ok(cotizacionEntity);
    }

    @GetMapping("/")
    public ResponseEntity<List<CotizacionEntity>> getAll(){
        List<CotizacionEntity> cotizacionEntities=cotizacionService.findAll();
        System.out.println(cotizacionEntities);
        if(cotizacionEntities != null){
            return ResponseEntity.ok(cotizacionEntities);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<CotizacionEntity> getCotizacionById(@PathVariable("id") int id){
        CotizacionEntity cotizacionEntity = cotizacionService.findById(id);
        System.out.println(cotizacionEntity);
        if(cotizacionEntity != null){
            return ResponseEntity.ok(cotizacionEntity);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/pedido/{pedido}")
    public ResponseEntity<List<CotizacionEntity>> getCotizacionByPedido(@PathVariable("pedido") String pedido){
        List<CotizacionEntity> cotizacionEntities = cotizacionService.findByPedido(pedido);
        System.out.println(cotizacionEntities);
        if(cotizacionEntities != null){
            return ResponseEntity.ok(cotizacionEntities);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CotizacionEntity>> getCotizacionByFecha(@PathVariable("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha){
        List<CotizacionEntity> cotizacionEntities = cotizacionService.findByFecha(fecha);
        System.out.println(cotizacionEntities);
        if(cotizacionEntities != null){
            return ResponseEntity.ok(cotizacionEntities);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CotizacionEntity>> getCotizacionByEstado(@PathVariable("estado") String estado){
        List<CotizacionEntity> cotizacionEntities = cotizacionService.findByEstado(estado);
        System.out.println(cotizacionEntities);
        if(cotizacionEntities != null){
            return ResponseEntity.ok(cotizacionEntities);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/rut/{rut}")
    public ResponseEntity<List<CotizacionEntity>> getCotizacionByRutCliente(@PathVariable("rut") String rutCliente){
        List<CotizacionEntity> cotizacionEntities = cotizacionService.findByRutCliente(rutCliente);
        System.out.println(cotizacionEntities);
        if(cotizacionEntities != null){
            return ResponseEntity.ok(cotizacionEntities);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CotizacionEntity> deleteCotizacion(@PathVariable("id") int id){
        CotizacionEntity cotizacionEntity = cotizacionService.delete(id);
        System.out.println(cotizacionEntity);
        if(cotizacionEntity != null){
            return ResponseEntity.ok(cotizacionEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<CotizacionEntity> updateCotizacion(@RequestBody CotizacionEntity cotizacionEntity){
        CotizacionEntity cotizacionEntity1 = cotizacionService.update(cotizacionEntity);
        System.out.println(cotizacionEntity1);
        if(cotizacionEntity1 != null){
            return ResponseEntity.ok(cotizacionEntity1);
        }
        return ResponseEntity.notFound().build();
    }

}