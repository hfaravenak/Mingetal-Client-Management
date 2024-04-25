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

    public void save(OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity){
        ordenesDeCompraProveedorRepository.save(ordenesDeCompraProveedorEntity);
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
        ordenesDeCompraProveedorRepository.delete(ordenesDeCompraProveedorEntity);
        return ordenesDeCompraProveedorEntity;
    }
}
