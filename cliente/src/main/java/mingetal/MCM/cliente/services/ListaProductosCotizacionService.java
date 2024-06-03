package mingetal.MCM.cliente.services;

import mingetal.MCM.cliente.entities.ListaProductosCotizacionEntity;
import mingetal.MCM.cliente.repositories.ListaProductosCotizacionRepository;
import mingetal.MCM.cliente.model.ProductosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
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
        System.out.println("id: "+id_OC_proveedor);
        System.out.println(listaProductosCotizacionRepository.findByIdCotizacion(id_OC_proveedor));
        return listaProductosCotizacionRepository.findByIdCotizacion(id_OC_proveedor);
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
