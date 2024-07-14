package mingetal.MCM.Login.controllers;

import mingetal.MCM.Login.dto.AuthRequest;
import mingetal.MCM.Login.entities.UsuarioEntity;
import mingetal.MCM.Login.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UsuarioEntity usuario) {
        return usuarioService.saveUser(usuario);
    }

    //ESTE ES EL LOGIN EL CUAL RETORNA UN TOKEN!!!
    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getCorreo(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return usuarioService.generateToken(authRequest.getCorreo());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        usuarioService.validateToken(token);
        return "Token is valid";
    }
}
