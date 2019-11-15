package vn.ehealth.emr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.ehealth.emr.model.EmrBenhNhan;
import vn.ehealth.emr.service.EmrBenhNhanService;
import vn.ehealth.emr.validate.ErrorMessage;
import vn.ehealth.emr.validate.JsonParser;

@RestController
@RequestMapping("/api/benh_nhan")
public class EmrBenhNhanController {
    
    private static Logger logger = LoggerFactory.getLogger(EmrBenhNhanController.class);
    private static String benhNhanSchema = "";
    private JsonParser jsonParser = new JsonParser();
    
    @Autowired EmrBenhNhanService emrBenhNhanService;
    
    static {
        
        try {
            benhNhanSchema = new String(new ClassPathResource("static/json/benhnhan_schema.json").getInputStream().readAllBytes());
        } catch (IOException e) {
            logger.error("Cannot read benhnhan schema", e);
        }
    }

    @PostMapping("/create_or_update_benhnhan")
    public ResponseEntity<?> createOrUpdateBenhNhan(@RequestBody String jsonSt) {
        var errors = new ArrayList<ErrorMessage>();
        var objMap = jsonParser.parseJson(jsonSt, benhNhanSchema, errors);
        
        if(errors.size() > 0) {
            var result = Map.of(
                "success" , false,
                "errors", errors 
            );
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        
        try {
            var mapper = new ObjectMapper();
            var benhNhan = mapper.convertValue(objMap, EmrBenhNhan.class);
            if(StringUtils.isEmpty(benhNhan.iddinhdanhchinh)) {
                benhNhan.iddinhdanhchinh = benhNhan.idhis;
            }
            
            if(StringUtils.isEmpty(benhNhan.iddinhdanhchinh)) {
                throw new RuntimeException("Empty iddinhdanhchinh");
            }
            
            benhNhan = emrBenhNhanService.createOrUpdate(benhNhan);
            
            var result = Map.of(
                "success" , true,
                "emrBenhNhan", benhNhan 
            );
                    
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
            logger.error("Error create/update benhnhan:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }        
    }
}
