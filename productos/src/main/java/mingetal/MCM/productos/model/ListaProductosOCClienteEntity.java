package mingetal.MCM.productos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListaProductosOCClienteEntity {
    private int id;
    private int id_OC_proveedor;
    private int id_producto;
    private int cantidad;
    private int valor_pago;

    public ListaProductosOCClienteEntity(int id_producto, int id_OC_proveedor, int cantidad, int valor_pago) {
        this.id_producto = id_producto;
        this.id_OC_proveedor = id_OC_proveedor;
        this.cantidad = cantidad;
        this.valor_pago = valor_pago;
    }
}
