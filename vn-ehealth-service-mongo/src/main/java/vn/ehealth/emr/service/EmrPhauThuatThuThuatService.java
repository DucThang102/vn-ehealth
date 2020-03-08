package vn.ehealth.emr.service;

import java.util.List;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.model.EmrPhauThuatThuThuat;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.emr.repository.EmrPhauThuatThuThuatRepository;
import vn.ehealth.emr.utils.Constants.TRANGTHAI_DULIEU;

@Service
public class EmrPhauThuatThuThuatService {

    @Autowired 
    private EmrPhauThuatThuThuatRepository emrPhauThuatThuThuatRepository;
    
    @Autowired
    private EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;
    
    public List<EmrPhauThuatThuThuat> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId){
        return emrPhauThuatThuThuatRepository.findByEmrHoSoBenhAnIdAndTrangThai(emrHoSoBenhAnId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public List<EmrPhauThuatThuThuat> getByEmrBenhNhanId(ObjectId emrBenhNhanId){
        return emrPhauThuatThuThuatRepository.findByEmrBenhNhanIdAndTrangThai(emrBenhNhanId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public EmrPhauThuatThuThuat save(@Nonnull EmrPhauThuatThuThuat pttt) {
        if(pttt.id == null && pttt.emrHoSoBenhAnId != null) {
            var hsba = emrHoSoBenhAnRepository.findById(pttt.emrHoSoBenhAnId).orElseThrow();
            pttt.emrHoSoBenhAnId = hsba.id;
            pttt.emrBenhNhanId = hsba.emrBenhNhanId;
            pttt.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
        }
        
        return emrPhauThuatThuThuatRepository.save(pttt);                
    }
    
    public void createOrUpdateFromHIS(@Nonnull EmrHoSoBenhAn hsba, @Nonnull List<EmrPhauThuatThuThuat> ptttList) {
        for(int i = 0; i < ptttList.size(); i++) {
            var pttt = ptttList.get(i);
            if(pttt.idhis != null) {
            	pttt.id = emrPhauThuatThuThuatRepository.findByIdhis(pttt.idhis).map(x -> x.id).orElse(null);
            }
            pttt.emrHoSoBenhAnId = hsba.id;
            pttt.emrBenhNhanId = hsba.emrBenhNhanId;
            pttt.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
            pttt = emrPhauThuatThuThuatRepository.save(pttt);
            ptttList.set(i, pttt);
        }         
    }
    
    public void delete(ObjectId id) {
        var pttt = emrPhauThuatThuThuatRepository.findById(id);
        pttt.ifPresent(x -> {
            x.trangThai = TRANGTHAI_DULIEU.DA_XOA;
            emrPhauThuatThuThuatRepository.save(x);
        });
    }
}
