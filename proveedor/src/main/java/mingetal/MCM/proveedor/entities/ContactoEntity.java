package mingetal.MCM.proveedor.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "contacto")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactoEntity {
    @Id
    @NotNull
    private int id_contacto;
    private String nombre;
    private String correo;
    private int foro_cel;
    private int fono_fijo;
    private String rut;

}
