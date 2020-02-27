package vn.ehealth.emr.controller;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.ehealth.emr.model.EmrChucNangSong;
import vn.ehealth.emr.service.EmrChucNangSongService;
import vn.ehealth.emr.service.EmrHoSoBenhAnService;
import vn.ehealth.emr.utils.EmrUtils;

@RestController
@RequestMapping("/api/chucnangsong")
public class EmrChucNangSongController {
    
    private Logger logger = LoggerFactory.getLogger(EmrChucNangSongController.class);
    @Autowired EmrChucNangSongService emrChucNangSongService;
    @Autowired EmrHoSoBenhAnService emrHoSoBenhAnService;
    
    @GetMapping("/get_ds_chucnangsong_by_bn")
    public ResponseEntity<?> getDsChucNangSongByBenhNhan(@RequestParam("benhnhan_id") String benhNhanId) {
        return ResponseEntity.ok(emrChucNangSongService.getByEmrBenhNhanId(new ObjectId(benhNhanId)));
    }
    
    @GetMapping("/get_ds_chucnangsong")
    public ResponseEntity<?> getDsChucNangSong(@RequestParam("hsba_id") String id) {
        return ResponseEntity.ok(emrChucNangSongService.getByEmrHoSoBenhAnId(new ObjectId(id)));
    }

    @GetMapping("/delete_chucnangsong")
    public ResponseEntity<?> deleteChucnangsong(@RequestParam("chucnangsong_id") String id) {
        try {
            emrChucNangSongService.delete(new ObjectId(id));
            var result = Map.of("success" , true);
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            logger.error("Error delete chucnangsong:", e);
            var result = Map.of("success" , false);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/save_chucnangsong")
    public ResponseEntity<?> saveChucnangsong(@RequestBody String jsonSt) {
        
        try {
            var mapper = EmrUtils.createObjectMapper();
            var chucnangsong = mapper.readValue(jsonSt, EmrChucNangSong.class);
            chucnangsong = emrChucNangSongService.save(chucnangsong);
            
            var result = Map.of(
                "success" , true,
                "emrChucNangSong", chucnangsong 
            );
                    
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            var result = Map.of(
                "success" , false,
                "errors", List.of(e.getMessage()) 
            );
            logger.error("Error save chucnangsong:", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
