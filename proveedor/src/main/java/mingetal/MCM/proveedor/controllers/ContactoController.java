package mingetal.MCM.proveedor.controllers;

import mingetal.MCM.proveedor.entities.ContactoEntity;
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

    @PostMapping()
    public ResponseEntity<ContactoEntity> createContacto(@RequestBody ContactoEntity contacto) {
        try {
            contactoService.save(contacto);
            return ResponseEntity.ok(contacto);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Ya existe un contacto registrado con este RUT.")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ContactoEntity>> getContactos() {
        List<ContactoEntity> contactos = contactoService.findAll();
        return new ResponseEntity<>(contactos, HttpStatus.OK);
    }
    @PutMapping("/update/")
    public ResponseEntity<ContactoEntity> updateContacto(@RequestBody ContactoEntity updatedContacto) {
        ContactoEntity contacto = contactoService.update(updatedContacto);
        return new ResponseEntity<>(contacto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContacto(@PathVariable String id) {
        contactoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
