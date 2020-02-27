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

import vn.ehealth.emr.model.EmrDonThuoc;
import vn.ehealth.emr.service.EmrDonThuocService;
import vn.ehealth.emr.utils.EmrUtils;

@RestController
@RequestMapping("/api/donthuoc")
public class EmrDonThuocController {
    
    private Logger logger = LoggerFactory.getLogger(EmrDonThuocController.class);
    
    @Autowired 
    private EmrDonThuocService emrDonThuocService;
    
    private ObjectMapper objectMapper = EmrUtils.createObjectMapper();
    
    @GetMapping("/get_ds_donthuoc")
    public ResponseEntity<?> getDsDonThuoc(@RequestParam("hsba_id") String id) {
        var donthuocList = emrDonThuocService.getByEmrHoSoBenhAnId(new ObjectId(id));
        return ResponseEntity.ok(donthuocList);
    }
    
    @GetMapping("/get_ds_donthuoc_by_bn")
    public ResponseEntity<?> getDsDonThuocByBenhNhan(@RequestParam("benhnhan_id") String benhNhanId) {
        var donthuocList = emrDonThuocService.getByEmrBenhNhanId(new ObjectId(benhNhanId));
        return ResponseEntity.ok(donthuocList);
    }
    
    @GetMapping("/delete_donthuoc")
    public ResponseEntity<?> deleteDonthuoc(@RequestParam("donthuoc_id") String id) {
        try {
            emrDonThuocService.delete(new ObjectId(id));
            var result = Map.of("success" , true);
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            logger.error("Error delete donthuoc:", e);
            var result = Map.of("success" , false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/save_donthuoc")
    public ResponseEntity<?> saveDonthuoc(@RequestBody String jsonSt) {
        
        try {
            var donthuoc = objectMapper.readValue(jsonSt, EmrDonThuoc.class);
            donthuoc = emrDonThuocService.save(donthuoc);
            
            var result = Map.of(
                "success" , true,
                "emrDonThuoc", donthuoc 
            );
                    
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
            logger.error("Error save donthuoc:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

}
