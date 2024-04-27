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
        if(findById(productosEntity.getId())==null){
            productosRepository.save(productosEntity);
        }

    }

    public List<ProductosEntity> findAll(){
        return productosRepository.findAll();
    }

    public ProductosEntity findById(int id){
        ProductosEntity productosEntity = productosRepository.findById(id);
        if(productosEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        return productosEntity;
    }

    public List<ProductosEntity> findByTipo(String tipo){
        return productosRepository.findByTipo(tipo);
    }

    public ProductosEntity findByNombre(String nombre){
        ProductosEntity productosEntity = productosRepository.findByNombre(nombre);
        if(productosEntity==null){
            //throw new IllegalArgumentException("El producto con nombre " + nombre + " no existe.");
            return null;
        }
        return productosEntity;
    }

    public ProductosEntity delete(int id){
        ProductosEntity productosEntity = findById(id);
        if(productosEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        productosRepository.delete(productosEntity);
        return productosEntity;
    }

    public ProductosEntity updateValor(int id, int valor){
        ProductosEntity productosEntity = productosRepository.findById(id);
        if(productosEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        productosEntity.setValor(valor);
        return productosRepository.save(productosEntity);
    }
    public ProductosEntity updateValorFinal(int id, int valor_final){
        ProductosEntity productosEntity = productosRepository.findById(id);
        if(productosEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        productosEntity.setValor_final(valor_final);
        return productosRepository.save(productosEntity);
    }
    public ProductosEntity updateTipo(int id, String tipo){
        ProductosEntity productosEntity = productosRepository.findById(id);
        if(productosEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        productosEntity.setTipo(tipo);
        return productosRepository.save(productosEntity);
    }
    public ProductosEntity updateNombre(int id, String nombre){
        ProductosEntity productosEntity = productosRepository.findById(id);
        if(productosEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        productosEntity.setNombre(nombre);
        return productosRepository.save(productosEntity);
    }
    public ProductosEntity updateCantidad(int id, int cantidad){
        ProductosEntity productosEntity = productosRepository.findById(id);
        if(productosEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        productosEntity.setCantidad(cantidad);
        return productosRepository.save(productosEntity);
    }

}
