package mingetal.MCM.cliente.services;

import mingetal.MCM.cliente.entities.ListaProductosCotizacionEntity;
import mingetal.MCM.cliente.model.ProductosEntity;
import mingetal.MCM.cliente.repositories.ListaProductosCotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListaProductosCotizacionService {
    @Autowired
    ListaProductosCotizacionRepository listaProductosCotizacionRepository;

    @Autowired
    RestTemplate restTemplate;

    public ListaProductosCotizacionEntity save(ListaProductosCotizacionEntity listaProductosCotizacionEntity){
        if(findById(listaProductosCotizacionEntity.getId())==null){
            return listaProductosCotizacionRepository.save(listaProductosCotizacionEntity);
        }
        return null;
    }

    public ListaProductosCotizacionEntity findById(int id){
        return listaProductosCotizacionRepository.findById(id);
    }

    public List<ListaProductosCotizacionEntity> findByIdCotizacion(int id_OC_proveedor){
        return listaProductosCotizacionRepository.findByIdCotizacion(id_OC_proveedor);
    }

    public ProductosEntity findProductoByIdProducto(int id_producto){
        return restTemplate.exchange(
                "http://localhost:8080/productos/"+id_producto,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ProductosEntity>() {}
        ).getBody();
    }

    public ListaProductosCotizacionEntity delete(int id){
        ListaProductosCotizacionEntity listaProductosOCProveedorEntity = findById(id);
        if(listaProductosOCProveedorEntity ==null){
            return null;
        }
        listaProductosCotizacionRepository.delete(listaProductosOCProveedorEntity);
        return listaProductosOCProveedorEntity;
    }

}
