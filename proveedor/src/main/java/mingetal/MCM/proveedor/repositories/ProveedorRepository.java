package mingetal.MCM.proveedor.repositories;

import mingetal.MCM.proveedor.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, String> {

    @Query("select e from ProveedorEntity e where e.id_proveedor = :id")
    ProveedorEntity findById(@Param("id") int id);

    @Query("select e from ProveedorEntity e where e.empresa = :empresa")
    ProveedorEntity findByEmpresa(@Param("empresa") String empresa);

    @Query("select e from ProveedorEntity e where e.rubro = :rubro")
    List<ProveedorEntity> findByRubro(@Param("rubro") String rubro);

}
