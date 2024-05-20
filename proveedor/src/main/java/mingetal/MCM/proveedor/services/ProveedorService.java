package mingetal.MCM.proveedor.services;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.entities.ProveedorEntity;
import mingetal.MCM.proveedor.model.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.proveedor.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    // Verificar si existe un proveedor con la misma empresa, rut o rubro
    public boolean existSupplier(ProveedorEntity proveedor) {
        return proveedorRepository.findByEmpresa(proveedor.getEmpresa()) != null &&
                proveedorRepository.findByRubro(proveedor.getRubro()) != null;
    }

    // Create
    public ProveedorEntity createSupplier(ProveedorEntity proveedorEntity) {
        if (existSupplier(proveedorEntity)) {
            //throw new IllegalStateException("El proveedor ya existe en la base de datos.");
            return null;
        }
        return proveedorRepository.save(proveedorEntity);
    }

    // Read
    public List<ProveedorEntity> findAll() {
        return proveedorRepository.findAll();
    }

    public List<ProveedorEntity> findByListOC(){
        List<OrdenesDeCompraProveedorEntity> response = restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/proveedor/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrdenesDeCompraProveedorEntity>>() {}
        ).getBody();

        if(response == null){
            return new ArrayList<>();
        }
        List<ProveedorEntity> proveedorEntities = new ArrayList<>();
        for (OrdenesDeCompraProveedorEntity OC: response) {
            System.out.println("OC: "+OC);
            proveedorEntities.add(findById(OC.getId_proveedor()));
        }
        return proveedorEntities;
    }

    public List<ProveedorEntity> findDespacho(){
        return proveedorRepository.findByDespacho("DESPACHOS");
    }

    public ProveedorEntity findById(int id_proveedor) {
        return proveedorRepository.findById(id_proveedor);
    }

    public ProveedorEntity findByEmpresa(String empresa) {
        return proveedorRepository.findByEmpresa(empresa);
    }

    public List<ProveedorEntity> findByRubro(String rubro) {
        return proveedorRepository.findByRubro(rubro);
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

    // find by contacto
    public List<ProveedorEntity> findByContacto(String nombre) {
        List<ContactoEntity> contactoEntity = contactoService.findContactoByNombre(nombre);
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
                proveedores.add(proveedor);
            }
        }
        //System.out.println(proveedor);
        return proveedores;
    }

    public ProveedorEntity findByNombreTextual(String nombre) {
        ContactoEntity contactoEntity = contactoService.findContactoByNombreTextual(nombre);
        ProveedorEntity proveedor = proveedorRepository.findByRut1(contactoEntity.getRut());
        if(proveedor==null) {
            proveedor = proveedorRepository.findByRut2(contactoEntity.getRut());
            if (proveedor == null) {
                proveedor = proveedorRepository.findByRut3(contactoEntity.getRut());
            }
        }
        //System.out.println(proveedor);
        return proveedor;
    }


    // Update
    // Update empresa
    public ProveedorEntity updateEmpresa(int id_proveedor, String nuevaEmpresa) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        if (proveedor == null) {
            return null;
        }
        proveedor.setEmpresa(nuevaEmpresa);
        return proveedorRepository.save(proveedor);
    }

    // Update rubro
    public ProveedorEntity updateRubro(int id_proveedor, String nuevoRubro) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        if (proveedor == null) {
            return null;
        }
        proveedor.setRubro(nuevoRubro);
        return proveedorRepository.save(proveedor);
    }

    // Update comentario
    public ProveedorEntity updateComentario(int id_proveedor, String nuevoComentario) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        if (proveedor == null) {
            return null;
        }
        proveedor.setComentario(nuevoComentario);
        return proveedorRepository.save(proveedor);
    }

    // Delete
    public ProveedorEntity deleteSupplier(int id_proveedor) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        // Verificar si el proveedor existe
        if (proveedor == null) {
            //throw new IllegalArgumentException("El proveedor con ID " + id_proveedor + " no existe.");
            return null;
        }
        // Eliminar el proveedor de la base de datos
        String name = proveedor.getEmpresa();
        proveedorRepository.delete(proveedor);
        System.out.println("Proveedor " + name + " ha sido eliminado correctamente.");
        return proveedor;
    }


}
