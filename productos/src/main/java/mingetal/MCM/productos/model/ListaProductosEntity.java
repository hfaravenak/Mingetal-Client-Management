package mingetal.MCM.productos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListaProductosEntity {
    private int id;
    private int id_OC_cliente;
    private int id_producto;
    private int id_OC_proveedor;
    private int cantidad;
    private int valor_pago;
    private int id_cotizacion;

    public ListaProductosEntity(int id_OC_cliente, int id_producto, int id_OC_proveedor, int cantidad, int valor_pago, int id_cotizacion) {
        this.id_OC_cliente = id_OC_cliente;
        this.id_producto = id_producto;
        this.id_OC_proveedor = id_OC_proveedor;
        this.cantidad = cantidad;
        this.valor_pago = valor_pago;
        this.id_cotizacion = id_cotizacion;
    }
}
