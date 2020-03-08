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

import vn.ehealth.emr.model.EmrThamDoChucNang;
import vn.ehealth.emr.service.EmrHoSoBenhAnService;
import vn.ehealth.emr.service.EmrThamDoChucNangService;
import vn.ehealth.emr.utils.EmrUtils;
import vn.ehealth.emr.validate.JsonParser;

@RestController
@RequestMapping("/api/tdcn")
public class EmrThamDoChucNangController {
    
    private Logger logger = LoggerFactory.getLogger(EmrThamDoChucNangController.class);
    
    @Autowired 
    private EmrThamDoChucNangService emrThamDoChucNangService;
    @Autowired EmrHoSoBenhAnService emrHoSoBenhAnService;
    
    private JsonParser jsonParser = new JsonParser();
    private ObjectMapper objectMapper = EmrUtils.createObjectMapper();
    
    @GetMapping("/get_ds_tdcn")
    public ResponseEntity<?> getDsThamDoChucNang(@RequestParam("hsba_id") String id) {
        var tdcnList = emrThamDoChucNangService.getByEmrHoSoBenhAnId(new ObjectId(id));
        return ResponseEntity.ok(tdcnList);
    }
    
    @GetMapping("/delete_tdcn")
    public ResponseEntity<?> deleteTdcn(@RequestParam("tdcn_id") String id) {
        try {
            emrThamDoChucNangService.delete(new ObjectId(id));
            var result = Map.of("success" , true);
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            logger.error("Error delete tdcn:", e);
            var result = Map.of("success" , false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/save_tdcn")
    public ResponseEntity<?> saveTdcn(@RequestBody String jsonSt) {
        
        try {
            var tdcn = objectMapper.readValue(jsonSt, EmrThamDoChucNang.class);
            tdcn = emrThamDoChucNangService.save(tdcn);
            
            var result = Map.of(
                "success" , true,
                "emrChanDoanHinhAnh", tdcn 
            );
                    
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
            logger.error("Error save tdcn:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
    @SuppressWarnings("unchecked")
    @PostMapping("/create_or_update_tdcn")
    public ResponseEntity<?> createOrUpdateTdcnFromHIS(@RequestBody String jsonSt) {
        try {
            var map = jsonParser.parseJson(jsonSt);
            var matraodoiHsba = (String) map.get("matraodoiHoSo");
            var hsba = emrHoSoBenhAnService.getByMatraodoi(matraodoiHsba).orElseThrow();
            
            var tdcnObjList = (List<Object>) map.get("emrThamDoChucNangs");
            var tdcnList = tdcnObjList.stream()
                                .map(obj -> objectMapper.convertValue(obj, EmrThamDoChucNang.class))
                                .collect(Collectors.toList());
            
            emrThamDoChucNangService.createOrUpdateFromHIS(hsba, tdcnList);
            
            var result = Map.of(
                "success" , true,
                "tdcnList", tdcnList  
            );
            
            return ResponseEntity.ok(result);
            
        }catch(Exception e) {
            var error = Optional.ofNullable(e.getMessage()).orElse("Unknown error");
            var result = Map.of(
                "success" , false,
                "error", error 
            );
            logger.error("Error save thamdochucnang from HIS:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
