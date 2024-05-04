package mingetal.MCM.proveedor.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "contacto")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactoEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_contacto;
    private String nombre;
    private String correo;
    private String foro_cel;
    private String fono_fijo;
    private String rut;

    public ContactoEntity(String nombre, String correo, String foro_cel, String fono_fijo, String rut) {
        this.nombre = nombre;
        this.correo = correo;
        this.foro_cel = foro_cel;
        this.fono_fijo = fono_fijo;
        this.rut = rut;
    }
}
