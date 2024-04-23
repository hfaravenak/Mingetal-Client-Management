package mingetal.MCM.productos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "productos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductosEntity {
    @Id
    @NotNull
    private int id;
    private String tipo;
    private String nombre;
    private int valor;
    private int valor_final;
    private int cantidad;

}
