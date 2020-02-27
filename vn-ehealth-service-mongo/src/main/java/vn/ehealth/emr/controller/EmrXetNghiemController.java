package vn.ehealth.emr.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

import vn.ehealth.emr.model.EmrXetNghiem;
import vn.ehealth.emr.service.EmrHoSoBenhAnService;
import vn.ehealth.emr.service.EmrXetNghiemService;
import vn.ehealth.emr.utils.EmrUtils;
import vn.ehealth.emr.validate.JsonParser;

@RestController
@RequestMapping("/api/xetnghiem")
public class EmrXetNghiemController {
    
    private Logger logger = LoggerFactory.getLogger(EmrXetNghiemController.class);
    
    @Autowired private EmrXetNghiemService emrXetNghiemService;
    
    @Autowired private EmrHoSoBenhAnService emrHoSoBenhAnService;

    private JsonParser jsonParser = new JsonParser();
    
    private ObjectMapper objectMapper = EmrUtils.createObjectMapper();
    
    @GetMapping("/get_ds_xetnghiem")
    public ResponseEntity<?> getDsXetNghiem(@RequestParam("hsba_id") String id) {
        var xetnghiemList = emrXetNghiemService.getByEmrHoSoBenhAnId(new ObjectId(id));
        return ResponseEntity.ok(xetnghiemList);
    }
  
    @GetMapping("/get_ds_xetnghiem_by_bn")
    public ResponseEntity<?> getDsXetNghiemByBenhNhan(@RequestParam("benhnhan_id") String benhNhanId) {
    	var result = emrXetNghiemService.getByEmrBenhNhanId(new ObjectId(benhNhanId));
        return ResponseEntity.ok(result);
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
    
    @PostMapping("/save_xetnghiem")
    public ResponseEntity<?> saveXetnghiem(@RequestBody String jsonSt) {
        
        try {
            var xetnghiem = objectMapper.readValue(jsonSt, EmrXetNghiem.class);
            xetnghiem = emrXetNghiemService.save(xetnghiem);
            
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
    
    @SuppressWarnings("unchecked")
    @PostMapping("/create_or_update_xetnghiem")
    public ResponseEntity<?> createOrUpdateXetnghiemFromHIS(@RequestBody String jsonSt) {
        try {
            var map = jsonParser.parseJson(jsonSt);
            var matraodoiHsba = (String) map.get("matraodoiHoSo");
            var hsba = emrHoSoBenhAnService.getByMatraodoi(matraodoiHsba).orElseThrow();
            
            var xetnghiemObjList = (List<Object>) map.get("emrXetNghiems");
            var xetnghiemList = xetnghiemObjList.stream()
                                .map(obj -> objectMapper.convertValue(obj, EmrXetNghiem.class))
                                .collect(Collectors.toList());
            
            emrXetNghiemService.createOrUpdateFromHIS(hsba, xetnghiemList);
            
            var result = Map.of(
                "success" , true,
                "xetnghiemList", xetnghiemList  
            );
            
            return ResponseEntity.ok(result);
            
        }catch(Exception e) {
            var error = Optional.ofNullable(e.getMessage()).orElse("Unknown error");
            var result = Map.of(
                "success" , false,
                "error", error 
            );
            logger.error("Error save xetnghiem from HIS:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
