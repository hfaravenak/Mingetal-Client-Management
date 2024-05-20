package mingetal.MCM.ordenesdecompra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactoEntity {
    private String rut;
    private String nombre;
    private String correo;
    private String foro_cel;
    private String fono_fijo;
}
