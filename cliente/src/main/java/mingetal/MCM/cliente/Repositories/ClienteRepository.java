package mingetal.MCM.cliente.Repositories;

import mingetal.MCM.cliente.Entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {

    ClienteEntity findByRut(String rut);

    ClienteEntity findByNombre(String nombre);

    ClienteEntity findByEmpresa(String empresa);
}
