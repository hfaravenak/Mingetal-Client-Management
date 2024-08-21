package mingetal.MCM.cliente.services;

import mingetal.MCM.cliente.entities.CotizacionEntity;
import mingetal.MCM.cliente.entities.ListaProductosCotizacionEntity;
import mingetal.MCM.cliente.model.ProductosEntity;
import mingetal.MCM.cliente.repositories.ListaProductosCotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListaProductosCotizacionService {
    @Autowired
    ListaProductosCotizacionRepository listaProductosCotizacionRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;

    public ListaProductosCotizacionEntity save(ListaProductosCotizacionEntity listaProductosCotizacionEntity){
        if(findById(listaProductosCotizacionEntity.getId())==null){
            return listaProductosCotizacionRepository.save(listaProductosCotizacionEntity);
        }
        return null;
    }

    public ListaProductosCotizacionEntity findById(int id){
        return listaProductosCotizacionRepository.findById(id);
    }

    public List<ListaProductosCotizacionEntity> findByIdCotizacion(int id_OC_proveedor){
        return listaProductosCotizacionRepository.findByIdCotizacion(id_OC_proveedor);
    }

    public ProductosEntity findProductoByIdProducto(int id_producto) {
        // Obtener el encabezado Authorization
        String authHeader = request.getHeader("Authorization");

        // Crear y configurar los encabezados HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Realizar la llamada al microservicio de productos
        ResponseEntity<ProductosEntity> response = restTemplate.exchange(
                "http://gateway:8080/productos/" + id_producto,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ProductosEntity>() {}
        );

        // Obtener el cuerpo de la respuesta
        return response.getBody();
    }


    public ListaProductosCotizacionEntity delete(int id){
        ListaProductosCotizacionEntity listaProductosOCProveedorEntity = findById(id);
        if(listaProductosOCProveedorEntity ==null){
            return null;
        }
        listaProductosCotizacionRepository.delete(listaProductosOCProveedorEntity);
        return listaProductosOCProveedorEntity;
    }

    public void cargaMasivaDatos(int last_id, String productos) {

        // Obtener el encabezado Authorization
        String authHeader = request.getHeader("Authorization");

        // Crear y configurar los encabezados HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);


        List<String> listado = List.of(productos.split(";"));
        for(String str : listado){ //recorrer productos
            List<String> info= List.of(str.split(" "));
            System.out.println("info: " + info);
            ListaProductosCotizacionEntity LP = new ListaProductosCotizacionEntity();
            //asignar id de cotización
            LP.setId_cotizacion(last_id);
            System.out.println("id_cotizacion: " + LP.getId_cotizacion());
            //asignar cantidad
            Integer cantidad = Integer.valueOf(info.get(0));
            LP.setCantidad(cantidad);
            System.out.println("cantidad: " + cantidad);

            //buscar id producto
            String name = String.join(" ", info.subList(1, info.size()));
            System.out.println("name: "+ name);

            // Realizar la llamada al microservicio de productos
            ResponseEntity<List<ProductosEntity>> response = restTemplate.exchange(
                    "http://gateway:8080/productos/nombre/" + name,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<ProductosEntity>>() {}
            );
            //########################################################
            //asignar ID producto
            System.out.println(response);
            ProductosEntity producto = response.getBody().get(0);

            // Obtener el id y nombre del producto
            Integer id_producto = producto.getId();
            LP.setId_producto(id_producto);
            System.out.println("ID del producto: " + id_producto);

            Integer valor_producto = response.getBody().get(0).getValor_final();
            Integer valor_pago = cantidad*valor_producto;
            LP.setValor_pago(valor_pago);
            System.out.println("valor_pago" + valor_pago);

            listaProductosCotizacionRepository.save(LP);
        }
    }
}

