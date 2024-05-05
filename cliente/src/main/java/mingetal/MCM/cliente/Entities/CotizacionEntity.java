package mingetal.MCM.cliente.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private String pedido;
    private LocalDate fecha; // Formato yyyy-MM-dd
    private String estado;
    private String rutCliente;
}
