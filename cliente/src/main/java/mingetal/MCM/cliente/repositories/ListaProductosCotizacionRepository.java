package mingetal.MCM.cliente.repositories;

import mingetal.MCM.cliente.entities.ListaProductosCotizacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ListaProductosCotizacionRepository extends JpaRepository<ListaProductosCotizacionEntity, Integer> {
    @Query("select e from ListaProductosCotizacionEntity e where e.id = :id")
    ListaProductosCotizacionEntity findById(@Param("id") int id);

    @Query("select e from ListaProductosCotizacionEntity e where e.id_cotizacion = :id_cotizacion")
    List<ListaProductosCotizacionEntity> findByIdCotizacion(@Param("id_cotizacion") int id_cotizacion);
}
