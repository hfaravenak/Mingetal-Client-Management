package mingetal.MCM.cliente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductosEntity {
    private int id;
    private String tipo;
    private String nombre;
    private int valor;
    private int valor_final;
    private int cantidad;
}
