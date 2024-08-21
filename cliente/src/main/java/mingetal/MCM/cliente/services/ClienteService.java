package mingetal.MCM.cliente.services;

import lombok.Generated;
import mingetal.MCM.cliente.entities.ClienteEntity;
import mingetal.MCM.cliente.repositories.ClienteRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    private RestTemplate restTemplate;

    //-------------------- Guardado --------------------

    public ClienteEntity save(ClienteEntity clienteEntity) {
        ClienteEntity existingCliente = findByRut(clienteEntity.getRut());
        if (existingCliente != null) {
            throw new RuntimeException("Ya existe un cliente registrado con este RUT.");
        }

        // Procesar el nombre
        String[] palabras = clienteEntity.getNombre().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String palabra : palabras) {
            sb.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1)).append(" ");
        }
        clienteEntity.setNombre(sb.toString().trim());
        return clienteRepository.save(clienteEntity);
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



    @Generated
    public List<List<Object>> getRankingCliente() {
        String authHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(HttpHeaders.AUTHORIZATION);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<List<Object>>> response = restTemplate.exchange(
                "http://gateway:8080/ordenes_de_compra/cliente/clientsbyyear",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<List<Object>>>() {}
        );

        List<List<Object>> ranking = response.getBody();
        if (ranking == null) {
            return new ArrayList<>();
        }

        for (List<Object> entry : ranking) {
            String rut = (String) entry.get(0);
            ClienteEntity cliente = clienteRepository.findByRut(rut);
            entry.add(cliente.getNombre());
            entry.add(cliente.getEmpresa());
            entry.add(cliente.getEmail());
            entry.add(cliente.getTelefono());
        }

        return ranking;
    }

    //-------------------- Carga masiva -----------------------
    @Generated
    public List<ClienteEntity> readExcelFile(MultipartFile file) {
        List<ClienteEntity> clientes = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            boolean skipHeader = true;
            for (Row row : sheet) {
                if (skipHeader) {
                    skipHeader = false; // Saltar la cabecera del archivo
                    continue;
                }
                ClienteEntity cliente = new ClienteEntity();
                cliente.setRut(row.getCell(0).getStringCellValue());
                cliente.setNombre(row.getCell(1).getStringCellValue());
                cliente.setEmail(row.getCell(2).getStringCellValue());
                cliente.setTelefono(row.getCell(3).getStringCellValue());
                cliente.setEmpresa(row.getCell(4).getStringCellValue());
                clientes.add(cliente);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    @Generated
    public void saveAll(List<ClienteEntity> clientes) {
        for (ClienteEntity cliente : clientes) {
            try {
                clienteRepository.save(cliente);
            } catch (Exception e) {
                System.err.println("Error al guardar el cliente: " + cliente.getRut());
                e.printStackTrace();
                // Puedes decidir continuar con el siguiente cliente o manejar de otra forma
            }
        }
    }
}
