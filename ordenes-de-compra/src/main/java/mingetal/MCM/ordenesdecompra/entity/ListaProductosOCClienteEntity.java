package mingetal.MCM.ordenesdecompra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mingetal.MCM.ordenesdecompra.model.ClienteEntity;

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
    @Column(name = "id_OC_cliente")
    private int id_OC_cliente;
    private int id_producto;
    private int cantidad;
    private int valor_pago;
    @ManyToOne
    @JoinColumn(name = "id_OC_cliente", insertable = false, updatable = false)
    private OrdenesDeCompraClienteEntity ordenCompraCliente;
    public ListaProductosOCClienteEntity(int id_OC_cliente, int id_producto, int cantidad, int valor_pago) {
        this.id_OC_cliente = id_OC_cliente;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.valor_pago = valor_pago;
    }
}
