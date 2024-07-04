package mingetal.MCM.Login.controllers;


import mingetal.MCM.Login.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UsuarioService usuarioService;

}
