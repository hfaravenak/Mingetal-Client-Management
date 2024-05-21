package mingetal.MCM.cliente.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
@Data
public class ClienteEntity {
    @Id
    @NotNull
    @Size(max=14, message = "El tamaño máximo del campo rut es 14")
    private String rut;
    @Size(max=100, message = "El tamaño máximo del campo nombre es 100")
    private String nombre;
    @Size(max=100, message = "El tamaño máximo del campo email es 100")
    private String email;
    @Size(max=13, message = "El tamaño máximo del campo telefono es 13")
    private String telefono;
    @Size(max=30, message = "El tamaño máximo del campo empresa es 30")
    private String empresa;
}
