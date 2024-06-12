package mingetal.MCM.cliente.services;

import mingetal.MCM.cliente.entities.ClienteEntity;
import mingetal.MCM.cliente.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    //-------------------- Guardado --------------------

    public ClienteEntity save(ClienteEntity clienteEntity) {
        if(findByRut(clienteEntity.getRut())==null){
            String[] palabras = clienteEntity.getNombre().split("\\s+");
            StringBuilder sb = new StringBuilder();

            for (String palabra : palabras) {
                // Convertir la primera letra de la palabra a mayúscula y agregar el resto de la palabra
                sb.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1)).append(" ");
            }
            clienteEntity.setNombre(sb.toString().trim());
            return clienteRepository.save(clienteEntity);
        }
        return null;
    }

    //-------------------- Buscar --------------------

    public List<ClienteEntity> findAll() {
        List<ClienteEntity> clienteEntities = clienteRepository.findAll();
        clienteEntities.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));
        return clienteEntities;
    }
    public ClienteEntity findByRut(String rut) {
        return clienteRepository.findByRut(rut);
    }
    public List<ClienteEntity> findByNombre(String nombre) {
        List<ClienteEntity> clienteEntities = findAll();
        List<ClienteEntity> resultados = new ArrayList<>();
        for (ClienteEntity nombreDeLista : clienteEntities) {
            if (nombreDeLista.getNombre().contains(nombre)) {
                resultados.add(nombreDeLista);
            }
        }

        resultados.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));

        return resultados;
    }
    public ClienteEntity findByNombreTextual(String nombre) {
        return clienteRepository.findByNombre(nombre);
    }
    public List<ClienteEntity> findByEmpresa(String empresa) {
        List<ClienteEntity> clienteEntities = clienteRepository.findByEmpresa(empresa);
        clienteEntities.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));
        return clienteEntities;
    }

    //-------------------- Eliminar --------------------

    public ClienteEntity delete(String rut) {
        ClienteEntity clienteEntity = findByRut(rut);
        if(clienteEntity==null){
            return null;
        }
        clienteRepository.delete(clienteEntity);
        return clienteEntity;
    }

    //-------------------- Editar --------------------

    public ClienteEntity update(ClienteEntity clienteEntity) {
        if(findByRut(clienteEntity.getRut())==null){
            return null;
        }
        return clienteRepository.save(clienteEntity);
    }
}
