package mingetal.MCM.ordenesdecompra.repository;

import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrdenesDeCompraProveedorRepository extends JpaRepository<OrdenesDeCompraProveedorEntity, String> {

    @Query("select e from OrdenesDeCompraProveedorEntity e where e.id = :id")
    OrdenesDeCompraProveedorEntity findById(@Param("id") int id);

    @Query("select e from OrdenesDeCompraProveedorEntity e where e.id_proveedor = :id_proveedor")
    List<OrdenesDeCompraProveedorEntity> findByIdProveedor(@Param("id_proveedor") int id_proveedor);

    // Query para venta productos por año
    @Query("SELECT l.id_producto, SUM(l.cantidad) AS cantidadTotal, YEAR(occ.fecha_pago) AS anio " +
            "FROM ListaProductosOCProveedorEntity l " +
            "JOIN OrdenesDeCompraProveedorEntity occ ON l.id_OC_proveedor = occ.id " +
            "WHERE occ.estado_pago = 'Pagado' " +
            "GROUP BY l.id_producto, YEAR(occ.fecha_pago) " +
            "ORDER BY YEAR(occ.fecha_pago) DESC")
    List<Object[]> findCompraProductosPorAnio();

    // Query para venta productos por año y mes
    @Query("SELECT l.id_producto, SUM(l.cantidad) AS cantidadTotal, MONTH(occ.fecha_pago) AS mes, YEAR(occ.fecha_pago) AS anio " +
            "FROM ListaProductosOCProveedorEntity l " +
            "JOIN OrdenesDeCompraProveedorEntity occ ON l.id_OC_proveedor = occ.id " +
            "WHERE occ.estado_pago = 'Pagado' " +
            "GROUP BY l.id_producto, YEAR(occ.fecha_pago), MONTH(occ.fecha_pago) " +
            "ORDER BY YEAR(occ.fecha_pago) DESC, MONTH(occ.fecha_pago) DESC")
    List<Object[]> findCompraProductosPorAnioYMes();

    // Query para ventas TOTALES por año
    @Query("SELECT SUM(l.cantidad) AS ventas_totales, YEAR(occ.fecha_pago) AS anio " +
            "FROM ListaProductosOCProveedorEntity l, OrdenesDeCompraProveedorEntity occ " +
            "WHERE l.id_OC_proveedor = occ.id AND occ.estado_pago = 'Pagado' " +
            "GROUP BY YEAR(occ.fecha_pago) " +
            "ORDER BY YEAR(occ.fecha_pago) DESC")
    List<Object[]> findComprasTotalesPorAnio();

    // Query para ventas TOTALES por año y mes
    @Query("SELECT SUM(l.cantidad) AS ventas_totales, MONTH(occ.fecha_pago) AS mes, YEAR(occ.fecha_pago) AS anio " +
            "FROM ListaProductosOCProveedorEntity l, OrdenesDeCompraProveedorEntity occ " +
            "WHERE l.id_OC_proveedor = occ.id AND occ.estado_pago = 'Pagado' " +
            "GROUP BY YEAR(occ.fecha_pago), MONTH(occ.fecha_pago) " +
            "ORDER BY YEAR(occ.fecha_pago) DESC, MONTH(occ.fecha_pago) DESC ")
    List<Object[]> findComprasTotalesPorAnioYMes();


}
