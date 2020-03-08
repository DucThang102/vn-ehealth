package vn.ehealth.emr.service;

import java.util.List;
import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.model.EmrThamDoChucNang;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.emr.repository.EmrThamDoChucNangRepository;
import vn.ehealth.emr.utils.Constants.TRANGTHAI_DULIEU;

@Service
public class EmrThamDoChucNangService {

    @Autowired
    private EmrThamDoChucNangRepository emrThamDoChucNangRepository;
    
    @Autowired
    private EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;
    
    public List<EmrThamDoChucNang> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrThamDoChucNangRepository.findByEmrHoSoBenhAnIdAndTrangThai(emrHoSoBenhAnId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public List<EmrThamDoChucNang> getByEmrBenhNhanId(ObjectId emrBenhNhanId) {
        return emrThamDoChucNangRepository.findByEmrBenhNhanIdAndTrangThai(emrBenhNhanId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public EmrThamDoChucNang save(EmrThamDoChucNang tdcn) {
        if(tdcn.id == null && tdcn.emrHoSoBenhAnId != null) {
            var hsba = emrHoSoBenhAnRepository.findById(tdcn.emrHoSoBenhAnId).orElseThrow();
            tdcn.emrBenhNhanId = hsba.emrBenhNhanId;
            tdcn.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
        }
        
        return emrThamDoChucNangRepository.save(tdcn);
    }
    
    public void delete(ObjectId id) {
        var tdcn = emrThamDoChucNangRepository.findById(id);
        tdcn.ifPresent(x -> {
            x.trangThai = TRANGTHAI_DULIEU.DA_XOA;
            emrThamDoChucNangRepository.save(x);
        });
    }
    
    public void createOrUpdateFromHIS(@Nonnull EmrHoSoBenhAn hsba, @Nonnull List<EmrThamDoChucNang> tdcnList) {
        for(int i = 0; i < tdcnList.size(); i++) {
            var tdcn = tdcnList.get(i);
            if(tdcn.idhis != null) {
            	tdcn.id = emrThamDoChucNangRepository.findByIdhis(tdcn.idhis).map(x -> x.id).orElse(null);
            }
            tdcn.emrHoSoBenhAnId = hsba.id;
            tdcn.emrBenhNhanId = hsba.emrBenhNhanId;
            tdcn.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
            tdcn = emrThamDoChucNangRepository.save(tdcn);
            tdcnList.set(i, tdcn);
        }         
    }
}
