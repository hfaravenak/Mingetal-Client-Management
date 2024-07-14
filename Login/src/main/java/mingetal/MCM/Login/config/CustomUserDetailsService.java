package mingetal.MCM.Login.config;

import mingetal.MCM.Login.entities.UsuarioEntity;
import mingetal.MCM.Login.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuarioEntity = Optional.ofNullable(usuarioRepository.findByCorreo(correo));
        return usuarioEntity.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + correo));
    }
}
