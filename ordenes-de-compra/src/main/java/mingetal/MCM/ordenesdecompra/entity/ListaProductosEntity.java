package mingetal.MCM.ordenesdecompra.entity;

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
public class ListaProductosEntity {
    @Id
    @NotNull
    private int id;
    private int id_OC_cliente;
    private int id_producto;
    private int id_OC_proveedor;
    private int cantidad;

}
