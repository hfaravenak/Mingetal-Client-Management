package mingetal.MCM.cliente.services;

import lombok.Generated;
import mingetal.MCM.cliente.entities.ClienteEntity;
import mingetal.MCM.cliente.entities.CotizacionEntity;
import mingetal.MCM.cliente.repositories.CotizacionRepository;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CotizacionService {
    @Autowired
    CotizacionRepository cotizacionRepository;

    @Autowired
    ListaProductosCotizacionService listaProductosCotizacionService;

    //-------------------- Guardado --------------------

    public CotizacionEntity save(CotizacionEntity cotizacionEntity) {
        if(findById(cotizacionEntity.getIdCotizacion())==null){
            return cotizacionRepository.save(cotizacionEntity);
        }
        return null;
    }

    //-------------------- Buscar --------------------

    public List<CotizacionEntity> findAll() {
        return cotizacionRepository.findAll();
    }
    public CotizacionEntity findById(int id) { return cotizacionRepository.findById(id).orElse(null); }
    public List<CotizacionEntity> findByPedido(String pedido) { return cotizacionRepository.findByPedido(pedido); }
    public List<CotizacionEntity> findByFecha(LocalDate fecha) { return cotizacionRepository.findByFecha(fecha); }
    public List<CotizacionEntity> findByEstado(String estado) { return cotizacionRepository.findByEstado(estado); }
    public List<CotizacionEntity> findByRutCliente(String rutCliente) { return cotizacionRepository.findByRutCliente(rutCliente); }

    //-------------------- Eliminar --------------------

    public CotizacionEntity delete(int id) {
        CotizacionEntity cotizacionEntity = findById(id);
        cotizacionRepository.delete(cotizacionEntity);
        return cotizacionEntity;
    }

    //-------------------- Editar --------------------

    public CotizacionEntity update(CotizacionEntity cotizacionEntity) {
        CotizacionEntity cotizacionEntity1 = findById(cotizacionEntity.getIdCotizacion());
        cotizacionEntity1.setPedido(cotizacionEntity.getPedido());
        cotizacionEntity1.setFecha(cotizacionEntity.getFecha());
        cotizacionEntity1.setEstado(cotizacionEntity.getEstado());
        cotizacionEntity1.setRutCliente(cotizacionEntity.getRutCliente());
        return cotizacionRepository.save(cotizacionEntity1);
    }

    //-------------------- Carga masiva -----------------------
    @Generated
    public void readExcelFile(MultipartFile file) {
        List<CotizacionEntity> cotizaciones = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            boolean skipHeader = true;
            for (Row row : sheet) {
                if (skipHeader) {
                    skipHeader = false; // Saltar la cabecera del archivo
                    continue;
                }

                if (row.getCell(0) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }
                CotizacionEntity cotizacion = new CotizacionEntity();
                cotizacion.setPedido(row.getCell(0).getStringCellValue());
                if (row.getCell(1) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }
                cotizacion.setRutCliente(row.getCell(1).getStringCellValue());

                if (row.getCell(2) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }
                Date excelDate = row.getCell(2).getDateCellValue();
                LocalDate date = excelDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                cotizacion.setFecha(date);

                if (row.getCell(3) == null) {
                    break; // Dejar de leer el archivo si la primera celda es nula
                }
                cotizacion.setEstado(row.getCell(3).getStringCellValue());

                try {
                    cotizacionRepository.save(cotizacion);
                } catch (Exception e) {
                    System.err.println("Error al guardar el producto: " + cotizacion.getPedido() );
                    e.printStackTrace();
                    // Puedes decidir continuar con el siguiente cliente o manejar de otra forma
                }

                //---------------- guardar listado de productos ---------------
                int last_id = cotizacionRepository.findAll().size();

                int valor  =  0;

                if (row.getCell(4) != null) {
                    if (row.getCell(4).getCellType() == CellType.NUMERIC) {
                        valor = (int) row.getCell(4).getNumericCellValue();
                    } else if (row.getCell(4).getCellType() == CellType.STRING) {
                        valor = Integer.parseInt(row.getCell(4).getStringCellValue());
                    }
                }
                String productos = row.getCell(5).getStringCellValue();
                listaProductosCotizacionService.cargaMasivaDatos(last_id, productos);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
