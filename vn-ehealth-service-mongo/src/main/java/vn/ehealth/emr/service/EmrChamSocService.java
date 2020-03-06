package vn.ehealth.emr.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import vn.ehealth.emr.model.EmrChamSoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrHoSoBenhAn;
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
    
    public void createOrUpdateFromHIS(@Nonnull EmrHoSoBenhAn hsba, @Nonnull List<EmrChamSoc> csList, List<Object> csObjList) {
        for(int i = 0; i < csList.size(); i++) {
            var cs = csList.get(i);
            if(cs.idhis != null) {
                cs.id = emrChamSocRepository.findByIdhis(cs.idhis).map(x -> x.id).orElse(null);
            }
            @SuppressWarnings("unchecked")
			var  csObject= (Map<String, Object>) csObjList.get(i);
            @SuppressWarnings("unchecked")
			Map<String, Object> emrDmKhoaDieuTri = (Map<String, Object>) csObject.get("emrDmKhoaDieuTri");
            if (emrDmKhoaDieuTri != null && hsba.emrVaoKhoas != null) {
            	var maKhoaDieuTri =  (String) emrDmKhoaDieuTri.get("ma");
            	cs.emrKhoaDieuTri = hsba.emrVaoKhoas.stream()
                      .filter(x -> x.emrDmKhoaDieuTri.ma.equals(maKhoaDieuTri))
                      .findFirst()
                      .orElse(null);
            }
            cs.emrHoSoBenhAnId = hsba.id;
            cs.emrBenhNhanId = hsba.emrBenhNhanId;
            cs.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
            cs = emrChamSocRepository.save(cs);
            csList.set(i, cs);
        }         
    }
}
