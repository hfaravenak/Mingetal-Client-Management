package mingetal.MCM.ordenesdecompra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
        @Size(max=13, message = "El tamaño máximo del campo id_cliente es 13")
        private String id_cliente;
        @Size(max=11, message = "El tamaño máximo del campo estado_factura es 11")
        private String estado_factura; //Emitida | No Emitida
        @Size(max=10, message = "El tamaño máximo del campo estado_pago es 10")
        private String estado_pago; //Pagado | No Pagado
        private int valor_pago;
        private LocalDate fecha_pago; // yyyy-mm-dd
        private LocalDate fecha_solicitud; // yyyy-mm-dd
        private LocalDate fecha_entrega;
        @Size(max=13, message = "El tamaño máximo del campo estado_entrega es 13")
        private String estado_entrega; //Entregado | No Entregado
        @Size(max=14, message = "El tamaño máximo del campo modo_pago es 14")
        private String modo_pago; //Transferencia, Efectivo, Cheque
        //
        //
        //
        // fecha_inicio_pago -> Será eliminado
        // tiempo_de_pago -> Será cambiado por una fecha en el cual será el máximo para pagar
        //
        //
        //
        private LocalDate fecha_inicio_pago; // yyyy-mm-dd
        private int tiempo_de_pago;
        @Size(max=30, message = "El tamaño máximo del campo empresa_despacho es 30")
        private String numero_cheque;
        @Size(max=30, message = "El tamaño máximo del campo empresa_despacho es 30")
        private String numero_factura;
        @Size(max=30, message = "El tamaño máximo del campo empresa_despacho es 30")
        private String empresa_despacho;

        public OrdenesDeCompraClienteEntity(String id_cliente,
                                            String estado_factura,
                                            String estado_pago,
                                            int valor_pago,
                                            LocalDate fecha_pago,
                                            LocalDate fecha_solicitud,
                                            String estado_entrega,
                                            String modo_pago,
                                            LocalDate fecha_inicio_pago,
                                            int tiempo_de_pago,
                                            String numero_cheque,
                                            String numero_factura,
                                            String empresa_despacho,
                                            LocalDate fecha_entrega) {
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
                this.fecha_entrega = fecha_entrega;
        }
}
