package mingetal.MCM.Login.services;

import mingetal.MCM.Login.entities.UsuarioEntity;
import mingetal.MCM.Login.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public List<UsuarioEntity> getUsuarios() {
        List<UsuarioEntity> usuarioEntities =  usuarioRepository.findAll();
        return usuarioEntities;
    }

    public UsuarioEntity login(String correo, String password) throws Exception {
        UsuarioEntity usuario = new UsuarioEntity();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        try{
            usuario = usuarioRepository.findByCorreo(correo);
            // si el usuario no existe
            if(usuario == null){
                throw new Exception("Usuario o contraseña incorrectos.");
            }
            // usuario bloqueado por cantidad de intentos
            if(usuario.isBloqueado() ){
                throw new Exception("Se ha alcanzado el limite de intentos.");
            }
            // usuario con contrasel
            if(!encoder.matches(password, usuario.getPassword())){
                usuario.setIntentos(usuario.getIntentos() + 1);
                // bloquear si supera los 3 intento
                if (usuario.getIntentos() >= 3) {
                    usuario.setBloqueado(true);
                }
                // falta aumentar cantidad de intentos
                throw new Exception("Usuario o contraseña incorrectos.");
            }else {
                usuario.setIntentos(0);
            }
        } catch (Exception e) {
            throw new Exception("Error en la autenticación.");
        }
        return usuario;
    }
}
