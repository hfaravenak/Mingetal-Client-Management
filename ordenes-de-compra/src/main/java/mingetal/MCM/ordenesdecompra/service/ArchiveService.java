package mingetal.MCM.ordenesdecompra.service;

import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ProductosEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ByteArrayInputStream generateExcelCliente() throws IOException {
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
                List<ListaProductosOCClienteEntity> listaProductosCotizacionEntities = listaProductosOCClienteService.findByIdOCCliente(entity.getId());
                StringBuilder productos = new StringBuilder();
                for(ListaProductosOCClienteEntity list : listaProductosCotizacionEntities){
                    productos.append(list.getCantidad());
                    ProductosEntity p = listaProductosOCClienteService.findProductoByIdProducto(list.getId_producto());
                    if(p != null){
                        productos.append(" ");
                        productos.append(p.getNombre());
                    }
                    productos.append("; ");
                }
                row.createCell(15).setCellValue(productos.toString());
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
                StringBuilder productos = new StringBuilder();
                for(ListaProductosOCProveedorEntity list : listaProductosOCProveedorEntities){
                    productos.append(list.getCantidad());
                    ProductosEntity p = listaProductosOCProveedorService.findProductoByIdProducto(list.getId_producto());
                    if(p != null){
                        productos.append(" ");
                        productos.append(p.getNombre());
                    }
                    productos.append("; ");
                }
                row.createCell(9).setCellValue(productos.toString());
            }

            for (int i = 0; i < columns2.length; i++) {
                sheet2.autoSizeColumn(i);
            }

            workbook.write(out);


            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
