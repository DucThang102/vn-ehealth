package vn.ehealth.emr.service;

import java.util.List;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrChanDoanHinhAnh;
import vn.ehealth.emr.model.EmrHoSoBenhAn;
import vn.ehealth.emr.repository.EmrChanDoanHinhAnhRepository;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.emr.utils.Constants.TRANGTHAI_DULIEU;

@Service
public class EmrChanDoanHinhAnhService {

    @Autowired 
    private EmrChanDoanHinhAnhRepository emrChanDoanHinhAnhRepository;
    
    @Autowired
    private EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;
    
    public List<EmrChanDoanHinhAnh> getByEmrHoSoBenhAnId(ObjectId emrHoSoBenhAnId) {
        return emrChanDoanHinhAnhRepository.findByEmrHoSoBenhAnIdAndTrangThai(emrHoSoBenhAnId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public List<EmrChanDoanHinhAnh> getByEmrBenhNhanId(ObjectId emrBenhNhanId) {
        return emrChanDoanHinhAnhRepository.findByEmrBenhNhanIdAndTrangThai(emrBenhNhanId, TRANGTHAI_DULIEU.DEFAULT);
    }
    
    public EmrChanDoanHinhAnh save(EmrChanDoanHinhAnh cdha) {
        if(cdha.id == null && cdha.emrHoSoBenhAnId != null) {
            var hsba = emrHoSoBenhAnRepository.findById(cdha.emrHoSoBenhAnId).orElseThrow();
            cdha.emrBenhNhanId = hsba.emrBenhNhanId;
            cdha.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
        }
        return emrChanDoanHinhAnhRepository.save(cdha);
    }
    
    public void createOrUpdateFromHIS(@Nonnull EmrHoSoBenhAn hsba, @Nonnull List<EmrChanDoanHinhAnh> cdhaList) {
        for(int i = 0; i < cdhaList.size(); i++) {
            var cdha = cdhaList.get(i);
            cdha.id = emrChanDoanHinhAnhRepository.findByIdhis(cdha.idhis).map(x -> x.id).orElse(null);
            cdha.emrHoSoBenhAnId = hsba.id;
            cdha.emrBenhNhanId = hsba.emrBenhNhanId;
            cdha.emrCoSoKhamBenhId = hsba.emrCoSoKhamBenhId;
            cdha = emrChanDoanHinhAnhRepository.save(cdha);
            cdhaList.set(i, cdha);
        }         
    }
    
    public void delete(ObjectId id) {
        var cdha = emrChanDoanHinhAnhRepository.findById(id);
        cdha.ifPresent(x -> {
            x.trangThai = TRANGTHAI_DULIEU.DA_XOA;
            emrChanDoanHinhAnhRepository.save(x);
        });
    }
}




