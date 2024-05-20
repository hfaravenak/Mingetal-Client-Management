package mingetal.MCM.ordenesdecompra.service;

import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ClienteEntity;
import mingetal.MCM.ordenesdecompra.model.ProveedorEntity;
import mingetal.MCM.ordenesdecompra.repository.OrdenesDeCompraProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    public boolean save(OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity){
        if(findById(ordenesDeCompraProveedorEntity.getId())==null){
            ordenesDeCompraProveedorRepository.save(ordenesDeCompraProveedorEntity);
            return true;
        }
        return false;
    }

    public List<OrdenesDeCompraProveedorEntity> findAll(){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorRepository.findAll();
        ordenesDeCompraProveedorEntities.sort(Comparator.comparing(OrdenesDeCompraProveedorEntity::getFecha_solicitud, Comparator.nullsFirst(Comparator.naturalOrder())));
        return ordenesDeCompraProveedorEntities;
    }

    public OrdenesDeCompraProveedorEntity findById(int id){
        return ordenesDeCompraProveedorRepository.findById(id);
    }

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

    public OrdenesDeCompraProveedorEntity deleteOCProveedor(int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = findById(id);
        if(ordenesDeCompraProveedorEntity==null){
            return null;
        }
        ordenesDeCompraProveedorRepository.delete(ordenesDeCompraProveedorEntity);
        return ordenesDeCompraProveedorEntity;
    }

    public OrdenesDeCompraProveedorEntity updateOCProveedorByEstadoPago(int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = findById(id);
        if(ordenesDeCompraProveedorEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        if(ordenesDeCompraProveedorEntity.getEstado_pago().equals("Pagado")){
            return null;
        }
        ordenesDeCompraProveedorEntity.setEstado_pago("Pagado");
        return ordenesDeCompraProveedorRepository.save(ordenesDeCompraProveedorEntity);
    }

    public OrdenesDeCompraProveedorEntity updateOCProveedor(OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity){
        if(ordenesDeCompraProveedorEntity!=null){
            return ordenesDeCompraProveedorRepository.save(ordenesDeCompraProveedorEntity);
        }
        return null;
    }
}
