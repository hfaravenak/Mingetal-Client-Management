package mingetal.MCM.productos.service;

import mingetal.MCM.productos.entity.ProductosEntity;
import mingetal.MCM.productos.model.ListaProductosEntity;
import mingetal.MCM.productos.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductosService {
    @Autowired
    ProductosRepository productosRepository;

    @Autowired
    RestTemplate restTemplate;

    //-------------------- Guardado --------------------

    public ProductosEntity save(ProductosEntity productosEntity){
        String[] palabras = productosEntity.getNombre().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String palabra : palabras) {
            // Convertir la primera letra de la palabra a mayúscula y agregar el resto de la palabra
            sb.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1)).append(" ");
        }
        productosEntity.setNombre(sb.toString().trim());
        if(findByNombreTextual(productosEntity.getNombre())==null){
            return productosRepository.save(productosEntity);
        }
        return null;
    }

    //-------------------- Buscar --------------------

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
    public List<ProductosEntity> findByOCCliente(int id){
        List<ListaProductosEntity> response = restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/productos/cliente/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ListaProductosEntity>>() {}
        ).getBody();

        return getProductosEntities(response);
    }
    public List<ProductosEntity> findByOCProveedor(int id){
        List<ListaProductosEntity> response = restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/productos/proveedor/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ListaProductosEntity>>() {}
        ).getBody();

        return getProductosEntities(response);
    }
    public List<ProductosEntity> findByCotizacion(int id){
        List<ListaProductosEntity> response = restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/productos/cotizacion/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ListaProductosEntity>>() {}
        ).getBody();

        return getProductosEntities(response);
    }

    private List<ProductosEntity> getProductosEntities(List<ListaProductosEntity> response) {
        if(response == null){
            return new ArrayList<>();
        }

        List<ProductosEntity> productosEntities = new ArrayList<>();

        for(ListaProductosEntity listaProductosEntity: response){
            productosEntities.add(findById(listaProductosEntity.getId_producto()));
        }

        return productosEntities;
    }

    //-------------------- Eliminar --------------------

    public ProductosEntity delete(int id){
        ProductosEntity productosEntity = findById(id);
        if(productosEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        productosRepository.delete(productosEntity);
        return productosEntity;
    }

    //-------------------- Editar --------------------

    public ProductosEntity update(ProductosEntity productosEntity) {
        if(findById(productosEntity.getId())==null){
            return null;
        }
        return productosRepository.save(productosEntity);
    }

    public List<ProductosEntity> updateCantidadProductos(List<List<Integer>> productosEntities){
        List<ProductosEntity> productosEntities1 = new ArrayList<>();
        for(List<Integer> productos:productosEntities){
            ProductosEntity productosEntity = findById(productos.get(0));
            if(productosEntity!=null){
                productosEntity.setCantidad(productosEntity.getCantidad()-productos.get(1));
                productosEntities1.add(update(productosEntity));
            }
        }
        return productosEntities1;
    }

}
