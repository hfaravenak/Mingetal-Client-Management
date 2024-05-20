package mingetal.MCM.productos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "productos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @Size(max=40, message = "El tamaño máximo del campo tipo es 40")
    private String tipo;
    @Size(max=100, message = "El tamaño máximo del campo nombre es 100")
    private String nombre;
    private int valor;
    private int valor_final;
    private int cantidad;

    public ProductosEntity(String tipo, String nombre, int valor, int valor_final, int cantidad) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.valor = valor;
        this.valor_final = valor_final;
        this.cantidad = cantidad;
    }
}
