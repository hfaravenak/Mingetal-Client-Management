package mingetal.MCM.cliente.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cotizacion")
@Data
public class CotizacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long idCotizacion;
    private String pedido;
    private LocalDate fecha;
    private String estado;
    private String rutCliente;
}
