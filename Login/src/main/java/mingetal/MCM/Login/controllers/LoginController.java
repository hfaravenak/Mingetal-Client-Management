package mingetal.MCM.Login.controllers;

import mingetal.MCM.Login.dto.AuthRequest;
import mingetal.MCM.Login.entities.UsuarioEntity;
import mingetal.MCM.Login.repositories.UsuarioRepository;
import mingetal.MCM.Login.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @GetMapping("/{correo}")
    public UsuarioEntity getUser(@PathVariable String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        usuarioService.validateToken(token);
        return "Token is valid";
    }


    @PostMapping("/recuperar-contrasenia")
    public ResponseEntity<String> recuperarContrasenia(@RequestParam String correo) {
        try {
            String code = usuarioService.generateNumericCode();
            usuarioService.setCode(correo, code);
            String text = "Se ha solicitado un cambio de contraseña para tu cuenta de Mingetal Client Management.\n"
                    + "Tu código es: " + code;
            usuarioService.sendEmail(correo, text);
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al enviar el correo: " + e.getMessage());
        }
    }

    @PostMapping("/codigo-reestablecimiento")
    public ResponseEntity<String> codigoReestablecimiento(@RequestParam String correo, @RequestParam String codigoReestablecimiento) {
        try {
            UsuarioEntity usuario = usuarioRepository.findByCorreo(correo);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
            if (usuario.getCodigo_cambio_contrasenia().equals(codigoReestablecimiento)) {
                usuario.setCodigo_cambio_contrasenia(null);
                usuario.setCambiando(true);
                usuarioRepository.save(usuario);
                return ResponseEntity.ok("El código es correcto");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El código es incorrecto");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al verificar el código: " + e.getMessage());
        }
    }

    @PostMapping("/cambio-contrasenia")
    public ResponseEntity<String> cambioContraseña(@RequestParam String correo, @RequestParam String nuevaContrasenia) {
        try {
            UsuarioEntity usuario = usuarioRepository.findByCorreo(correo);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            } else {
                nuevaContrasenia = passwordEncoder.encode(nuevaContrasenia);
                usuario.setPassword(nuevaContrasenia);
                usuario.setCambiando(false);
                usuarioRepository.save(usuario);
                return ResponseEntity.ok("Cambio de contraseña exitoso");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cambiar la contraseña: " + e.getMessage());
        }
    }

    @PostMapping("/cambio-contrasenia/cancelar")
    public ResponseEntity<String> cambioContraseñaCancelar(@RequestParam String correo) {
        try {
            UsuarioEntity usuario = usuarioRepository.findByCorreo(correo);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            } else {
                usuario.setCambiando(false);
                usuarioRepository.save(usuario);
                return ResponseEntity.ok("Cancelado correctamente");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cancelar la contraseña: " + e.getMessage());
        }
    }
}