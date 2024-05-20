package mingetal.MCM.proveedor;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.entities.ProveedorEntity;
import mingetal.MCM.proveedor.services.ContactoService;
import mingetal.MCM.proveedor.services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProveedorServiceTests {
    @Autowired
    private ProveedorService proveedorService;

    @Test
    void creatProveedorTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        assertEquals(proveedorService.createSupplier(proveedorEntity),  proveedorEntity);
        assertNotNull(proveedorService.findById(proveedorEntity.getId_proveedor()));
        proveedorService.deleteSupplier(proveedorEntity.getId_proveedor());
    }
    @Test
    void creatProveedorTestFalse(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.createSupplier(proveedorEntity);
        assertNull(proveedorService.createSupplier(proveedorEntity));

        proveedorService.deleteSupplier(proveedorEntity.getId_proveedor());
    }

    @Test
    void findProveedorByEmpresaTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.createSupplier(proveedorEntity);

        assertEquals(proveedorEntity, proveedorService.findByEmpresa("Empresa X"));

        proveedorService.deleteSupplier(proveedorEntity.getId_proveedor());
    }
    @Test
    void findProveedorByEmpresaTestFalse(){
        assertNull(proveedorService.findByEmpresa("Esto no existe ñññññññ"));
    }

    @Test
    void findProveedorByRubroTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.createSupplier(proveedorEntity);

        assertFalse(proveedorService.findByRubro("Algo X").isEmpty());

        proveedorService.deleteSupplier(proveedorEntity.getId_proveedor());
    }
    @Test
    void findProveedorByRubroTestFalse(){
        assertTrue(proveedorService.findByRubro("Algo X").isEmpty());
    }


    @Test
    void findProveedorByIdTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.createSupplier(proveedorEntity);

        assertEquals(proveedorEntity, proveedorService.findById(proveedorEntity.getId_proveedor()));

        proveedorService.deleteSupplier(proveedorEntity.getId_proveedor());
    }
    @Test
    void findProveedorByIdTestFalse(){
        assertNull(proveedorService.findById(-1));
    }


    @Test
    void updateProveedorEmpresaTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.createSupplier(proveedorEntity);

        ProveedorEntity newProveedorEntity = proveedorService.updateEmpresa(proveedorEntity.getId_proveedor(), "Nuevo nombre");
        ProveedorEntity ComprobarBD = proveedorService.findById(proveedorEntity.getId_proveedor());
        assertEquals(ComprobarBD.getEmpresa(), newProveedorEntity.getEmpresa());
        assertEquals("Nuevo nombre", newProveedorEntity.getEmpresa());

        proveedorService.deleteSupplier(proveedorEntity.getId_proveedor());
    }
    @Test
    void updateProveedorEmpresaTestFalse(){
        assertNull(proveedorService.updateEmpresa(-1, "Algo"));
    }

    @Test
    void updateProveedorRubroTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.createSupplier(proveedorEntity);

        ProveedorEntity newProveedorEntity = proveedorService.updateRubro(proveedorEntity.getId_proveedor(), "Nuevo rubro");
        ProveedorEntity ComprobarBD = proveedorService.findById(proveedorEntity.getId_proveedor());
        assertEquals(ComprobarBD.getRubro(), newProveedorEntity.getRubro());
        assertEquals("Nuevo rubro", newProveedorEntity.getRubro());

        proveedorService.deleteSupplier(proveedorEntity.getId_proveedor());
    }
    @Test
    void updateProveedorRubroTestFalse(){
        assertNull(proveedorService.updateRubro(-1, "Algo"));
    }

    @Test
    void updateProveedorComentarioTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.createSupplier(proveedorEntity);

        ProveedorEntity newProveedorEntity = proveedorService.updateComentario(proveedorEntity.getId_proveedor(), "Nuevo comentario");
        ProveedorEntity ComprobarBD = proveedorService.findById(proveedorEntity.getId_proveedor());
        assertEquals(ComprobarBD.getComentario(), newProveedorEntity.getComentario());
        assertEquals("Nuevo comentario", newProveedorEntity.getComentario());

        proveedorService.deleteSupplier(proveedorEntity.getId_proveedor());
    }
    @Test
    void updateProveedorComentarioTestFalse(){
        assertNull(proveedorService.updateComentario(-1, "Algo"));
    }

    @Test
    void deleteContactoTestTrue(){
        ProveedorEntity proveedorEntity = new ProveedorEntity(
                "Empresa X",
                "Algo X",
                "Algun que otro comentario aqui"
        );
        proveedorService.createSupplier(proveedorEntity);

        ProveedorEntity proveedorDelete = proveedorService.deleteSupplier(proveedorEntity.getId_proveedor());
        assertEquals(proveedorEntity, proveedorDelete);
        assertNull(proveedorService.findById(proveedorEntity.getId_proveedor()));
    }
    @Test
    void deleteContactoTestFalse(){
        assertNull(proveedorService.deleteSupplier(-1));
    }
}
