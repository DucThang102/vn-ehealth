package vn.ehealth.emr.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.jasperreports.engine.JRException;
import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.service.EmrBenhNhanService;
import vn.ehealth.emr.service.EmrCoSoKhamBenhService;
import vn.ehealth.emr.service.EmrHoSoBenhAnService;
import vn.ehealth.emr.service.EmrVaoKhoaService;
import vn.ehealth.emr.utils.ExportUtil;
import vn.ehealth.emr.utils.JasperUtils;
import vn.ehealth.emr.validate.ErrorMessage;
import vn.ehealth.emr.validate.JsonParser;

import java.io.*;

@RestController
@RequestMapping("/api/hsba")
public class EmrHoSoBenhAnController {
    
    private static Logger logger = LoggerFactory.getLogger(EmrHoSoBenhAnController.class);
    
    private JsonParser jsonParser = new JsonParser();
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    private static String hsbaSchema = "";
    
    
    static {
        try {
            hsbaSchema = new String(new ClassPathResource("static/json/hsba_schema.json").getInputStream().readAllBytes());
        } catch (IOException e) {
            logger.error("Cannot read hsba schema", e);
        }
    }
    
    @Autowired EmrHoSoBenhAnService emrHoSoBenhAnService;    
    @Autowired EmrBenhNhanService emrBenhNhanService;
    @Autowired EmrCoSoKhamBenhService emrCoSoKhamBenhService;
    @Autowired EmrVaoKhoaService emrVaoKhoaService;
        
    JasperUtils jasperUtils = new JasperUtils();

    @GetMapping("/count_ds_hs")
    public long countHsba(@RequestParam int trangthai, @RequestParam String mayte) {
        return emrHoSoBenhAnService.countHoSo(trangthai, mayte);
    }
    
    @GetMapping("/get_ds_hs")
    public ResponseEntity<?> getDsHsba(@RequestParam int trangthai ,
                                                @RequestParam String mayte,
                                                @RequestParam int start, 
                                                @RequestParam int count) {
        
        
        var result =  emrHoSoBenhAnService.getDsHoSo(trangthai, mayte, start, count);
        
        result.forEach(x -> {
            if(x.emrCoSoKhamBenhId != null) {
                x.emrCoSoKhamBenh = emrCoSoKhamBenhService.getById(x.emrCoSoKhamBenhId).orElse(null);
            }
            
            if(x.emrBenhNhanId != null) {
                x.emrBenhNhan = emrBenhNhanService.getById(x.emrBenhNhanId).orElse(null);
            }
            
            if(x.emrBenhNhan != null) {
                x.emrBenhNhan.tuoi = jasperUtils.getTuoi(x);
            }
            
            var emrVaoKhoas = emrVaoKhoaService.getByEmrHoSoBenhAnId(x.id, false);
            
            if(emrVaoKhoas.size() > 0) {
                var emrKhoaRaVien = emrVaoKhoas.get(emrVaoKhoas.size() - 1);
                x.khoaRaVien = emrKhoaRaVien.tenkhoa;
                if(StringUtils.isEmpty(x.khoaRaVien) && emrKhoaRaVien.emrDmKhoaDieuTri != null) {
                    x.khoaRaVien = emrKhoaRaVien.emrDmKhoaDieuTri.ten;
                }
            }
            
        });
        
        return ResponseEntity.ok(result);
    }       
   
    
    @GetMapping("/get_hsba_by_ma")
    public ResponseEntity<?> getHsbaByMa(@RequestParam("mayte") String mayte) {
        
        var hsba = emrHoSoBenhAnService.getByMayte(mayte);
        
        hsba.ifPresent(x -> {
            if(x.emrBenhNhan != null) {
                x.emrBenhNhan.tuoi = jasperUtils.getTuoi(x);
            }
        });
        
        return ResponseEntity.of(hsba);
    }
    
    @GetMapping("/get_hsba_by_id")
    public ResponseEntity<?> getHsbaById(@RequestParam("hsba_id") String id) {
        
        var hsba = emrHoSoBenhAnService.getById(new ObjectId(id), false);
        
        hsba.ifPresent(x -> {
            if(x.emrBenhNhan != null) {
                x.emrBenhNhan.tuoi = jasperUtils.getTuoi(x);
            }
        });
        
        return ResponseEntity.of(hsba);
    }
    
    @GetMapping("/view_pdf")
    public ResponseEntity<?> viewPdf(@RequestParam("hsba_id") String id) {
        
        var hsba = emrHoSoBenhAnService.getById(new ObjectId(id), true);
        
        if(hsba.isPresent()) {
            try {
                var data = ExportUtil.exportPdf(hsba.get(), "");
                var resource = new ByteArrayResource(data);
                
                return ResponseEntity.ok()
                        .contentLength(data.length)
                        .contentType(MediaType.parseMediaType("application/pdf"))
                        .body(resource);
            }catch(JRException | IOException  | NullPointerException e) {
                logger.error("Error exporting pdf :", e);
            }
        }
        
        return ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/update_hsba")
    public ResponseEntity<?> updateHsba(@RequestBody String jsonSt) {
        var errors = new ArrayList<ErrorMessage>();
        var objMap = jsonParser.parseJson(jsonSt, hsbaSchema, errors);
        
        if(errors.size() > 0) {
            var result = Map.of(
                "success" , false,
                "errors", errors 
            );
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        try {
            var mapper = new ObjectMapper();
            var hsba = mapper.convertValue(objMap, EmrHoSoBenhAn.class);
            hsba.isLatest = true;
            hsba = emrHoSoBenhAnService.save2(hsba);            
            
            var result = Map.of(
                "success" , true,
                "emrHoSoBenhAn", hsba 
            );
                        
            return ResponseEntity.ok(result);            
        }catch(Exception e) {
            var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
            logger.error("Error save hsba:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @PostMapping("/create_or_update_hsba")
    public ResponseEntity<?> createOrUpdateHsba(@RequestBody String jsonSt) {
        
        try {
            FileOutputStream fo = new FileOutputStream(String.valueOf(System.currentTimeMillis()) + ".json");
            fo.write(jsonSt.getBytes());
        } catch (IOException e) {
            logger.error("Cannot save json file ", e);
        }
        
        
        var errors = new ArrayList<ErrorMessage>();
        var objMap = jsonParser.parseJson(jsonSt, hsbaSchema, errors);
        
        if(errors.size() > 0) {
            var result = Map.of(
                "success" , false,
                "errors", errors 
            );
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        
        try {
            var mapper = new ObjectMapper();            
            var hsba = mapper.convertValue(objMap, EmrHoSoBenhAn.class);
            emrHoSoBenhAnService.save(hsba);
            emrHoSoBenhAnService.setAsLatest(hsba);
                        
            mapper.setDateFormat(sdf);
            
            return ResponseEntity.ok(mapper.writeValueAsString(objMap));
        } catch(Exception e) {
            var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
            logger.error("Error save hsba:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}