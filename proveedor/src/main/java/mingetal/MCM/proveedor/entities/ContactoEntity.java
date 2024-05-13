package mingetal.MCM.proveedor.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contacto")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactoEntity {
    @Id
    @NotNull
    @Size(max=13, message = "El tamaño máximo del campo rut es 13")
    private String rut;
    @Size(max=100, message = "El tamaño máximo del campo nombre es 100")
    private String nombre;
    @Size(max=100, message = "El tamaño máximo del campo correo es 100")
    private String correo;
    @Size(max=18, message = "El tamaño máximo del campo fono_cel es 18")
    private String fono_cel;
    @Size(max=18, message = "El tamaño máximo del campo fono_fijo es 18")
    private String fono_fijo;
}
