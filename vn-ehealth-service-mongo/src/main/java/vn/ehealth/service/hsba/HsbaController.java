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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import vn.ehealth.emr.EmrHoSoBenhAn;
import vn.ehealth.emr.service.EmrHoSoBenhAnService;
import vn.ehealth.validate.ErrorMessage;
import vn.ehealth.validate.JsonParser;


@RestController
@RequestMapping("/api/hsba")
public class HsbaController {
	
	private static Logger logger = LoggerFactory.getLogger(HsbaController.class);
	
	private JsonParser jsonParser = new JsonParser();
	
	private static String hsbaSchema = "";
	
	static {
	    try {
            hsbaSchema = new String(new ClassPathResource("static/json/hsba_schema.json").getInputStream().readAllBytes());
        } catch (IOException e) {
            logger.error("Cannot read hsba schema", e);
        }
	}
	
	@Autowired EmrHoSoBenhAnService emrHoSoBenhAnService;
	
	@GetMapping("/test")
	public String test() {
	    return "";	    
	}

	@GetMapping("/count_ds_hs")
	public int countHsba(@RequestParam int trangthai, @RequestParam("ma_yte")String maYte) {
		return 0;//rawHsbaService.countDsHoSo(trangthai, maYte);		
	}
	
	@GetMapping("/get_ds_hs")
	public ResponseEntity<?> getDsHsbaChuaxuly(@RequestParam int trangthai ,
												@RequestParam("ma_yte")String maYte,
												@RequestParam int start, 
												@RequestParam int count) {
		
		var result = new ArrayList<EmrHoSoBenhAn>();// rawHsbaService.getDsHoSo(trangthai, maYte, start, count);
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get_hsba/{id}")
    public ResponseEntity<?> getHs(@PathVariable("id") String id) {
        
        var result = emrHoSoBenhAnService.getEmrHoSoBenhAnById(new ObjectId(id));
        
        return ResponseEntity.of(result);
    }
	
	
	@GetMapping("/view_pdf")
	public ResponseEntity<?> viewPdf(@RequestParam("idhsba") int idhsba,
	                                @RequestParam(value="loai_report", required=false, defaultValue="") 
	                                String loaiReport) {
	    
	    /*
	    var coSoKhamBenh = hsbaService.getCoSoKhamBenh();	    	    
	    var dsHsba = hsbaService.getEmrHoSoBenhAnById(idhsba);
	    
	    System.out.println(coSoKhamBenh.ten);
	    
	    if(dsHsba.isPresent()) {
	        try {
	            var data = ExportUtil.exportPdf(dsHsba.get(), coSoKhamBenh, loaiReport, "");
	            var resource = new ByteArrayResource(data);
	            
	            return ResponseEntity.ok()
	                    .contentLength(data.length)
	                    .contentType(MediaType.parseMediaType("application/pdf"))
	                    .body(resource);
	        }catch(JRException | IOException  | NullPointerException e) {
	            logger.error("Error exporting pdf :", e);
	        }	        
	    }*/
	    
	    
	    return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/add_hoso")
	public ResponseEntity<?> addHoSo(@RequestBody String jsonSt) {
	    
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
    	    System.out.println(hsba.mayte);
    	    
    	    var result = Map.of(
                    "success" , true,
                    "hsba", hsba 
            );
    	            
    	    return ResponseEntity.ok(result);
	    }catch(Exception e) {
	        var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
	        logger.error("Error save hsba:", e);
	        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	    }
	}
}
