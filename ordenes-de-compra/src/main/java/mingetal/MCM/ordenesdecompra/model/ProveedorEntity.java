package mingetal.MCM.ordenesdecompra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProveedorEntity {
    private int id_proveedor;
    private String empresa;
    private String rubro;
    private String id_contacto;
    private String id_contacto2;
    private String id_contacto3;
    private String comentario;
}
