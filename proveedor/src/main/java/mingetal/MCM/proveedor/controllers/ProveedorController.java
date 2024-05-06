package mingetal.MCM.proveedor.controllers;

import mingetal.MCM.proveedor.entities.ProveedorEntity;
import mingetal.MCM.proveedor.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // Endpoint para crear un nuevo proveedor
    @PostMapping("/crear")
    public ResponseEntity<ProveedorEntity> createSupplier(@RequestParam String empresa,
                                                          @RequestParam String rubro,
                                                          @RequestParam String comentario) {
        ProveedorEntity proveedor = proveedorService.createSupplier(new ProveedorEntity(empresa, rubro, comentario));
        return new ResponseEntity<>(proveedor, HttpStatus.CREATED);
    }

    // Endpoint para actualizar la empresa de un proveedor
    @PutMapping("/{id}/empresa")
    public ResponseEntity<ProveedorEntity> updateSupplierEmpresa(@PathVariable int id,
                                                                 @RequestParam String nuevaEmpresa) {
        ProveedorEntity proveedor = proveedorService.updateEmpresa(id, nuevaEmpresa);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    // Endpoint para actualizar el rubro de un proveedor
    @PutMapping("/{id}/rubro")
    public ResponseEntity<ProveedorEntity> updateSupplierRubro(@PathVariable int id,
                                                               @RequestParam String nuevoRubro) {
        ProveedorEntity proveedor = proveedorService.updateRubro(id, nuevoRubro);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    // Endpoint para actualizar el comentario de un proveedor
    @PutMapping("/{id}/comentario")
    public ResponseEntity<ProveedorEntity> updateSupplierComentario(@PathVariable int id,
                                                                    @RequestParam String nuevoComentario) {
        ProveedorEntity proveedor = proveedorService.updateComentario(id, nuevoComentario);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    // Endpoint para eliminar un proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable int id) {
        proveedorService.deleteSupplier(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint para obtener todos los proveedores
    @GetMapping("/todos")
    public ResponseEntity<List<ProveedorEntity>> getAllSuppliers() {
        List<ProveedorEntity> proveedores = proveedorService.findAll();
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }

    // Endpoint para obtener un proveedor por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorEntity> getSupplierById(@PathVariable int id) {
        ProveedorEntity proveedor = proveedorService.findById(id);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    // Endpoint para buscar proveedores por empresa
    @GetMapping("/buscar/empresa")
    public ResponseEntity<ProveedorEntity> getSupplierByEmpresa(@RequestParam String empresa) {
        ProveedorEntity proveedor = proveedorService.findByEmpresa(empresa);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    // Endpoint para buscar proveedores por rut
    @GetMapping("/buscar/rut")
    public ResponseEntity<ProveedorEntity> getSupplierByRut(@RequestParam String rut) {
        ProveedorEntity proveedor = proveedorService.findByRut(rut);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    // Endpoint para buscar proveedores por rubro
    @GetMapping("/buscar/rubro")
    public ResponseEntity<List<ProveedorEntity>> getSupplierByRubro(@RequestParam String rubro) {
        List<ProveedorEntity> proveedores = proveedorService.findByRubro(rubro);
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }
}
