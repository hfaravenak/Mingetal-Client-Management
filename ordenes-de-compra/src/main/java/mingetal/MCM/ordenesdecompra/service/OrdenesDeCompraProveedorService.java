package mingetal.MCM.ordenesdecompra.service;

import lombok.Generated;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ProveedorEntity;
import mingetal.MCM.ordenesdecompra.repository.OrdenesDeCompraProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OrdenesDeCompraProveedorService {
    @Autowired
    OrdenesDeCompraProveedorRepository ordenesDeCompraProveedorRepository;

    @Autowired
    RestTemplate restTemplate;

    //-------------------- Guardado --------------------

    public OrdenesDeCompraProveedorEntity save(OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity){
        if(findById(ordenesDeCompraProveedorEntity.getId())==null){
            return ordenesDeCompraProveedorRepository.save(ordenesDeCompraProveedorEntity);
        }
        return null;
    }

    //-------------------- Buscar --------------------

    public List<OrdenesDeCompraProveedorEntity> findAll(){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorRepository.findAll();
        ordenesDeCompraProveedorEntities.sort(Comparator.comparing(OrdenesDeCompraProveedorEntity::getFecha_solicitud, Comparator.nullsFirst(Comparator.naturalOrder())));
        return ordenesDeCompraProveedorEntities;
    }
    public OrdenesDeCompraProveedorEntity findById(int id){
        return ordenesDeCompraProveedorRepository.findById(id);
    }

    @Generated
    public List<OrdenesDeCompraProveedorEntity> findByNameProveedor(String nombre){

        List<ProveedorEntity> response = restTemplate.exchange(
                "http://localhost:8080/proveedor/nombre/"+nombre,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProveedorEntity>>() {}
        ).getBody();

        if(response==null){
            return new ArrayList<>();
        }

        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = new ArrayList<>();

        for (ProveedorEntity proveedor:response) {
            ordenesDeCompraProveedorEntities.addAll(findByIdProveedor(proveedor.getId_proveedor()));
        }
        return ordenesDeCompraProveedorEntities;
    }

    @Generated
    public List<OrdenesDeCompraProveedorEntity> findByRubro(String rubro){

        List<ProveedorEntity> response = restTemplate.exchange(
                "http://localhost:8080/proveedor/rubro/"+rubro,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProveedorEntity>>() {}
        ).getBody();

        if(response==null){
            return new ArrayList<>();
        }

        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = new ArrayList<>();

        for(ProveedorEntity proveedor:response){
            ordenesDeCompraProveedorEntities.addAll(findByIdProveedor(proveedor.getId_proveedor()));
        }

        return ordenesDeCompraProveedorEntities;
    }

    @Generated
    public List<OrdenesDeCompraProveedorEntity> findByEmpresa(String empresa){

        ProveedorEntity response = restTemplate.exchange(
                "http://localhost:8080/proveedor/empresa/"+empresa,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ProveedorEntity>() {}
        ).getBody();

        if(response==null){
            return new ArrayList<>();
        }
        return findByIdProveedor(response.getId_proveedor());
    }
    public  List<OrdenesDeCompraProveedorEntity> findByIdProveedor(int id_proveedor){
        return ordenesDeCompraProveedorRepository.findByIdProveedor(id_proveedor);
    }

    //-------------------- Eliminar --------------------

    public OrdenesDeCompraProveedorEntity delete(int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = findById(id);
        if(ordenesDeCompraProveedorEntity==null){
            return null;
        }
        ordenesDeCompraProveedorRepository.delete(ordenesDeCompraProveedorEntity);
        return ordenesDeCompraProveedorEntity;
    }

    //-------------------- Editar --------------------

    public OrdenesDeCompraProveedorEntity update(OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity){
        if(ordenesDeCompraProveedorEntity!=null && findById(ordenesDeCompraProveedorEntity.getId())!=null){
            return ordenesDeCompraProveedorRepository.save(ordenesDeCompraProveedorEntity);
        }
        return null;
    }

    // ------------------ Estadística ------------------

    @Generated
    public List<Object[]> getPurchasesByYear(){
        return ordenesDeCompraProveedorRepository.findCompraProductosPorAnio();
    }

    // Función para obtener todos los productos comprados por año y mes
    // Entrega en orden:
    // id del producto
    // cantidad de productos comprados por año
    // Mes de la venta
    // Año de compra
    @Generated
    public List<Object[]> getPurchasesByYearAndMonth(){
        return ordenesDeCompraProveedorRepository.findCompraProductosPorAnioYMes();
    }

    // Función para obtener todas las compras totales por año
    // Entrega en orden:
    // cantidad de productos comprados por año
    // Año de compra
    @Generated
    public List<Object[]> getAllPurchasesByYear(){
        return ordenesDeCompraProveedorRepository.findComprasTotalesPorAnio();
    }

    // Función para obtener todas las compras totales por año mes
    // Entrega en orden:
    // cantidad de productos comprados por año
    // mes
    // Año de compra
    @Generated
    public List<Object[]> getAllPurchasesByYearAndMont(){
        return ordenesDeCompraProveedorRepository.findComprasTotalesPorAnioYMes();
    }



}
