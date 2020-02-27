package vn.ehealth.emr.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrChamSoc;
import vn.ehealth.emr.repository.EmrChamSocRepository;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.emr.utils.Constants.TRANGTHAI_DULIEU;

@Service
public class EmrChamSocService {

    @Autowired
    private EmrChamSocRepository emrChamSocRepository;
    
    @Autowired
    private EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;
    
    public Optional<EmrChamSoc> getById(ObjectId id) {
        return emrChamSocRepository.findById(id);
    }
    
    public List<EmrChamSoc> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrChamSocRepository.findByEmrHoSoBenhAnIdAndTrangThai(emrHoSoBenhAnId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public List<EmrChamSoc> getByEmrBenhNhanId(ObjectId emrBenhNhanId) {
        return emrChamSocRepository.findByEmrBenhNhanIdAndTrangThai(emrBenhNhanId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public EmrChamSoc save(@Nonnull EmrChamSoc chamsoc) {
        if(chamsoc.id == null && chamsoc.emrHoSoBenhAnId != null) {
            var hsba = emrHoSoBenhAnRepository.findById(chamsoc.emrHoSoBenhAnId).orElseThrow();
            chamsoc.emrBenhNhanId = hsba.emrBenhNhanId;
            chamsoc.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
        }
        return emrChamSocRepository.save(chamsoc);
    }
    
    public void delete(ObjectId id) {
        var emrChamSoc = emrChamSocRepository.findById(id);
        emrChamSoc.ifPresent(x -> {
            x.trangThai = TRANGTHAI_DULIEU.DA_XOA;
            emrChamSocRepository.save(x);
        });
    }
}
