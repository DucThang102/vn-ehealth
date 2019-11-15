package vn.ehealth.emr.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrDieuTri;
import vn.ehealth.emr.repository.EmrDieuTriRepository;

@Service
public class EmrDieuTriService {
    
    @Autowired EmrDieuTriRepository emrDieuTriRepository;
    
    public List<EmrDieuTri> getByEmrVaoKhoaId(ObjectId emrVaoKhoaId) {
        return emrDieuTriRepository.findByEmrVaoKhoaId(emrVaoKhoaId);        
    }    

    public EmrDieuTri createOrUpdate(EmrDieuTri emrDieuTri) {
        return emrDieuTriRepository.save(emrDieuTri);
    }
    
    public void delete(ObjectId id) {
        emrDieuTriRepository.deleteById(id);
    }
}
