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

import vn.ehealth.emr.model.EmrXetNghiem;
import vn.ehealth.emr.service.EmrXetNghiemService;
import vn.ehealth.emr.utils.EmrUtils;

@RestController
@RequestMapping("/api/xetnghiem")
public class EmrXetNghiemController {

    private Logger logger = LoggerFactory.getLogger(EmrXetNghiemController.class);
    @Autowired EmrXetNghiemService emrXetNghiemService;
    
    @GetMapping("/get_ds_xetnghiem")
    public ResponseEntity<?> getDsXetNghiem(@RequestParam("hsba_id") String id) {
        var xetnghiemList = emrXetNghiemService.getByEmrHoSoBenhAnId(new ObjectId(id));
        return ResponseEntity.ok(xetnghiemList);
    }
    
    @GetMapping("/delete_xetnghiem")
    public ResponseEntity<?> deleteXetnghiem(@RequestParam("xetnghiem_id") String id) {
        try {
            emrXetNghiemService.delete(new ObjectId(id));
            var result = Map.of("success" , true);
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            logger.error("Error delete xetnghiem:", e);
            var result = Map.of("success" , false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/create_or_update_xetnghiem")
    public ResponseEntity<?> createOrUpdateXetnghiem(@RequestBody String jsonSt) {
        
        try {
            var mapper = EmrUtils.createObjectMapper();
            var xetnghiem = mapper.readValue(jsonSt, EmrXetNghiem.class);
            xetnghiem = emrXetNghiemService.createOrUpdate(xetnghiem);
            
            var result = Map.of(
                "success" , true,
                "emrXetNghiem", xetnghiem 
            );
                    
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
            logger.error("Error save xetnghiem:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
