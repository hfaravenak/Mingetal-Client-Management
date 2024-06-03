package mingetal.MCM.productos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListaProductosOCProveedorEntity {
    private int id;
    private int id_OC_cliente;
    private int id_producto;
    private int cantidad;
    private int valor_pago;

    public ListaProductosOCProveedorEntity(int id_OC_cliente, int id_producto, int cantidad, int valor_pago) {
        this.id_OC_cliente = id_OC_cliente;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.valor_pago = valor_pago;
    }
}
