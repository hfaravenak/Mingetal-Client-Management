package mingetal.MCM.ordenesdecompra.service;

import lombok.Generated;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.model.ClienteEntity;
import mingetal.MCM.ordenesdecompra.repository.OrdenesDeCompraClienteRepository;
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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<OrdenesDeCompraClienteEntity> findPagado(){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = ordenesDeCompraClienteRepository.findPagado();
        ordenesDeCompraClienteEntities.sort(Comparator.comparing(OrdenesDeCompraClienteEntity::getFecha_solicitud, Comparator.nullsFirst(Comparator.reverseOrder())));
        return ordenesDeCompraClienteEntities;
    }

    public OrdenesDeCompraClienteEntity findById(int id){
        return ordenesDeCompraClienteRepository.findById(id);
    }

    public  List<OrdenesDeCompraClienteEntity> findByIdCliente(String id_cliente){
        return ordenesDeCompraClienteRepository.findByIdCliente(id_cliente);

    }

    public List<OrdenesDeCompraClienteEntity> findByNameCliente(String nombre) {
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

        // Realizar la llamada al microservicio de clientes por nombre
        ResponseEntity<List<ClienteEntity>> clienteResponse = restTemplate.exchange(
                "http://localhost:8080/cliente/nombre/" + nombre,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ClienteEntity>>() {}
        );

        // Obtener la respuesta del cliente
        List<ClienteEntity> clientes = clienteResponse.getBody();

        if (clientes == null) {
            return new ArrayList<>();
        }

        // Buscar órdenes de compra para cada cliente encontrado por nombre
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = new ArrayList<>();
        for (ClienteEntity cliente : clientes) {
            ordenesDeCompraClienteEntities.addAll(findByIdCliente(cliente.getRut()));
        }

        return ordenesDeCompraClienteEntities;
    }

    public List<OrdenesDeCompraClienteEntity> findByEmpresaCliente(String empresa) {
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

        // Realizar la llamada al microservicio de clientes por empresa
        ResponseEntity<List<ClienteEntity>> clienteResponse = restTemplate.exchange(
                "http://localhost:8080/cliente/empresa/" + empresa,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ClienteEntity>>() {}
        );

        // Obtener la respuesta del cliente
        List<ClienteEntity> clientes = clienteResponse.getBody();

        if (clientes == null) {
            return new ArrayList<>();
        }

        // Buscar órdenes de compra para cada cliente encontrado por empresa
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = new ArrayList<>();
        for (ClienteEntity cliente : clientes) {
            ordenesDeCompraClienteEntities.addAll(findByIdCliente(cliente.getRut()));
        }

        return ordenesDeCompraClienteEntities;
    }

    public List<OrdenesDeCompraClienteEntity> findNoPagados(){
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = ordenesDeCompraClienteRepository.findNoPagado();
        ordenesDeCompraClienteEntities.sort(Comparator.comparing(OrdenesDeCompraClienteEntity::getFecha_pago, Comparator.nullsFirst(Comparator.naturalOrder())));

        LocalDate current = LocalDate.now();
        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntitiesPorFecha = new ArrayList<>();
        for(OrdenesDeCompraClienteEntity oc: ordenesDeCompraClienteEntities){
            if(oc.getFecha_pago().getYear()<current.getYear()){
                ordenesDeCompraClienteEntitiesPorFecha.add(oc);
            }
            else if(oc.getFecha_pago().getYear()==current.getYear()){
                if(oc.getFecha_pago().getMonthValue()<current.getMonthValue()){
                    ordenesDeCompraClienteEntitiesPorFecha.add(oc);
                }
                else if(oc.getFecha_pago().getMonthValue()==current.getMonthValue()){
                    if(oc.getFecha_pago().getDayOfMonth()<current.getDayOfMonth()){
                        ordenesDeCompraClienteEntitiesPorFecha.add(oc);
                    }
                    else{
                        if(oc.getFecha_pago().getDayOfMonth()-current.getDayOfMonth()<15){
                            ordenesDeCompraClienteEntitiesPorFecha.add(oc);
                        }
                    }
                }
            }
        }

        return ordenesDeCompraClienteEntitiesPorFecha;
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
    @Generated
    public List<Object[]> getProductByYear(){
        return ordenesDeCompraClienteRepository.findVentaProductosPorAnio();
    }

    // Función para obtener todos los productos por año y mes
    // Entrega en orden:
    // id del producto
    // cantidad de productos vendidos por año
    // Mes de la venta
    // Año de venta
    @Generated
    public List<Object[]> getProductByYearAndMonth(){
        return ordenesDeCompraClienteRepository.findVentaProductosPorAnioYMes();
    }

    // Función para obtener todas las ventas por año
    // Entrega en orden:
    // Monto vendido por año
    // cantidad de ventas por año
    // Año de venta
    @Generated
    public List<Object[]> getSalesByYear(){
        return ordenesDeCompraClienteRepository.findVentasTotalesPorAnio();
    }

    // Función para obtener todas las ventas por año y mes
    // Entrega en orden:
    // monto vendido por mes de cada año
    // cantidad de ventas por mes de cada año
    // mes
    // Año de venta
    @Generated
    public List<Object[]> getSalesByYearAndMonth(){
        return ordenesDeCompraClienteRepository.findVentasTotalesPorAnioYMes();
    }

    // Función para obtener todas las ventas por año de cada cliente
    // Entrega en orden:
    // Rut del cliente
    // cantidad de productos comprados por el cliente en el año
    // Año de venta
    @Generated
    public List<Object[]> getClientsByYear(){
        return ordenesDeCompraClienteRepository.clientList();
    }

    // Función para hacer la comparación entre similes de meses anteriores
    // Entrega en orden:
    // Monto de ventas
    // Ventas totales
    // Mes
    // Año
    @Generated
    public List<Object[]> similarPreviusMonths() {
        List<Object[]> ventasPorMesYAnio = ordenesDeCompraClienteRepository.comparacionMesesIgualesAnteriores();

        // Extraer los años únicos y ordenarlos en orden descendente
        Set<Integer> aniosUnicos = ventasPorMesYAnio.stream()
                .map(row -> (Integer) row[3]) // Índice 3 corresponde a 'anio'
                .collect(Collectors.toCollection(TreeSet::new)).descendingSet();

        // Tomar los últimos 3 años (los más recientes)
        List<Integer> ultimosTresAnios = aniosUnicos.stream().limit(3).collect(Collectors.toList());

        // Crear una lista con todos los meses para los últimos tres años
        List<Object[]> todosMeses = new ArrayList<>();
        for (Integer anio : ultimosTresAnios) {
            for (int mes = 1; mes <= 12; mes++) {
                todosMeses.add(new Object[]{0, 0, mes, anio});
            }
        }

        // Filtrar los datos para incluir solo los últimos 3 años
        List<Object[]> datosFiltrados = ventasPorMesYAnio.stream()
                .filter(row -> ultimosTresAnios.contains(row[3])) // Índice 3 corresponde a 'anio'
                .collect(Collectors.toList());

        // Crear un mapa para facilitar la actualización de los meses con datos
        Map<String, Object[]> mapaMeses = todosMeses.stream()
                .collect(Collectors.toMap(
                        row -> row[2] + "-" + row[3], // Llave: "mes-año"
                        row -> row
                ));

        // Actualizar los meses con los datos reales
        for (Object[] row : datosFiltrados) {
            String clave = row[2] + "-" + row[3];
            mapaMeses.put(clave, row);
        }

        // Ordenar y colectar los resultados finales agrupados por mes
        List<Object[]> resultadoFinal = mapaMeses.values().stream()
                .sorted(Comparator.comparing((Object[] row) -> (Integer) row[2]).reversed()
                        .thenComparing((Object[] row) -> (Integer) row[3]).reversed())
                .collect(Collectors.toList());

        return resultadoFinal;
    }
}
