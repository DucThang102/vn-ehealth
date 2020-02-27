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

import vn.ehealth.emr.model.EmrPhauThuatThuThuat;
import vn.ehealth.emr.service.EmrHoSoBenhAnService;
import vn.ehealth.emr.service.EmrPhauThuatThuThuatService;
import vn.ehealth.emr.utils.EmrUtils;
import vn.ehealth.emr.validate.JsonParser;

@RestController
@RequestMapping("/api/pttt")
public class EmrPhauThuatThuThuatController {
    
    private Logger logger = LoggerFactory.getLogger(EmrPhauThuatThuThuatController.class);
            
    @Autowired private EmrPhauThuatThuThuatService emrPhauThuatThuThuatService;
    @Autowired private EmrHoSoBenhAnService emrHoSoBenhAnService;
    
    private ObjectMapper objectMapper = EmrUtils.createObjectMapper();
    private JsonParser jsonParser = new JsonParser();    
    
    @GetMapping("/get_ds_pttt")
    public ResponseEntity<?> getDsPhauThuatThuThuat(@RequestParam("hsba_id") String id) {
        var ptttList = emrPhauThuatThuThuatService.getByEmrHoSoBenhAnId(new ObjectId(id));
        return ResponseEntity.ok(ptttList);
    }
    
    @GetMapping("/get_ds_pttt_by_bn")
    public ResponseEntity<?> getDsPhauThuatThuThuatByBenhNhan(@RequestParam("benhnhan_id") String benhNhanId) {  	
    	var result = emrPhauThuatThuThuatService.getByEmrBenhNhanId(new ObjectId(benhNhanId));  
        return ResponseEntity.ok(result);
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
    
    @PostMapping("/save_pttt")
    public ResponseEntity<?> createOrUpdatePttt(@RequestBody String jsonSt) {
        
        try {
            var pttt = objectMapper.readValue(jsonSt, EmrPhauThuatThuThuat.class);
            pttt = emrPhauThuatThuThuatService.save(pttt);
            
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
    
    @SuppressWarnings("unchecked")
    @PostMapping("/create_or_update_pttt")
    public ResponseEntity<?> createOrUpdatePtttFromHIS(@RequestBody String jsonSt) {
        try {
            var map = jsonParser.parseJson(jsonSt);
            var matraodoiHsba = (String) map.get("matraodoiHoSo");
            var hsba = emrHoSoBenhAnService.getByMatraodoi(matraodoiHsba).orElseThrow();
            
            var ptttObjList = (List<Object>) map.get("emrPhauThuatThuThuats");
            var ptttList = ptttObjList.stream()
                                .map(obj -> objectMapper.convertValue(obj, EmrPhauThuatThuThuat.class))
                                .collect(Collectors.toList());
            
            emrPhauThuatThuThuatService.createOrUpdateFromHIS(hsba, ptttList);
            
            var result = Map.of(
                "success" , true,
                "ptttList", ptttList  
            );
            
            return ResponseEntity.ok(result);
            
        }catch(Exception e) {
            var error = Optional.ofNullable(e.getMessage()).orElse("Unknown error");
            var result = Map.of(
                "success" , false,
                "error", error 
            );
            logger.error("Error save phauthuathuthuat from HIS:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
