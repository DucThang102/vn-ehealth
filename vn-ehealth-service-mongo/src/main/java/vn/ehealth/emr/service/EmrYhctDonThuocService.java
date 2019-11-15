package vn.ehealth.emr.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrYhctDonThuoc;
import vn.ehealth.emr.repository.EmrYhctDonThuocRepository;

@Service
public class EmrYhctDonThuocService {

    @Autowired EmrYhctDonThuocRepository emrYhctDonThuocRepository;
    
    public List<EmrYhctDonThuoc> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrYhctDonThuocRepository.findByEmrHoSoBenhAnId(emrHoSoBenhAnId);
    }
    
    public EmrYhctDonThuoc createOrUpdate(EmrYhctDonThuoc emrYhctDonThuoc) {
        return emrYhctDonThuocRepository.save(emrYhctDonThuoc);
    }
}
