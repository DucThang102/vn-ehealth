package vn.ehealth.emr.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vn.ehealth.emr.EmrHoSoBenhAn;
import vn.ehealth.emr.repository.EmrChanDoanHinhAnhRepository;
import vn.ehealth.emr.repository.EmrDonThuocRepository;
import vn.ehealth.emr.repository.EmrGiaiPhauBenhRepository;
import vn.ehealth.emr.repository.EmrHinhAnhTonThuongRepository;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.emr.repository.EmrPhauThuatThuThuatRepository;
import vn.ehealth.emr.repository.EmrThamDoChucNangRepository;
import vn.ehealth.emr.repository.EmrXetNghiemRepository;
import vn.ehealth.emr.repository.EmrYhctDonThuocRepository;
import vn.ehealth.service.Constants.NGUON_DU_LIEU;
import vn.ehealth.service.Constants.TRANGTHAI_HOSO;

@Service
public class EmrHoSoBenhAnService {
   
    Logger logger = LoggerFactory.getLogger(EmrHoSoBenhAnService.class);
            
    @Autowired EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;  
    @Autowired EmrHinhAnhTonThuongRepository emrHinhAnhTonThuongRepository;
    @Autowired EmrGiaiPhauBenhRepository emrGiaiPhauBenhRepository;
    @Autowired EmrThamDoChucNangRepository emrThamDoChucNangRepository;
    @Autowired EmrPhauThuatThuThuatRepository emrPhauThuatThuThuatRepository;
    @Autowired EmrChanDoanHinhAnhRepository emrChanDoanHinhAnhRepository;
    @Autowired EmrDonThuocRepository emrDonThuocRepository;
    @Autowired EmrYhctDonThuocRepository emrYhctDonThuocRepository;
    @Autowired EmrXetNghiemRepository emrXetNghiemRepository;
    
    @Autowired EmrDmService emrDmService;
    @Autowired EmrVaoKhoaService emrVaoKhoaService;
    @Autowired EmrBenhNhanService emrBenhNhanService;
    @Autowired EmrCoSoKhamBenhService emrCoSoKhamBenhService;
    
    public List<EmrHoSoBenhAn> findByTrangThaiAndIsLatest(int trangThai, boolean isLatest, int offset, int limit){
        var sort = new Sort(Sort.Direction.DESC, "ngaytiepnhan");
        return emrHoSoBenhAnRepository.findByTrangThaiAndIsLatest(trangThai, isLatest, offset, limit, sort);
    }
    
    public int countByTrangThaiAndIsLatest(int trangThai, boolean isLatest) {
        return emrHoSoBenhAnRepository.countByTrangThaiAndIsLatest(trangThai, isLatest);
    }
    
    public Optional<EmrHoSoBenhAn> getById(ObjectId id){
        var emrHoSoBenhAn = emrHoSoBenhAnRepository.findById(id);
        emrHoSoBenhAn.ifPresent(x -> {
           x.emrBenhNhan = emrBenhNhanService.getById(x.emrBenhNhanId).orElse(null);
           x.emrCoSoKhamBenh = emrCoSoKhamBenhService.getById(x.emrCoSoKhamBenhId).orElse(null);
           x.emrVaoKhoas = emrVaoKhoaService.getEmrVaoKhoaByHsbaId(id);
           x.emrHinhAnhTonThuongs = emrHinhAnhTonThuongRepository.findByEmrHoSoBenhAnId(id);
           x.emrGiaiPhauBenhs = emrGiaiPhauBenhRepository.findByEmrHoSoBenhAnId(id) ;
           x.emrThamDoChucNangs = emrThamDoChucNangRepository.findByEmrHoSoBenhAnId(id);
           x.emrPhauThuatThuThuats = emrPhauThuatThuThuatRepository.findByEmrHoSoBenhAnId(id);
           x.emrChanDoanHinhAnhs = emrChanDoanHinhAnhRepository.findByEmrHoSoBenhAnId(id);
           x.emrDonThuocs = emrDonThuocRepository.findByEmrHoSoBenhAnId(id);
           x.emrYhctDonThuocs = emrYhctDonThuocRepository.findByEmrHoSoBenhAnId(id);
           x.emrXetNghiems = emrXetNghiemRepository.findByEmrHoSoBenhAnId(id);                   
        });
        
        return emrHoSoBenhAn;
    }
    
    
    public EmrHoSoBenhAn save(EmrHoSoBenhAn hsba) {
        var maCoSoKhamBenh = hsba.emrCoSoKhamBenh != null? hsba.emrCoSoKhamBenh.ma : "";
        var emrCoSoKhamBenh = emrCoSoKhamBenhService.getByMa(maCoSoKhamBenh).orElse(null);
        
        if(hsba.emrBenhNhan == null || emrCoSoKhamBenh == null) {
            throw new RuntimeException();
        }
        
        var emrBenhNhan = hsba.emrBenhNhan;
        
        if(StringUtils.isEmpty(emrBenhNhan.iddinhdanhchinh)) {
            emrBenhNhan.iddinhdanhchinh = emrBenhNhan.idhis;
        }
        
        if(StringUtils.isEmpty(emrBenhNhan.iddinhdanhchinh)) {
            throw new RuntimeException("Empty emrBenhNhan.iddinhdanhchinh");
        }        
        
        emrBenhNhan = emrBenhNhanService.createOrUpdate(emrBenhNhan);
        
        hsba.emrBenhNhan = emrBenhNhan;
        hsba.emrBenhNhanId = emrBenhNhan.id;
        
        hsba.emrCoSoKhamBenh = emrCoSoKhamBenh;
        hsba.emrCoSoKhamBenhId = emrCoSoKhamBenh.id;
        
        hsba.ngaytao = new Date();
        
        //var maLoaiBenhAn = hsba.emrDmLoaiBenhAn != null? hsba.emrDmLoaiBenhAn.ma : null;
        //hsba.emrDmLoaiBenhAn = emrDmService.getEmrDmByNhom_Ma(MA_NHOM_DANH_MUC.LOAI_BENH_AN, maLoaiBenhAn);
        hsba.nguonDuLieu = NGUON_DU_LIEU.TU_HIS;
        hsba.trangThai = TRANGTHAI_HOSO.NHAP;
        hsba = emrHoSoBenhAnRepository.save(hsba);
        
        final var hsbaId = hsba.id;
        
        if(hsba.emrVaoKhoas != null) {
            for(var item : hsba.emrVaoKhoas) item.emrHoSoBenhAnId = hsba.id;
            
            hsba.emrVaoKhoas = hsba.emrVaoKhoas.stream()
                                    .map(x -> emrVaoKhoaService.save(x))
                                    .collect(Collectors.toList());
        }
        
        if(hsba.emrHinhAnhTonThuongs != null) {
            hsba.emrHinhAnhTonThuongs.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrHinhAnhTonThuongs = hsba.emrHinhAnhTonThuongs.stream()
                                            .map(x -> emrHinhAnhTonThuongRepository.save(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrGiaiPhauBenhs != null) {
            hsba.emrGiaiPhauBenhs.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrGiaiPhauBenhs = hsba.emrGiaiPhauBenhs.stream()
                                            .map(x -> emrGiaiPhauBenhRepository.save(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrThamDoChucNangs != null) {
            hsba.emrThamDoChucNangs.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrThamDoChucNangs = hsba.emrThamDoChucNangs.stream()
                                            .map(x -> emrThamDoChucNangRepository.save(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrPhauThuatThuThuats != null) {
            for(var item : hsba.emrPhauThuatThuThuats) item.emrHoSoBenhAnId = hsbaId;
            
            hsba.emrPhauThuatThuThuats = hsba.emrPhauThuatThuThuats.stream()
                                            .map(x -> emrPhauThuatThuThuatRepository.save(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrChanDoanHinhAnhs != null) {
            hsba.emrChanDoanHinhAnhs.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrChanDoanHinhAnhs = hsba.emrChanDoanHinhAnhs.stream()
                                            .map(x -> emrChanDoanHinhAnhRepository.save(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrDonThuocs != null) {
            hsba.emrDonThuocs.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrDonThuocs = hsba.emrDonThuocs.stream()
                                            .map(x -> emrDonThuocRepository.save(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrYhctDonThuocs != null) {
            hsba.emrYhctDonThuocs.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrYhctDonThuocs = hsba.emrYhctDonThuocs.stream()
                                            .map(x -> emrYhctDonThuocRepository.save(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrXetNghiems != null) {
            hsba.emrXetNghiems.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrXetNghiems = hsba.emrXetNghiems.stream()
                                            .map(x -> emrXetNghiemRepository.save(x))
                                            .collect(Collectors.toList());
        }
        
        return hsba;
    }
    
    
    public void setAsLatest(final EmrHoSoBenhAn hsba) {
                
        var hsbaList = emrHoSoBenhAnRepository.findByMayte(hsba.mayte);
        hsbaList.forEach(x -> {
            if(x.id.compareTo(hsba.id) != 0) {
                x.isLatest = false;
                emrHoSoBenhAnRepository.save(x);              
            }            
        });
        
        hsba.isLatest = true;
        hsba.trangThai = TRANGTHAI_HOSO.CHUA_XULY;
        emrHoSoBenhAnRepository.save(hsba);
    }
    
}