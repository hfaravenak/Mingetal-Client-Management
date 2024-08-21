package mingetal.MCM.proveedor.services;


import lombok.Generated;
import mingetal.MCM.proveedor.entities.ContactoEntity;
import mingetal.MCM.proveedor.entities.ProveedorEntity;
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
    ProveedorService proveedorService;
    @Autowired
    ContactoService contactoService;
    @Generated
    public ByteArrayInputStream generateExcelProveedor() throws IOException {
        String[] columns = {"ID", "Rubro", "Empresa",
                "RUT", "Nombre", "Correo", "Telefono Celular", "Telefono Fijo",
                "RUT", "Nombre", "Correo", "Telefono Celular", "Telefono Fijo",
                "RUT", "Nombre", "Correo", "Telefono Celular", "Telefono Fijo",
                "Comentario"};

        List<ProveedorEntity> entities = proveedorService.findAll();
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Font headerFont = workbook.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);

            CellStyle bodyStyle = workbook.createCellStyle();
            bodyStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            bodyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle body2Style = workbook.createCellStyle();
            body2Style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
            body2Style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle body3Style = workbook.createCellStyle();
            body3Style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            body3Style.setFillPattern(FillPatternType.SOLID_FOREGROUND);



            Sheet sheet = workbook.createSheet("Proveedor");

            // Cabecera
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Datos
            int rowNum = 1;
            for (ProveedorEntity entity : entities) {
                Row row = sheet.createRow(rowNum++);
                ContactoEntity contacto = contactoService.findByRut(entity.getId_contacto());
                ContactoEntity contacto2 = contactoService.findByRut(entity.getId_contacto2());
                ContactoEntity contacto3 = contactoService.findByRut(entity.getId_contacto3());
                row.createCell(0).setCellValue(entity.getId_proveedor());
                row.createCell(1).setCellValue(entity.getRubro());
                row.createCell(2).setCellValue(entity.getEmpresa());
                Cell cell = row.createCell(3);
                cell.setCellValue(contacto.getRut());
                cell.setCellStyle(bodyStyle);
                cell = row.createCell(4);
                cell.setCellValue(contacto.getNombre());
                cell.setCellStyle(bodyStyle);
                cell = row.createCell(5);
                cell.setCellValue(contacto.getCorreo());
                cell.setCellStyle(bodyStyle);
                cell = row.createCell(6);
                cell.setCellValue(contacto.getFono_cel());
                cell.setCellStyle(bodyStyle);
                cell = row.createCell(7);
                cell.setCellValue(contacto.getFono_fijo());
                cell.setCellStyle(bodyStyle);
                cell = row.createCell(8);
                cell.setCellValue(contacto.getRut());
                cell.setCellStyle(body2Style);
                cell = row.createCell(9);
                cell.setCellValue(contacto.getNombre());
                cell.setCellStyle(body2Style);
                cell = row.createCell(10);
                cell.setCellValue(contacto.getCorreo());
                cell.setCellStyle(body2Style);
                cell = row.createCell(11);
                cell.setCellValue(contacto.getFono_cel());
                cell.setCellStyle(body2Style);
                cell = row.createCell(12);
                cell.setCellValue(contacto.getFono_fijo());
                cell.setCellStyle(body2Style);
                cell = row.createCell(13);
                cell.setCellValue(contacto.getRut());
                cell.setCellStyle(body3Style);
                cell = row.createCell(14);
                cell.setCellValue(contacto.getNombre());
                cell.setCellStyle(body3Style);
                cell = row.createCell(15);
                cell.setCellValue(contacto.getCorreo());
                cell.setCellStyle(body3Style);
                cell = row.createCell(16);
                cell.setCellValue(contacto.getFono_cel());
                cell.setCellStyle(body3Style);
                cell = row.createCell(17);
                cell.setCellValue(contacto.getFono_fijo());
                cell.setCellStyle(body3Style);
                row.createCell(18).setCellValue(entity.getComentario());
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);


            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
