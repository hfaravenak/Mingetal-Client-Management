package mingetal.MCM.ordenesdecompra.service;

import lombok.Generated;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ProveedorEntity;
import mingetal.MCM.ordenesdecompra.repository.OrdenesDeCompraProveedorRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OrdenesDeCompraProveedorService {
    @Autowired
    OrdenesDeCompraProveedorRepository ordenesDeCompraProveedorRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ListaProductosOCProveedorService listaProductosOCProveedorService;

    @Autowired
    private HttpServletRequest request;

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


    public void readExcelFile(MultipartFile file) {
        // Obtener el encabezado Authorization
        String authHeader = request.getHeader("Authorization");

        // Crear y configurar los encabezados HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedor = new ArrayList<>();
        try{
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            boolean skipHeader = true;
            for (Row row : sheet) {
                if (skipHeader) {
                    skipHeader = false; // Saltar la cabecera del archivo
                    continue;
                }

                //Rut cliente
                if (row.getCell(0) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }
                OrdenesDeCompraProveedorEntity oc = new OrdenesDeCompraProveedorEntity();
                //RUT	Fecha de la Solicitud	Estado Pago	Valor	Fecha del Pago	Fecha de la Entrega	Estado de la entrega	Numero de Factura	Productos
                String rut_proveedor = row.getCell(0).getStringCellValue();
                ResponseEntity<ProveedorEntity> response = restTemplate.exchange(
                        "http://localhost:8080/proveedor/rut/" + rut_proveedor,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<ProveedorEntity>() {}
                );

                /*System.out.println(response);
                Integer id_proveedor = response.getBody().getId_proveedor();
                oc.setId_proveedor(id_proveedor);
                System.out.println(oc.getId_proveedor());*/

                // Verificar si la respuesta no es null y contiene un cuerpo
                if (response != null && response.getBody() != null) {
                    ProveedorEntity proveedor = response.getBody();
                    Integer id_proveedor = proveedor.getId_proveedor();
                    // Usar id_proveedor según lo necesites
                    System.out.println("ID del proveedor: " + id_proveedor);
                } else {
                    // Manejar el caso en que la respuesta es null o no contiene un cuerpo
                    System.out.println("No se encontró un proveedor con el RUT especificado o el servicio no respondió correctamente.");
                    // Puedes lanzar una excepción, retornar un valor por defecto, o manejar el error de la forma que más te convenga
                }

                //Fecha de la Solicitud
                if (row.getCell(1) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }

                LocalDate fecha_solicitud = row.getCell(1).getLocalDateTimeCellValue().toLocalDate();
                oc.setFecha_solicitud(fecha_solicitud);
                System.out.println(oc.getFecha_solicitud());

                //Estado Pago
                if (row.getCell(2) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }

                String estado_pago = row.getCell(2).getStringCellValue();
                oc.setEstado_pago(estado_pago);
                System.out.println(oc.getEstado_pago());

                //Valor
                if (row.getCell(3) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }

                double valor = row.getCell(3).getNumericCellValue();
                oc.setValor_pago((int) valor);
                System.out.println(oc.getValor_pago());

                // Fecha del Pago
                if (row.getCell(4) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }

                LocalDate fecha_pago = row.getCell(4).getLocalDateTimeCellValue().toLocalDate();
                oc.setFecha_pago(fecha_pago);
                System.out.println(oc.getFecha_pago());

                // Fecha de la Entrega
                if (row.getCell(5) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }

                LocalDate fecha_entrega = row.getCell(5).getLocalDateTimeCellValue().toLocalDate();
                oc.setFecha_entrega(fecha_entrega);
                System.out.println(oc.getFecha_entrega());


                //Estado de la entrega
                if (row.getCell(6) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }

                String estado_entrega = row.getCell(6).getStringCellValue();
                oc.setEstado_pago(estado_entrega);
                System.out.println(oc.getEstado_entrega());

                //Numero de Factura
                if (row.getCell(7) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }

                String num_factura = String.valueOf(row.getCell(7).getNumericCellValue());
                oc.setFactura(num_factura);
                System.out.println(oc.getFactura());

                //save OC cliente
                try {
                    ordenesDeCompraProveedorRepository.save(oc);
                } catch (Exception e) {
                    System.err.println("Error al guardar la OC de : " + rut_proveedor );
                    e.printStackTrace();
                    // Puedes decidir continuar con el siguiente cliente o manejar de otra forma
                }

                if (row.getCell(8) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }

                Integer id_oc_proveedor = ordenesDeCompraProveedorRepository.findAll().size();
                //leer productos,ingresar lista de productos, asignar id a OC de lista productos
                String productos = row.getCell(8).getStringCellValue();

                listaProductosOCProveedorService.saveList(id_oc_proveedor,productos);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
