package mingetal.MCM.Login.controllers;


import mingetal.MCM.Login.entities.UsuarioEntity;
import mingetal.MCM.Login.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/getUsers")
    public ResponseEntity<List<UsuarioEntity>> getUsuarios() {
        List<UsuarioEntity> usuarioEntities = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarioEntities);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestParam String nombres, @RequestParam String correo, @RequestParam String password) {
        try {
            UsuarioEntity usuario = usuarioService.registerUser(nombres, correo, password);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String correo, @RequestParam String password) {
        try {
            UsuarioEntity usuario = usuarioService.login(correo, password);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
