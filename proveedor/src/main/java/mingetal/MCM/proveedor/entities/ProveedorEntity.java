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
    private String rubro;
    private String id_contacto;
    private String id_contacto2;
    private String id_contacto3;
    private String comentario;

    public ProveedorEntity(String empresa, String rubro, String comentario) {
        this.empresa = empresa;
        this.rubro = rubro;
        this.comentario = comentario;
    }
}
