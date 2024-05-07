package mingetal.MCM.ordenesdecompra.service;

import javassist.expr.NewArray;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ClienteEntity;
import mingetal.MCM.ordenesdecompra.model.ProveedorEntity;
import mingetal.MCM.ordenesdecompra.repository.OrdenesDeCompraClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenesDeCompraClienteService {
    @Autowired
    OrdenesDeCompraClienteRepository ordenesDeCompraClienteRepository;

    @Autowired
    RestTemplate restTemplate;

    public boolean save(OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity){
        if(findById(ordenesDeCompraClienteEntity.getId())==null){
            ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
            return true;
        }
        return false;
    }

    public List<OrdenesDeCompraClienteEntity> findAll(){
        return ordenesDeCompraClienteRepository.findAll();
    }

    public OrdenesDeCompraClienteEntity findById(int id){
        return ordenesDeCompraClienteRepository.findById(id);
    }

    public  List<OrdenesDeCompraClienteEntity> findByIdCliente(String id_cliente){
        return ordenesDeCompraClienteRepository.findByIdCliente(id_cliente);

    }
    public List<OrdenesDeCompraClienteEntity> findByNameCliente(String nombre){

        ClienteEntity response = restTemplate.exchange(
                "http://localhost:8080/cliente/nombre/"+nombre,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ClienteEntity>() {}
        ).getBody();

        if(response == null){
            return null;
        }

        return findByIdCliente(response.getRut());
    }
    public List<OrdenesDeCompraClienteEntity> findByEmpresaCliente(String empresa){

        List<ClienteEntity> response = restTemplate.exchange(
                "http://localhost:8080/cliente/empresa/"+empresa,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ClienteEntity>>() {}
        ).getBody();

        List<OrdenesDeCompraClienteEntity> ordenesDeCompraClienteEntities = new ArrayList<>();

        for (ClienteEntity dato:response){
            ordenesDeCompraClienteEntities.addAll(findByIdCliente(dato.getRut()));
        }

        System.out.println(ordenesDeCompraClienteEntities);


        return ordenesDeCompraClienteEntities;
    }

    public OrdenesDeCompraClienteEntity deleteOCCliente(int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = findById(id);
        if(ordenesDeCompraClienteEntity==null){
            return null;
        }
        ordenesDeCompraClienteRepository.delete(ordenesDeCompraClienteEntity);
        return ordenesDeCompraClienteEntity;
    }

    public OrdenesDeCompraClienteEntity updateOCClienteByEstadoFactura(int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteRepository.findById(id);
        if(ordenesDeCompraClienteEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        if(ordenesDeCompraClienteEntity.getEstado_factura().equals("Emitida")){
            return null;
        }
        ordenesDeCompraClienteEntity.setEstado_factura("Emitida");
        return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
    }

    public OrdenesDeCompraClienteEntity updateOCClienteByEstadoPago(int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteRepository.findById(id);
        if(ordenesDeCompraClienteEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        if(ordenesDeCompraClienteEntity.getEstado_pago().equals("Pagado")){
            return null;
        }
        ordenesDeCompraClienteEntity.setEstado_pago("Pagado");
        return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
    }

    public OrdenesDeCompraClienteEntity updateOCClienteByEstadoEntrega(int id){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteRepository.findById(id);
        if(ordenesDeCompraClienteEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        if(ordenesDeCompraClienteEntity.getEstado_entrega().equals("Entregado")){
            return null;
        }
        ordenesDeCompraClienteEntity.setEstado_entrega("Entregado");
        return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
    }

    public OrdenesDeCompraClienteEntity updateOCClienteByModoPago(int id, int modo_pago){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteRepository.findById(id);
        if(ordenesDeCompraClienteEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        switch (modo_pago) {
            case 1 -> {
                ordenesDeCompraClienteEntity.setModo_pago("Transferencia");
                return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
            }
            case 2 -> {
                ordenesDeCompraClienteEntity.setModo_pago("Cheque");
                return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
            }
            case 3 -> {
                ordenesDeCompraClienteEntity.setModo_pago("Efectivo");
                return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
            }
            default -> {
                return null;
            }
        }

    }

    public OrdenesDeCompraClienteEntity updateOCClienteByFechaPago(int id, LocalDate fecha_pago){
        OrdenesDeCompraClienteEntity ordenesDeCompraClienteEntity = ordenesDeCompraClienteRepository.findById(id);
        if(ordenesDeCompraClienteEntity==null){
            //throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
            return null;
        }
        ordenesDeCompraClienteEntity.setFecha_pago(fecha_pago);
        return ordenesDeCompraClienteRepository.save(ordenesDeCompraClienteEntity);
    }
}
