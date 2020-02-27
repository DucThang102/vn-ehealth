package vn.ehealth.emr.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrChucNangSong;
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
            
            if(hsba.emrVaoKhoas != null && cns.emrVaoKhoa != null) {
                var maKhoaDieuTri = cns.emrVaoKhoa.emrDmKhoaDieuTri.ma;
                
                var vaokhoa = hsba.emrVaoKhoas.stream()
                                .filter(x -> x.emrDmKhoaDieuTri.ma == maKhoaDieuTri)
                                .findFirst();
                
                vaokhoa.ifPresent(x -> cns.emrVaoKhoa = x);
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
}
