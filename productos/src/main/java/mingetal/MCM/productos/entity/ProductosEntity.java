package mingetal.MCM.productos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "productos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    private String tipo;
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
