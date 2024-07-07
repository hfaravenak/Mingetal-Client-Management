package mingetal.MCM.Login.controllers;


import mingetal.MCM.Login.entities.UsuarioEntity;
import mingetal.MCM.Login.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/")
    public ResponseEntity<List<UsuarioEntity>> getUsuarios() {
        List<UsuarioEntity> usuarioEntities = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarioEntities);
    }


    @PostMapping
    public ResponseEntity<UsuarioEntity> login(@RequestBody UsuarioEntity usuarioEntity) {
        try {
            UsuarioEntity usuario = usuarioService.login(usuarioEntity.getCorreo(), usuarioEntity.getPassword());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
