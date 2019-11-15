package vn.ehealth.emr.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.ehealth.emr.service.EmrVaoKhoaService;

@RestController
@RequestMapping("/api/vaokhoa")
public class EmrVaoKhoaController {

    @Autowired EmrVaoKhoaService emrVaoKhoaService;
    
    @GetMapping("/get_ds_vaokhoa")
    public ResponseEntity<?> getDsVaoKhoa(@RequestParam("hsba_id") String id, @RequestParam boolean detail) {
        var vkList = emrVaoKhoaService.getByEmrHoSoBenhAnId(new ObjectId(id), detail);
        return ResponseEntity.ok(vkList);
    }
}
