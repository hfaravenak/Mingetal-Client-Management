package mingetal.MCM.cliente.controllers;

import mingetal.MCM.cliente.services.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/cliente/archive")
public class ArchiveController {
    @Autowired
    private ArchiveService archiveService;

    @GetMapping("/download/excel")
    public ResponseEntity<InputStreamResource> downloadExcel() throws IOException {
        // Aquí obtén la lista de entidades desde tu base de datos o cualquier otra fuente

        ByteArrayInputStream in = archiveService.generateExcelCliente();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Cliente.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }

}
