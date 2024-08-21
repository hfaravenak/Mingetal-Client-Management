package mingetal.MCM.productos.service;

import lombok.Generated;
import mingetal.MCM.productos.entity.ProductosEntity;
import mingetal.MCM.productos.model.ListaProductosCotizacionEntity;
import mingetal.MCM.productos.model.ListaProductosOCClienteEntity;
import mingetal.MCM.productos.model.ListaProductosOCProveedorEntity;
import mingetal.MCM.productos.repository.ProductosRepository;
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

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Optional;


@Service
public class ProductosService {
    @Autowired
    ProductosRepository productosRepository;

    @Autowired
    RestTemplate restTemplate;

    //-------------------- Guardado --------------------

    @Transactional
    public ProductosEntity save(ProductosEntity productosEntity) {
        String[] palabras = productosEntity.getNombre().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String palabra : palabras) {
            // Convertir la primera letra de la palabra a mayúscula y agregar el resto de la palabra
            sb.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1)).append(" ");
        }
        productosEntity.setNombre(sb.toString().trim());
        if (findByNombreTextual(productosEntity.getNombre()) == null) {
            return productosRepository.save(productosEntity);
        }
        return null;
    }

    //-------------------- Buscar --------------------

    @Transactional(readOnly = true)
    public List<ProductosEntity> findAll() {
        List<ProductosEntity> productosEntities = productosRepository.findAll();
        productosEntities.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));
        return productosEntities;
    }

    @Transactional(readOnly = true)
    public ProductosEntity findById(int id) {
        return productosRepository.findById(id);
    }
    @Transactional(readOnly = true)
    public List<ProductosEntity> findByTipo(String tipo) {
        List<ProductosEntity> productosEntities = productosRepository.findByTipo(tipo);
        productosEntities.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));
        return productosEntities;
    }
    @Transactional(readOnly = true)
    public List<ProductosEntity> findByNombre(String nombre) {
        List<ProductosEntity> productoEntities = findAll();
        List<ProductosEntity> resultados = new ArrayList<>();
        for (ProductosEntity nombreDeLista : productoEntities) {
            if (nombreDeLista.getNombre().contains(nombre)) {
                resultados.add(nombreDeLista);
            }
        }
        resultados.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));
        return resultados;
    }
    @Generated
    @Transactional(readOnly = true)
    public ProductosEntity findByNombreTextual(String nombre) {
        return productosRepository.findByNombre(nombre);
    }
    @Generated
    @Transactional(readOnly = true)
    public List<ProductosEntity> findByOCCliente(int id) {
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

        // Realizar la llamada al microservicio de órdenes de compra cliente para obtener los productos
        ResponseEntity<List<ListaProductosOCClienteEntity>> response = restTemplate.exchange(
                "http://gateway:8080/ordenes_de_compra/cliente/productos/" + id,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ListaProductosOCClienteEntity>>() {}
        );

        // Obtener la lista de productos desde la respuesta
        List<ListaProductosOCClienteEntity> productosOCClienteEntities = response.getBody();

        if (productosOCClienteEntities == null) {
            return new ArrayList<>();
        }

        // Construir la lista de entidades de productos a partir de los IDs obtenidos
        List<ProductosEntity> productosEntities = new ArrayList<>();
        for (ListaProductosOCClienteEntity listaProductosOCClienteEntity : productosOCClienteEntities) {
            ProductosEntity producto = findById(listaProductosOCClienteEntity.getId_producto());
            if (producto != null) {
                productosEntities.add(producto);
            }
        }

        return productosEntities;
    }
    @Generated
    @Transactional(readOnly = true)
    public List<ProductosEntity> findByOCProveedor(int id) {
        // Obtener el encabezado Authorization de la solicitud actual
        String authHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(HttpHeaders.AUTHORIZATION);

        // Configurar el encabezado en la solicitud HTTP saliente
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Realizar la solicitud HTTP usando RestTemplate con el encabezado configurado
        ResponseEntity<List<ListaProductosOCProveedorEntity>> response = restTemplate.exchange(
                "http://gateway:8080/ordenes_de_compra/proveedor/productos/" + id,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ListaProductosOCProveedorEntity>>() {}
        );

        List<ListaProductosOCProveedorEntity> productosOCProveedorEntities = response.getBody();
        if (productosOCProveedorEntities == null) {
            return new ArrayList<>();
        }

        List<ProductosEntity> productosEntities = new ArrayList<>();

        for (ListaProductosOCProveedorEntity listaProductosOCProveedorEntity : productosOCProveedorEntities) {
            productosEntities.add(findById(listaProductosOCProveedorEntity.getId_producto()));
        }

        return productosEntities;
    }

    @Generated
    @Transactional(readOnly = true)
    public List<ProductosEntity> findByCotizacion(int id) {
        // Obtener el encabezado Authorization de la solicitud actual
        String authHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader(HttpHeaders.AUTHORIZATION);

        // Configurar el encabezado en la solicitud HTTP saliente
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Realizar la solicitud HTTP usando RestTemplate con el encabezado configurado
        ResponseEntity<List<ListaProductosCotizacionEntity>> response = restTemplate.exchange(
                "http://gateway:8080/cliente/cotizacion/productos/" + id,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ListaProductosCotizacionEntity>>() {}
        );

        List<ListaProductosCotizacionEntity> productosCotizacionEntities = response.getBody();
        if (productosCotizacionEntities == null) {
            return new ArrayList<>();
        }

        List<ProductosEntity> productosEntities = new ArrayList<>();

        for (ListaProductosCotizacionEntity listaProductosCotizacionEntity : productosCotizacionEntities) {
            productosEntities.add(findById(listaProductosCotizacionEntity.getId_producto()));
        }

        return productosEntities;
    }

    @Transactional(readOnly = true)
    @Generated
    public List<ProductosEntity> findPocosProductos() {
        return productosRepository.findPocoProductos();
    }

    //-------------------- Eliminar --------------------

    @Transactional
    public ProductosEntity delete(int id) {
        ProductosEntity productosEntity = findById(id);
        if (productosEntity == null) {
            return null;
        }
        productosRepository.delete(productosEntity);
        return productosEntity;
    }

    //-------------------- Editar --------------------

    @Transactional
    public ProductosEntity update(ProductosEntity producto) {
        if (findById(producto.getId()) == null) {
            return null;
        }
        return productosRepository.save(producto);
    }
    @Transactional
    @Generated
    public ProductosEntity updateCount(int id, int count) {
        ProductosEntity productos = findById(id);
        if (productos == null) {
            return null;
        }
        productos.setCantidad(productos.getCantidad()+count);
        return productosRepository.save(productos);
    }

    //-------------------- Carga masiva -----------------------
    @Generated
    public List<ProductosEntity> readExcelFile(MultipartFile file) {
        List<ProductosEntity> productos = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            boolean skipHeader = true;
            for (Row row : sheet) {
                if (skipHeader) {
                    skipHeader = false; // Saltar la cabecera del archivo
                    continue;
                }
                ProductosEntity producto = new ProductosEntity();
                if (row.getCell(0) != null) {
                    producto.setTipo(row.getCell(0).getStringCellValue());
                }

                if (row.getCell(1) != null) {
                    producto.setNombre(row.getCell(1).getStringCellValue());
                }

                if (row.getCell(2) != null) {
                    if (row.getCell(2).getCellType() == CellType.NUMERIC) {
                        producto.setValor((int) row.getCell(2).getNumericCellValue());
                    } else if (row.getCell(2).getCellType() == CellType.STRING) {
                        producto.setValor(Integer.parseInt(row.getCell(2).getStringCellValue()));
                    }
                }

                if (row.getCell(3) != null) {
                    if (row.getCell(3).getCellType() == CellType.NUMERIC) {
                        producto.setValor_final((int) row.getCell(3).getNumericCellValue());
                    } else if (row.getCell(3).getCellType() == CellType.STRING) {
                        producto.setValor_final(Integer.parseInt(row.getCell(3).getStringCellValue()));
                    }
                }

                if (row.getCell(4) != null) {
                    if (row.getCell(4).getCellType() == CellType.NUMERIC) {
                        producto.setCantidad((int) row.getCell(4).getNumericCellValue());
                    } else if (row.getCell(4).getCellType() == CellType.STRING) {
                        producto.setCantidad(Integer.parseInt(row.getCell(4).getStringCellValue()));
                    }
                }
                producto.setImagen(null);
                producto.setTipoImagen(null);
                productos.add(producto);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Generated
    public void saveAll(List<ProductosEntity> productos) {
        for (ProductosEntity producto : productos) {
            try {
                productosRepository.save(producto);
            } catch (Exception e) {
                System.err.println("Error al guardar el producto: " + producto.getNombre() );
                e.printStackTrace();
                // Puedes decidir continuar con el siguiente cliente o manejar de otra forma
            }
        }
    }

}