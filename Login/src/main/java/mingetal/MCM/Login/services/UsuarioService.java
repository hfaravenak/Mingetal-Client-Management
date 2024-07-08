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
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<UsuarioEntity> getUsuarios() {
        List<UsuarioEntity> usuarioEntities =  usuarioRepository.findAll();
        return usuarioEntities;
    }

    public UsuarioEntity registerUser(String nombres, String correo, String password) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombres(nombres);
        usuario.setCorreo(correo);
        usuario.setPassword(encoder.encode(password));
        usuario.setIntentos(0);
        usuario.setBloqueado(false);
        return usuarioRepository.save(usuario);
    }

    public UsuarioEntity login(String correo, String password) throws Exception {
        UsuarioEntity usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            throw new Exception("Usuario o contraseña incorrectos.");
        }

        if (usuario.isBloqueado()) {
            throw new Exception("Se ha alcanzado el límite de intentos.");
        }

        if (!encoder.matches(password, usuario.getPassword())) {
            usuario.setIntentos(usuario.getIntentos() + 1);
            if (usuario.getIntentos() >= 3) {
                usuario.setBloqueado(true);
            }
            usuarioRepository.save(usuario);
            throw new Exception("Usuario o contraseña incorrectos.");
        } else {
            usuario.setIntentos(0);
            usuarioRepository.save(usuario);
        }

        return usuario;
    }


}
