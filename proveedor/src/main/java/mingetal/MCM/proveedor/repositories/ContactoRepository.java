package mingetal.MCM.proveedor.repositories;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<ContactoEntity, Integer> {
    @Query("select e from ContactoEntity e where e.id_contacto = :id_contacto")
    ContactoEntity findById(@Param("id_contacto") int id_contacto);
}
