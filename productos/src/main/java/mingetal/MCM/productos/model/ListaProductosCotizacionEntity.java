package mingetal.MCM.productos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListaProductosCotizacionEntity {
    private int id;
    private int id_cotizacion;
    private int id_producto;
    private int cantidad;
    private int valor_pago;

    public ListaProductosCotizacionEntity(int id_producto, int cantidad, int valor_pago, int id_cotizacion) {
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.valor_pago = valor_pago;
        this.id_cotizacion = id_cotizacion;
    }
}
