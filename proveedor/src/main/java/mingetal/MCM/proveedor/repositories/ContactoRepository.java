package mingetal.MCM.proveedor.repositories;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<ContactoEntity, Integer> {
    @Query("select e from ContactoEntity e where e.rut = :rut")
    ContactoEntity findById(@Param("rut") String rut);

    // find by nombre contacto
    @Query("select e from ContactoEntity e where e.nombre = :nombre")
    ContactoEntity findByNombreContacto(@Param("nombre") String nombre);
}
