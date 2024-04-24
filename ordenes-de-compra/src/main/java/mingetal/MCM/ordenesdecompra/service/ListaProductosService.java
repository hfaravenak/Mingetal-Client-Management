package mingetal.MCM.ordenesdecompra.service;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosEntity;
import mingetal.MCM.ordenesdecompra.repository.ListaProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaProductosService {
    @Autowired
    ListaProductosRepository listaProductosRepository;

    public void save(ListaProductosEntity listaProductosEntity){
        listaProductosRepository.save(listaProductosEntity);
    }

    public List<ListaProductosEntity> findAll(){
        return listaProductosRepository.findAll();
    }

    public ListaProductosEntity findById(int id){
        return listaProductosRepository.findById(id);
    }
}
