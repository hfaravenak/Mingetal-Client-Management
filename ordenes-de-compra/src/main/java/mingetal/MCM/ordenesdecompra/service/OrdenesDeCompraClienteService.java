package mingetal.MCM.ordenesdecompra.service;

import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.model.ClienteEntity;
import mingetal.MCM.ordenesdecompra.repository.OrdenesDeCompraClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OrdenesDeCompraClienteService {
    @Autowired
    OrdenesDeCompraClienteRepository ordenesDeCompraClienteRepository;

    @Autowired
    RestTemplate restTemplate;

    //-------------------- Guardado --------------------

    public OrdenesDeCompraClienteEntity save(OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity){
        if(findById(ordenesDeCompraClienteEntity.getId())==null){
            return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
        }
        return null;
    }

    //-------------------- Buscar --------------------

    public List<OrdenesDeCompraClienteEntity> findAll(){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = ordenesDeCompraClienteRepository.findAll();
        ordenesDeCompraClienteEntities.sort(Comparator.comparing(OrdenesDeCompraClienteEntity::getFecha_solicitud, Comparator.nullsFirst(Comparator.naturalOrder())));
        return ordenesDeCompraClienteEntities;
    }

    public List<OrdenesDeCompraClienteEntity> findPagadoEntregado(){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = ordenesDeCompraClienteRepository.findPagadoEntregado();
        ordenesDeCompraClienteEntities.sort(Comparator.comparing(OrdenesDeCompraClienteEntity::getFecha_solicitud, Comparator.nullsFirst(Comparator.naturalOrder())));
        return ordenesDeCompraClienteEntities;
    }
    public OrdenesDeCompraClienteEntity findById(int id){
        return ordenesDeCompraClienteRepository.findById(id);
    }
    public  List<OrdenesDeCompraClienteEntity> findByIdCliente(String id_cliente){
        return ordenesDeCompraClienteRepository.findByIdCliente(id_cliente);

    }
    public List<OrdenesDeCompraClienteEntity> findByNameCliente(String nombre){

        List<ClienteEntity> response = restTemplate.exchange(
                "http://localhost:8080/cliente/nombre/"+nombre,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ClienteEntity>>() {}
        ).getBody();

        if(response == null){
            return new ArrayList<>();
        }

        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = new ArrayList<>();

        for (ClienteEntity client:response) {
            ordenesDeCompraClienteEntities.addAll(findByIdCliente(client.getRut()));
        }
        return ordenesDeCompraClienteEntities;
    }
    public List<OrdenesDeCompraClienteEntity> findByEmpresaCliente(String empresa){

        List<ClienteEntity> response = restTemplate.exchange(
                "http://localhost:8080/cliente/empresa/"+empresa,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ClienteEntity>>() {}
        ).getBody();

        if(response == null){
            return new ArrayList<>();
        }

        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = new ArrayList<>();

        for (ClienteEntity dato:response){
            ordenesDeCompraClienteEntities.addAll(findByIdCliente(dato.getRut()));
        }

        System.out.println(ordenesDeCompraClienteEntities);


        return ordenesDeCompraClienteEntities;
    }

    //-------------------- Eliminar --------------------

    public OrdenesDeCompraClienteEntity delete(int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = findById(id);
        if(ordenesDeCompraClienteEntity==null){
            return null;
        }
        ordenesDeCompraClienteRepository.delete(ordenesDeCompraClienteEntity);
        return ordenesDeCompraClienteEntity;
    }

    //-------------------- Editar --------------------

    public OrdenesDeCompraClienteEntity update(OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity){
        if(ordenesDeCompraClienteEntity!=null && findById(ordenesDeCompraClienteEntity.getId())!=null){
            return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
        }
        return null;
    }

    // -------------------- Estadística --------------------

    // Función para obtener todos los productos por año
    // Entrega en orden:
    // id del producto
    // cantidad de productos vendidos por año
    // Año de venta
    public List<Object[]> getProductByYear(){
        return ordenesDeCompraClienteRepository.findVentaProductosPorAnio();
    }

    // Función para obtener todos los productos por año y mes
    // Entrega en orden:
    // id del producto
    // cantidad de productos vendidos por año
    // Mes de la venta
    // Año de venta
    public List<Object[]> getProductByYearAndMonth(){
        return ordenesDeCompraClienteRepository.findVentaProductosPorAnioYMes();
    }

    // Función para obtener todas las ventas por año
    // Entrega en orden:
    // cantidad de productos vendidos por año
    // Año de venta
    public List<Object[]> getSalesByYear(){
        return ordenesDeCompraClienteRepository.findVentasTotalesPorAnio();
    }

    // Función para obtener todas las ventas por año y mes
    // Entrega en orden:
    // cantidad de productos vendidos por año
    // mes
    // Año de venta
    public List<Object[]> getSalesByYearAndMonth(){
        return ordenesDeCompraClienteRepository.findVentasTotalesPorAnioYMes();
    }

    // Función para obtener todas las ventas por año de cada cliente
    // Entrega en orden:
    // Rut del cliente
    // cantidad de productos comprados por el cliente en el año
    // Año de venta
    public List<Object[]> getClientsByYear(){
        return ordenesDeCompraClienteRepository.clientList();
    }

}
