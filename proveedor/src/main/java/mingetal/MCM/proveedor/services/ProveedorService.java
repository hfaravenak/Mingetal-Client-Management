package mingetal.MCM.proveedor.services;

import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.entities.ProveedorEntity;
import mingetal.MCM.proveedor.model.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.proveedor.repositories.ProveedorRepository;
import org.apache.poi.ss.usermodel.CellType;
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

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired
    ContactoService contactoService;
    @Autowired
    RestTemplate restTemplate;


    //-------------------- Guardado --------------------

    public ProveedorEntity save(ProveedorEntity proveedorEntity) {
        if (findById(proveedorEntity.getId_proveedor())==null) {
            return proveedorRepository.save(proveedorEntity);
        }
        return null;
    }

    //-------------------- Buscar --------------------
    
    public List<ProveedorEntity> findAll() {
        return proveedorRepository.findAll();
    }
    public List<ProveedorEntity> findByListOC() {
        // Obtener el encabezado Authorization de la solicitud actual
        String authHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(HttpHeaders.AUTHORIZATION);

        // Configurar el encabezado en la solicitud HTTP saliente
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Realizar la solicitud HTTP usando RestTemplate con el encabezado configurado
        ResponseEntity<List<OrdenesDeCompraProveedorEntity>> response = restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/proveedor/",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<OrdenesDeCompraProveedorEntity>>() {}
        );

        List<OrdenesDeCompraProveedorEntity> ordenesDeCompraProveedorEntities = response.getBody();
        List<ProveedorEntity> proveedorEntities = new ArrayList<>();

        if (ordenesDeCompraProveedorEntities != null) {
            for (OrdenesDeCompraProveedorEntity ocProveedor : ordenesDeCompraProveedorEntities) {
                proveedorEntities.add(findById(ocProveedor.getId_proveedor()));
            }
        }

        return proveedorEntities;
    }
    public ProveedorEntity findByEmpresa(String empresa) {
        return proveedorRepository.findByEmpresa(empresa);
    }
    public ProveedorEntity findByRut(String rut) {
        ProveedorEntity proveedor;
        proveedor = proveedorRepository.findByRut1(rut);
        if(proveedor==null){
            proveedor = proveedorRepository.findByRut2(rut);
            if(proveedor ==null){
                proveedor = proveedorRepository.findByRut3(rut);
            }
        }
        System.out.println(proveedor);
        return proveedor;
    }
    public List<ProveedorEntity> findByNombre(String nombre) {
        List<ContactoEntity> contactoEntity = contactoService.findByNombre(nombre);
        List<ProveedorEntity> proveedores = new ArrayList<>();
        for(ContactoEntity contacto:contactoEntity){
            ProveedorEntity proveedor = proveedorRepository.findByRut1(contacto.getRut());
            if(proveedor==null) {
                proveedor = proveedorRepository.findByRut2(contacto.getRut());
                if (proveedor == null) {
                    proveedor = proveedorRepository.findByRut3(contacto.getRut());
                }
            }

            if(proveedor!=null){
                if(!proveedores.contains(proveedor)){
                    proveedores.add(proveedor);
                }
            }
        }
        return proveedores;
    }
    public List<ProveedorEntity> findByRubro(String rubro) {
        return proveedorRepository.findByRubro(rubro);
    }
    public List<String> findRubros(){
        List<String> rubros = new ArrayList<>();
        List<ProveedorEntity> proveedorEntities = proveedorRepository.findAll();

        for(ProveedorEntity proveedor: proveedorEntities){
            if(!rubros.contains(proveedor.getRubro())){
                rubros.add(proveedor.getRubro());
            }
        }

        return rubros;
    }
    public List<ProveedorEntity> findDespacho(){
        return proveedorRepository.findByDespacho("DESPACHOS");
    }
    public ProveedorEntity findByNombreTextual(String nombre) {
        ContactoEntity contactoEntity = contactoService.findByNombreTextual(nombre);
        if(contactoEntity==null){
            return null;
        }
        ProveedorEntity proveedor = proveedorRepository.findByRut1(contactoEntity.getRut());
        if(proveedor==null) {
            proveedor = proveedorRepository.findByRut2(contactoEntity.getRut());
            if (proveedor == null) {
                proveedor = proveedorRepository.findByRut3(contactoEntity.getRut());
            }
        }
        return proveedor;
    }
    public ProveedorEntity findById(int id_proveedor) {
        return proveedorRepository.findById(id_proveedor);
    }

    //-------------------- Eliminar --------------------

    public ProveedorEntity delete(int id_proveedor) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor);
        if (proveedor == null) {
            return null;
        }
        proveedorRepository.delete(proveedor);
        return proveedor;
    }

    //-------------------- Editar --------------------

    public ProveedorEntity update(ProveedorEntity proveedorEntity){
        ProveedorEntity proveedor = proveedorRepository.findById(proveedorEntity.getId_proveedor());
        if (proveedor == null) {
            return null;
        }
        return proveedorRepository.save(proveedorEntity);
    }

    //-------------------- Carga masiva -----------------------
    public void readExcelFile(MultipartFile file) {
        List<ProveedorEntity> cotizaciones = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            boolean skipHeader = true;
            for (Row row : sheet) {
                if (skipHeader) {
                    skipHeader = false; // Saltar la cabecera del archivo
                    continue;
                }

                /*
                @Id
                @NotNull
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private int id_proveedor;
                @Size(max=30, message = "El tamaño máximo del campo empresa es 30")
                private String empresa;
                @Size(max=30, message = "El tamaño máximo del campo rubro es 30")
                private String rubro;
                @Size(max=13, message = "El tamaño máximo del campo id_contacto es 13")
                private String id_contacto;
                @Size(max=13, message = "El tamaño máximo del campo id_contacto2 es 13")
                private String id_contacto2;
                @Size(max=13, message = "El tamaño máximo del campo id_contacto3 es 13")
                private String id_contacto3;
                private String comentario;
                * */
                if (row.getCell(0) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }
                ProveedorEntity proveedor = new ProveedorEntity();
                proveedor.setRubro(row.getCell(0).getStringCellValue());
                System.out.println(proveedor.getRubro());

                if (row.getCell(1) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }
                proveedor.setEmpresa(row.getCell(1).getStringCellValue());
                System.out.println(proveedor.getEmpresa());

                //--------------------- Guardar contactos

                //Crear contacto 1

                String rut_1 = row.getCell(2).getStringCellValue();
                try {
                    String nombre_1 = row.getCell(3).getStringCellValue();
                    String correo_1 = row.getCell(4).getStringCellValue();
                    String fono_cel_1 = row.getCell(5).getStringCellValue();
                    String fono_fijo_1 = row.getCell(6).getStringCellValue();
                    ContactoEntity contacto_1 = new ContactoEntity(rut_1,nombre_1,correo_1,fono_cel_1,fono_fijo_1);
                    contactoService.save(contacto_1);
                } catch (Exception e) {
                    System.err.println("Error al guardar el proveedor: " + proveedor.getEmpresa() + " , este NO TIENE CONTACTO");
                    e.printStackTrace();
                    // Puedes decidir continuar con el siguiente cliente o manejar de otra forma
                }

                //id contactos
                int id_1= contactoService.findAll().size() + 1;
                int id_2= -1;
                int id_3= -1;


                //Crear contacto 2
                String rut_2 = row.getCell(7).getStringCellValue();
                if(rut_2 != null){
                    String nombre_2 = row.getCell(8).getStringCellValue();
                    String correo_2 = row.getCell(9).getStringCellValue();
                    String fono_cel_2 = row.getCell(10).getStringCellValue();
                    String fono_fijo_2 = row.getCell(11).getStringCellValue();

                    ContactoEntity contacto_2 = new ContactoEntity(rut_2,nombre_2,correo_2,fono_cel_2,fono_fijo_2);
                    contactoService.save(contacto_2);
                    id_2 = id_1 + 1;
                }



                //Crear contacto 3
                String rut_3 = row.getCell(12).getStringCellValue();
                if(rut_3 != null){
                    String nombre_3 = row.getCell(13).getStringCellValue();
                    String correo_3 = row.getCell(14).getStringCellValue();
                    String fono_cel_3 = row.getCell(15).getStringCellValue();
                    String fono_fijo_3 = row.getCell(16).getStringCellValue();

                    ContactoEntity contacto_3 = new ContactoEntity(rut_3,nombre_3,correo_3,fono_cel_3,fono_fijo_3);
                    contactoService.save(contacto_3);
                    id_3 = id_2 + 1;
                }


                proveedor.setId_contacto(String.valueOf(id_1));

                if(id_2 != -1){
                    proveedor.setId_contacto2(String.valueOf(id_2));
                }
                if(id_3 != -1){

                    proveedor.setId_contacto3(String.valueOf(id_3));
                }

                try {
                    proveedorRepository.save(proveedor);
                } catch (Exception e) {
                    System.err.println("Error al guardar el proveedor: " + proveedor.getEmpresa());
                    e.printStackTrace();
                    // Puedes decidir continuar con el siguiente cliente o manejar de otra forma
                }

            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
