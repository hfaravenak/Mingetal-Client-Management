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
        ContactoEntity createdContacto = contactoService.save(contacto);
        return new ResponseEntity<>(createdContacto, HttpStatus.CREATED);
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
