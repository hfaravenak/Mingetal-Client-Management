package mingetal.MCM.cliente.controllers;


import mingetal.MCM.cliente.entities.ListaProductosCotizacionEntity;
import mingetal.MCM.cliente.services.ListaProductosCotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente/cotizacion/productos")
public class ListaProductosCotizacionController {

    @Autowired
    ListaProductosCotizacionService listaProductosCotizacionService;

    @PostMapping(("/"))
    public ResponseEntity<ListaProductosCotizacionEntity> saveListProductos(@RequestBody ListaProductosCotizacionEntity listaProductosCotizacionEntities) {
        return ResponseEntity.ok(listaProductosCotizacionService.save(listaProductosCotizacionEntities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ListaProductosCotizacionEntity>> getListProductosByIdOCCliente(@PathVariable("id") int id){
        return ResponseEntity.ok(listaProductosCotizacionService.findByIdCotizacion(id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ListaProductosCotizacionEntity> deleteListProductos(@PathVariable("id") int id){
        return ResponseEntity.ok(listaProductosCotizacionService.delete(id));
    }
}
