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

import vn.ehealth.emr.model.EmrChanDoanHinhAnh;
import vn.ehealth.emr.service.EmrChanDoanHinhAnhService;
import vn.ehealth.emr.service.EmrHoSoBenhAnService;
import vn.ehealth.emr.utils.EmrUtils;
import vn.ehealth.emr.validate.JsonParser;

@RestController
@RequestMapping("/api/cdha")
public class EmrChanDoanHinhAnhController {
    
    private JsonParser jsonParser = new JsonParser();
    private ObjectMapper objectMapper = EmrUtils.createObjectMapper();
    
    private Logger logger = LoggerFactory.getLogger(EmrChanDoanHinhAnhController.class);
    
    @Autowired private EmrChanDoanHinhAnhService emrChanDoanHinhAnhService;
    @Autowired private EmrHoSoBenhAnService emrHoSoBenhAnService;
    
    @GetMapping("/get_ds_cdha")
    public ResponseEntity<?> getDsChanDoanHinhAnh(@RequestParam("hsba_id") String hsbaId) {
        var cdhaList = emrChanDoanHinhAnhService.getByEmrHoSoBenhAnId(new ObjectId(hsbaId));
        return ResponseEntity.ok(cdhaList);
    }
    
    @GetMapping("/get_ds_cdha_by_bn")  
    public ResponseEntity<?> getDsChanDoanHinhAnhByBenhNhan(@RequestParam("benhnhan_id") String benhNhanId) {
        return ResponseEntity.ok(emrChanDoanHinhAnhService.getByEmrBenhNhanId(new ObjectId(benhNhanId)));
    }
    
    @GetMapping("/delete_cdha")
    public ResponseEntity<?> deleteCdha(@RequestParam("cdha_id") String id) {
        try {
            emrChanDoanHinhAnhService.delete(new ObjectId(id));
            var result = Map.of("success" , true);
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            logger.error("Error delete cdha:", e);
            var result = Map.of("success" , false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/save_cdha")
    public ResponseEntity<?> saveCdha(@RequestBody String jsonSt) {
        
        try {
            var cdha = objectMapper.readValue(jsonSt, EmrChanDoanHinhAnh.class);
            cdha = emrChanDoanHinhAnhService.save(cdha);
            
            var result = Map.of(
                "success" , true,
                "emrChanDoanHinhAnh", cdha 
            );
                    
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
            logger.error("Error save cdha:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }   
    
    @SuppressWarnings("unchecked")
    @PostMapping("/create_or_update_cdha")
    public ResponseEntity<?> createOrUpdateCdhaFromHIS(@RequestBody String jsonSt) {
        try {
            var map = jsonParser.parseJson(jsonSt);
            var matraodoiHsba = (String) map.get("matraodoiHoSo");
            var hsba = emrHoSoBenhAnService.getByMatraodoi(matraodoiHsba).orElseThrow();
            
            var cdhaObjList = (List<Object>) map.get("emrChanDoanHinhAnhs");
            var cdhaList = cdhaObjList.stream()
                                .map(obj -> objectMapper.convertValue(obj, EmrChanDoanHinhAnh.class))
                                .collect(Collectors.toList());
            
            emrChanDoanHinhAnhService.createOrUpdateFromHIS(hsba, cdhaList);
            
            var result = Map.of(
                "success" , true,
                "cdhaList", cdhaList  
            );
            
            return ResponseEntity.ok(result);
            
        }catch(Exception e) {
            var error = Optional.ofNullable(e.getMessage()).orElse("Unknown error");
            var result = Map.of(
                "success" , false,
                "error", error 
            );
            logger.error("Error save chandoanhinhanh from HIS:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
