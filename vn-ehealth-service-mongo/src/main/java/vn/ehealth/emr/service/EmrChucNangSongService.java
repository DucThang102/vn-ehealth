package vn.ehealth.emr.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrChucNangSong;
import vn.ehealth.emr.repository.EmrChucNangSongRepository;

@Service
public class EmrChucNangSongService {

    @Autowired EmrChucNangSongRepository emrChucNangSongRepository;
    
    public List<EmrChucNangSong> getByEmrVaoKhoaId(ObjectId emrVaoKhoaId) {
        return emrChucNangSongRepository.findByEmrVaoKhoaId(emrVaoKhoaId);
    }
    
    public EmrChucNangSong createOrUpdate(EmrChucNangSong emrChucNangSong) {
        return emrChucNangSongRepository.save(emrChucNangSong);
    }
    
    public void delete(ObjectId id) {
        emrChucNangSongRepository.deleteById(id);
    }
}
