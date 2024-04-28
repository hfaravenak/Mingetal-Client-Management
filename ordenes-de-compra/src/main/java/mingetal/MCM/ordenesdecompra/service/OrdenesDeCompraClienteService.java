package mingetal.MCM.ordenesdecompra.service;

import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.repository.OrdenesDeCompraClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrdenesDeCompraClienteService {
    @Autowired
    OrdenesDeCompraClienteRepository ordenesDeCompraClienteRepository;

    public boolean save(OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity){
        if(findById(ordenesDeCompraClienteEntity.getId())==null){
            ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
            return true;
        }
        return false;
    }

    public List<OrdenesDeCompraClienteEntity> findAll(){
        return ordenesDeCompraClienteRepository.findAll();
    }

    public OrdenesDeCompraClienteEntity findById(int id){
        return ordenesDeCompraClienteRepository.findById(id);
    }

    public  List<OrdenesDeCompraClienteEntity> findByIdCliente(int id_cliente){
        return ordenesDeCompraClienteRepository.findByIdCliente(id_cliente);

    }

    public OrdenesDeCompraClienteEntity deleteOCCliente(int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = findById(id);
        if(ordenesDeCompraClienteEntity==null){
            return null;
        }
        ordenesDeCompraClienteRepository.delete(ordenesDeCompraClienteEntity);
        return ordenesDeCompraClienteEntity;
    }

    public OrdenesDeCompraClienteEntity updateOCClienteByEstadoFactura(int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteRepository.findById(id);
        if(ordenesDeCompraClienteEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        if(ordenesDeCompraClienteEntity.getEstado_factura().equals("Emitida")){
            return null;
        }
        ordenesDeCompraClienteEntity.setEstado_factura("Emitida");
        return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
    }

    public OrdenesDeCompraClienteEntity updateOCClienteByEstadoPago(int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteRepository.findById(id);
        if(ordenesDeCompraClienteEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        if(ordenesDeCompraClienteEntity.getEstado_pago().equals("Pagado")){
            return null;
        }
        ordenesDeCompraClienteEntity.setEstado_pago("Pagado");
        return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
    }

    public OrdenesDeCompraClienteEntity updateOCClienteByEstadoEntrega(int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteRepository.findById(id);
        if(ordenesDeCompraClienteEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        if(ordenesDeCompraClienteEntity.getEstado_entrega().equals("Entregado")){
            return null;
        }
        ordenesDeCompraClienteEntity.setEstado_entrega("Entregado");
        return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
    }

    public OrdenesDeCompraClienteEntity updateOCClienteByModoPago(int id, String modo_pago){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteRepository.findById(id);
        if(ordenesDeCompraClienteEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        ordenesDeCompraClienteEntity.setModo_pago(modo_pago);
        return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
    }

    public OrdenesDeCompraClienteEntity updateOCClienteByFechaPago(int id, LocalDate fecha_pago){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteRepository.findById(id);
        if(ordenesDeCompraClienteEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        ordenesDeCompraClienteEntity.setFecha_pago(fecha_pago);
        return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
    }
}
