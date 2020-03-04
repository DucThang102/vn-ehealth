package vn.ehealth.emr.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.model.EmrHoiChan;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.emr.repository.EmrHoiChanRepository;
import vn.ehealth.emr.utils.Constants.TRANGTHAI_DULIEU;

@Service
public class EmrHoiChanService {

    @Autowired
    private EmrHoiChanRepository emrHoiChanRepository;
    
    @Autowired
    private EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;
    
    public Optional<EmrHoiChan> getById(ObjectId id) {
        return emrHoiChanRepository.findById(id);
    }
    
    public List<EmrHoiChan> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrHoiChanRepository.findByEmrHoSoBenhAnIdAndTrangThai(emrHoSoBenhAnId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public List<EmrHoiChan> getByEmrBenhNhanId(ObjectId emrBenhNhanId) {
        return emrHoiChanRepository.findByEmrBenhNhanIdAndTrangThai(emrBenhNhanId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public EmrHoiChan save(@Nonnull EmrHoiChan hoichan) {
        if(hoichan.id == null && hoichan.emrHoSoBenhAnId != null) {
            var hsba = emrHoSoBenhAnRepository.findById(hoichan.emrHoSoBenhAnId).orElseThrow();
            hoichan.emrBenhNhanId = hsba.emrBenhNhanId;
            hoichan.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
            
            if(hsba.emrVaoKhoas != null && hoichan.emrKhoaDieuTri != null) {
                var maKhoaDieuTri = hoichan.emrKhoaDieuTri.emrDmKhoaDieuTri.ma;
                
                var vaokhoa = hsba.emrVaoKhoas.stream()
                                .filter(x -> x.emrDmKhoaDieuTri.ma == maKhoaDieuTri)
                                .findFirst();
                
                vaokhoa.ifPresent(x -> hoichan.emrKhoaDieuTri = x);
            }
        }
        
        return emrHoiChanRepository.save(hoichan);
    }
    
    public void delete(ObjectId id) {
        var hoichan = emrHoiChanRepository.findById(id);
        hoichan.ifPresent(x -> {
            x.trangThai = TRANGTHAI_DULIEU.DA_XOA;
            emrHoiChanRepository.save(x);
        });
    }
    
    public void createOrUpdateFromHIS(@Nonnull EmrHoSoBenhAn hsba, @Nonnull List<EmrHoiChan> hcList, @Nonnull List<Object> hcObjList) {
        for(int i = 0; i < hcList.size(); i++) {
            var hc = hcList.get(i);
            if(hc.idhis != null) {
            	hc.id = emrHoiChanRepository.findByIdhis(hc.idhis).map(x -> x.id).orElse(null);
            }
            @SuppressWarnings("unchecked")
			var  hcObject= (Map<String, Object>) hcObjList.get(i);
            @SuppressWarnings("unchecked")
			Map<String, Object> emrDmKhoaDieuTri = (Map<String, Object>) hcObject.get("emrDmKhoaDieuTri");
            if (emrDmKhoaDieuTri != null && hsba.emrVaoKhoas != null) {
            	var maKhoaDieuTri =  (String) emrDmKhoaDieuTri.get("ma");
            	
            	hc.emrKhoaDieuTri = hsba.emrVaoKhoas.stream()
                      .filter(x -> x.emrDmKhoaDieuTri.ma.equals(maKhoaDieuTri))
                      .findFirst()
                      .orElse(null);
            }
            hc.emrHoSoBenhAnId = hsba.id;
            hc.emrBenhNhanId = hsba.emrBenhNhanId;
            hc.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
            hc = emrHoiChanRepository.save(hc);
            hcList.set(i, hc);
        }         
    }
}
