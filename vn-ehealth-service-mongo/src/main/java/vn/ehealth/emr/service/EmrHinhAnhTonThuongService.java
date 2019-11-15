package vn.ehealth.emr.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrHinhAnhTonThuong;
import vn.ehealth.emr.repository.EmrHinhAnhTonThuongRepository;

@Service
public class EmrHinhAnhTonThuongService {

    @Autowired EmrHinhAnhTonThuongRepository emrHinhAnhTonThuongRepository;
    
    public List<EmrHinhAnhTonThuong> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrHinhAnhTonThuongRepository.findByEmrHoSoBenhAnId(emrHoSoBenhAnId);
    }
    
    public EmrHinhAnhTonThuong createOrUpdate(EmrHinhAnhTonThuong emrHinhAnhTonThuong) {
        return emrHinhAnhTonThuongRepository.save(emrHinhAnhTonThuong);
    }
    
    public void delete(ObjectId id) {
        emrHinhAnhTonThuongRepository.deleteById(id);
    }    
}
