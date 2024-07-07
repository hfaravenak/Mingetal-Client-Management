package mingetal.MCM.Login.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
@Data
public class UsuarioEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombres;
    private String password;
    private String correo;
    private int intentos;
    private boolean bloqueado;

}
