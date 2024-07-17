package mingetal.MCM.cliente.controllers;

import mingetal.MCM.cliente.entities.ClienteEntity;
import mingetal.MCM.cliente.services.ClienteService;
import org.apache.catalina.core.AsyncContextImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Por favor seleccione un archivo para cargar.");
        }

        try {
            // Lógica para manejar el archivo, por ejemplo, guardarlo en el servidor
            // clienteService.saveFile(file);
            System.out.println("#####################");
            List<ClienteEntity> clientes = clienteService.readExcelFile(file);
            System.out.println(clientes);
            System.out.println("**********************");
            clienteService.saveAll(clientes);
            System.out.println("----------------------");
            return ResponseEntity.ok("Archivo cargado exitosamente: " + file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo cargar el archivo: " + e.getMessage());
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
    public ResponseEntity<List<List<Object>>> getRanking(@RequestHeader("Authorization") String authHeader){

        List<List<Object>> ranking  = clienteService.getRankingCliente(authHeader);
        System.out.println(ranking);
        return ResponseEntity.ok(ranking);
    }

}
