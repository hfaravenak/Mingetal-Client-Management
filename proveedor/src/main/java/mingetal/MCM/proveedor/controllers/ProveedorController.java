package mingetal.MCM.proveedor.controllers;

import mingetal.MCM.proveedor.entities.ProveedorEntity;
import mingetal.MCM.proveedor.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @PostMapping("")
    public ResponseEntity<ProveedorEntity> createProveedor(@RequestBody ProveedorEntity proveedor) {
        ProveedorEntity proveedorNew = proveedorService.save(proveedor);
        return new ResponseEntity<>(proveedorNew, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProveedorEntity>> getProveedores() {
        List<ProveedorEntity> proveedores = proveedorService.findAll();
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }
    @GetMapping("/listOC/")
    public ResponseEntity<List<ProveedorEntity>> getProveedorByListOC(){
        List<ProveedorEntity> proveedores = proveedorService.findByListOC();
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }
    @GetMapping("/empresa/{empresa}")
    public ResponseEntity<ProveedorEntity> getProveedorByEmpresa(@PathVariable("empresa") String empresa) {
        ProveedorEntity proveedor = proveedorService.findByEmpresa(empresa);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }
    @GetMapping("/rut/{rut}")
    public ResponseEntity<ProveedorEntity> getProveedorByRut(@PathVariable("rut") String rut) {
        ProveedorEntity proveedor = proveedorService.findByRut(rut);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ProveedorEntity>> getProveedorByNombre(@PathVariable("nombre") String nombre) {
        List<ProveedorEntity> proveedor = proveedorService.findByNombre(nombre);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }
    @GetMapping("/rubro/{rubro}")
    public ResponseEntity<List<ProveedorEntity>> getProveedorByRubro(@PathVariable("rubro") String rubro) {
        List<ProveedorEntity> proveedores = proveedorService.findByRubro(rubro);
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }
    @GetMapping("/rubro/")
    public ResponseEntity<List<String>> getRubroProveedores() {
        List<String> rubros = proveedorService.findRubros();
        return new ResponseEntity<>(rubros, HttpStatus.OK);
    }
    @GetMapping("/despacho/")
    public ResponseEntity<List<ProveedorEntity>> getDespacho() {
        List<ProveedorEntity> proveedores = proveedorService.findDespacho();
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }
    @GetMapping("/fullNombre/{nombre}")
    public ResponseEntity<ProveedorEntity> getProveedorByNombreTextual(@PathVariable("nombre") String nombre){
        ProveedorEntity proveedorEntity = proveedorService.findByNombreTextual(nombre);
        System.out.println(proveedorEntity);
        return ResponseEntity.ok(proveedorEntity);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable int id) {
        proveedorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/")
    public ResponseEntity<ProveedorEntity> updateProveedor(@RequestBody ProveedorEntity proveedorEntity) {
        ProveedorEntity proveedor = proveedorService.update(proveedorEntity);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Por favor seleccione un archivo para cargar.");
        }
        try {
            // Lógica para manejar el archivo, por ejemplo, guardarlo en el servidor
            System.out.println("#####################");
            proveedorService.readExcelFile(file);
            //System.out.println(cotizaciones);
            System.out.println("**********************");
            //cotizacionService.saveAll(cotizaciones);
            System.out.println("----------------------");
            return ResponseEntity.ok("Archivo cargado exitosamente: " + file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo cargar el archivo: " + e.getMessage());
        }
    }


}
