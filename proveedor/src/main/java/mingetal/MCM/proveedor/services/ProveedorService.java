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

    public ProveedorEntity findById(int id_proveedor) {
        return proveedorRepository.findById(id_proveedor);
    }

    public ProveedorEntity findByEmpresa(String empresa) {
        return proveedorRepository.findByEmpresa(empresa);
    }

    public List<ProveedorEntity> findByRubro(String rubro) {
        return proveedorRepository.findByRubro(rubro);
    }

    // Update
    // Update empresa
    public ProveedorEntity updateEmpresa(int id_proveedor, String nuevaEmpresa) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        if (proveedor == null) {
            //throw new IllegalArgumentException("El proveedor con ID " + id_proveedor + " no existe.");
            return null;
        }
        proveedor.setEmpresa(nuevaEmpresa);
        return proveedorRepository.save(proveedor);
    }

    // Update rubro
    public ProveedorEntity updateRubro(int id_proveedor, String nuevoRubro) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        if (proveedor == null) {
            //throw new IllegalArgumentException("El proveedor con ID " + id_proveedor + " no existe.");
            return null;
        }
        proveedor.setRubro(nuevoRubro);
        return proveedorRepository.save(proveedor);
    }

    // Update comentario
    public ProveedorEntity updateComentario(int id_proveedor, String nuevoComentario) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        if (proveedor == null) {
            //throw new IllegalArgumentException("El proveedor con ID " + id_proveedor + " no existe.");
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
