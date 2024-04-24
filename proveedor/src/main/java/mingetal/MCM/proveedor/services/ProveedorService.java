package mingetal.MCM.proveedor.services;

import mingetal.MCM.proveedor.entities.ProveedorEntity;
import mingetal.MCM.proveedor.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    // Verificar si existe un proveedor con la misma empresa, rut o rubro
    public boolean existSupplier(String empresa, String rut, String rubro) {
        return proveedorRepository.findByEmpresa(empresa) != null ||
                proveedorRepository.findByRut(rut) != null ||
                proveedorRepository.findByRubro(rubro) != null;
    }

    // Create
    public ProveedorEntity createSupplier(String empresa, String rut, String rubro, String comentario) {
        if (existSupplier(empresa, rut, rubro)) {
            throw new IllegalStateException("El proveedor ya existe en la base de datos.");
        } else {
            ProveedorEntity proveedor = new ProveedorEntity();
            proveedor.setEmpresa(empresa);
            proveedor.setRut(rut);
            proveedor.setRubro(rubro);
            proveedor.setComentario(comentario);
            return proveedorRepository.save(proveedor);
        }
    }
    public void save(ProveedorEntity proveedorEntity) {
        proveedorRepository.save(proveedorEntity);
    }

    // Read
    public List<ProveedorEntity> findAll() {
        return proveedorRepository.findAll();
    }

    public ProveedorEntity findById(int id_proveedor) {
        return proveedorRepository.findById(id_proveedor);
    }

    public ProveedorEntity findByEmpresa(String empresa) {
        return proveedorRepository.findByEmpresa(empresa);
    }

    public ProveedorEntity findByRut(String rut) {
        return proveedorRepository.findByRut(rut);
    }

    public List<ProveedorEntity> findByRubro(String rubro) {
        return proveedorRepository.findByRubro(rubro);
    }

    // Update
    // Update empresa
    public ProveedorEntity updateEmpresa(int id_proveedor, String nuevaEmpresa) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor con ID " + id_proveedor + " no existe.");
        }
        proveedor.setEmpresa(nuevaEmpresa);
        return proveedorRepository.save(proveedor);
    }

    // Update rut
    public ProveedorEntity updateRut(int id_proveedor, String nuevoRut) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor con ID " + id_proveedor + " no existe.");
        }
        proveedor.setRut(nuevoRut);
        return proveedorRepository.save(proveedor);
    }

    // Update rubro
    public ProveedorEntity updateRubro(int id_proveedor, String nuevoRubro) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor con ID " + id_proveedor + " no existe.");
        }
        proveedor.setRubro(nuevoRubro);
        return proveedorRepository.save(proveedor);
    }

    // Update comentario
    public ProveedorEntity updateComentario(int id_proveedor, String nuevoComentario) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor con ID " + id_proveedor + " no existe.");
        }
        proveedor.setComentario(nuevoComentario);
        return proveedorRepository.save(proveedor);
    }

    // Delete
    public void deleteSupplier(int id_proveedor) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        // Verificar si el proveedor existe
        if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor con ID " + id_proveedor + " no existe.");
        }
        // Eliminar el proveedor de la base de datos
        String name = proveedor.getEmpresa();
        proveedorRepository.delete(proveedor);
        System.out.println("Proveedor " + name + " ha sido eliminado correctamente.");
    }

}
