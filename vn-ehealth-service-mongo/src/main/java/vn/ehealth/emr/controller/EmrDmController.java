package vn.ehealth.emr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.ehealth.emr.service.EmrDmService;

@RestController
@RequestMapping("/api/danhmuc")
public class EmrDmController {
    
    //private Logger logger = LoggerFactory.getLogger(EmrDmController.class);
    
    @Autowired EmrDmService emrDmService;

    @GetMapping("/count_dm_list")
    public long countDmList(@RequestParam("dm_type") String dmType, 
                            @RequestParam String name,
                            @RequestParam int level,
                            @RequestParam String parentCode) {
        return emrDmService.countEmrDm(dmType, name, level, parentCode);
    }
    
    @GetMapping("/get_dm_list")
    public ResponseEntity<?> getDmList(@RequestParam("dm_type") String dmType, 
                                        @RequestParam String name,
                                        @RequestParam int level,
                                        @RequestParam String parentCode,
                                        @RequestParam int start, 
                                        @RequestParam int count) {
        var lst = emrDmService.getEmrDm(dmType, name, level, parentCode, start, count);
        return ResponseEntity.ok(lst);
    }
}
