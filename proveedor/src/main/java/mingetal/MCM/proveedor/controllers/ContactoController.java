package mingetal.MCM.proveedor.controllers;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.entities.ProveedorEntity;
import mingetal.MCM.proveedor.services.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedor/contactos")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    // Create
    @PostMapping()
    public ResponseEntity<ContactoEntity> createContacto(@RequestBody ContactoEntity contacto) {
        ContactoEntity createdContacto = contactoService.createContacto(contacto);
        return new ResponseEntity<>(createdContacto, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/")
    public ResponseEntity<List<ContactoEntity>> getAllContactos() {
        List<ContactoEntity> contactos = contactoService.findAllContactos();
        return new ResponseEntity<>(contactos, HttpStatus.OK);
    }

    @GetMapping("/proveedores")
    public ResponseEntity<List<ContactoEntity>> getAllContactos1Proveedor() {
        List<ContactoEntity> contactos = contactoService.getAllContactos1Proveedor();
        return new ResponseEntity<>(contactos, HttpStatus.OK);
    }

     @GetMapping("/listProv/")
    public ResponseEntity<List<ContactoEntity>> findByProveedorContacto1(){
         List<ContactoEntity> contactos = contactoService.findByProveedorContacto1();
         return new ResponseEntity<>(contactos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactoEntity> getContactoById(@PathVariable String id) {
        ContactoEntity contacto = contactoService.findContactoById(id);
        return new ResponseEntity<>(contacto, HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ContactoEntity>> getContactoByNombre(@PathVariable String nombre) {
        List<ContactoEntity> contactos = contactoService.findContactoByNombre(nombre);
        return new ResponseEntity<>(contactos, HttpStatus.OK);
    }
    @GetMapping("/empresa/{empresa}")
    public ResponseEntity<List<ContactoEntity>> getContactoByEmpresa(@PathVariable String empresa) {
        List<ContactoEntity> contactos = contactoService.findByContactosFromEmpresa(empresa);
        return new ResponseEntity<>(contactos, HttpStatus.OK);
    }

    // Update
    @PutMapping("/update/")
    public ResponseEntity<ContactoEntity> updateContacto(@RequestBody ContactoEntity updatedContacto) {
        ContactoEntity contacto = contactoService.updateContacto(updatedContacto);
        return new ResponseEntity<>(contacto, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContacto(@PathVariable String id) {
        contactoService.deleteContacto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
