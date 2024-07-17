package mingetal.MCM.ordenesdecompra.service;

import lombok.Generated;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ProveedorEntity;
import mingetal.MCM.ordenesdecompra.repository.OrdenesDeCompraProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OrdenesDeCompraProveedorService {
    @Autowired
    OrdenesDeCompraProveedorRepository ordenesDeCompraProveedorRepository;

    @Autowired
    RestTemplate restTemplate;

    //-------------------- Guardado --------------------

    public OrdenesDeCompraProveedorEntity save(OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity){
        if(findById(ordenesDeCompraProveedorEntity.getId())==null){
            return ordenesDeCompraProveedorRepository.save(ordenesDeCompraProveedorEntity);
        }
        return null;
    }

    //-------------------- Buscar --------------------

    public List<OrdenesDeCompraProveedorEntity> findAll(){
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = ordenesDeCompraProveedorRepository.findAll();
        ordenesDeCompraProveedorEntities.sort(Comparator.comparing(OrdenesDeCompraProveedorEntity::getFecha_solicitud, Comparator.nullsFirst(Comparator.naturalOrder())));
        return ordenesDeCompraProveedorEntities;
    }
    public OrdenesDeCompraProveedorEntity findById(int id){
        return ordenesDeCompraProveedorRepository.findById(id);
    }

    public List<OrdenesDeCompraProveedorEntity> findByNameProveedor(String nombre) {
        // Obtener el encabezado Authorization del contexto de la solicitud HTTP
        String authHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(HttpHeaders.AUTHORIZATION);

        // Validar si el encabezado es nulo o vacío (depende de tu lógica de manejo de errores)
        if (authHeader == null || authHeader.isEmpty()) {
            // Manejo de error o lanzamiento de excepción si el encabezado no está presente
            throw new RuntimeException("No se encontró el encabezado Authorization");
        }

        // Crear y configurar los encabezados HTTP con el token de autorización
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Realizar la llamada al microservicio de proveedores por nombre
        ResponseEntity<List<ProveedorEntity>> proveedorResponse = restTemplate.exchange(
                "http://localhost:8080/proveedor/nombre/" + nombre,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProveedorEntity>>() {}
        );

        // Obtener la respuesta de proveedores
        List<ProveedorEntity> proveedores = proveedorResponse.getBody();

        if (proveedores == null) {
            return new ArrayList<>();
        }

        // Buscar órdenes de compra para cada proveedor encontrado por nombre
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = new ArrayList<>();
        for (ProveedorEntity proveedor : proveedores) {
            ordenesDeCompraProveedorEntities.addAll(findByIdProveedor(proveedor.getId_proveedor()));
        }

        return ordenesDeCompraProveedorEntities;
    }

    public List<OrdenesDeCompraProveedorEntity> findByRubro(String rubro) {
        // Obtener el encabezado Authorization del contexto de la solicitud HTTP
        String authHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(HttpHeaders.AUTHORIZATION);

        // Validar si el encabezado es nulo o vacío (depende de tu lógica de manejo de errores)
        if (authHeader == null || authHeader.isEmpty()) {
            // Manejo de error o lanzamiento de excepción si el encabezado no está presente
            throw new RuntimeException("No se encontró el encabezado Authorization");
        }

        // Crear y configurar los encabezados HTTP con el token de autorización
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Realizar la llamada al microservicio de proveedores por rubro
        ResponseEntity<List<ProveedorEntity>> proveedorResponse = restTemplate.exchange(
                "http://localhost:8080/proveedor/rubro/" + rubro,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProveedorEntity>>() {}
        );

        // Obtener la respuesta de proveedores
        List<ProveedorEntity> proveedores = proveedorResponse.getBody();

        if (proveedores == null) {
            return new ArrayList<>();
        }

        // Buscar órdenes de compra para cada proveedor encontrado por rubro
        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = new ArrayList<>();
        for (ProveedorEntity proveedor : proveedores) {
            ordenesDeCompraProveedorEntities.addAll(findByIdProveedor(proveedor.getId_proveedor()));
        }

        return ordenesDeCompraProveedorEntities;
    }

    public List<OrdenesDeCompraProveedorEntity> findByEmpresa(String empresa) {
        // Obtener el encabezado Authorization del contexto de la solicitud HTTP
        String authHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(HttpHeaders.AUTHORIZATION);

        // Validar si el encabezado es nulo o vacío (depende de tu lógica de manejo de errores)
        if (authHeader == null || authHeader.isEmpty()) {
            // Manejo de error o lanzamiento de excepción si el encabezado no está presente
            throw new RuntimeException("No se encontró el encabezado Authorization");
        }

        // Crear y configurar los encabezados HTTP con el token de autorización
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Realizar la llamada al microservicio de proveedor por empresa
        ResponseEntity<ProveedorEntity> proveedorResponse = restTemplate.exchange(
                "http://localhost:8080/proveedor/empresa/" + empresa,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ProveedorEntity>() {}
        );

        // Obtener la respuesta del proveedor
        ProveedorEntity proveedor = proveedorResponse.getBody();

        if (proveedor == null) {
            return new ArrayList<>();
        }

        // Buscar las órdenes de compra para el proveedor encontrado por empresa
        return findByIdProveedor(proveedor.getId_proveedor());
    }

    public  List<OrdenesDeCompraProveedorEntity> findByIdProveedor(int id_proveedor){
        return ordenesDeCompraProveedorRepository.findByIdProveedor(id_proveedor);
    }

    //-------------------- Eliminar --------------------

    public OrdenesDeCompraProveedorEntity delete(int id){
        OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity = findById(id);
        if(ordenesDeCompraProveedorEntity==null){
            return null;
        }
        ordenesDeCompraProveedorRepository.delete(ordenesDeCompraProveedorEntity);
        return ordenesDeCompraProveedorEntity;
    }

    //-------------------- Editar --------------------

    public OrdenesDeCompraProveedorEntity update(OrdenesDeCompraProveedorEntity ordenesDeCompraProveedorEntity){
        if(ordenesDeCompraProveedorEntity!=null && findById(ordenesDeCompraProveedorEntity.getId())!=null){
            return ordenesDeCompraProveedorRepository.save(ordenesDeCompraProveedorEntity);
        }
        return null;
    }

    // ------------------ Estadística ------------------

    @Generated
    public List<Object[]> getPurchasesByYear(){
        return ordenesDeCompraProveedorRepository.findCompraProductosPorAnio();
    }

    // Función para obtener todos los productos comprados por año y mes
    // Entrega en orden:
    // id del producto
    // cantidad de productos comprados por año
    // Mes de la venta
    // Año de compra
    @Generated
    public List<Object[]> getPurchasesByYearAndMonth(){
        return ordenesDeCompraProveedorRepository.findCompraProductosPorAnioYMes();
    }

    // Función para obtener todas las compras totales por año
    // Entrega en orden:
    // Monto total de compras
    // Cantidad de compras
    // Año de compra
    @Generated
    public List<Object[]> getAllPurchasesByYear(){
        return ordenesDeCompraProveedorRepository.findComprasTotalesPorAnio();
    }

    // Función para obtener todas las compras totales por año mes
    // Entrega en orden:
    // Monto total de compras
    // Cantidad de compras
    // mes
    // Año de compra
    @Generated
    public List<Object[]> getAllPurchasesByYearAndMont(){
        return ordenesDeCompraProveedorRepository.findComprasTotalesPorAnioYMes();
    }



}
