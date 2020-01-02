package vn.ehealth.emr.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
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
                            @RequestParam String keyword,
                            @RequestParam int level,
                            @RequestParam String parentCode) {
        return emrDmService.countEmrDm(dmType, keyword, level, parentCode);
    }
    
    @GetMapping("/get_dm_list")
    public ResponseEntity<?> getDmList(@RequestParam("dm_type") String dmType, 
                                        @RequestParam Optional<String> keyword,
                                        @RequestParam Optional<Integer> level,
                                        @RequestParam Optional<String> parentCode,
                                        @RequestParam Optional<Integer> start, 
                                        @RequestParam Optional<Integer> count) {
        var lst = emrDmService.getEmrDmList(dmType, keyword, level, parentCode, start, count);
        return ResponseEntity.ok(lst);
    }
    
    @GetMapping("/get_all_dm_list")
    public ResponseEntity<?> getAllDmList(@RequestParam("dm_type") String dmType) {
        var lst = emrDmService.getAllEmrDm(dmType);
        return ResponseEntity.ok(lst);
    }
    
    @GetMapping("/export_dm_list")
    public ResponseEntity<?> export(@RequestParam("dm_type") String dmType) {
        var builder = new StringBuilder("STT,Mã,Tên\n");
        var lst = emrDmService.getAllEmrDm(dmType);
        for(int i = 0; i < lst.size(); i++) {
            var item = lst.get(i);
            builder.append((i+1)).append(",")
                    .append(item.ma).append(",")
                    .append(item.ten).append("\n");
        }
        
        var data = builder.toString().getBytes();
        return ResponseEntity.ok()
                .contentLength(data.length)
                .contentType(MediaType.parseMediaType("text/csv"))
                .header("Content-disposition", "attachment; filename=" + dmType + ".csv")
                .body(new ByteArrayResource(data));
        
    }    
}
