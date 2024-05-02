package mingetal.MCM.cliente.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
@Data
public class ClienteEntity {
    @Id
    @NotNull
    private String rut;
    private String nombre;
    private String email;
    private String telefono;
    private String empresa;
}
