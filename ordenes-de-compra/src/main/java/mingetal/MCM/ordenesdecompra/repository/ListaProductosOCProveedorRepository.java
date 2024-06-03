package mingetal.MCM.ordenesdecompra.repository;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ListaProductosOCProveedorRepository extends JpaRepository<ListaProductosOCProveedorEntity, Integer> {
    @Query("select e from ListaProductosOCProveedorEntity e where e.id = :id")
    ListaProductosOCProveedorEntity findById(@Param("id") int id);

    @Query("select e from ListaProductosOCProveedorEntity e where e.id_OC_proveedor = :id_OC_proveedor")
    List<ListaProductosOCProveedorEntity> findByIdProveedor(@Param("id_OC_proveedor") int id_OC_proveedor);
}
