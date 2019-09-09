package vn.ehealth.service.hsba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/hsba")
public class HsbaController {
	
	static Logger logger = LoggerFactory.getLogger(HsbaController.class);
		
	@Autowired HsbaService hsbaService;
	

	@GetMapping("/count_ds_hs")
	public int countHsba(@RequestParam int trangthai, @RequestParam("ma_yte")String maYte) {
		return hsbaService.countDsHoso(trangthai, maYte);		
	}
	
	@GetMapping("/get_ds_hs")
	public ResponseEntity<?> getDsHsbaChuaxuly(@RequestParam int trangthai ,
												@RequestParam("ma_yte")String maYte,
												@RequestParam int start, 
												@RequestParam int count) {
		
		var result = hsbaService.getDsHoso(trangthai, maYte, start, count);
		
		return ResponseEntity.ok(result);
	}	
}
