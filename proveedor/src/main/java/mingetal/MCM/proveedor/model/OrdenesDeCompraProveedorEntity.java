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

    public OrdenesDeCompraProveedorEntity(int id_proveedor, String estado_pago, LocalDate fecha_pago, LocalDate fecha_entrega, String estado_entrega, LocalDate fecha_solicitud, String factura, int valor_pago) {
        this.id_proveedor = id_proveedor;
        this.estado_pago = estado_pago;
        this.fecha_pago = fecha_pago;
        this.fecha_entrega = fecha_entrega;
        this.estado_entrega = estado_entrega;
        this.fecha_solicitud = fecha_solicitud;
        this.factura = factura;
        this.valor_pago = valor_pago;
    }
}
