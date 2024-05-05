package mingetal.MCM.cliente;

import mingetal.MCM.cliente.Entities.ClienteEntity;
import mingetal.MCM.cliente.Entities.CotizacionEntity;
import mingetal.MCM.cliente.Services.CotizacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

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

    @Test
    void findAllTest() {
        CotizacionEntity cotizacion1 = new CotizacionEntity();
        cotizacion1.setPedido("PedidoX");
        cotizacion1.setFecha(LocalDate.parse("2000-12-24"));
        cotizacion1.setEstado("Enviado");
        cotizacion1.setRutCliente("12.345.678-9");

        CotizacionEntity cotizacion2 = new CotizacionEntity();
        cotizacion2.setPedido("PedidoY");
        cotizacion2.setFecha(LocalDate.parse("2001-12-24"));
        cotizacion2.setEstado("Enviado");
        cotizacion2.setRutCliente("98.765.432-1");

        cotizacionService.save(cotizacion1);
        cotizacionService.save(cotizacion2);

        assertFalse(cotizacionService.findAll().isEmpty());

        cotizacionService.delete(cotizacion1.getIdCotizacion());
        cotizacionService.delete(cotizacion2.getIdCotizacion());
    }

    @Test
    void findByIdTest() {
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

    @Test
    void findByPedidoTest() {
        CotizacionEntity cotizacion1 = new CotizacionEntity();

        cotizacion1.setPedido("PedidoX");
        cotizacion1.setFecha(LocalDate.parse("2000-12-24"));
        cotizacion1.setEstado("Enviado");
        cotizacion1.setRutCliente("12.345.678-9");

        CotizacionEntity cotizacion2 = new CotizacionEntity();

        cotizacion2.setPedido("PedidoX");
        cotizacion2.setFecha(LocalDate.parse("2001-12-24"));
        cotizacion2.setEstado("Enviado");
        cotizacion2.setRutCliente("98.765.432-1");

        cotizacionService.save(cotizacion1);
        cotizacionService.save(cotizacion2);

        List<CotizacionEntity> cotizacionesEncontradas = cotizacionService.findByPedido("PedidoX");

        assertNotNull(cotizacionesEncontradas);
        assertFalse(cotizacionesEncontradas.isEmpty());

        for (CotizacionEntity cotizacion : cotizacionesEncontradas) {
            assertEquals("PedidoX", cotizacion.getPedido());
        }

        for (CotizacionEntity cotizacion : cotizacionesEncontradas) {
            cotizacionService.delete(cotizacion.getIdCotizacion());
        }
    }

    @Test
    void findByFechaTest() {
        CotizacionEntity cotizacion1 = new CotizacionEntity();

        cotizacion1.setPedido("PedidoX");
        cotizacion1.setFecha(LocalDate.parse("2000-12-24"));
        cotizacion1.setEstado("Enviado");
        cotizacion1.setRutCliente("12.345.678-9");

        CotizacionEntity cotizacion2 = new CotizacionEntity();

        cotizacion2.setPedido("PedidoY");
        cotizacion2.setFecha(LocalDate.parse("2000-12-24"));
        cotizacion2.setEstado("Enviado");
        cotizacion2.setRutCliente("98.765.432-1");

        cotizacionService.save(cotizacion1);
        cotizacionService.save(cotizacion2);

        List<CotizacionEntity> cotizacionesEncontradas = cotizacionService.findByFecha(LocalDate.parse("2000-12-24"));

        assertNotNull(cotizacionesEncontradas);
        assertFalse(cotizacionesEncontradas.isEmpty());

        for (CotizacionEntity cotizacion : cotizacionesEncontradas) {
            assertEquals(LocalDate.parse("2000-12-24"), cotizacion.getFecha());
        }

        for (CotizacionEntity cotizacion : cotizacionesEncontradas) {
            cotizacionService.delete(cotizacion.getIdCotizacion());
        }
    }

    @Test
    void findByEstadoTest() {
        CotizacionEntity cotizacion1 = new CotizacionEntity();

        cotizacion1.setPedido("PedidoX");
        cotizacion1.setFecha(LocalDate.parse("2000-12-24"));
        cotizacion1.setEstado("Enviado");
        cotizacion1.setRutCliente("12.345.678-9");

        CotizacionEntity cotizacion2 = new CotizacionEntity();

        cotizacion2.setPedido("PedidoY");
        cotizacion2.setFecha(LocalDate.parse("2001-12-24"));
        cotizacion2.setEstado("Enviado");
        cotizacion2.setRutCliente("98.765.432-1");

        cotizacionService.save(cotizacion1);
        cotizacionService.save(cotizacion2);

        List<CotizacionEntity> cotizacionesEncontradas = cotizacionService.findByEstado("Enviado");

        assertNotNull(cotizacionesEncontradas);
        assertFalse(cotizacionesEncontradas.isEmpty());

        for (CotizacionEntity cotizacion : cotizacionesEncontradas) {
            assertEquals("Enviado", cotizacion.getEstado());
        }

        for (CotizacionEntity cotizacion : cotizacionesEncontradas) {
            cotizacionService.delete(cotizacion.getIdCotizacion());
        }
    }

    @Test
    void findByRutClienteTest() {
        CotizacionEntity cotizacion1 = new CotizacionEntity();

        cotizacion1.setPedido("PedidoX");
        cotizacion1.setFecha(LocalDate.parse("2000-12-24"));
        cotizacion1.setEstado("Enviado");
        cotizacion1.setRutCliente("12.345.678-9");

        CotizacionEntity cotizacion2 = new CotizacionEntity();

        cotizacion2.setPedido("PedidoY");
        cotizacion2.setFecha(LocalDate.parse("2001-12-24"));
        cotizacion2.setEstado("En espera");
        cotizacion2.setRutCliente("12.345.678-9");

        cotizacionService.save(cotizacion1);
        cotizacionService.save(cotizacion2);

        List<CotizacionEntity> cotizacionesEncontradas = cotizacionService.findByRutCliente("12.345.678-9");

        assertNotNull(cotizacionesEncontradas);
        assertFalse(cotizacionesEncontradas.isEmpty());

        for (CotizacionEntity cotizacion : cotizacionesEncontradas) {
            assertEquals("12.345.678-9", cotizacion.getRutCliente());
        }

        for (CotizacionEntity cotizacion : cotizacionesEncontradas) {
            cotizacionService.delete(cotizacion.getIdCotizacion());
        }
    }

    @Test
    void deleteTest() {
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

    @Test
    void updateTest() {
        CotizacionEntity cotizacion = new CotizacionEntity();

        cotizacion.setPedido("PedidoX");
        cotizacion.setFecha(LocalDate.parse("2000-12-24"));
        cotizacion.setEstado("Enviado");
        cotizacion.setRutCliente("12.345.678-9");

        cotizacionService.save(cotizacion);

        cotizacion.setPedido("PedidoY");
        CotizacionEntity updatedCotizacion = cotizacionService.update(cotizacion);

        assertNotNull(updatedCotizacion);
        assertEquals("PedidoY", updatedCotizacion.getPedido());

        cotizacionService.delete(updatedCotizacion.getIdCotizacion());
    }
}
