package vn.ehealth.emr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import vn.ehealth.emr.service.UserService;
import vn.ehealth.emr.utils.ExportUtil;
import vn.ehealth.emr.utils.UserUtil;
import vn.ehealth.emr.validate.ErrorMessage;
import vn.ehealth.emr.validate.JsonParser;

import java.io.*;

@RestController
@RequestMapping("/api/hsba")
public class EmrHoSoBenhAnController {
    
    private static Logger logger = LoggerFactory.getLogger(EmrHoSoBenhAnController.class);
    
    private JsonParser jsonParser = new JsonParser();
    
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
    @Autowired UserService userService;

    @GetMapping("/count_ds_hs")
    public ResponseEntity<?> countHsba(@RequestParam int trangthai, @RequestParam String mayte) {
        try {
            var user = UserUtil.getCurrentUser();
            var count = emrHoSoBenhAnService.countHoSo(user.get().emrCoSoKhamBenhId, trangthai, mayte);
            return ResponseEntity.ok(count);
        }catch (Exception e) {
            logger.error("Error countHsba:", e);
            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
        }        
    }
    
    @GetMapping("/get_ds_hs")
    public ResponseEntity<?> getDsHsba(@RequestParam int trangthai ,
                                                @RequestParam String mayte,
                                                @RequestParam int start, 
                                                @RequestParam int count) {
        
        try {
            var user = UserUtil.getCurrentUser();
            var result = emrHoSoBenhAnService.getDsHoSo(user.get().emrCoSoKhamBenhId, trangthai, mayte, start, count);
            return ResponseEntity.ok(result);
            
        }catch(Exception e) {
            logger.error("Error getDsHsba:", e);
            return new ResponseEntity<>(new ArrayList<EmrHoSoBenhAn>(), HttpStatus.BAD_REQUEST);
        }        
    }
    
    @GetMapping("/get_hsba_logs")
    public ResponseEntity<?> getHsbaLogs(@RequestParam("hsba_id") String id) {        
        return ResponseEntity.ok(emrHoSoBenhAnService.getHistory(new ObjectId(id)));
    }    
    
    @GetMapping("/get_hsba_by_id")
    public ResponseEntity<?> getHsbaById(@RequestParam("hsba_id") String id) {        
        var hsba = emrHoSoBenhAnService.getById(new ObjectId(id));
        //emrHoSoBenhAnService.getEmrHoSoBenhAnDetail(hsba.get());
        return ResponseEntity.of(hsba);
    }
    
    @GetMapping("/archive_hsba")
    public ResponseEntity<?> archiveHsba(@RequestParam("hsba_id") String id) {
        
        try {
            var user = UserUtil.getCurrentUser();
            emrHoSoBenhAnService.archiveHsba(new ObjectId(id), user.get().id);
            return ResponseEntity.ok(Map.of("success", true));
        }catch(Exception e) {
            var error = Optional.ofNullable(e.getMessage()).orElse("Unknown error");
            return ResponseEntity.ok(Map.of("success", false, "error", error));
        }
    }
    
    @GetMapping("/unarchive_hsba")
    public ResponseEntity<?> unArchiveHsba(@RequestParam("hsba_id") String id) {
        try {
            var user = UserUtil.getCurrentUser();            
            emrHoSoBenhAnService.unArchiveHsba(new ObjectId(id), user.get().id);
            return ResponseEntity.ok(Map.of("success", true));
        }catch(Exception e) {
            var error = Optional.ofNullable(e.getMessage()).orElse("Unknown error");
            return ResponseEntity.ok(Map.of("success", false, "error", error));
        }
    }
    
    @GetMapping("/delete_hsba")
    public ResponseEntity<?> deleteHsba(@RequestParam("hsba_id") String id) {
        try {
            var user = UserUtil.getCurrentUser();
            emrHoSoBenhAnService.deleteHsba(new ObjectId(id), user.get().id);
            return ResponseEntity.ok(Map.of("success", true));
        }catch(Exception e) {
            var error = Optional.ofNullable(e.getMessage()).orElse("Unknown error");
            return ResponseEntity.ok(Map.of("success", false, "error", error));
        }
    }
     
    @GetMapping("/view_pdf")
    public ResponseEntity<?> viewPdf(@RequestParam("hsba_id") String id) {
        
        var hsba = emrHoSoBenhAnService.getById(new ObjectId(id));
        
        if(hsba.isPresent()) {
            try {
                emrHoSoBenhAnService.getEmrHoSoBenhAnDetail(hsba.get());
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
            var user = UserUtil.getCurrentUser();
            var mapper = new ObjectMapper();
            var hsba = mapper.convertValue(objMap, EmrHoSoBenhAn.class);
            hsba = emrHoSoBenhAnService.update(hsba, user.get().id);            
            
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
    public ResponseEntity<?> createOrUpdateHsbaHIS(@RequestBody String jsonSt) {        
        
        var errors = new ArrayList<ErrorMessage>();
        var objMap = jsonParser.parseJson(jsonSt, hsbaSchema, errors);
        objMap.get("emrChanDoan");
        
        if(errors.size() > 0) {
            var result = Map.of(
                "success" , false,
                "errors", errors 
            );
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        
        try {
            var user = UserUtil.getCurrentUser();
            var userId = user.map(x -> x.id).orElse(null);
            var mapper = new ObjectMapper();            
            var hsba = mapper.convertValue(objMap, EmrHoSoBenhAn.class);
            hsba.jsonSt = jsonSt;
            emrHoSoBenhAnService.createOrUpdateHIS(hsba, userId);
                        
            var result = Map.of(
                "success" , true,
                "emrHoSoBenhAn", hsba  
            );
            
            return ResponseEntity.ok(result);
        
        } catch(Exception e) {
            var error = Optional.ofNullable(e.getMessage()).orElse("Unknown error");
            var result = Map.of(
                "success" , false,
                "errors", List.of(error) 
            );
            logger.error("Error save hsba:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}