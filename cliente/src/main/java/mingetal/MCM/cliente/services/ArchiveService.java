package mingetal.MCM.cliente.services;

import mingetal.MCM.cliente.entities.ClienteEntity;
import mingetal.MCM.cliente.entities.CotizacionEntity;
import mingetal.MCM.cliente.entities.ListaProductosCotizacionEntity;
import mingetal.MCM.cliente.model.ProductosEntity;
import mingetal.MCM.cliente.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArchiveService {
    @Autowired
    ClienteService clienteService;
    @Autowired
    CotizacionService cotizacionService;
    @Autowired
    ListaProductosCotizacionService listaProductosCotizacionService;

    public ByteArrayInputStream generateExcelCliente() throws IOException {
        String[] columns = {"RUT", "Nombre", "Email", "Teléfono", "Empresa"};
        String[] columns2 = {"ID", "RUT", "Pedido", "Fecha", "Estado", "Productos", "ValorTotal"};

        List<ClienteEntity> entities = clienteService.findAll();
        List<CotizacionEntity> entities2 = cotizacionService.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Font headerFont = workbook.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);

            Sheet sheet = workbook.createSheet("Cliente");

            // Cabecera
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Datos
            int rowNum = 1;
            for (ClienteEntity entity : entities) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getRut());
                row.createCell(1).setCellValue(entity.getNombre());
                row.createCell(2).setCellValue(entity.getEmail());
                row.createCell(3).setCellValue(entity.getTelefono());
                row.createCell(4).setCellValue(entity.getEmpresa());
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            Sheet sheet2 = workbook.createSheet("Cotización");

            // Cabecera
            headerRow = sheet2.createRow(0);
            for (int i = 0; i < columns2.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns2[i]);
                cell.setCellStyle(headerStyle);
            }

            // Datos
            rowNum = 1;
            for (CotizacionEntity entity : entities2) {
                Row row = sheet2.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getIdCotizacion());
                row.createCell(1).setCellValue(entity.getRutCliente());
                row.createCell(2).setCellValue(entity.getPedido());
                row.createCell(3).setCellValue(entity.getFecha().toString());
                row.createCell(4).setCellValue(entity.getEstado());
                List<ListaProductosCotizacionEntity> listaProductosCotizacionEntities = listaProductosCotizacionService.findByIdCotizacion(entity.getIdCotizacion());
                StringBuilder productos = new StringBuilder();
                int valor = 0;
                for(ListaProductosCotizacionEntity list : listaProductosCotizacionEntities){
                    valor += list.getValor_pago();
                    productos.append(list.getCantidad());
                    ProductosEntity p = listaProductosCotizacionService.findProductoByIdProducto(list.getId_producto());
                    if(p != null){
                        productos.append(" ");
                        productos.append(p.getNombre());
                    }
                    productos.append("; ");
                }
                row.createCell(5).setCellValue(productos.toString());
                row.createCell(6).setCellValue(valor);
            }
            for (int i = 0; i < columns2.length; i++) {
                sheet2.autoSizeColumn(i);
            }

            workbook.write(out);


            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
