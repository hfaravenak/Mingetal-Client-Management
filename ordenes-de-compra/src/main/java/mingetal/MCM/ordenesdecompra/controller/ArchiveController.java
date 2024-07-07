package mingetal.MCM.ordenesdecompra.controller;

import mingetal.MCM.ordenesdecompra.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/ordenes_de_compra/archive/")
public class ArchiveController {
    @Autowired
    private ArchiveService archiveService;

    @GetMapping("/download/excel")
    public ResponseEntity<InputStreamResource> downloadExcel() throws IOException {
        // Aquí obtén la lista de entidades desde tu base de datos o cualquier otra fuente

        ByteArrayInputStream in = archiveService.generateExcelOC();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Ordenes de Compra.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }

    @GetMapping("/download/excel/ventas")
    public ResponseEntity<InputStreamResource> downloadExcelVentas() throws IOException {
        // Aquí obtén la lista de entidades desde tu base de datos o cualquier otra fuente

        ByteArrayInputStream in = archiveService.generateExcelVentas();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Ventas.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }

    @GetMapping("/download/excel/estadistica")
    public ResponseEntity<InputStreamResource> downloadExcelEstadistica() throws IOException {
        // Aquí obtén la lista de entidades desde tu base de datos o cualquier otra fuente

        ByteArrayInputStream in = archiveService.generateExcelEstadistica();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Estadisticas.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }

    @GetMapping("/download/excel/productos/estadistica")
    public ResponseEntity<InputStreamResource> downloadExcelProductosEstadistica() throws IOException {
        // Aquí obtén la lista de entidades desde tu base de datos o cualquier otra fuente

        ByteArrayInputStream in = archiveService.generateExcelProductosEstadisticas();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Estadistica Productos.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }
}
