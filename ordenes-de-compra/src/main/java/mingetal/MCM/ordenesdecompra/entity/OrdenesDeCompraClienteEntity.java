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
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @NotNull
        private int id;
        private int id_cliente;
        private String estado_factura; //Emitida | No Emitida
        private String estado_pago; //Pagado | No Pagado
        private int valor_pago;
        private LocalDate fecha_pago; // yyyy-mm-dd
        private LocalDate fecha_solicitud; // yyyy-mm-dd
        private String estado_entrega; //Entregado | No Entregado
        private String modo_pago; //Transferencia, Efectivo, Cheque
        private LocalDate fecha_inicio_pago; // yyyy-mm-dd
        private int tiempo_de_pago;
        private int numero_cheque;
        private int numero_factura;
        private String empresa_despacho;

        public OrdenesDeCompraClienteEntity(int id_cliente, String estado_factura, String estado_pago, int valor_pago, LocalDate fecha_pago, LocalDate fecha_solicitud, String estado_entrega, String modo_pago, LocalDate fecha_inicio_pago, int tiempo_de_pago, int numero_cheque, int numero_factura, String empresa_despacho) {
                this.id_cliente = id_cliente;
                this.estado_factura = estado_factura;
                this.estado_pago = estado_pago;
                this.valor_pago = valor_pago;
                this.fecha_pago = fecha_pago;
                this.fecha_solicitud = fecha_solicitud;
                this.estado_entrega = estado_entrega;
                this.modo_pago = modo_pago;
                this.fecha_inicio_pago = fecha_inicio_pago;
                this.tiempo_de_pago = tiempo_de_pago;
                this.numero_cheque = numero_cheque;
                this.numero_factura = numero_factura;
                this.empresa_despacho = empresa_despacho;
        }
}
