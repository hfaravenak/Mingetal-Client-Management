package mingetal.MCM.ordenesdecompra.repository;

import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrdenesDeCompraClienteRepository extends JpaRepository<OrdenesDeCompraClienteEntity, String> {

    @Query("select e from OrdenesDeCompraClienteEntity e where e.id = :id")
    OrdenesDeCompraClienteEntity findById(@Param("id") int id);

    @Query("select e from OrdenesDeCompraClienteEntity e where e.id_cliente = :id_cliente")
    List<OrdenesDeCompraClienteEntity> findByIdCliente(@Param("id_cliente") String id_cliente);
    @Query("select e from OrdenesDeCompraClienteEntity e where e.estado_pago='Pagado'")
    List<OrdenesDeCompraClienteEntity> findPagado();
    @Query("select e from OrdenesDeCompraClienteEntity e where e.estado_pago='No Pagado'")
    List<OrdenesDeCompraClienteEntity> findNoPagado();

    // Query para venta productos por año
    @Query("SELECT l.id_producto, SUM(l.cantidad) AS cantidadTotal, YEAR(occ.fecha_pago) AS anio " +
            "FROM ListaProductosOCClienteEntity l " +
            "JOIN OrdenesDeCompraClienteEntity occ ON l.id_OC_cliente = occ.id " +
            "WHERE occ.estado_pago = 'Pagado' " +
            "GROUP BY l.id_producto, YEAR(occ.fecha_pago) " +
            "ORDER BY YEAR(occ.fecha_pago) DESC")
    List<Object[]> findVentaProductosPorAnio();

    // Query para venta productos por año y mes
    @Query("SELECT l.id_producto, SUM(l.cantidad) AS cantidadTotal, MONTH(occ.fecha_pago) AS mes, YEAR(occ.fecha_pago) AS anio " +
            "FROM ListaProductosOCClienteEntity l " +
            "JOIN OrdenesDeCompraClienteEntity occ ON l.id_OC_cliente = occ.id " +
            "WHERE occ.estado_pago = 'Pagado' " +
            "GROUP BY l.id_producto, YEAR(occ.fecha_pago), MONTH(occ.fecha_pago) " +
            "ORDER BY YEAR(occ.fecha_pago) DESC, MONTH(occ.fecha_pago) DESC")
    List<Object[]> findVentaProductosPorAnioYMes();

    // Query para ventas TOTALES por año
    @Query("SELECT SUM(occ.valor_pago) AS monto_ventas, COUNT(occ.id) AS ventas_totales, YEAR(occ.fecha_pago) AS anio " +
            "FROM ListaProductosOCClienteEntity l, OrdenesDeCompraClienteEntity occ " +
            "WHERE l.id_OC_cliente = occ.id AND occ.estado_pago = 'Pagado' " +
            "GROUP BY YEAR(occ.fecha_pago) " +
            "ORDER BY YEAR(occ.fecha_pago) DESC")
    List<Object[]> findVentasTotalesPorAnio();

    // Query para ventas TOTALES por año y mes
    @Query("SELECT SUM(occ.valor_pago) AS monto_ventas, COUNT(occ.id) AS ventas_totales, MONTH(occ.fecha_pago) AS mes, YEAR(occ.fecha_pago) AS anio " +
            "FROM ListaProductosOCClienteEntity l, OrdenesDeCompraClienteEntity occ " +
            "WHERE l.id_OC_cliente = occ.id AND occ.estado_pago = 'Pagado' " +
            "GROUP BY YEAR(occ.fecha_pago), MONTH(occ.fecha_pago) " +
            "ORDER BY YEAR(occ.fecha_pago) DESC, MONTH(occ.fecha_pago) DESC")
    List<Object[]> findVentasTotalesPorAnioYMes();

    // Query para la comparación de meses anteriores
    @Query("SELECT SUM(occ.valor_pago) AS monto_ventas, COUNT(occ.id) AS ventas_totales, "+
            "MONTH(occ.fecha_pago) AS mes, YEAR(occ.fecha_pago) AS anio " +
            "FROM OrdenesDeCompraClienteEntity occ " +
            "WHERE occ.estado_pago = 'Pagado'" +
            "GROUP BY MONTH(occ.fecha_pago), YEAR(occ.fecha_pago)" +
            "ORDER BY MONTH(occ.fecha_pago) DESC, YEAR(occ.fecha_pago) DESC")
    List<Object[]> comparacionMesesIgualesAnteriores();


    // Query para cliente con más compras por año
    @Query("SELECT occ.id_cliente, SUM(occ.valor_pago) AS monto_ventas, COUNT(occ.id) AS ventas_totales," +
            " YEAR(occ.fecha_pago) AS anio " +
            "FROM ListaProductosOCClienteEntity l, OrdenesDeCompraClienteEntity occ " +
            "WHERE occ.estado_pago = 'Pagado' AND YEAR(occ.fecha_pago) = YEAR(CURRENT_DATE)" +
            "GROUP BY occ.id_cliente, YEAR(occ.fecha_pago) " +
            "ORDER BY ventas_totales DESC, monto_ventas DESC")
    List<Object[]> clientList();

}
