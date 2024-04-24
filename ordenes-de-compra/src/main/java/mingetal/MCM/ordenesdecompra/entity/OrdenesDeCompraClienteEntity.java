package mingetal.MCM.ordenesdecompra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "ordenes_de_compra_cliente")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdenesDeCompraClienteEntity {
        @Id
        @NotNull
        private int id;
        private int id_cliente;
        private String estado_factura;
        private String estado_pago;
        private int valor_pago;
        private LocalDate fecha_pago;
        private LocalDate fecha_solicitud;
        private String estado_entrega;
        private String modo_pago;
        private LocalDate fecha_inicio_pago;
        private int tiempo_de_pago;
        private int numero_cheque;
        private int numero_factura;
        private String empresa_despacho;
}
