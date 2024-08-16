package mingetal.MCM.cliente.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
@Data
@ApiModel(description = "Es una entidad que representa a un Cliente con datos básicos")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id_cliente;

    @Size(max=14, message = "El tamaño máximo del campo rut es 14")
    @ApiModelProperty(notes = "RUT del cliente", example = "12.345.678-9", required = true)
    private String rut;

    @Size(max=100, message = "El tamaño máximo del campo nombre es 100")
    @ApiModelProperty(notes = "Nombre del cliente", example = "Juan Perez", required = true)
    private String nombre;

    @Size(max=100, message = "El tamaño máximo del campo email es 100")
    @ApiModelProperty(notes = "Correo del cliente", example = "correfake@gmail.com", required = true)
    private String email;

    @Size(max=16, message = "El tamaño máximo del campo telefono es 16")
    @ApiModelProperty(notes = "Telefono celular del cliente", example = "+56912345678", required = true)
    private String telefono;

    @Size(max=30, message = "El tamaño máximo del campo empresa es 30")
    @ApiModelProperty(notes = "Empresa del cliente", example = "EmpresaX", required = true)
    private String empresa;
}
