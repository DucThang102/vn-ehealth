package vn.ehealth.service.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.ehealth.emr.service.EmrGiaiPhauBenhService;

@RestController
@RequestMapping("/api/gpb")
public class EmrGiaiPhauBenhController {
    
    @Autowired EmrGiaiPhauBenhService emrGiaiPhauBenhService;
    
    @GetMapping("/get_ds_gpb")
    public ResponseEntity<?> getDsGiaiPhauBenh(@RequestParam("hsba_id") String id) {
        var gpbList = emrGiaiPhauBenhService.getByEmrHoSoBenhAnId(new ObjectId(id));
        return ResponseEntity.ok(gpbList);
    }
}
