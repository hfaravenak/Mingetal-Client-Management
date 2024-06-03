package mingetal.MCM.ordenesdecompra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lista_producto_cliente")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListaProductosOCClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    private int id_OC_cliente;
    private int id_producto;
    private int cantidad;
    private int valor_pago;
    public ListaProductosOCClienteEntity(int id_OC_cliente, int id_producto, int cantidad, int valor_pago) {
        this.id_OC_cliente = id_OC_cliente;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.valor_pago = valor_pago;
    }
}
