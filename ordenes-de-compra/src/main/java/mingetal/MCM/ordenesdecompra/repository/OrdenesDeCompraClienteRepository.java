package mingetal.MCM.ordenesdecompra.repository;

import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrdenesDeCompraClienteRepository extends JpaRepository<OrdenesDeCompraClienteEntity, String> {

    @Query("select e from OrdenesDeCompraClienteEntity e where e.id = :id")
    OrdenesDeCompraClienteEntity findById(@Param("id") int id);

    @Query("select e from OrdenesDeCompraClienteEntity e where e.id_cliente = :id_cliente")
    List<OrdenesDeCompraClienteEntity> findByIdCliente(@Param("id_cliente") int id_cliente);
}
