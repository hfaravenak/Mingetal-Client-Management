package mingetal.MCM.ordenesdecompra.service;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosEntity;
import mingetal.MCM.ordenesdecompra.model.ProductosEntity;
import mingetal.MCM.ordenesdecompra.repository.ListaProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ListaProductosService {
    @Autowired
    ListaProductosRepository listaProductosRepository;

    @Autowired
    RestTemplate restTemplate;

    public ListaProductosEntity save(ListaProductosEntity listaProductosEntity){
        if(findById(listaProductosEntity.getId())==null){
            return listaProductosRepository.save(listaProductosEntity);
        }
        return null;

    }

    public List<ListaProductosEntity> findAll(){
        return listaProductosRepository.findAll();
    }

    public ListaProductosEntity findById(int id){
        return listaProductosRepository.findById(id);
    }

    public List<ListaProductosEntity> findByIdOCCliente(int id_OC_cliente){
        return  listaProductosRepository.findByIdCliente(id_OC_cliente);
    }

    public List<ListaProductosEntity> findByIdProducto(int id_producto){
        return listaProductosRepository.findByIdProducto(id_producto);
    }

    public List<ListaProductosEntity> findByIdOCProveedor(int id_OC_proveedor){
        return listaProductosRepository.findByIdProveedor(id_OC_proveedor);
    }
    public List<ListaProductosEntity> findByIdCotizacion(int id_OC_proveedor){
        return listaProductosRepository.findByIdCotizacion(id_OC_proveedor);
    }

    public List<ListaProductosEntity> findByNameProducto(String nombre){

        ProductosEntity response = restTemplate.exchange(
                "http://localhost:8080/productos/nombre/"+nombre,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ProductosEntity>() {}
        ).getBody();

        if(response == null){
            return null;
        }

        return findByIdProducto(response.getId());
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
