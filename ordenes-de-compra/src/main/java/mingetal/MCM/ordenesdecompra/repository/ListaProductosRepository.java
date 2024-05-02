package mingetal.MCM.ordenesdecompra.repository;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ListaProductosRepository extends JpaRepository<ListaProductosEntity, String> {
    @Query("select e from ListaProductosEntity e where e.id = :id")
    ListaProductosEntity findById(@Param("id") int id);

    @Query("select e from ListaProductosEntity e where e.id_OC_cliente = :id_OC_cliente")
    List<ListaProductosEntity> findByIdCliente(@Param("id_OC_cliente") int id_OC_cliente);

    @Query("select e from ListaProductosEntity e where e.id_producto = :id_producto")
    List<ListaProductosEntity> findByIdProducto(@Param("id_producto") int id_producto);

    @Query("select e from ListaProductosEntity e where e.id_OC_proveedor = :id_OC_proveedor")
    List<ListaProductosEntity> findByIdProveedor(@Param("id_OC_proveedor") int id_OC_proveedor);
}
