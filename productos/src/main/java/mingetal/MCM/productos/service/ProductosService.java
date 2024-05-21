package mingetal.MCM.productos.service;

import mingetal.MCM.productos.entity.ProductosEntity;
import mingetal.MCM.productos.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductosService {
    @Autowired
    ProductosRepository productosRepository;

    public void save(ProductosEntity productosEntity){
        String[] palabras = productosEntity.getNombre().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String palabra : palabras) {
            // Convertir la primera letra de la palabra a mayúscula y agregar el resto de la palabra
            sb.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1)).append(" ");
        }

        productosEntity.setNombre(sb.toString().trim());
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

    public List<ProductosEntity> findByNombre(String nombre) {
        List<ProductosEntity> productoEntities = findAll();
        List<ProductosEntity> resultados = new ArrayList<>();
        for (ProductosEntity nombreDeLista : productoEntities) {
            if (nombreDeLista.getNombre().contains(nombre)) {
                resultados.add(nombreDeLista);
            }
        }

        resultados.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));

        return resultados;
    }

    public ProductosEntity findByNombreTextual(String nombre) {
        return productosRepository.findByNombre(nombre);
    }

    public ProductosEntity deleteProductos(int id){
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
