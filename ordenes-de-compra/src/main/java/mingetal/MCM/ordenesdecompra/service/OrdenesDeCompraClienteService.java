package mingetal.MCM.ordenesdecompra.service;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.repository.OrdenesDeCompraClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenesDeCompraClienteService {
    @Autowired
    OrdenesDeCompraClienteRepository ordenesDeCompraClienteRepository;

    public void save(OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity){
        ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
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
        ordenesDeCompraClienteRepository.delete(ordenesDeCompraClienteEntity);
        return ordenesDeCompraClienteEntity;
    }
}
