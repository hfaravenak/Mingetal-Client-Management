package mingetal.MCM.ordenesdecompra.service;

import lombok.Generated;
import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ProductosEntity;
import mingetal.MCM.ordenesdecompra.repository.ListaProductosOCClienteRepository;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListaProductosOCClienteService {
    @Autowired
    ListaProductosOCClienteRepository listaProductosOCClienteRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;

    public ListaProductosOCClienteEntity save(ListaProductosOCClienteEntity listaProductosOCClienteEntities){
        if(findById(listaProductosOCClienteEntities.getId())==null){
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

            restTemplate.exchange(
                    "http://localhost:8080/productos/update/count/?id=" + listaProductosOCClienteEntities.getId_producto()+"&count=-"+listaProductosOCClienteEntities.getCantidad(),
                    HttpMethod.PUT,
                    entity,
                    ProductosEntity.class
            );
            return listaProductosOCClienteRepository.save(listaProductosOCClienteEntities);
        }
        return null;
    }

    public List<ListaProductosOCClienteEntity> findAll(){
        return listaProductosOCClienteRepository.findAll();
    }

    public ListaProductosOCClienteEntity findById(int id){
        return listaProductosOCClienteRepository.findById(id);
    }

    public List<ListaProductosOCClienteEntity> findByIdOCCliente(int id_OC_cliente){
        return  listaProductosOCClienteRepository.findByIdCliente(id_OC_cliente);
    }
    @Generated
    public ProductosEntity findProductoByIdProducto(int id_producto) {
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

        // Realizar la llamada al microservicio de productos
        ResponseEntity<ProductosEntity> response = restTemplate.exchange(
                "http://localhost:8080/productos/" + id_producto,
                HttpMethod.GET,
                entity,
                ProductosEntity.class
        );

        // Obtener y retornar el cuerpo de la respuesta
        return response.getBody();
    }

    public ListaProductosOCClienteEntity delete(int id){
        ListaProductosOCClienteEntity listaProductosOCClienteEntity = findById(id);
        if(listaProductosOCClienteEntity ==null){
            return null;
        }
        listaProductosOCClienteRepository.delete(listaProductosOCClienteEntity);
        return listaProductosOCClienteEntity;
    }
    @Generated
    public void saveList(int last_id, String productos) {


        String authHeader = request.getHeader("Authorization");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        List<String> listado = List.of(productos.split(";"));
        for(String str : listado){ //recorrer productos

            List<String> info= List.of(str.split(" "));
            if(info.isEmpty()){
                break;
            }
            ListaProductosOCClienteEntity LP = new ListaProductosOCClienteEntity();
            //asignar id de cotización
            LP.setId_OC_cliente(last_id);
            //asignar cantidad
            Integer cantidad = Integer.valueOf(info.get(0));
            LP.setCantidad(cantidad);

            //buscar id producto
            String name = String.join(" ", info.subList(1, info.size()));

            // Realizar la llamada al microservicio de productos
            ResponseEntity<List<ProductosEntity>> response = restTemplate.exchange(
                    "http://localhost:8080/productos/nombre/" + name,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<ProductosEntity>>() {}
            );
            //########################################################
            //asignar ID producto
            Integer id_producto = response.getBody().get(0).getId();
            LP.setId_producto(id_producto);

            Integer valor_producto = response.getBody().get(0).getValor_final();
            Integer valor_pago = cantidad*valor_producto;
            LP.setValor_pago(valor_pago);

            listaProductosOCClienteRepository.save(LP);
        }
    }
}
