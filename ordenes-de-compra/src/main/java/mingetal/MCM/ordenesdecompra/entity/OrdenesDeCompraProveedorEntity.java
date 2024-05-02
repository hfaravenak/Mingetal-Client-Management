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
    private String estado_pago; //Pagado | No Pagado
    private LocalDate fecha_pago; // yyyy-mm-dd
    private LocalDate fecha_entrega; //yyyy-mm-dd
    private String factura;

    public OrdenesDeCompraProveedorEntity(int id_proveedor, String estado_pago, LocalDate fecha_pago, LocalDate fecha_entrega, String factura) {
        this.id_proveedor = id_proveedor;
        this.estado_pago = estado_pago;
        this.fecha_pago = fecha_pago;
        this.fecha_entrega = fecha_entrega;
        this.factura = factura;
    }
}
