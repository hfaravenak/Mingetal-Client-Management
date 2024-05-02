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

    public boolean save(ListaProductosEntity listaProductosEntity){
        if(findById(listaProductosEntity.getId())==null){
            listaProductosRepository.save(listaProductosEntity);
            return true;
        }
        return false;

    }

    public List<ListaProductosEntity> findAll(){
        return listaProductosRepository.findAll();
    }

    public ListaProductosEntity findById(int id){
        return listaProductosRepository.findById(id);
    }

    public List<ListaProductosEntity> findByIdCliente(int id_OC_cliente){
        return  listaProductosRepository.findByIdCliente(id_OC_cliente);
    }

    public List<ListaProductosEntity> findByIdProducto(int id_producto){
        return listaProductosRepository.findByIdProducto(id_producto);
    }

    public List<ListaProductosEntity> findByIdProveedor(int id_OC_proveedor){
        return listaProductosRepository.findByIdProveedor(id_OC_proveedor);
    }


    public ListaProductosEntity delete(int id){
        ListaProductosEntity listaProductosEntity = findById(id);
        if(listaProductosEntity==null){
            return null;
        }
        listaProductosRepository.delete(listaProductosEntity);
        return listaProductosEntity;
    }

    public ListaProductosEntity updateCantidad(int id, int cantidad){
        ListaProductosEntity listaProductosEntity = listaProductosRepository.findById(id);
        if(listaProductosEntity==null){
            //throw new IllegalArgumentException("El producto con ID "+ id + "no existe");
            return null;
        }
        listaProductosEntity.setCantidad(cantidad);
        return listaProductosRepository.save(listaProductosEntity);
    }
}
