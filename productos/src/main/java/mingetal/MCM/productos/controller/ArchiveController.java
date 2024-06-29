package mingetal.MCM.productos.controller;


import mingetal.MCM.productos.service.ArchiveService;
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
@RequestMapping("/productos/archive/")
public class ArchiveController {
    @Autowired
    private ArchiveService archiveService;

    @GetMapping("/download/excel")
    public ResponseEntity<InputStreamResource> downloadExcel() throws IOException {
        // Aquí obtén la lista de entidades desde tu base de datos o cualquier otra fuente

        ByteArrayInputStream in = archiveService.generateExcelProductos();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Productos.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }

}
