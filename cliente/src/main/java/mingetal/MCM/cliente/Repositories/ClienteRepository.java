package mingetal.MCM.cliente.Repositories;

import mingetal.MCM.cliente.Entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {
    ClienteEntity findByRut(String rut);
    ClienteEntity findByNombre(String nombre);
<<<<<<< HEAD

    ClienteEntity findByEmpresa(String empresa);
=======
    List<ClienteEntity> findByEmpresa(String empresa);
>>>>>>> c72a1d92eb9ddb5a03bb97c9076a3b1c72d34b9f
}
