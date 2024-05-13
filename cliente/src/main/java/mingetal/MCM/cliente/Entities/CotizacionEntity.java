package mingetal.MCM.cliente.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cotizacion")
@Data
public class CotizacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int idCotizacion;
    @Size(max=100, message = "El tamaño máximo del campo pedido es 100")
    private String pedido;
    private LocalDate fecha; // Formato yyyy-MM-dd
    @Size(max=10, message = "El tamaño máximo del campo estado es 10")
    private String estado; // En espera // Listo
    @Size(max=13, message = "El tamaño máximo del campo rutCliente es 13")
    private String rutCliente;
}
