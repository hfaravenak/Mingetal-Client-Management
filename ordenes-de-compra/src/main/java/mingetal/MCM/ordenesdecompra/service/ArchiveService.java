package mingetal.MCM.ordenesdecompra.service;

import lombok.Generated;
import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ClienteEntity;
import mingetal.MCM.ordenesdecompra.model.ProductosEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ArchiveService {
    @Autowired
    OrdenesDeCompraClienteService ordenesDeCompraClienteService;
    @Autowired
    OrdenesDeCompraProveedorService ordenesDeCompraProveedorService;
    @Autowired
    ListaProductosOCClienteService listaProductosOCClienteService;
    @Autowired
    ListaProductosOCProveedorService listaProductosOCProveedorService;
    @Autowired
    RestTemplate restTemplate;

    @Generated
    public ClienteEntity buscarCliente(List<ClienteEntity> clientes, String rut){
        return clientes.stream()
                .filter(cliente -> cliente.getRut().equals(rut))
                .findFirst().orElse(null);
    }

    @Generated
    public int ganancias(List<ProductosEntity> productosEntities, List<ListaProductosOCClienteEntity> productosOCClienteEntities, int valor_pago){
        int ganancia = 0;
        for(ListaProductosOCClienteEntity entitie : productosOCClienteEntities){
            ProductosEntity productoOpt = productosEntities.stream()
                    .filter(producto -> producto.getId() == entitie.getId_producto())
                    .findFirst().orElse(null);
            if(productoOpt!=null){
                ganancia += productoOpt.getValor() * entitie.getCantidad();
            }
        }
        return valor_pago-ganancia;
    }

    @Generated
    private String listarProductosOCClientes(List<ListaProductosOCClienteEntity> productosOCClienteEntities) {
        StringBuilder productos = new StringBuilder();
        for(ListaProductosOCClienteEntity list : productosOCClienteEntities){
            productos.append(list.getCantidad());
            ProductosEntity p = listaProductosOCClienteService.findProductoByIdProducto(list.getId_producto());
            if(p != null){
                productos.append(" ");
                productos.append(p.getNombre());
            }
            productos.append("; ");
        }
        return productos.toString();
    }

    @Generated
    private String listarProductosOCProveedores(List<ListaProductosOCProveedorEntity> productosOCProveedorEntities) {
        StringBuilder productos = new StringBuilder();
        for(ListaProductosOCProveedorEntity list : productosOCProveedorEntities){
            productos.append(list.getCantidad());
            ProductosEntity p = listaProductosOCClienteService.findProductoByIdProducto(list.getId_producto());
            if(p != null){
                productos.append(" ");
                productos.append(p.getNombre());
            }
            productos.append("; ");
        }
        return productos.toString();
    }

    @Generated
    public ByteArrayInputStream generateExcelOC() throws IOException {
        String[] columns = {"ID", "RUT", "Fecha de la Solicitud",
                "Estado Pago", "Valor", "Fecha del Pago", "Modo de Pago", "Fecha de Inicio de Pago",
                "Tiempo para pagar", "Numero del Cheque", "Fecha de la Entrega", "Estado de la entrega",
                "Numero de Factura", "Estado Factura", "Empresa de Despacho", "Productos"};
        String[] columns2 = {"ID", "RUT", "Fecha de la Solicitud",
                "Estado Pago", "Valor", "Fecha del Pago",
                "Fecha de la Entrega", "Estado de la entrega",
                "Numero de Factura", "Productos"};

        List<OrdenesDeCompraClienteEntity> entities = ordenesDeCompraClienteService.findAll();
        List<OrdenesDeCompraProveedorEntity> entities2 = ordenesDeCompraProveedorService.findAll();
        List<ListaProductosOCClienteEntity> entities3 = listaProductosOCClienteService.findAll();
        List<ListaProductosOCProveedorEntity> entities4 = listaProductosOCProveedorService.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Cliente");

            // Cabecera
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Datos
            int rowNum = 1;
            for (OrdenesDeCompraClienteEntity entity : entities) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getId());
                row.createCell(1).setCellValue(entity.getId_cliente());
                row.createCell(2).setCellValue(entity.getFecha_solicitud().toString());
                row.createCell(3).setCellValue(entity.getEstado_pago());
                row.createCell(4).setCellValue(entity.getValor_pago());
                row.createCell(5).setCellValue(entity.getFecha_pago().toString());
                row.createCell(6).setCellValue(entity.getModo_pago());
                row.createCell(7).setCellValue(entity.getFecha_inicio_pago().toString());
                row.createCell(8).setCellValue(entity.getTiempo_de_pago());
                row.createCell(9).setCellValue(entity.getNumero_cheque());
                row.createCell(10).setCellValue(entity.getFecha_entrega().toString());
                row.createCell(11).setCellValue(entity.getEstado_entrega());
                row.createCell(12).setCellValue(entity.getNumero_factura());
                row.createCell(13).setCellValue(entity.getEstado_factura());
                row.createCell(14).setCellValue(entity.getEmpresa_despacho());
                List<ListaProductosOCClienteEntity> listaProductosClientesEntities = listaProductosOCClienteService.findByIdOCCliente(entity.getId());
                row.createCell(15).setCellValue(listarProductosOCClientes(listaProductosClientesEntities));
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            Sheet sheet2 = workbook.createSheet("Proveedor");

            // Cabecera
            headerRow = sheet2.createRow(0);
            for (int i = 0; i < columns2.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns2[i]);
            }

            // Datos
            rowNum = 1;
            for (OrdenesDeCompraProveedorEntity entity : entities2) {
                Row row = sheet2.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getId());
                row.createCell(1).setCellValue(entity.getId_proveedor());
                row.createCell(2).setCellValue(entity.getFecha_solicitud().toString());
                row.createCell(3).setCellValue(entity.getEstado_pago());
                row.createCell(4).setCellValue(entity.getValor_pago());
                row.createCell(5).setCellValue(entity.getFecha_pago().toString());
                row.createCell(6).setCellValue(entity.getFecha_entrega().toString());
                row.createCell(7).setCellValue(entity.getEstado_entrega());
                row.createCell(8).setCellValue(entity.getFactura());
                List<ListaProductosOCProveedorEntity> listaProductosOCProveedorEntities = listaProductosOCProveedorService.findByIdOCProveedor(entity.getId());
                row.createCell(9).setCellValue(listarProductosOCProveedores(listaProductosOCProveedorEntities));
            }

            for (int i = 0; i < columns2.length; i++) {
                sheet2.autoSizeColumn(i);
            }

            workbook.write(out);


            return new ByteArrayInputStream(out.toByteArray());
        }
    }


    @Generated
    public ByteArrayInputStream generateExcelVentas() throws IOException {
        String[] columns = {"ID", "Empresa", "Venta Neta", "Ganancia", "Fecha", "Productos"};

        List<OrdenesDeCompraClienteEntity> OCClientes = ordenesDeCompraClienteService.findPagado();
        List<ClienteEntity> Clientes = restTemplate.exchange(
                "http://localhost:8080/cliente/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ClienteEntity>>() {}
        ).getBody();
        assert Clientes != null;
        List<ProductosEntity> Productos = restTemplate.exchange(
                "http://localhost:8080/productos/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductosEntity>>() {}
        ).getBody();

        assert Productos != null;

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Ventas");

            // Cabecera
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Datos
            int rowNum = 1;
            for (OrdenesDeCompraClienteEntity entity : OCClientes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getId());
                row.createCell(1).setCellValue(buscarCliente(Clientes, entity.getId_cliente()).getEmpresa());
                row.createCell(2).setCellValue(entity.getValor_pago());
                List<ListaProductosOCClienteEntity> productosOCClienteEntities = listaProductosOCClienteService.findByIdOCCliente(entity.getId());
                row.createCell(3).setCellValue(ganancias(Productos, productosOCClienteEntities, entity.getValor_pago()));
                row.createCell(4).setCellValue(entity.getFecha_solicitud().toString());
                row.createCell(5).setCellValue(listarProductosOCClientes(productosOCClienteEntities));
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);


            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
