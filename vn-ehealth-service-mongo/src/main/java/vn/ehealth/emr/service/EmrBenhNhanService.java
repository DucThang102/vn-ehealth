package vn.ehealth.emr.service;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrBenhNhan;
import vn.ehealth.emr.repository.EmrBenhNhanRepository;

@Service
public class EmrBenhNhanService {

    @Autowired EmrBenhNhanRepository emrBenhNhanRepository;
    
    public EmrBenhNhan createOrUpdate(EmrBenhNhan emrBenhNhan) {
        var emrBenhNhan0 = emrBenhNhanRepository.findByIddinhdanhchinh(emrBenhNhan.iddinhdanhchinh);
        emrBenhNhan0.ifPresent(x -> {
            emrBenhNhan.id = x.id;
        });
        return emrBenhNhanRepository.save(emrBenhNhan);
    }
    
    public Optional<EmrBenhNhan> getById(ObjectId id) {
        return emrBenhNhanRepository.findById(id);
    }
}
