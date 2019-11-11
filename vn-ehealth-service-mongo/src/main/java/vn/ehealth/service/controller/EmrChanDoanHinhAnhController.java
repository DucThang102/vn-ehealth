package vn.ehealth.service.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.ehealth.emr.service.EmrChanDoanHinhAnhService;

@RestController
@RequestMapping("/api/cdha")
public class EmrChanDoanHinhAnhController {
    
    @Autowired EmrChanDoanHinhAnhService emrChanDoanHinhAnhService;
    
    @GetMapping("/get_ds_cdha")
    public ResponseEntity<?> getDsChanDoanHinhAnh(@RequestParam("hsba_id") String id) {
        var cdhaList = emrChanDoanHinhAnhService.getByEmrHoSoBenhAnId(new ObjectId(id));
        return ResponseEntity.ok(cdhaList);
    }

}
