package vn.ehealth.service.controller;

import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.ehealth.emr.EmrChucNangSong;
import vn.ehealth.emr.service.EmrChucNangSongService;
import vn.ehealth.emr.service.EmrVaoKhoaService;

@RestController
@RequestMapping("/api/chucnangsong")
public class EmrChucNangSongController {
    
    private Logger logger = LoggerFactory.getLogger(EmrChucNangSongController.class);
    @Autowired EmrVaoKhoaService emrVaoKhoaService;
    @Autowired EmrChucNangSongService emrChucNangSongService;
    
    @GetMapping("/get_ds_chucnangsong")
    public ResponseEntity<?> getDsChucNangSong(@RequestParam("hsba_id") String id) {
        var result = new ArrayList<EmrChucNangSong>();
        var vkList = emrVaoKhoaService.getByEmrHoSoBenhAnId(new ObjectId(id), false);
        
        for(var vk : vkList) {
            var chucNangSongList = emrChucNangSongService.getByEmrVaoKhoaId(vk.id);
            chucNangSongList.forEach(x -> x.emrVaoKhoa = vk);
            result.addAll(chucNangSongList);
        }
        
        return ResponseEntity.ok(result);
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
    
    @PostMapping("/create_or_update_chucnangsong")
    public ResponseEntity<?> createOrUpdateChucnangsong(@RequestBody String jsonSt) {
        
        try {
            var mapper = new ObjectMapper();
            var chucnangsong = mapper.readValue(jsonSt, EmrChucNangSong.class);
            chucnangsong = emrChucNangSongService.createOrUpdate(chucnangsong);
            
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
