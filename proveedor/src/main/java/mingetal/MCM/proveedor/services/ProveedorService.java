package mingetal.MCM.proveedor.services;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.entities.ProveedorEntity;
import mingetal.MCM.proveedor.model.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.proveedor.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired
    ContactoService contactoService;
    @Autowired
    RestTemplate restTemplate;


    //-------------------- Guardado --------------------

    public ProveedorEntity save(ProveedorEntity proveedorEntity) {
        if (findById(proveedorEntity.getId_proveedor())==null) {
            return proveedorRepository.save(proveedorEntity);
        }
        return null;
    }

    //-------------------- Buscar --------------------
    
    public List<ProveedorEntity> findAll() {
        return proveedorRepository.findAll();
    }
    public List<ProveedorEntity> findByListOC() {
        // Obtener el encabezado Authorization de la solicitud actual
        String authHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(HttpHeaders.AUTHORIZATION);

        // Configurar el encabezado en la solicitud HTTP saliente
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Realizar la solicitud HTTP usando RestTemplate con el encabezado configurado
        ResponseEntity<List<OrdenesDeCompraProveedorEntity>> response = restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/proveedor/",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<OrdenesDeCompraProveedorEntity>>() {}
        );

        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = response.getBody();
        List<ProveedorEntity> proveedorEntities = new ArrayList<>();

        if (ordenesDeCompraProveedorEntities != null) {
            for (OrdenesDeCompraProveedorEntity ocProveedor : ordenesDeCompraProveedorEntities) {
                proveedorEntities.add(findById(ocProveedor.getId_proveedor()));
            }
        }

        return proveedorEntities;
    }
    public ProveedorEntity findByEmpresa(String empresa) {
        return proveedorRepository.findByEmpresa(empresa);
    }
    public ProveedorEntity findByRut(String rut) {
        ProveedorEntity proveedor;
        proveedor = proveedorRepository.findByRut1(rut);
        if(proveedor==null){
            proveedor = proveedorRepository.findByRut2(rut);
            if(proveedor ==null){
                proveedor = proveedorRepository.findByRut3(rut);
            }
        }
        return proveedor;
    }
    public List<ProveedorEntity> findByNombre(String nombre) {
        List<ContactoEntity> contactoEntity = contactoService.findByNombre(nombre);
        List<ProveedorEntity> proveedores = new ArrayList<>();
        for(ContactoEntity contacto:contactoEntity){
            ProveedorEntity proveedor = proveedorRepository.findByRut1(contacto.getRut());
            if(proveedor==null) {
                proveedor = proveedorRepository.findByRut2(contacto.getRut());
                if (proveedor == null) {
                    proveedor = proveedorRepository.findByRut3(contacto.getRut());
                }
            }

            if(proveedor!=null){
                if(!proveedores.contains(proveedor)){
                    proveedores.add(proveedor);
                }
            }
        }
        return proveedores;
    }
    public List<ProveedorEntity> findByRubro(String rubro) {
        return proveedorRepository.findByRubro(rubro);
    }
    public List<String> findRubros(){
        List<String> rubros = new ArrayList<>();
        List<ProveedorEntity> proveedorEntities = proveedorRepository.findAll();

        for(ProveedorEntity proveedor: proveedorEntities){
            if(!rubros.contains(proveedor.getRubro())){
                rubros.add(proveedor.getRubro());
            }
        }

        return rubros;
    }
    public List<ProveedorEntity> findDespacho(){
        return proveedorRepository.findByDespacho("DESPACHOS");
    }
    public ProveedorEntity findByNombreTextual(String nombre) {
        ContactoEntity contactoEntity = contactoService.findByNombreTextual(nombre);
        if(contactoEntity==null){
            return null;
        }
        ProveedorEntity proveedor = proveedorRepository.findByRut1(contactoEntity.getRut());
        if(proveedor==null) {
            proveedor = proveedorRepository.findByRut2(contactoEntity.getRut());
            if (proveedor == null) {
                proveedor = proveedorRepository.findByRut3(contactoEntity.getRut());
            }
        }
        return proveedor;
    }
    public ProveedorEntity findById(int id_proveedor) {
        return proveedorRepository.findById(id_proveedor);
    }

    //-------------------- Eliminar --------------------

    public ProveedorEntity delete(int id_proveedor) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        if (proveedor == null) {
            return null;
        }
        proveedorRepository.delete(proveedor);
        return proveedor;
    }

    //-------------------- Editar --------------------

    public ProveedorEntity update(ProveedorEntity proveedorEntity){
        ProveedorEntity proveedor = proveedorRepository.findById(proveedorEntity.getId_proveedor());
        if (proveedor == null) {
            return null;
        }
        return proveedorRepository.save(proveedorEntity);
    }
}
