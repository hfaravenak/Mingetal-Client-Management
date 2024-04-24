package mingetal.MCM.proveedor.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "proveedor")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProveedorEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_proveedor;
    private String empresa;
    private String rut;
    private String rubro;
    private int id_contacto;
    private int id_contacto2;
    private int id_contacto3;
    private String comentario;
}
