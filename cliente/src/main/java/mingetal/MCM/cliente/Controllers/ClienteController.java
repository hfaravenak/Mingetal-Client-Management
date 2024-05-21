package mingetal.MCM.cliente.Controllers;

import mingetal.MCM.cliente.Entities.ClienteEntity;
import mingetal.MCM.cliente.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @PostMapping("/guardar-cliente")
    public ResponseEntity<ClienteEntity> saveCliente(@RequestBody ClienteEntity clienteEntity) {
        clienteService.save(clienteEntity);
        return ResponseEntity.ok(clienteEntity);
    }

    @GetMapping("/")
    public ResponseEntity<List<ClienteEntity>> getAll(){
        List<ClienteEntity> clienteEntities=clienteService.findAll();
        System.out.println(clienteEntities);
        return ResponseEntity.ok(clienteEntities);
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<ClienteEntity> getClienteByRut(@PathVariable("rut") String rut){
        ClienteEntity clienteEntity = clienteService.findByRut(rut);
        System.out.println(clienteEntity);
        return ResponseEntity.ok(clienteEntity);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ClienteEntity>> getClienteByNombre(@PathVariable("nombre") String nombre){
        List<ClienteEntity> clienteEntity = clienteService.findByNombre(nombre);
        System.out.println(clienteEntity);
        return ResponseEntity.ok(clienteEntity);

    }

    @GetMapping("/fullNombre/{nombre}")
    public ResponseEntity<ClienteEntity> getClienteByNombreTextual(@PathVariable("nombre") String nombre){
        ClienteEntity clienteEntity = clienteService.findByNombreTextual(nombre);
        System.out.println(clienteEntity);
        return ResponseEntity.ok(clienteEntity);

    }

    @GetMapping("/empresa/{empresa}")
    public ResponseEntity<List<ClienteEntity>> getClienteByEmpresa(@PathVariable("empresa") String empresa){
        List<ClienteEntity> clienteEntities = clienteService.findByEmpresa(empresa);
        System.out.println(clienteEntities);
        return ResponseEntity.ok(clienteEntities);
    }

    @DeleteMapping("/delete-rut/{rut}")
    public ResponseEntity<ClienteEntity> deleteCliente(@PathVariable("rut") String rut){
        ClienteEntity clienteEntity = clienteService.delete(rut);
        System.out.println(clienteEntity);
        return ResponseEntity.ok(clienteEntity);
    }

    @DeleteMapping("/delete-nombre/{nombre}")
    public ResponseEntity<ClienteEntity> deleteClienteByNombre(@PathVariable("nombre") String nombre){
        ClienteEntity clienteEntity = clienteService.deleteByNombre(nombre);
        System.out.println(clienteEntity);
        return ResponseEntity.ok(clienteEntity);
    }

    @PutMapping("/update")
    public ResponseEntity<ClienteEntity> updateCliente(@RequestBody ClienteEntity clienteEntity){
        ClienteEntity clienteEntity1 = clienteService.update(clienteEntity);
        System.out.println(clienteEntity1);
        return ResponseEntity.ok(clienteEntity1);
    }

}
