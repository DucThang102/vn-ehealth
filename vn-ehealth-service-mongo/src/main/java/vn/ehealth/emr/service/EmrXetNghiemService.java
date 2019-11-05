package vn.ehealth.emr.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.EmrXetNghiem;
import vn.ehealth.emr.repository.EmrXetNghiemRepository;

@Service
public class EmrXetNghiemService {

    @Autowired EmrXetNghiemRepository emrXetNghiemRepository;
    
    public List<EmrXetNghiem> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrXetNghiemRepository.findByEmrHoSoBenhAnId(emrHoSoBenhAnId);
    }
    
    public EmrXetNghiem createOrUpdate(EmrXetNghiem emrXetNghiem) {
        return emrXetNghiemRepository.save(emrXetNghiem);
    }
}
