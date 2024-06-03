package mingetal.MCM.ordenesdecompra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lista_producto_proveedor")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListaProductosOCProveedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    private int id_OC_proveedor;
    private int id_producto;
    private int cantidad;
    private int valor_pago;

    public ListaProductosOCProveedorEntity(int id_OC_proveedor, int id_producto, int cantidad, int valor_pago) {
        this.id_OC_proveedor = id_OC_proveedor;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.valor_pago = valor_pago;
    }
}
