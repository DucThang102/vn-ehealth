package vn.ehealth.emr.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrHinhAnhTonThuong;
import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.repository.EmrHinhAnhTonThuongRepository;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.emr.utils.Constants.TRANGTHAI_DULIEU;

@Service
public class EmrHinhAnhTonThuongService {

    @Autowired 
    private EmrHinhAnhTonThuongRepository emrHinhAnhTonThuongRepository;
    
    @Autowired
    private EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;
    
    public Optional<EmrHinhAnhTonThuong> getById(ObjectId id) {
        return emrHinhAnhTonThuongRepository.findById(id);
    }
    
    public List<EmrHinhAnhTonThuong> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrHinhAnhTonThuongRepository.findByEmrHoSoBenhAnIdAndTrangThai(emrHoSoBenhAnId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public List<EmrHinhAnhTonThuong> getByEmrBenhNhanId(ObjectId emrBenhNhanId) {
        return emrHinhAnhTonThuongRepository.findByEmrBenhNhanIdAndTrangThai(emrBenhNhanId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public EmrHinhAnhTonThuong save(EmrHinhAnhTonThuong hatt) {
        if(hatt.id == null && hatt.emrHoSoBenhAnId != null) {
            var hsba = emrHoSoBenhAnRepository.findById(hatt.emrHoSoBenhAnId).orElseThrow();
            hatt.emrBenhNhanId = hsba.emrBenhNhanId;
            hatt.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
        }        
        
        return emrHinhAnhTonThuongRepository.save(hatt);
    }
    
    public void delete(ObjectId id) {
        var hatt = emrHinhAnhTonThuongRepository.findById(id);
        hatt.ifPresent(x -> {
            x.trangThai = TRANGTHAI_DULIEU.DA_XOA;
            emrHinhAnhTonThuongRepository.save(x);
        });
    }  
    
    public void createOrUpdateFromHIS(@Nonnull EmrHoSoBenhAn hsba, @Nonnull List<EmrHinhAnhTonThuong> hattList) {
        for(int i = 0; i < hattList.size(); i++) {
            var hatt = hattList.get(i);
            if(hatt.idhis != null) {
            	hatt.id = emrHinhAnhTonThuongRepository.findByIdhis(hatt.idhis).map(x -> x.id).orElse(null);
            }
            hatt.emrHoSoBenhAnId = hsba.id;
            hatt.emrBenhNhanId = hsba.emrBenhNhanId;
            hatt.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
            hatt = emrHinhAnhTonThuongRepository.save(hatt);
            hattList.set(i, hatt);
        }         
    }
}
