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

import vn.ehealth.emr.model.EmrGiaiPhauBenh;
import vn.ehealth.emr.service.EmrGiaiPhauBenhService;
import vn.ehealth.emr.utils.EmrUtils;

@RestController
@RequestMapping("/api/gpb")
public class EmrGiaiPhauBenhController {
    
    private Logger logger = LoggerFactory.getLogger(EmrGiaiPhauBenhController.class);
    @Autowired 
    private EmrGiaiPhauBenhService emrGiaiPhauBenhService;
    
    private ObjectMapper objectMapper = EmrUtils.createObjectMapper();
    
    @GetMapping("/get_ds_gpb")
    public ResponseEntity<?> getDsGiaiPhauBenh(@RequestParam("hsba_id") String id) {
        var gpbList = emrGiaiPhauBenhService.getByEmrHoSoBenhAnId(new ObjectId(id));
        return ResponseEntity.ok(gpbList);
    }
    
    @GetMapping("/get_ds_gpb_by_bn")
    public ResponseEntity<?> getDsGiaiPhauBenhByBenhNhan(@RequestParam("benhnhan_id") String benhNhanId) {
        var gpbList = emrGiaiPhauBenhService.getByEmrHoSoBenhAnId(new ObjectId(benhNhanId));
        return ResponseEntity.ok(gpbList);
    }
    
    @GetMapping("/delete_gpb")
    public ResponseEntity<?> deleteGpb(@RequestParam("gpb_id") String id) {
        try {
            emrGiaiPhauBenhService.delete(new ObjectId(id));
            var result = Map.of("success" , true);
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            logger.error("Error delete gbp:", e);
            var result = Map.of("success" , false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/save_gpb")
    public ResponseEntity<?> saveGpb(@RequestBody String jsonSt) {
        
        try {
            var gbp = objectMapper.readValue(jsonSt, EmrGiaiPhauBenh.class);
            gbp = emrGiaiPhauBenhService.save(gbp);
            
            var result = Map.of(
                "success" , true,
                "emrChanDoanHinhAnh", gbp 
            );
                    
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
            logger.error("Error save gbp:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
