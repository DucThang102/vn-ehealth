package vn.ehealth.emr.controller;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.ehealth.emr.model.EmrChamSoc;
import vn.ehealth.emr.service.EmrChamSocService;
import vn.ehealth.emr.utils.EmrUtils;

@RestController
@RequestMapping("/api/chamsoc")
public class EmrChamSocController {
    
    private Logger logger = LoggerFactory.getLogger(EmrChamSocController.class);
    
    @Autowired 
    private EmrChamSocService emrChamSocService;
    
    private ObjectMapper objectMapper = EmrUtils.createObjectMapper();
    
    @GetMapping("/get_ds_chamsoc")
    public ResponseEntity<?> getDsChamSoc(@RequestParam("hsba_id") String id) {
    
        return ResponseEntity.ok(emrChamSocService.getByEmrHoSoBenhAnId(new ObjectId(id)));
    }
    
    @GetMapping("/get_ds_chamsoc_by_bn")
    public ResponseEntity<?> getDsChamSocByBenhNhan(@RequestParam("benhnhan_id") String benhNhanId) {
    
        return ResponseEntity.ok(emrChamSocService.getByEmrBenhNhanId(new ObjectId(benhNhanId)));
    }
    
    @GetMapping("/delete_chamsoc")
    public ResponseEntity<?> deleteChamSoc(@RequestParam("chamsoc_id") String id) {
        try {
            emrChamSocService.delete(new ObjectId(id));
            var result = Map.of("success" , true);
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            logger.error("Error delete chamsoc:", e);
            var result = Map.of("success" , false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/save_chamsoc")
    public ResponseEntity<?> save(@RequestBody String jsonSt) {
        
        try {
            var chamsoc = objectMapper.readValue(jsonSt, EmrChamSoc.class);
            chamsoc = emrChamSocService.save(chamsoc);
            
            var result = Map.of(
                "success" , true,
                "emrChamSoc", chamsoc 
            );
                    
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
            logger.error("Error save chamsoc:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
