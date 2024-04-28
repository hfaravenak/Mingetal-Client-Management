package mingetal.MCM.cliente.Repositories;

import mingetal.MCM.cliente.Entities.CotizacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CotizacionRepository extends JpaRepository<CotizacionEntity, Long> {
    List<CotizacionEntity> findByPedido(String pedido);
    List<CotizacionEntity> findByFecha(LocalDate fecha);
    List<CotizacionEntity> findByEstado(String estado);
    List<CotizacionEntity> findByRutCliente(String rutCliente);
}
