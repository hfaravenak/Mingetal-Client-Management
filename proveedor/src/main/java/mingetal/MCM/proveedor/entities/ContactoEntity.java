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
    private String rut;
    private String nombre;
    private String correo;
    private String fono_cel;
    private String fono_fijo;
}
