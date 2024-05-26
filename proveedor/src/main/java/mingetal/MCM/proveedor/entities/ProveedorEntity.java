package mingetal.MCM.proveedor.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Size(max=30, message = "El tamaño máximo del campo empresa es 30")
    private String empresa;
    @Size(max=30, message = "El tamaño máximo del campo rubro es 30")
    private String rubro;
    @Size(max=13, message = "El tamaño máximo del campo id_contacto es 13")
    private String id_contacto;
    @Size(max=13, message = "El tamaño máximo del campo id_contacto2 es 13")
    private String id_contacto2;
    @Size(max=13, message = "El tamaño máximo del campo id_contacto3 es 13")
    private String id_contacto3;
    private String comentario;

    public ProveedorEntity(String empresa, String rubro, String comentario) {
        this.empresa = empresa;
        this.rubro = rubro;
        this.comentario = comentario;
    }

    public ProveedorEntity(String empresa, String rubro, String id_contacto, String id_contacto2, String id_contacto3, String comentario) {
        this.empresa = empresa;
        this.rubro = rubro;
        this.id_contacto = id_contacto;
        this.id_contacto2 = id_contacto2;
        this.id_contacto3 = id_contacto3;
        this.comentario = comentario;
    }
}
