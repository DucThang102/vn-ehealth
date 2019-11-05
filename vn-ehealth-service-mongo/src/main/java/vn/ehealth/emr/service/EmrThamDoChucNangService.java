package vn.ehealth.emr.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.EmrThamDoChucNang;
import vn.ehealth.emr.repository.EmrThamDoChucNangRepository;

@Service
public class EmrThamDoChucNangService {

    @Autowired EmrThamDoChucNangRepository emrThamDoChucNangRepository;
    
    public List<EmrThamDoChucNang> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrThamDoChucNangRepository.findByEmrHoSoBenhAnId(emrHoSoBenhAnId);
    }
    
    public EmrThamDoChucNang createOrUpdate(EmrThamDoChucNang emrThamDoChucNang) {
        return emrThamDoChucNangRepository.save(emrThamDoChucNang);
    }
}
