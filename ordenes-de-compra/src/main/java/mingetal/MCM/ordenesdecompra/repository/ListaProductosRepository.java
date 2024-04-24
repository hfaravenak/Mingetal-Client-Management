package mingetal.MCM.ordenesdecompra.repository;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ListaProductosRepository extends JpaRepository<ListaProductosEntity, String> {
    @Query("select e from ListaProductosEntity e where e.id = :id")
    ListaProductosEntity findById(@Param("id") int id);
}
