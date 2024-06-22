package mingetal.MCM.productos.service;


import mingetal.MCM.productos.entity.ProductosEntity;
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
    ProductosService productosService;

    public ByteArrayInputStream generateExcelCliente() throws IOException {
        String[] columns = {"ID", "Tipo", "Nombre", "Valor", "Valor Final", "Cantidad"};

        List<ProductosEntity> entities = productosService.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Productos");

            // Cabecera
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Datos
            int rowNum = 1;
            for (ProductosEntity entity : entities) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getId());
                row.createCell(1).setCellValue(entity.getTipo());
                row.createCell(2).setCellValue(entity.getNombre());
                row.createCell(3).setCellValue(entity.getValor());
                row.createCell(4).setCellValue(entity.getValor_final());
                row.createCell(5).setCellValue(entity.getCantidad());
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);


            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
