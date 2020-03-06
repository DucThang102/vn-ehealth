package vn.ehealth.emr.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ehealth.emr.model.EmrChucNangSong;
import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.repository.EmrChucNangSongRepository;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.emr.utils.Constants.TRANGTHAI_DULIEU;

@Service
public class EmrChucNangSongService {

    @Autowired 
    private EmrChucNangSongRepository emrChucNangSongRepository;
    
    @Autowired
    private EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;
    
    public Optional<EmrChucNangSong> getById(ObjectId id) {
        return emrChucNangSongRepository.findById(id);
    }
    
    public List<EmrChucNangSong> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrChucNangSongRepository.findByEmrHoSoBenhAnIdAndTrangThai(emrHoSoBenhAnId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public List<EmrChucNangSong> getByEmrBenhNhanId(ObjectId emrBenhNhanId) {
        return emrChucNangSongRepository.findByEmrBenhNhanIdAndTrangThai(emrBenhNhanId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public EmrChucNangSong save(@Nonnull EmrChucNangSong cns) {
        if(cns.id == null && cns.emrHoSoBenhAnId == null) {
            var hsba = emrHoSoBenhAnRepository.findById(cns.emrHoSoBenhAnId).orElseThrow();
            cns.emrBenhNhanId = hsba.emrBenhNhanId;
            cns.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
            
            if(hsba.emrVaoKhoas != null && cns.emrKhoaDieuTri != null) {
                var maKhoaDieuTri = cns.emrKhoaDieuTri.emrDmKhoaDieuTri.ma;
                
                var vaokhoa = hsba.emrVaoKhoas.stream()
                                .filter(x -> x.emrDmKhoaDieuTri.ma == maKhoaDieuTri)
                                .findFirst();
                
                vaokhoa.ifPresent(x -> cns.emrKhoaDieuTri = x);
            }
        }
        return emrChucNangSongRepository.save(cns);
    }
    
    public void delete(ObjectId id) {
        var cns = emrChucNangSongRepository.findById(id);
        cns.ifPresent(x -> {
            x.trangThai = TRANGTHAI_DULIEU.DA_XOA;
            emrChucNangSongRepository.save(x);
            
        });
    }
    
    public void createOrUpdateFromHIS(@Nonnull EmrHoSoBenhAn hsba, @Nonnull List<EmrChucNangSong> cnsList, @Nonnull List<Object> cnsObjList) {
        for(int i = 0; i < cnsList.size(); i++) {
            var cns = cnsList.get(i);
            if(cns.idhis != null) {
            	cns.id = emrChucNangSongRepository.findByIdhis(cns.idhis).map(x -> x.id).orElse(null);
            }
            @SuppressWarnings("unchecked")
			var  cnsObject= (Map<String, Object>) cnsObjList.get(i);
            @SuppressWarnings("unchecked")
			Map<String, Object> emrDmKhoaDieuTri = (Map<String, Object>) cnsObject.get("emrDmKhoaDieuTri");
            if (emrDmKhoaDieuTri != null && hsba.emrVaoKhoas != null) {
            	var maKhoaDieuTri =  (String) emrDmKhoaDieuTri.get("ma");
            	
            	cns.emrKhoaDieuTri = hsba.emrVaoKhoas.stream()
                      .filter(x -> x.emrDmKhoaDieuTri.ma.equals(maKhoaDieuTri))
                      .findFirst()
                      .orElse(null);
            }
            
            cns.emrHoSoBenhAnId = hsba.id;
            cns.emrBenhNhanId = hsba.emrBenhNhanId;
            cns.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
            cns = emrChucNangSongRepository.save(cns);
            cnsList.set(i, cns);
        }         
    }
}
