package vn.ehealth.emr.controller;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.ehealth.emr.model.EmrHoiChan;
import vn.ehealth.emr.service.EmrHoiChanService;
import vn.ehealth.emr.service.EmrVaoKhoaService;

@RestController
@RequestMapping("/api/hoichan")
public class EmrHoiChanController {
    
    @Autowired EmrVaoKhoaService emrVaoKhoaService;
    @Autowired EmrHoiChanService emrHoiChanService;
    
    @GetMapping("/get_ds_hoichan")
    public ResponseEntity<?> getDsHoiChan(@RequestParam("hsba_id") String id) {
        var result = new ArrayList<EmrHoiChan>();
        var vkList = emrVaoKhoaService.getByEmrHoSoBenhAnId(new ObjectId(id), false);
        
        for(var vk : vkList) {
            var hoiChanList = emrHoiChanService.getByEmrVaoKhoaId(vk.id);
            hoiChanList.forEach(x -> x.emrVaoKhoa = vk);
            result.addAll(hoiChanList);
        }
        
        return ResponseEntity.ok(result);
    }

}
