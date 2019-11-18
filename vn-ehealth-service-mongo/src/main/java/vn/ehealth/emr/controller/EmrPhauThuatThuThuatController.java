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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.ehealth.emr.model.EmrPhauThuatThuThuat;
import vn.ehealth.emr.service.EmrPhauThuatThuThuatService;

@RestController
@RequestMapping("/api/pttt")
public class EmrPhauThuatThuThuatController {
    
    private Logger logger = LoggerFactory.getLogger(EmrPhauThuatThuThuatController.class);
            
    @Autowired EmrPhauThuatThuThuatService emrPhauThuatThuThuatService;
    
    @GetMapping("/get_ds_pttt")
    public ResponseEntity<?> getDsPhauThuatThuThuat(@RequestParam("hsba_id") String id) {
        var ptttList = emrPhauThuatThuThuatService.getByEmrHoSoBenhAnId(new ObjectId(id));
        return ResponseEntity.ok(ptttList);
    }

    @GetMapping("/delete_pttt")
    public ResponseEntity<?> deletePttt(@RequestParam("pttt_id") String id) {
        try {
            emrPhauThuatThuThuatService.delete(new ObjectId(id));
            var result = Map.of("success" , true);
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            logger.error("Error delete pttt:", e);
            var result = Map.of("success" , false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/create_or_update_pttt")
    public ResponseEntity<?> createOrUpdatePttt(@RequestBody String jsonSt) {
        
        try {
            var mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            var pttt = mapper.readValue(jsonSt, EmrPhauThuatThuThuat.class);
            pttt = emrPhauThuatThuThuatService.createOrUpdate(pttt);
            
            var result = Map.of(
                "success" , true,
                "emrPhauThuatThuThuat", pttt 
            );
                    
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
            logger.error("Error save pttt:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
