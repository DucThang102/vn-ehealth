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

import vn.ehealth.emr.model.EmrHinhAnhTonThuong;
import vn.ehealth.emr.service.EmrHinhAnhTonThuongService;
import vn.ehealth.emr.utils.EmrUtils;

@RestController
@RequestMapping("/api/hatt")
public class EmrHinhAnhTonThuongController {
    
    private Logger logger = LoggerFactory.getLogger(EmrHinhAnhTonThuongController.class);
    
    @Autowired 
    private EmrHinhAnhTonThuongService emrHinhAnhTonThuongService;
    
    private ObjectMapper objectMapper = EmrUtils.createObjectMapper();

    @GetMapping("/get_hatt")
    public ResponseEntity<?> getHatt(@RequestParam("hatt_id") String id) {
        var hatt = emrHinhAnhTonThuongService.getById(new ObjectId(id));
        return ResponseEntity.of(hatt);
    }
    
    @GetMapping("/get_ds_hatt")
    public ResponseEntity<?> getDsHatt(@RequestParam("hsba_id") String hsbaId) {
        var hattList = emrHinhAnhTonThuongService.getByEmrHoSoBenhAnId(new ObjectId(hsbaId));
        return ResponseEntity.ok(hattList);
    }
    
    @GetMapping("/get_ds_hatt_by_bn")
    public ResponseEntity<?> getDsHattByBenhNhan(@RequestParam("benhnhan_id") String benhNhanId) {
        var hattList = emrHinhAnhTonThuongService.getByEmrBenhNhanId(new ObjectId(benhNhanId));
        return ResponseEntity.ok(hattList);
    }
    
    @GetMapping("/delete_hatt")
    public ResponseEntity<?> deleteHatt(@RequestParam("hatt_id") String id) {
        try {
            emrHinhAnhTonThuongService.delete(new ObjectId(id));
            var result = Map.of("success" , true);
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            logger.error("Error delete hatt:", e);
            var result = Map.of("success" , false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/save_hatt")
    public ResponseEntity<?> saveHatt(@RequestBody String jsonSt) {
        
        try {
            var hatt = objectMapper.readValue(jsonSt, EmrHinhAnhTonThuong.class);
            hatt = emrHinhAnhTonThuongService.save(hatt);
            
            var result = Map.of(
                "success" , true,
                "emrHinhAnhTonThuong", hatt 
            );
                    
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
            logger.error("Error save hatt:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
