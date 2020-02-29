package vn.ehealth.emr.service;

import java.util.List;
import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.model.EmrXetNghiem;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.emr.repository.EmrXetNghiemRepository;
import vn.ehealth.emr.utils.Constants.TRANGTHAI_DULIEU;

@Service
public class EmrXetNghiemService {

    @Autowired 
    private EmrXetNghiemRepository emrXetNghiemRepository;
    
    @Autowired
    private EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;
    
    public List<EmrXetNghiem> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrXetNghiemRepository.findByEmrHoSoBenhAnIdAndTrangThai(emrHoSoBenhAnId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public List<EmrXetNghiem> getByEmrBenhNhanId(ObjectId emrBenhNhanId) {
        return emrXetNghiemRepository.findByEmrBenhNhanIdAndTrangThai(emrBenhNhanId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public EmrXetNghiem save(@Nonnull EmrXetNghiem xn) {
        if(xn.id == null && xn.emrHoSoBenhAnId != null) {
            var hsba = emrHoSoBenhAnRepository.findById(xn.emrHoSoBenhAnId).orElseThrow();
            xn.emrBenhNhanId = hsba.emrBenhNhanId;
            xn.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
        }
        return emrXetNghiemRepository.save(xn);
    }
    
    public void createOrUpdateFromHIS(@Nonnull EmrHoSoBenhAn hsba, @Nonnull List<EmrXetNghiem> xetnghiemList) {
        for(int i = 0; i < xetnghiemList.size(); i++) {
            var xetnghiem = xetnghiemList.get(i);
            if(xetnghiem.idhis != null) {
                xetnghiem.id = emrXetNghiemRepository.findByIdhis(xetnghiem.idhis).map(x -> x.id).orElse(null);
            }
            xetnghiem.emrHoSoBenhAnId = hsba.id;
            xetnghiem.emrBenhNhanId = hsba.emrBenhNhanId;
            xetnghiem.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
            xetnghiem = emrXetNghiemRepository.save(xetnghiem);
            xetnghiemList.set(i, xetnghiem);
        }
    }
    
    public void delete(ObjectId id) {
        var xn = emrXetNghiemRepository.findById(id);
        xn.ifPresent(x -> {
            x.trangThai = TRANGTHAI_DULIEU.DA_XOA;
            emrXetNghiemRepository.save(x);
        });
    }
}
