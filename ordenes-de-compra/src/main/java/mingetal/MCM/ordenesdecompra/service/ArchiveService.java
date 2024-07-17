package mingetal.MCM.ordenesdecompra.service;

import lombok.Generated;
import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.ListaProductosOCProveedorEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraClienteEntity;
import mingetal.MCM.ordenesdecompra.entity.OrdenesDeCompraProveedorEntity;
import mingetal.MCM.ordenesdecompra.model.ClienteEntity;
import mingetal.MCM.ordenesdecompra.model.ProductosEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.xml.stream.Space;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

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
    @Autowired
    private HttpServletRequest request;

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
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Font headerFont = workbook.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);

            Font totalFont = workbook.createFont();
            totalFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle blueStyle = workbook.createCellStyle();
            blueStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            blueStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            blueStyle.setFont(totalFont);

            CellStyle greenStyle = workbook.createCellStyle();
            greenStyle.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
            greenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            greenStyle.setFont(totalFont);

            CellStyle redStyle = workbook.createCellStyle();
            redStyle.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
            redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            redStyle.setFont(totalFont);

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
            for (OrdenesDeCompraClienteEntity entity : entities) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getId());
                row.createCell(1).setCellValue(entity.getId_cliente());
                row.createCell(2).setCellValue(entity.getFecha_solicitud().toString());
                Cell cell = row.createCell(3);
                cell.setCellValue(entity.getEstado_pago());
                if(entity.getEstado_pago().equals("Pagado")){
                    cell.setCellStyle(greenStyle);
                }
                else{
                    cell.setCellStyle(redStyle);
                }
                row.createCell(4).setCellValue(entity.getValor_pago());
                row.createCell(5).setCellValue(entity.getFecha_pago().toString());
                row.createCell(6).setCellValue(entity.getModo_pago());
                row.createCell(7).setCellValue(entity.getFecha_inicio_pago().toString());
                row.createCell(8).setCellValue(entity.getTiempo_de_pago());
                row.createCell(9).setCellValue(entity.getNumero_cheque());
                row.createCell(10).setCellValue(entity.getFecha_entrega().toString());
                cell = row.createCell(11);
                cell.setCellValue(entity.getEstado_entrega());
                if(entity.getEstado_entrega().equals("Entregado")){
                    cell.setCellStyle(greenStyle);
                }
                else{
                    cell.setCellStyle(redStyle);
                }
                row.createCell(12).setCellValue(entity.getNumero_factura());
                cell = row.createCell(13);
                cell.setCellValue(entity.getEstado_factura());
                if(entity.getEstado_factura().equals("Emitida")){
                    cell.setCellStyle(greenStyle);
                }
                else{
                    cell.setCellStyle(redStyle);
                }
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
                cell.setCellStyle(headerStyle);
            }

            // Datos
            rowNum = 1;
            for (OrdenesDeCompraProveedorEntity entity : entities2) {
                Row row = sheet2.createRow(rowNum++);
                row.createCell(0).setCellValue(entity.getId());
                row.createCell(1).setCellValue(entity.getId_proveedor());
                row.createCell(2).setCellValue(entity.getFecha_solicitud().toString());
                Cell cell = row.createCell(3);
                cell.setCellValue(entity.getEstado_pago());
                if(entity.getEstado_pago().equals("Pagado")){
                    cell.setCellStyle(greenStyle);
                }
                else{
                    cell.setCellStyle(redStyle);
                }
                row.createCell(4).setCellValue(entity.getValor_pago());
                row.createCell(5).setCellValue(entity.getFecha_pago().toString());
                row.createCell(6).setCellValue(entity.getFecha_entrega().toString());
                cell = row.createCell(7);
                cell.setCellValue(entity.getEstado_entrega());
                if(entity.getEstado_entrega().equals("Entregado")){
                    cell.setCellStyle(greenStyle);
                }
                else{
                    cell.setCellStyle(redStyle);
                }
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
    public ByteArrayInputStream generateExcelVentas(@RequestHeader("Authorization") String authHeader) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entityHeader = new HttpEntity(headers);
        String[] columns = {"ID", "Empresa", "Venta Neta", "Ganancia", "Fecha", "Productos"};

        List<OrdenesDeCompraClienteEntity> OCClientes = ordenesDeCompraClienteService.findPagado();
        ResponseEntity<List<ClienteEntity>> responseClientes = restTemplate.exchange(
                "http://localhost:8080/cliente/",
                HttpMethod.GET,
                entityHeader,
                new ParameterizedTypeReference<List<ClienteEntity>>() {}
        );
        List<ClienteEntity> Clientes = responseClientes.getBody();
        assert Clientes != null;
        ResponseEntity<List<ProductosEntity>> responseProductos = restTemplate.exchange(
                "http://localhost:8080/productos/",
                HttpMethod.GET,
                entityHeader,
                new ParameterizedTypeReference<List<ProductosEntity>>() {}
        );

        List<ProductosEntity> Productos = responseProductos.getBody();
        assert Productos != null;

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Font headerFont = workbook.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);

            Sheet sheet = workbook.createSheet("Ventas");

            // Cabecera
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
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

    @Generated
    public ByteArrayInputStream generateExcelEstadistica() throws IOException {
        String[] columns = {"", "Año", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre", "Total"};

        List<Object[]> VentarMonth = ordenesDeCompraClienteService.getSalesByYearAndMonth();

        Map<Integer, Map<Integer, List<Integer>>> datosOrganizados = new TreeMap<>(Collections.reverseOrder());

        for (Object[] elemento : VentarMonth) {
            int valor = ((Number) elemento[0]).intValue();
            int cantidad = ((Number) elemento[1]).intValue();
            int mes = ((Number) elemento[2]).intValue();
            int anio = ((Number) elemento[3]).intValue();

            // Si el año no está en el mapa, agregarlo con 12 meses iniciados en 0
            datosOrganizados.putIfAbsent(anio, new HashMap<>());
            for (int i = 1; i <= 12; i++) {
                datosOrganizados.get(anio).putIfAbsent(i, Arrays.asList(0, 0));
            }

            // Registrar el valor y la cantidad en el mes correspondiente
            datosOrganizados.get(anio).put(mes, Arrays.asList(valor, cantidad));
        }

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Estadistica");

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
            dataStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle SpaceStyle = workbook.createCellStyle();
            SpaceStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            SpaceStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Cabecera
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Datos
            int rowNum = 1;
            for (Map.Entry<Integer, Map<Integer, List<Integer>>> anioEntry : datosOrganizados.entrySet()) {
                Row row = sheet.createRow(rowNum++);
                int anio = anioEntry.getKey();
                int pos = 0;

                Cell cell = row.createCell(pos++);
                cell.setCellValue("Cantidades");
                cell.setCellStyle(headerStyle);

                cell = row.createCell(pos++);
                cell.setCellValue(anio);
                cell.setCellStyle(dataStyle);

                int cantTotal = 0;
                for (Map.Entry<Integer, List<Integer>> mesEntry : anioEntry.getValue().entrySet()) {
                    Integer valor = mesEntry.getValue().get(1);
                    cell = row.createCell(pos++);
                    cell.setCellValue(valor);
                    cell.setCellStyle(dataStyle);
                    cantTotal += valor;
                }
                cell = row.createCell(pos);
                cell.setCellValue(cantTotal);
                cell.setCellStyle(dataStyle);

                row = sheet.createRow(rowNum++);
                pos = 0;

                cell = row.createCell(pos++);
                cell.setCellValue("Valores");
                cell.setCellStyle(headerStyle);

                cell = row.createCell(pos++);
                cell.setCellValue(anio);
                cell.setCellStyle(dataStyle);

                int valorTotal = 0;
                for (Map.Entry<Integer, List<Integer>> mesEntry : anioEntry.getValue().entrySet()) {
                    Integer valor = mesEntry.getValue().get(0);
                    cell = row.createCell(pos++);
                    cell.setCellValue(valor);
                    cell.setCellStyle(dataStyle);
                    valorTotal += valor;
                }
                cell = row.createCell(pos);
                cell.setCellValue(valorTotal);
                cell.setCellStyle(dataStyle);

                pos=0;
                row = sheet.createRow(rowNum++);
                for(int i=0;i<=14;i++){
                    cell = row.createCell(pos++);
                    cell.setCellStyle(SpaceStyle);
                }
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Generated
    public ByteArrayInputStream generateExcelProductosEstadisticas() throws IOException {
        // Obtener el encabezado Authorization
        String authHeader = request.getHeader("Authorization");

        // Crear y configurar los encabezados HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Definir columnas para el Excel
        String[] columns = {"Producto", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre", "Total"};

        // Obtener productos del microservicio de productos
        List<ProductosEntity> productos = restTemplate.exchange(
                "http://localhost:8080/productos/",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProductosEntity>>() {}
        ).getBody();

        assert productos != null;

        // Obtener ventas de productos por año y mes
        List<Object[]> ventaProductos = ordenesDeCompraClienteService.getProductByYearAndMonth();

        // Crear conjunto de IDs únicos de productos
        Set<Integer> idsUnicos = new HashSet<>();
        for (ProductosEntity producto : productos) {
            idsUnicos.add(producto.getId());
        }

        // Organizar datos por año, mes e ID de producto
        Map<Integer, Map<Integer, Map<Integer, Integer>>> datosOrganizados = new TreeMap<>(Collections.reverseOrder());
        for (Object[] elemento : ventaProductos) {
            int id = ((Number) elemento[0]).intValue();
            int cantidad = ((Number) elemento[1]).intValue();
            int mes = ((Number) elemento[2]).intValue();
            int anio = ((Number) elemento[3]).intValue();

            if (idsUnicos.contains(id)) {
                datosOrganizados.putIfAbsent(anio, new HashMap<>());
                datosOrganizados.get(anio).putIfAbsent(id, new HashMap<>());
                datosOrganizados.get(anio).get(id).put(mes, cantidad);
            }
        }

        for (int anio : datosOrganizados.keySet()) {
            for (int id : idsUnicos) {
                datosOrganizados.get(anio).putIfAbsent(id, new HashMap<>());
                for (int mes = 1; mes <= 12; mes++) {
                    datosOrganizados.get(anio).get(id).putIfAbsent(mes, 0);
                }
            }
        }

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            for (Map.Entry<Integer, Map<Integer, Map<Integer, Integer>>> anioEntry : datosOrganizados.entrySet()) {
                int anio = anioEntry.getKey();
                Sheet sheet = workbook.createSheet(String.valueOf(anio));

                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                CellStyle nameStyle = workbook.createCellStyle();
                nameStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
                nameStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                CellStyle dataStyle = workbook.createCellStyle();
                dataStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
                dataStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                CellStyle spaceStyle = workbook.createCellStyle();
                spaceStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
                spaceStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                Font totalFont = workbook.createFont();
                totalFont.setColor(IndexedColors.WHITE.getIndex());
                spaceStyle.setFont(totalFont);

                // Cabecera
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < columns.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                    cell.setCellStyle(headerStyle);
                }

                int[] total = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                int rowNum = 1;
                for (Map.Entry<Integer, Map<Integer, Integer>> idEntry : anioEntry.getValue().entrySet()) {
                    int id = idEntry.getKey();
                    Row row = sheet.createRow(rowNum++);
                    Cell cell = row.createCell(0);
                    cell.setCellValue(productos.get(id - 1).getNombre());
                    cell.setCellStyle(nameStyle);
                    int totalProducto = 0;
                    for (Map.Entry<Integer, Integer> mesEntry : idEntry.getValue().entrySet()) {
                        int mes = mesEntry.getKey();
                        int cantidad = mesEntry.getValue();
                        totalProducto += cantidad;
                        total[mes - 1] += cantidad;
                        cell = row.createCell(mes);
                        cell.setCellValue(cantidad);
                        cell.setCellStyle(dataStyle);
                    }
                    cell = row.createCell(13);
                    cell.setCellValue(totalProducto);
                    cell.setCellStyle(spaceStyle);
                }

                int num = 0;
                Row row = sheet.createRow(rowNum);
                Cell cell = row.createCell(num++);
                cell.setCellStyle(spaceStyle);
                for (int valor : total) {
                    cell = row.createCell(num++);
                    cell.setCellValue(valor);
                    cell.setCellStyle(spaceStyle);
                }

                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
