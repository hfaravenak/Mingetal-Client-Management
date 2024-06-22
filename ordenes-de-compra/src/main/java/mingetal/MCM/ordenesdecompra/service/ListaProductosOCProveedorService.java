package mingetal.MCM.ordenesdecompra.service;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ProductosEntity;
import mingetal.MCM.ordenesdecompra.repository.ListaProductosOCProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListaProductosOCProveedorService {
    @Autowired
    ListaProductosOCProveedorRepository listaProductosOCProveedorRepository;

    @Autowired
    RestTemplate restTemplate;

    public ListaProductosOCProveedorEntity save(ListaProductosOCProveedorEntity listaProductosOCProveedorEntity){
        if(findById(listaProductosOCProveedorEntity.getId())==null){
            return listaProductosOCProveedorRepository.save(listaProductosOCProveedorEntity);
        }
        return null;
    }

    public List<ListaProductosOCProveedorEntity> findAll(){
        return listaProductosOCProveedorRepository.findAll();
    }
    public ListaProductosOCProveedorEntity findById(int id){
        return listaProductosOCProveedorRepository.findById(id);
    }

    public List<ListaProductosOCProveedorEntity> findByIdOCProveedor(int id_OC_proveedor){
        return listaProductosOCProveedorRepository.findByIdProveedor(id_OC_proveedor);
    }

    public ProductosEntity findProductoByIdProducto(int id_producto){
        return restTemplate.exchange(
                "http://localhost:8080/productos/"+id_producto,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ProductosEntity>() {}
        ).getBody();
    }
    public ListaProductosOCProveedorEntity delete(int id){
        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = findById(id);
        if(listaProductosOCProveedorEntity ==null){
            return null;
        }
        listaProductosOCProveedorRepository.delete(listaProductosOCProveedorEntity);
        return listaProductosOCProveedorEntity;
    }

}
