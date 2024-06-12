package mingetal.MCM.ordenesdecompra.service;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ProductosEntity;
import mingetal.MCM.ordenesdecompra.repository.ListaProductosOCClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListaProductosOCClienteService {
    @Autowired
    ListaProductosOCClienteRepository listaProductosOCClienteRepository;

    @Autowired
    RestTemplate restTemplate;

    public ListaProductosOCClienteEntity save(ListaProductosOCClienteEntity listaProductosOCClienteEntities){
        if(findById(listaProductosOCClienteEntities.getId())==null){
            return listaProductosOCClienteRepository.save(listaProductosOCClienteEntities);
        }
        return null;
    }

    public List<ListaProductosOCClienteEntity> findAll(){
        return listaProductosOCClienteRepository.findAll();
    }

    public ListaProductosOCClienteEntity findById(int id){
        return listaProductosOCClienteRepository.findById(id);
    }

    public List<ListaProductosOCClienteEntity> findByIdOCCliente(int id_OC_cliente){
        return  listaProductosOCClienteRepository.findByIdCliente(id_OC_cliente);
    }

    public ListaProductosOCClienteEntity delete(int id){
        ListaProductosOCClienteEntity listaProductosOCClienteEntity = findById(id);
        if(listaProductosOCClienteEntity ==null){
            return null;
        }
        listaProductosOCClienteRepository.delete(listaProductosOCClienteEntity);
        return listaProductosOCClienteEntity;
    }

}
