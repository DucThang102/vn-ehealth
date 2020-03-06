package vn.ehealth.emr.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrDieuTri;
import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.repository.EmrDieuTriRepository;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.emr.utils.Constants.TRANGTHAI_DULIEU;

@Service
public class EmrDieuTriService {
    
    @Autowired 
    private EmrDieuTriRepository emrDieuTriRepository;
    
    @Autowired
    private EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;
    
    public Optional<EmrDieuTri> getById(ObjectId id) {
        return emrDieuTriRepository.findById(id);
    }
    
    public List<EmrDieuTri> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrDieuTriRepository.findByEmrHoSoBenhAnIdAndTrangThai(emrHoSoBenhAnId, TRANGTHAI_DULIEU.DEFAULT);        
    }
    
    public List<EmrDieuTri> getByEmrBenhNhanId(ObjectId emrBenhNhanId) {
        return emrDieuTriRepository.findByEmrBenhNhanIdAndTrangThai(emrBenhNhanId, TRANGTHAI_DULIEU.DEFAULT);        
    }

    public EmrDieuTri save(@Nonnull EmrDieuTri dieutri) {
        if(dieutri.id == null && dieutri.emrHoSoBenhAnId != null) {
            var hsba = emrHoSoBenhAnRepository.findById(dieutri.emrHoSoBenhAnId).orElseThrow();
            dieutri.emrBenhNhanId = hsba.emrBenhNhanId;
            dieutri.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
            
            if(hsba.emrVaoKhoas != null && dieutri.emrKhoaDieuTri != null) {
                var maKhoaDieuTri = dieutri.emrKhoaDieuTri.emrDmKhoaDieuTri.ma;
                
                var vaokhoa = hsba.emrVaoKhoas.stream()
                                .filter(x -> x.emrDmKhoaDieuTri.ma == maKhoaDieuTri)
                                .findFirst();
                
                vaokhoa.ifPresent(x -> dieutri.emrKhoaDieuTri = x);
            }
        }
        return emrDieuTriRepository.save(dieutri);        
    }
    
    public void delete(ObjectId id) {
        var dieutri = emrDieuTriRepository.findById(id);
        dieutri.ifPresent(x -> {            
            x.trangThai = TRANGTHAI_DULIEU.DA_XOA;
            emrDieuTriRepository.save(x);
        });
        
    }
    
    public void createOrUpdateFromHIS(@Nonnull EmrHoSoBenhAn hsba, @Nonnull List<EmrDieuTri> dtList, @Nonnull List<Object> dtObjList) {
        for(int i = 0; i < dtList.size(); i++) {
            var dt = dtList.get(i);
            if(dt.idhis != null) {
            	dt.id = emrDieuTriRepository.findByIdhis(dt.idhis).map(x -> x.id).orElse(null);
            }
            @SuppressWarnings("unchecked")
			var  dtObject= (Map<String, Object>) dtObjList.get(i);
            @SuppressWarnings("unchecked")
			Map<String, Object> emrDmKhoaDieuTri = (Map<String, Object>) dtObject.get("emrDmKhoaDieuTri");
            if (emrDmKhoaDieuTri != null && hsba.emrVaoKhoas != null) {
            	var maKhoaDieuTri =  (String) emrDmKhoaDieuTri.get("ma");
            	
            	dt.emrKhoaDieuTri = hsba.emrVaoKhoas.stream()
                      .filter(x -> x.emrDmKhoaDieuTri.ma.equals(maKhoaDieuTri))
                      .findFirst()
                      .orElse(null);
            }
            dt.emrHoSoBenhAnId = hsba.id;
            dt.emrBenhNhanId = hsba.emrBenhNhanId;
            dt.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
            dt = emrDieuTriRepository.save(dt);
            dtList.set(i, dt);
        }         
    }
}
