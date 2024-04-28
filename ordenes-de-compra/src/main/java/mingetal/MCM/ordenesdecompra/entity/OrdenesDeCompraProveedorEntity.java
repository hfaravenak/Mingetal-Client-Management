package mingetal.MCM.ordenesdecompra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "ordenes_de_compra_proveedor")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdenesDeCompraProveedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    private int id_proveedor;
    private String estado_pago;
    private LocalDate fecha_pago;
    private LocalDate fecha_entrega;
    private String factura;
}
