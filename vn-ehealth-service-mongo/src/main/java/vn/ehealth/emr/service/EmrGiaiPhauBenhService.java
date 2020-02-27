package vn.ehealth.emr.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrGiaiPhauBenh;
import vn.ehealth.emr.repository.EmrGiaiPhauBenhRepository;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.emr.utils.Constants.TRANGTHAI_DULIEU;

@Service
public class EmrGiaiPhauBenhService {

    @Autowired 
    private EmrGiaiPhauBenhRepository emrGiaiPhauBenhRepository;
    
    @Autowired
    private EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;
    
    public List<EmrGiaiPhauBenh> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrGiaiPhauBenhRepository.findByEmrHoSoBenhAnIdAndTrangThai(emrHoSoBenhAnId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public List<EmrGiaiPhauBenh> getByEmrBenhNhanId(ObjectId emrBenhNhanId) {
        return emrGiaiPhauBenhRepository.findByEmrBenhNhanIdAndTrangThai(emrBenhNhanId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public EmrGiaiPhauBenh save(EmrGiaiPhauBenh gpb) {
        if(gpb.id == null && gpb.emrHoSoBenhAnId != null) {
            var hsba = emrHoSoBenhAnRepository.findById(gpb.emrHoSoBenhAnId).orElseThrow();
            gpb.emrBenhNhanId = hsba.emrBenhNhanId;
            gpb.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
        }
        
        return emrGiaiPhauBenhRepository.save(gpb);
    }
    
    public void delete(ObjectId id) {
        var gpb = emrGiaiPhauBenhRepository.findById(id);
        gpb.ifPresent(x -> {
            x.trangThai = TRANGTHAI_DULIEU.DA_XOA;
            emrGiaiPhauBenhRepository.save(x);
        });
    }
}
