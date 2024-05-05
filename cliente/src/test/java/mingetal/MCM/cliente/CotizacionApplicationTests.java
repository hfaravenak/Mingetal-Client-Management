package mingetal.MCM.cliente;

import mingetal.MCM.cliente.Entities.ClienteEntity;
import mingetal.MCM.cliente.Entities.CotizacionEntity;
import mingetal.MCM.cliente.Services.CotizacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CotizacionApplicationTests {
    @Autowired
    private CotizacionService cotizacionService;

    @Test
    void saveTest() {
        CotizacionEntity cotizacion = new CotizacionEntity();

        cotizacion.setPedido("PedidoX");
        cotizacion.setFecha(LocalDate.parse("2000-12-24"));
        cotizacion.setEstado("Enviado");
        cotizacion.setRutCliente("12.345.678-9");

        cotizacionService.save(cotizacion);

        CotizacionEntity cotizacionGuardada = cotizacionService.findById(cotizacion.getIdCotizacion());
        assertNotNull(cotizacionGuardada);
        cotizacionService.delete(cotizacionGuardada.getIdCotizacion());
    }


}
