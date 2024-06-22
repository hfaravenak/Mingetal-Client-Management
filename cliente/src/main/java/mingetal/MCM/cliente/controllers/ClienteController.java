package mingetal.MCM.cliente.controllers;

import mingetal.MCM.cliente.entities.ClienteEntity;
import mingetal.MCM.cliente.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @PostMapping("")
    public ResponseEntity<ClienteEntity> saveCliente(@RequestBody ClienteEntity clienteEntity) {
        try {
            clienteService.save(clienteEntity);
            return ResponseEntity.ok(clienteEntity);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Ya existe un cliente registrado con este RUT.")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ClienteEntity>> getAll(){
        List<ClienteEntity> clienteEntities=clienteService.findAll();
        return ResponseEntity.ok(clienteEntities);
    }
    @GetMapping("/rut/{rut}")
    public ResponseEntity<ClienteEntity> getClienteByRut(@PathVariable("rut") String rut){
        ClienteEntity clienteEntity = clienteService.findByRut(rut);
        return ResponseEntity.ok(clienteEntity);
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ClienteEntity>> getClienteByNombre(@PathVariable("nombre") String nombre){
        List<ClienteEntity> clienteEntity = clienteService.findByNombre(nombre);
        return ResponseEntity.ok(clienteEntity);

    }
    @GetMapping("/fullNombre/{nombre}")
    public ResponseEntity<ClienteEntity> getClienteByNombreTextual(@PathVariable("nombre") String nombre){
        ClienteEntity clienteEntity = clienteService.findByNombreTextual(nombre);
        return ResponseEntity.ok(clienteEntity);

    }
    @GetMapping("/empresa/{empresa}")
    public ResponseEntity<List<ClienteEntity>> getClienteByEmpresa(@PathVariable("empresa") String empresa){
        List<ClienteEntity> clienteEntities = clienteService.findByEmpresa(empresa);
        return ResponseEntity.ok(clienteEntities);
    }

    @DeleteMapping("/delete/{rut}")
    public ResponseEntity<ClienteEntity> deleteCliente(@PathVariable("rut") String rut){
        ClienteEntity clienteEntity = clienteService.delete(rut);
        return ResponseEntity.ok(clienteEntity);
    }

    @PutMapping("/update")
    public ResponseEntity<ClienteEntity> updateCliente(@RequestBody ClienteEntity clienteEntity){
        ClienteEntity clienteEntity1 = clienteService.update(clienteEntity);
        return ResponseEntity.ok(clienteEntity1);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<List<Object>>> getRanking(){
        List<List<Object>> ranking  = clienteService.getRankingCliente();
        System.out.println(ranking);
        return ResponseEntity.ok(ranking);
    }

}
