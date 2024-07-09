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
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
        List<ListaProductosOCClienteEntity> response = restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/cliente/productos/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ListaProductosOCClienteEntity>>() {
                }
        ).getBody();
        if (response == null) {
            return new ArrayList<>();
        }

        List<ProductosEntity> productosEntities = new ArrayList<>();

        for (ListaProductosOCClienteEntity listaProductosOCClienteEntity : response) {
            productosEntities.add(findById(listaProductosOCClienteEntity.getId_producto()));
        }

        return productosEntities;
    }
    @Generated
    @Transactional(readOnly = true)
    public List<ProductosEntity> findByOCProveedor(int id) {
        List<ListaProductosOCProveedorEntity> response = restTemplate.exchange(
                "http://localhost:8080/ordenes_de_compra/proveedor/productos/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ListaProductosOCProveedorEntity>>() {
                }
        ).getBody();

        if (response == null) {
            return new ArrayList<>();
        }

        List<ProductosEntity> productosEntities = new ArrayList<>();

        for (ListaProductosOCProveedorEntity listaProductosOCProveedorEntity : response) {
            productosEntities.add(findById(listaProductosOCProveedorEntity.getId_producto()));
        }

        return productosEntities;
    }

    @Generated
    @Transactional(readOnly = true)
    public List<ProductosEntity> findByCotizacion(int id) {
        System.out.println("id: " + id);
        List<ListaProductosCotizacionEntity> response = restTemplate.exchange(
                "http://localhost:8080/cliente/cotizacion/productos/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ListaProductosCotizacionEntity>>() {
                }
        ).getBody();

        if (response == null) {
            return new ArrayList<>();
        }

        List<ProductosEntity> productosEntities = new ArrayList<>();

        for (ListaProductosCotizacionEntity listaProductosCotizacionEntity : response) {
            productosEntities.add(findById(listaProductosCotizacionEntity.getId_producto()));
        }

        return productosEntities;
    }

    @Transactional(readOnly = true)
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
        System.out.println("update service: " + producto);
        if (findById(producto.getId()) == null) {
            return null;
        }
        return productosRepository.save(producto);
    }

    //-------------------- Carga masiva -----------------------
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

                System.out.println("........................");
                System.out.println(row.getCell(0));
                System.out.println(row.getCell(1));
                System.out.println(row.getCell(2));
                System.out.println(row.getCell(3));
                System.out.println(row.getCell(4));
                System.out.println("........................");
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