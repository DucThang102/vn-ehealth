package vn.ehealth.emr.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.EmrChamSoc;
import vn.ehealth.emr.repository.EmrChamSocRepository;

@Service
public class EmrChamSocService {

    @Autowired EmrChamSocRepository emrChamSocRepository;
    
    public List<EmrChamSoc> getByEmrVaoKhoaId(ObjectId emrVaoKhoaId) {
        return emrChamSocRepository.findByEmrVaoKhoaId(emrVaoKhoaId);
    }
    
    public EmrChamSoc createOrUpdate(EmrChamSoc emrChamSoc) {
        return emrChamSocRepository.save(emrChamSoc);
    }
    
    public void delete(ObjectId id) {
        emrChamSocRepository.deleteById(id);
    }
}
