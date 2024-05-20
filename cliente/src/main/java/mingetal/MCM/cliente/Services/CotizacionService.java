package mingetal.MCM.cliente.Services;

import mingetal.MCM.cliente.Entities.CotizacionEntity;
import mingetal.MCM.cliente.Repositories.CotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CotizacionService {
    @Autowired
    CotizacionRepository cotizacionRepository;

    public void save(CotizacionEntity cotizacionEntity) { cotizacionRepository.save(cotizacionEntity); }
    public List<CotizacionEntity> findAll() { return cotizacionRepository.findAll(); }
    public CotizacionEntity findById(int id) { return cotizacionRepository.findById(id).orElse(null); }

    public List<CotizacionEntity> findByPedido(String pedido) { return cotizacionRepository.findByPedido(pedido); }
    public List<CotizacionEntity> findByFecha(LocalDate fecha) { return cotizacionRepository.findByFecha(fecha); }
    public List<CotizacionEntity> findByEstado(String estado) { return cotizacionRepository.findByEstado(estado); }
    public List<CotizacionEntity> findByRutCliente(String rutCliente) { return cotizacionRepository.findByRutCliente(rutCliente); }
    public CotizacionEntity delete(int id) {
        CotizacionEntity cotizacionEntity = findById(id);
        cotizacionRepository.delete(cotizacionEntity);
        return cotizacionEntity;
    }
    public CotizacionEntity update(CotizacionEntity cotizacionEntity) {
        CotizacionEntity cotizacionEntity1 = findById(cotizacionEntity.getIdCotizacion());
        cotizacionEntity1.setPedido(cotizacionEntity.getPedido());
        cotizacionEntity1.setFecha(cotizacionEntity.getFecha());
        cotizacionEntity1.setEstado(cotizacionEntity.getEstado());
        cotizacionEntity1.setRutCliente(cotizacionEntity.getRutCliente());
        return cotizacionRepository.save(cotizacionEntity1);
    }

}
