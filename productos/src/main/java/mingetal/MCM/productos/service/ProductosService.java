package mingetal.MCM.productos.service;

import lombok.Generated;
import mingetal.MCM.productos.entity.ProductosEntity;
import mingetal.MCM.productos.model.ListaProductosCotizacionEntity;
import mingetal.MCM.productos.model.ListaProductosOCClienteEntity;
import mingetal.MCM.productos.model.ListaProductosOCProveedorEntity;
import mingetal.MCM.productos.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
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
        List<ProductosEntity> productos = productosRepository.findAll();
        productos.sort(Comparator.comparing(ProductosEntity::getTipo, Comparator.nullsFirst(Comparator.naturalOrder())));
        return productos;
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
    @Generated
    public ProductosEntity findByNombreTextual(String nombre) {
        return productosRepository.findByNombre(nombre);
    }
    @Generated
    public List<ProductosEntity> findByOCCliente(int id){
        List<ListaProductosOCClienteEntity> response = restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/cliente/productos/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ListaProductosOCClienteEntity>>() {}
        ).getBody();
        if(response == null){
            return new ArrayList<>();
        }

        List<ProductosEntity> productosEntities = new ArrayList<>();

        for(ListaProductosOCClienteEntity listaProductosOCClienteEntity : response){
            productosEntities.add(findById(listaProductosOCClienteEntity.getId_producto()));
        }

        return productosEntities;
    }
    @Generated
    public List<ProductosEntity> findByOCProveedor(int id){
        List<ListaProductosOCProveedorEntity> response = restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/proveedor/productos/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ListaProductosOCProveedorEntity>>() {}
        ).getBody();

        if(response == null){
            return new ArrayList<>();
        }

        List<ProductosEntity> productosEntities = new ArrayList<>();

        for(ListaProductosOCProveedorEntity listaProductosOCProveedorEntity : response){
            productosEntities.add(findById(listaProductosOCProveedorEntity.getId_producto()));
        }

        return productosEntities;
    }
    @Generated
    public List<ProductosEntity> findByCotizacion(int id){
        System.out.println("id: "+id);
        List<ListaProductosCotizacionEntity> response = restTemplate.exchange(
                "http://localhost:8080/cliente/cotizacion/productos/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ListaProductosCotizacionEntity>>() {}
        ).getBody();

        if(response == null){
            return new ArrayList<>();
        }

        List<ProductosEntity> productosEntities = new ArrayList<>();

        for(ListaProductosCotizacionEntity listaProductosCotizacionEntity : response){
            productosEntities.add(findById(listaProductosCotizacionEntity.getId_producto()));
        }

        return productosEntities;
    }

    public List<ProductosEntity> findPocosProductos(){
        return productosRepository.findPocoProductos();
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

}
