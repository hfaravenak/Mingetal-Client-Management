package mingetal.MCM.cliente.Services;

import mingetal.MCM.cliente.Entities.ClienteEntity;
import mingetal.MCM.cliente.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    public void save(ClienteEntity clienteEntity) { clienteRepository.save(clienteEntity); }

    public List<ClienteEntity> findAll() { return clienteRepository.findAll(); }

    public ClienteEntity findByRut(String rut) { return clienteRepository.findByRut(rut); }

    public ClienteEntity findByNombre(String nombre) { return clienteRepository.findByNombre(nombre); }

    public ClienteEntity findByEmpresa(String empresa) { return clienteRepository.findByEmpresa(empresa); }

    public ClienteEntity delete(String rut) {
        ClienteEntity clienteEntity = findByRut(rut);
        clienteRepository.delete(clienteEntity);
        return clienteEntity;
    }

    public ClienteEntity deleteByNombre(String nombre) {
        ClienteEntity clienteEntity = findByNombre(nombre);
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
