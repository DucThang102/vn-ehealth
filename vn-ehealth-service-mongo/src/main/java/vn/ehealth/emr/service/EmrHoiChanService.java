package vn.ehealth.emr.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrHoiChan;
import vn.ehealth.emr.repository.EmrHoiChanRepository;

@Service
public class EmrHoiChanService {

    @Autowired EmrHoiChanRepository emrHoiChanRepository;
    
    public List<EmrHoiChan> getByEmrVaoKhoaId(ObjectId emrVaoKhoaId) {
        return emrHoiChanRepository.findByEmrVaoKhoaId(emrVaoKhoaId);
    }
    
    public EmrHoiChan createOrUpdate(EmrHoiChan emrHoiChan) {
        return emrHoiChanRepository.save(emrHoiChan);
    }
    
    public void delete(ObjectId id) {
        emrHoiChanRepository.deleteById(id);
    }
}
