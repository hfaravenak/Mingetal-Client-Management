package mingetal.MCM.proveedor.services;


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

    public ByteArrayInputStream generateExcelCliente() throws IOException {
        String[] columns = {"ID", "Rubro", "Empresa",
                "RUT", "Nombre", "Correo", "Telefono Celular", "Telefono Fijo",
                "RUT", "Nombre", "Correo", "Telefono Celular", "Telefono Fijo",
                "RUT", "Nombre", "Correo", "Telefono Celular", "Telefono Fijo",
                "Comentario"};

        List<ProveedorEntity> entities = proveedorService.findAll();
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Proveedor");

            // Cabecera
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
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
                row.createCell(3).setCellValue(contacto.getRut());
                row.createCell(4).setCellValue(contacto.getNombre());
                row.createCell(5).setCellValue(contacto.getCorreo());
                row.createCell(6).setCellValue(contacto.getFono_cel());
                row.createCell(7).setCellValue(contacto.getFono_fijo());
                row.createCell(8).setCellValue(contacto2.getRut());
                row.createCell(9).setCellValue(contacto2.getNombre());
                row.createCell(10).setCellValue(contacto2.getCorreo());
                row.createCell(11).setCellValue(contacto2.getFono_cel());
                row.createCell(12).setCellValue(contacto2.getFono_fijo());
                row.createCell(13).setCellValue(contacto3.getRut());
                row.createCell(14).setCellValue(contacto3.getNombre());
                row.createCell(15).setCellValue(contacto3.getCorreo());
                row.createCell(16).setCellValue(contacto3.getFono_cel());
                row.createCell(17).setCellValue(contacto3.getFono_fijo());
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
