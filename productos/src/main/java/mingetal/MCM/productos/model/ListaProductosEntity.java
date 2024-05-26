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
}
