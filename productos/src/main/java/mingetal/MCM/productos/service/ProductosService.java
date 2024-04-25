package mingetal.MCM.productos.service;

import mingetal.MCM.productos.entity.ProductosEntity;
import mingetal.MCM.productos.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductosService {
    @Autowired
    ProductosRepository productosRepository;

    public void save(ProductosEntity productosEntity){
        productosRepository.save(productosEntity);
    }

    public List<ProductosEntity> findAll(){
        return productosRepository.findAll();
    }

    public ProductosEntity findById(int id){
        return productosRepository.findById(id);
    }

    public List<ProductosEntity> findByTipo(String tipo){
        return productosRepository.findByTipo(tipo);
    }

    public ProductosEntity findByNombre(String nombre){
        return productosRepository.findByNombre(nombre);
    }

    public ProductosEntity delete(int id){
        ProductosEntity productosEntity = findById(id);
        productosRepository.delete(productosEntity);
        return productosEntity;
    }
}
