package mingetal.MCM.cliente.Services;

import mingetal.MCM.cliente.Entities.ClienteEntity;
import mingetal.MCM.cliente.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    public void save(ClienteEntity clienteEntity) {
        String[] palabras = clienteEntity.getNombre().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String palabra : palabras) {
            // Convertir la primera letra de la palabra a mayúscula y agregar el resto de la palabra
            sb.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1)).append(" ");
        }
        clienteEntity.setNombre(sb.toString().trim());
        clienteRepository.save(clienteEntity);
    }

    public List<ClienteEntity> findAll() {
        List<ClienteEntity> clienteEntities = clienteRepository.findAll();
        clienteEntities.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));
        return clienteEntities;
    }

    public ClienteEntity findByRut(String rut) { return clienteRepository.findByRut(rut); }

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

    public List<ClienteEntity> findByEmpresa(String empresa) {
        List<ClienteEntity> clienteEntities = clienteRepository.findByEmpresa(empresa);
        clienteEntities.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));
        return clienteEntities;
    }

    public ClienteEntity delete(String rut) {
        ClienteEntity clienteEntity = findByRut(rut);
        clienteRepository.delete(clienteEntity);
        return clienteEntity;
    }

    public ClienteEntity deleteByNombre(String nombre) {
        ClienteEntity clienteEntity = findByNombre(nombre).get(0);
        clienteRepository.delete(clienteEntity);
        return clienteEntity;
    }

    public ClienteEntity update(ClienteEntity clienteEntity) {
        ClienteEntity clienteEntity1 = findByRut(clienteEntity.getRut());
        clienteEntity1.setNombre(clienteEntity.getNombre());
        clienteEntity1.setEmail(clienteEntity.getEmail());
        clienteEntity1.setTelefono(clienteEntity.getTelefono());
        clienteEntity1.setEmpresa(clienteEntity.getEmpresa());
        return clienteRepository.save(clienteEntity1);
    }

}
