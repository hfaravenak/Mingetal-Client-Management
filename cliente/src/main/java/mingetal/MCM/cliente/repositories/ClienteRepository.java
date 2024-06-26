package mingetal.MCM.cliente.repositories;

import mingetal.MCM.cliente.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {
    ClienteEntity findByRut(String rut);
    ClienteEntity findByNombre(String nombre);
    List<ClienteEntity> findByEmpresa(String empresa);
}
