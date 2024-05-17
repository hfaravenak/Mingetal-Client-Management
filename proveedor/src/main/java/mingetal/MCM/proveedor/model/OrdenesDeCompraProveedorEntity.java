package mingetal.MCM.proveedor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdenesDeCompraProveedorEntity {
    private int id;
    private int id_proveedor;
    private String estado_pago; //Pagado | No Pagado
    private LocalDate fecha_pago; // yyyy-mm-dd
    private LocalDate fecha_entrega; //yyyy-mm-dd
    private String estado_entrega;
    private LocalDate fecha_solicitud; //yyyy-mm-dd
    private String factura;
    private int valor_pago;
}
