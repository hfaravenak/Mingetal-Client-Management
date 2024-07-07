package mingetal.MCM.Login.repositories;

import mingetal.MCM.Login.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, String> {
    UsuarioEntity findByCorreo(String correo);
}
