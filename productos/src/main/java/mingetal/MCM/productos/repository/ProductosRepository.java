package mingetal.MCM.productos.repository;

import mingetal.MCM.productos.entity.ProductosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<ProductosEntity, String> {

    @Query("select e from ProductosEntity e where e.id = :id")
    ProductosEntity findById(@Param("id") int id);

    @Query("select e from ProductosEntity e where e.tipo = :tipo")
    List<ProductosEntity> findByTipo(@Param("tipo") String tipo);

    @Query("select e from ProductosEntity e where e.nombre = :nombre")
    ProductosEntity findByNombre(@Param("nombre") String nombre);

    @Query("select e from ProductosEntity e where e.cantidad <= 5")
    List<ProductosEntity> findPocoProductos();
}
