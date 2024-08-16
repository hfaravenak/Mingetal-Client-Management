package mingetal.MCM.ordenesdecompra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClienteEntity {
    private int id_cliente;
    private String rut;
    private String nombre;
    private String email;
    private String telefono;
    private String empresa;
}
