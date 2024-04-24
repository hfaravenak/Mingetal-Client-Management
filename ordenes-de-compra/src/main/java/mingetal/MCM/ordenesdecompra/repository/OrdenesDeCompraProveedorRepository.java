package mingetal.MCM.ordenesdecompra.repository;

import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrdenesDeCompraProveedorRepository extends JpaRepository<OrdenesDeCompraProveedorEntity, String> {

    @Query("select e from OrdenesDeCompraProveedorEntity e where e.id = :id")
    OrdenesDeCompraProveedorEntity findById(@Param("id") int id);

    @Query("select e from OrdenesDeCompraProveedorEntity e where e.id_proveedor = :id_proveedor")
    List<OrdenesDeCompraProveedorEntity> findByIdProveedor(@Param("id_proveedor") int id_proveedor);
}
