package vn.ehealth.emr.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrDonThuoc;
import vn.ehealth.emr.repository.EmrDonThuocRepository;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.emr.utils.Constants.TRANGTHAI_DULIEU;

@Service
public class EmrDonThuocService {

    @Autowired 
    private EmrDonThuocRepository emrDonThuocRepository;
    
    @Autowired
    private EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;
    
    public Optional<EmrDonThuoc> getById(ObjectId id) {
        return emrDonThuocRepository.findById(id);
    }
    
    public List<EmrDonThuoc> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrDonThuocRepository.findByEmrHoSoBenhAnIdAndTrangThai(emrHoSoBenhAnId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public List<EmrDonThuoc> getByEmrBenhNhanId(ObjectId emrBenhNhanId) {
        return emrDonThuocRepository.findByEmrBenhNhanIdAndTrangThai(emrBenhNhanId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public EmrDonThuoc save(@Nonnull EmrDonThuoc donthuoc) {
        if(donthuoc.id == null && donthuoc.emrHoSoBenhAnId != null) {
            var hsba = emrHoSoBenhAnRepository.findById(donthuoc.emrHoSoBenhAnId).orElseThrow();
            donthuoc.emrBenhNhanId = hsba.emrBenhNhanId;
            donthuoc.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
        }
        return emrDonThuocRepository.save(donthuoc);
    }
    
    public void delete(ObjectId id) {
        var donthuoc = emrDonThuocRepository.findById(id);
        donthuoc.ifPresent(x -> {
            x.trangThai = TRANGTHAI_DULIEU.DA_XOA;
            emrDonThuocRepository.save(x); 
        });
    }
}
