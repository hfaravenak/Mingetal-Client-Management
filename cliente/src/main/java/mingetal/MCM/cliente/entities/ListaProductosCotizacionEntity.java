package mingetal.MCM.cliente.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lista_producto")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListaProductosCotizacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    private int id_cotizacion;
    private int id_producto;
    private int cantidad;
    private int valor_pago;

    public ListaProductosCotizacionEntity(int id_cotizacion, int id_producto, int cantidad, int valor_pago) {
        this.id_cotizacion = id_cotizacion;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.valor_pago = valor_pago;
    }
}
