package vn.ehealth.emr.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrChanDoanHinhAnh;
import vn.ehealth.emr.repository.EmrChanDoanHinhAnhRepository;

@Service
public class EmrChanDoanHinhAnhService {

    @Autowired EmrChanDoanHinhAnhRepository emrChanDoanHinhAnhRepository;
    
    public List<EmrChanDoanHinhAnh> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrChanDoanHinhAnhRepository.findByEmrHoSoBenhAnId(emrHoSoBenhAnId);
    }
    
    public void deleteAllByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        for(var cdha : getByEmrHoSoBenhAnId(emrHoSoBenhAnId)) {
            emrChanDoanHinhAnhRepository.delete(cdha);
        }
    }    
    
    public EmrChanDoanHinhAnh createOrUpdate(EmrChanDoanHinhAnh emrChanDoanHinhAnh) {
        return emrChanDoanHinhAnhRepository.save(emrChanDoanHinhAnh);
    }
    
    public void delete(ObjectId id) {
        emrChanDoanHinhAnhRepository.deleteById(id);
    }
}
