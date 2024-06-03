package mingetal.MCM.ordenesdecompra.repository;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ListaProductosOCClienteRepository extends JpaRepository<ListaProductosOCClienteEntity, Integer> {
    @Query("select e from ListaProductosOCClienteEntity e where e.id = :id")
    ListaProductosOCClienteEntity findById(@Param("id") int id);

    @Query("select e from ListaProductosOCClienteEntity e where e.id_OC_cliente = :id_OC_cliente")
    List<ListaProductosOCClienteEntity> findByIdCliente(@Param("id_OC_cliente") int id_OC_cliente);
}
