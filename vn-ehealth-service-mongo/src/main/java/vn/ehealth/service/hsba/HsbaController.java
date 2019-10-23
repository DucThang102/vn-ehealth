package vn.ehealth.service.hsba;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.ehealth.emr.EmrBenhNhan;
import vn.ehealth.emr.EmrHoSoBenhAn;
import vn.ehealth.emr.service.EmrBenhNhanService;
import vn.ehealth.emr.service.EmrHoSoBenhAnService;
import vn.ehealth.validate.ErrorMessage;
import vn.ehealth.validate.JsonParser;


@RestController
@RequestMapping("/api/hsba")
public class HsbaController {
	
	private static Logger logger = LoggerFactory.getLogger(HsbaController.class);
	
	private JsonParser jsonParser = new JsonParser();
	
	private static String hsbaSchema = "";
	private static String benhNhanSchema = "";
	
	static {
	    try {
            hsbaSchema = new String(new ClassPathResource("static/json/hsba_schema.json").getInputStream().readAllBytes());
        } catch (IOException e) {
            logger.error("Cannot read hsba schema", e);
        }
	    
	    try {
	        benhNhanSchema = new String(new ClassPathResource("static/json/benhnhan_schema.json").getInputStream().readAllBytes());
        } catch (IOException e) {
            logger.error("Cannot read benhnhan schema", e);
        }
	}
	
	@Autowired EmrHoSoBenhAnService emrHoSoBenhAnService;
	
	@Autowired EmrBenhNhanService emrBenhNhanService;
	
	@GetMapping("/test")
	public String test() {
	    return "";	    
	}

	@GetMapping("/count_ds_hs")
	public int countHsba(@RequestParam int trangthai, @RequestParam("ma_yte")String maYte) {
		return emrHoSoBenhAnService.countByTrangThaiAndIsLatest(trangthai, true);		
	}
	
	@GetMapping("/get_ds_hs")
	public ResponseEntity<?> getDsHsba(@RequestParam int trangthai ,
												@RequestParam("ma_yte")String maYte,
												@RequestParam int start, 
												@RequestParam int count) {
		
		
	    var lst = emrHoSoBenhAnService.findByTrangThaiAndIsLatest(trangthai, true);
	    var result = new ArrayList<EmrHoSoBenhAn>();// rawHsbaService.getDsHoSo(trangthai, maYte, start, count);
	    for(int i = 0; i < count; i++) {
	        if(i + start < lst.size()) {
	            result.add(lst.get(i + start));
	        }
	    }
		
		return ResponseEntity.ok(result);
	}
	
	//@GetMapping("/get_hsba/{id}")
    //public ResponseEntity<?> getHs(@PathVariable("id") String id) {
	    
	@GetMapping("/get_hs")
	public ResponseEntity<?> getHs(@RequestParam("hoso_id") String id) {
        
        var result = emrHoSoBenhAnService.getById(new ObjectId(id));
        
        return ResponseEntity.of(result);
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
            System.out.println(benhNhan.iddinhdanhchinh);
            
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
	
	@PostMapping("/create_hsba")
	public ResponseEntity<?> createHsba(@RequestBody String jsonSt) {
	    
	    var errors = new ArrayList<ErrorMessage>();
	    var objMap = jsonParser.parseJson(jsonSt, hsbaSchema, errors);
	    
	    if(errors.size() > 0) {
	        var result = Map.of(
	            "success" , false,
	            "errors", errors 
	        );
	        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	    }
	    
	    try {
	        var mapper = new ObjectMapper();
	        var hsba = mapper.convertValue(objMap, EmrHoSoBenhAn.class);
    	    hsba = emrHoSoBenhAnService.save(hsba);
    	    emrHoSoBenhAnService.setAsLatest(hsba);
    	    System.out.println(hsba.mayte);
    	    
    	    var result = Map.of(
	            "success" , true,
                "emrHoSoBenhAn", hsba 
            );
    	            
    	    return ResponseEntity.ok(result);
	    } catch(Exception e) {
	        var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
	        logger.error("Error save hsba:", e);
	        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	    }
	}
}
