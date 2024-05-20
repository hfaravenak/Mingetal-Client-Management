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
    List<ProveedorEntity> findByDespacho(@Param("rubro") String rubro);

    @Query("select e from ProveedorEntity e where e.rubro = :rubro")
    List<ProveedorEntity> findByRubro(@Param("rubro") String rubro);

    @Query("select e from ProveedorEntity e where e.id_contacto = :id_contacto")
    ProveedorEntity findByRut1(@Param("id_contacto") String id_contacto);
    @Query("select e from ProveedorEntity e where e.id_contacto2 = :id_contacto2")
    ProveedorEntity findByRut2(@Param("id_contacto2") String id_contacto2);
    @Query("select e from ProveedorEntity e where e.id_contacto3 = :id_contacto3")
    ProveedorEntity findByRut3(@Param("id_contacto3") String id_contacto3);

}
