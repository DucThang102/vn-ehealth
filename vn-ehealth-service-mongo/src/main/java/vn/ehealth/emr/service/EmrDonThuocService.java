package vn.ehealth.emr.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrDonThuoc;
import vn.ehealth.emr.repository.EmrDonThuocRepository;

@Service
public class EmrDonThuocService {

    @Autowired EmrDonThuocRepository emrDonThuocRepository;
    
    public List<EmrDonThuoc> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrDonThuocRepository.findByEmrHoSoBenhAnId(emrHoSoBenhAnId);
    }
    
    public EmrDonThuoc createOrUpdate(EmrDonThuoc emrDonThuoc) {
        return emrDonThuocRepository.save(emrDonThuoc);
    }
    
    public void delete(ObjectId id) {
        emrDonThuocRepository.deleteById(id);
    }
}
