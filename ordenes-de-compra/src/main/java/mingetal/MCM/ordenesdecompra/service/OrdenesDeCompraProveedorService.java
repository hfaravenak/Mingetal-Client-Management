package mingetal.MCM.ordenesdecompra.service;

import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.repository.OrdenesDeCompraProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenesDeCompraProveedorService {
    @Autowired
    OrdenesDeCompraProveedorRepository ordenesDeCompraProveedorRepository;

    public boolean save(OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity){
        if(findById(ordenesDeCompraProveedorEntity.getId())==null){
            ordenesDeCompraProveedorRepository.save(ordenesDeCompraProveedorEntity);
            return true;
        }
        return false;
    }

    public List<OrdenesDeCompraProveedorEntity> findAll(){
        return ordenesDeCompraProveedorRepository.findAll();
    }

    public OrdenesDeCompraProveedorEntity findById(int id){
        return ordenesDeCompraProveedorRepository.findById(id);
    }

    public  List<OrdenesDeCompraProveedorEntity> findByIdProveedor(int id_proveedor){
        return ordenesDeCompraProveedorRepository.findByIdProveedor(id_proveedor);
    }

    public OrdenesDeCompraProveedorEntity deleteOCProveedor(int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = findById(id);
        if(ordenesDeCompraProveedorEntity==null){
            return null;
        }
        ordenesDeCompraProveedorRepository.delete(ordenesDeCompraProveedorEntity);
        return ordenesDeCompraProveedorEntity;
    }

    public OrdenesDeCompraProveedorEntity updateOCProveedorByEstadoPago(int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = findById(id);
        if(ordenesDeCompraProveedorEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        if(ordenesDeCompraProveedorEntity.getEstado_pago().equals("Pagado")){
            return null;
        }
        ordenesDeCompraProveedorEntity.setEstado_pago("Pagado");
        return ordenesDeCompraProveedorRepository.save(ordenesDeCompraProveedorEntity);
    }
}
