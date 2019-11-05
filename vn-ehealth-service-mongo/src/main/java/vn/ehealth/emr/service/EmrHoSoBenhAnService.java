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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vn.ehealth.emr.EmrHoSoBenhAn;
import vn.ehealth.emr.repository.EmrHoSoBenhAnRepository;
import vn.ehealth.service.Constants.NGUON_DU_LIEU;
import vn.ehealth.service.Constants.TRANGTHAI_HOSO;

@Service
public class EmrHoSoBenhAnService {
   
    Logger logger = LoggerFactory.getLogger(EmrHoSoBenhAnService.class);
            
    @Autowired EmrHoSoBenhAnRepository emrHoSoBenhAnRepository;
    
    @Autowired EmrHinhAnhTonThuongService emrHinhAnhTonThuongService;
    @Autowired EmrGiaiPhauBenhService emrGiaiPhauBenhService;
    @Autowired EmrThamDoChucNangService emrThamDoChucNangService;
    @Autowired EmrPhauThuatThuThuatService emrPhauThuatThuThuatService;
    @Autowired EmrChanDoanHinhAnhService emrChanDoanHinhAnhService;
    @Autowired EmrDonThuocService emrDonThuocService;
    @Autowired EmrYhctDonThuocService emrYhctDonThuocService;
    @Autowired EmrXetNghiemService emrXetNghiemService;
    
    @Autowired EmrDmService emrDmService;
    @Autowired EmrVaoKhoaService emrVaoKhoaService;
    @Autowired EmrBenhNhanService emrBenhNhanService;
    @Autowired EmrCoSoKhamBenhService emrCoSoKhamBenhService;
    
    @Autowired MongoTemplate mongoTemplate;
    
    public long countHoSo(int trangThai, String mayte) {
        var query = new Query(Criteria.where("trangThai").is(trangThai)
                                        .and("mayte").regex(mayte)
                                        .and("isLatest").is(true)
                             );
        
        return mongoTemplate.count(query, EmrHoSoBenhAn.class);
    }
    
    public List<EmrHoSoBenhAn> getDsHoSo(int trangThai, String mayte, int offset, int limit) {
        var sort = new Sort(Sort.Direction.DESC, "ngaytiepnhan");
        var pageable = new OffsetBasedPageRequest(limit, offset, sort);
        var query = new Query(Criteria.where("trangThai").is(trangThai)
                                        .and("mayte").regex(mayte)
                                        .and("isLatest").is(true)
                             ).with(pageable);
        
        return mongoTemplate.find(query, EmrHoSoBenhAn.class);
    }
    
    /*
    public List<EmrHoSoBenhAn> getByTrangThaiAndIsLatest(int trangThai, boolean isLatest, int offset, int limit){
        var sort = new Sort(Sort.Direction.DESC, "ngaytiepnhan");
        var pageable = new OffsetBasedPageRequest(limit, offset, sort);        
        return emrHoSoBenhAnRepository.findByTrangThaiAndIsLatest(trangThai, isLatest, pageable);
    }*/
    
    public long countByTrangThaiAndIsLatest(int trangThai, boolean isLatest) {
        return emrHoSoBenhAnRepository.countByTrangThaiAndIsLatest(trangThai, isLatest);
    }
    
    public Optional<EmrHoSoBenhAn> getById(ObjectId id){
        var emrHoSoBenhAn = emrHoSoBenhAnRepository.findById(id);
        emrHoSoBenhAn.ifPresent(x -> {
           x.emrBenhNhan = emrBenhNhanService.getById(x.emrBenhNhanId).orElse(null);
           x.emrCoSoKhamBenh = emrCoSoKhamBenhService.getById(x.emrCoSoKhamBenhId).orElse(null);
           /*x.emrVaoKhoas = emrVaoKhoaService.getByEmrHoSoBenhAnId(id);
           x.emrHinhAnhTonThuongs = emrHinhAnhTonThuongService.getByEmrHoSoBenhAnId(id);
           x.emrGiaiPhauBenhs = emrGiaiPhauBenhService.getByEmrHoSoBenhAnId(id) ;
           x.emrThamDoChucNangs = emrThamDoChucNangService.getByEmrHoSoBenhAnId(id);
           x.emrPhauThuatThuThuats = emrPhauThuatThuThuatService.getByEmrHoSoBenhAnId(id);
           x.emrChanDoanHinhAnhs = emrChanDoanHinhAnhService.getByEmrHoSoBenhAnId(id);
           x.emrDonThuocs = emrDonThuocService.getByEmrHoSoBenhAnId(id);
           x.emrYhctDonThuocs = emrYhctDonThuocService.getByEmrHoSoBenhAnId(id);
           x.emrXetNghiems = emrXetNghiemService.getByEmrHoSoBenhAnId(id);*/
        });
        
        return emrHoSoBenhAn;
    }
    
    public Optional<EmrHoSoBenhAn> getByMayte(String mayte) {
        return emrHoSoBenhAnRepository.findByMayteAndIsLatest(mayte, true);
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
                                            .map(x -> emrHinhAnhTonThuongService.createOrUpdate(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrGiaiPhauBenhs != null) {
            hsba.emrGiaiPhauBenhs.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrGiaiPhauBenhs = hsba.emrGiaiPhauBenhs.stream()
                                            .map(x -> emrGiaiPhauBenhService.createOrUpdate(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrThamDoChucNangs != null) {
            hsba.emrThamDoChucNangs.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrThamDoChucNangs = hsba.emrThamDoChucNangs.stream()
                                            .map(x -> emrThamDoChucNangService.createOrUpdate(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrPhauThuatThuThuats != null) {
            for(var item : hsba.emrPhauThuatThuThuats) item.emrHoSoBenhAnId = hsbaId;
            
            hsba.emrPhauThuatThuThuats = hsba.emrPhauThuatThuThuats.stream()
                                            .map(x -> emrPhauThuatThuThuatService.createOrUpdate(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrChanDoanHinhAnhs != null) {
            hsba.emrChanDoanHinhAnhs.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrChanDoanHinhAnhs = hsba.emrChanDoanHinhAnhs.stream()
                                            .map(x -> emrChanDoanHinhAnhService.createOrUpdate(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrDonThuocs != null) {
            hsba.emrDonThuocs.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrDonThuocs = hsba.emrDonThuocs.stream()
                                            .map(x -> emrDonThuocService.createOrUpdate(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrYhctDonThuocs != null) {
            hsba.emrYhctDonThuocs.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrYhctDonThuocs = hsba.emrYhctDonThuocs.stream()
                                            .map(x -> emrYhctDonThuocService.createOrUpdate(x))
                                            .collect(Collectors.toList());
        }
        
        if(hsba.emrXetNghiems != null) {
            hsba.emrXetNghiems.forEach(x -> x.emrHoSoBenhAnId = hsbaId);
            
            hsba.emrXetNghiems = hsba.emrXetNghiems.stream()
                                            .map(x -> emrXetNghiemService.createOrUpdate(x))
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