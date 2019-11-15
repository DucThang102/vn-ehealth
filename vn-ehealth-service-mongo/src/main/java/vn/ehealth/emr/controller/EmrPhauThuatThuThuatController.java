package vn.ehealth.emr.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.ehealth.emr.service.EmrPhauThuatThuThuatService;

@RestController
@RequestMapping("/api/pttt")
public class EmrPhauThuatThuThuatController {
    
    @Autowired EmrPhauThuatThuThuatService emrPhauThuatThuThuatService;
    
    @GetMapping("/get_ds_pttt")
    public ResponseEntity<?> getDsPhauThuatThuThuat(@RequestParam("hsba_id") String id) {
        var ptttList = emrPhauThuatThuThuatService.getByEmrHoSoBenhAnId(new ObjectId(id));
        return ResponseEntity.ok(ptttList);
    }

}
