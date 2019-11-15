package vn.ehealth.emr.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.ehealth.emr.service.EmrThamDoChucNangService;

@RestController
@RequestMapping("/api/tdcn")
public class EmrThamDoChucNangController {
    
    @Autowired EmrThamDoChucNangService emrThamDoChucNangService;
    
    @GetMapping("/get_ds_tdcn")
    public ResponseEntity<?> getDsThamDoChucNang(@RequestParam("hsba_id") String id) {
        var tdcnList = emrThamDoChucNangService.getByEmrHoSoBenhAnId(new ObjectId(id));
        return ResponseEntity.ok(tdcnList);
    }

}
