package mingetal.MCM.ordenesdecompra.service;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ProductosEntity;
import mingetal.MCM.ordenesdecompra.repository.ListaProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListaProductosService {
    @Autowired
    ListaProductosRepository listaProductosRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<ListaProductosEntity> save(List<ListaProductosEntity> listaProductosEntity){
        List<ListaProductosEntity> listaProductosEntities = new ArrayList<>();
        List<List<Integer>> lists = new ArrayList<>();
        for(ListaProductosEntity productos:listaProductosEntity){
            if(findById(productos.getId())==null){
                List<Integer> integers = new ArrayList<>();
                integers.add(productos.getId_producto());
                integers.add(productos.getCantidad());
                lists.add(integers);
                listaProductosEntities.add(listaProductosRepository.save(productos));
            }
        }

        HttpEntity<List<List<Integer>>> requestEntity = new HttpEntity<>(lists);
        restTemplate.exchange(
                "http://localhost:8080/productos/update/cantidadProductos",
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<List<ProductosEntity>>() {}
        ).getBody();
        return listaProductosEntities;
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
