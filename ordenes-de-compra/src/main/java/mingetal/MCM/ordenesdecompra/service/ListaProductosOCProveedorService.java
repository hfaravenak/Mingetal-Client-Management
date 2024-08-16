package mingetal.MCM.ordenesdecompra.service;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ProductosEntity;
import mingetal.MCM.ordenesdecompra.repository.ListaProductosOCProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListaProductosOCProveedorService {
    @Autowired
    ListaProductosOCProveedorRepository listaProductosOCProveedorRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;
    public ListaProductosOCProveedorEntity save(ListaProductosOCProveedorEntity listaProductosOCProveedorEntity){
        if(findById(listaProductosOCProveedorEntity.getId())==null){
            return listaProductosOCProveedorRepository.save(listaProductosOCProveedorEntity);
        }
        return null;
    }

    public List<ListaProductosOCProveedorEntity> findAll(){
        return listaProductosOCProveedorRepository.findAll();
    }
    public ListaProductosOCProveedorEntity findById(int id){
        return listaProductosOCProveedorRepository.findById(id);
    }

    public List<ListaProductosOCProveedorEntity> findByIdOCProveedor(int id_OC_proveedor){
        return listaProductosOCProveedorRepository.findByIdProveedor(id_OC_proveedor);
    }

    /*public ProductosEntity findProductoByIdProducto(int id_producto){
        return restTemplate.exchange(
                "http://localhost:8080/productos/"+id_producto,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ProductosEntity>() {}
        ).getBody();
    }*/
    public ListaProductosOCProveedorEntity delete(int id){
        ListaProductosOCProveedorEntity listaProductosOCProveedorEntity = findById(id);
        if(listaProductosOCProveedorEntity ==null){
            return null;
        }
        listaProductosOCProveedorRepository.delete(listaProductosOCProveedorEntity);
        return listaProductosOCProveedorEntity;
    }

    public void saveList(Integer last_id, String productos) {

        String authHeader = request.getHeader("Authorization");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        System.out.println("############################################");
        System.out.println(productos);
        System.out.println("############################################");

        List<String> listado = List.of(productos.split(";"));
        for(String str : listado){ //recorrer productos

            List<String> info= List.of(str.split(" "));
            if(info.isEmpty()){
                System.out.println("A");
                break;
            }
            System.out.println("info: " + info);
            ListaProductosOCProveedorEntity LP = new ListaProductosOCProveedorEntity();
            //asignar id de cotización
            LP.setId_OC_proveedor(last_id);
            System.out.println("id_cotizacion: " + LP.getId_OC_proveedor());
            //asignar cantidad
            Integer cantidad = Integer.valueOf(info.get(0));
            LP.setCantidad(cantidad);
            System.out.println("cantidad: " + cantidad);

            //buscar id producto
            String name = String.join(" ", info.subList(1, info.size()));
            System.out.println("name: "+ name);

            // Realizar la llamada al microservicio de productos
            ResponseEntity<List<ProductosEntity>> response = restTemplate.exchange(
                    "http://localhost:8080/productos/nombre/" + name,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<ProductosEntity>>() {}
            );
            //########################################################
            //asignar ID producto
            System.out.println(response);
            Integer id_producto = response.getBody().get(0).getId();
            LP.setId_producto(id_producto);
            System.out.println("id_producto: " + LP.getId_producto());

            Integer valor_producto = response.getBody().get(0).getValor_final();
            Integer valor_pago = cantidad*valor_producto;
            LP.setValor_pago(valor_pago);
            System.out.println("valor_pago" + valor_pago);

            listaProductosOCProveedorRepository.save(LP);
        }
    }
}
