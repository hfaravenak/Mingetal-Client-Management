package mingetal.MCM.proveedor.controllers;

import mingetal.MCM.proveedor.entities.ProveedorEntity;
import mingetal.MCM.proveedor.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // Endpoint para crear un nuevo proveedor
    @PostMapping("")
    public ResponseEntity<ProveedorEntity> createSupplier(@RequestBody ProveedorEntity proveedor) {
        ProveedorEntity proveedorNew = proveedorService.createSupplier(proveedor);
        return new ResponseEntity<>(proveedorNew, HttpStatus.CREATED);
    }

    // Endpoint para actualizar la empresa de un proveedor
    @PutMapping("/empresa/{id}")
    public ResponseEntity<ProveedorEntity> updateSupplierEmpresa(@PathVariable int id,
                                                                 @RequestParam String nuevaEmpresa) {
        ProveedorEntity proveedor = proveedorService.updateEmpresa(id, nuevaEmpresa);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    // Endpoint para actualizar el rubro de un proveedor
    @PutMapping("/rubro/{id}")
    public ResponseEntity<ProveedorEntity> updateSupplierRubro(@PathVariable int id,
                                                               @RequestParam String nuevoRubro) {
        ProveedorEntity proveedor = proveedorService.updateRubro(id, nuevoRubro);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    // Endpoint para actualizar el comentario de un proveedor
    @PutMapping("/comentario/{id}")
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
    @GetMapping("/")
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
    @GetMapping("/empresa/{empresa}")
    public ResponseEntity<ProveedorEntity> getSupplierByEmpresa(@PathVariable("empresa") String empresa) {
        ProveedorEntity proveedor = proveedorService.findByEmpresa(empresa);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    // Endpoint para buscar proveedores por rut
    @GetMapping("/rut/{rut}")
    public ResponseEntity<ProveedorEntity> getSupplierByRut(@PathVariable("rut") String rut) {
        ProveedorEntity proveedor = proveedorService.findByRut(rut);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ProveedorEntity> getSupplierByNombre(@PathVariable("nombre") String nombre) {
        ProveedorEntity proveedor = proveedorService.findByContacto(nombre);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    // Endpoint para buscar proveedores por rubro
    @GetMapping("/rubro/{rubro}")
    public ResponseEntity<List<ProveedorEntity>> getSupplierByRubro(@PathVariable("rubro") String rubro) {
        List<ProveedorEntity> proveedores = proveedorService.findByRubro(rubro);
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }
}
