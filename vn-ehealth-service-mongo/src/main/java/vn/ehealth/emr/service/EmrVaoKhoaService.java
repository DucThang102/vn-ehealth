package vn.ehealth.emr.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrVaoKhoa;
import vn.ehealth.emr.repository.EmrVaoKhoaRespository;

@Service
public class EmrVaoKhoaService {

    @Autowired EmrVaoKhoaRespository emrVaoKhoaRespository;
    @Autowired EmrChamSocService emrChamSocService;
    @Autowired EmrChucNangSongService emrChucNangSongService;
    @Autowired EmrDieuTriService emrDieuTriService;
    @Autowired EmrHoiChanService emrHoiChanService;
    
    public List<EmrVaoKhoa> getByEmrHoSoBenhAnId(ObjectId hsbaId, boolean detail) {
        var sort = new Sort(Sort.Direction.ASC, "ngaygiovaokhoa");                
        var lst = emrVaoKhoaRespository.findByEmrHoSoBenhAnId(hsbaId, sort);
        if(detail) {
            lst.forEach(x -> {
                x.emrChamSocs = emrChamSocService.getByEmrVaoKhoaId(x.id);
                x.emrChucNangSongs = emrChucNangSongService.getByEmrVaoKhoaId(x.id);
                x.emrDieuTris = emrDieuTriService.getByEmrVaoKhoaId(x.id);
                x.emrHoiChans = emrHoiChanService.getByEmrVaoKhoaId(x.id);
            });
        }
        
        return lst;        
    }
    
    public EmrVaoKhoa save(@Nonnull EmrVaoKhoa emrVaoKhoa) {
        emrVaoKhoa = emrVaoKhoaRespository.save(emrVaoKhoa);
        final ObjectId emrVaoKhoaId = emrVaoKhoa.id;
        
        if(emrVaoKhoa.emrChamSocs != null) {
            emrVaoKhoa.emrChamSocs.forEach(x -> x.emrVaoKhoaId = emrVaoKhoaId);
            
            emrVaoKhoa.emrChamSocs = emrVaoKhoa.emrChamSocs.stream()
                                        .map(x -> emrChamSocService.createOrUpdate(x))
                                        .collect(Collectors.toList());
        }
        
        if(emrVaoKhoa.emrChucNangSongs != null) {
            emrVaoKhoa.emrChucNangSongs.forEach(x -> x.emrVaoKhoaId = emrVaoKhoaId);
            
            emrVaoKhoa.emrChucNangSongs = emrVaoKhoa.emrChucNangSongs.stream()
                                        .map(x -> emrChucNangSongService.createOrUpdate(x))
                                        .collect(Collectors.toList());
        }
        
        if(emrVaoKhoa.emrDieuTris != null) {
            emrVaoKhoa.emrDieuTris.forEach(x -> x.emrVaoKhoaId = emrVaoKhoaId);
            
            emrVaoKhoa.emrDieuTris = emrVaoKhoa.emrDieuTris.stream()
                                        .map(x -> emrDieuTriService.createOrUpdate(x))
                                        .collect(Collectors.toList());
        }
        
        if(emrVaoKhoa.emrHoiChans != null) {
            emrVaoKhoa.emrHoiChans.forEach(x -> x.emrVaoKhoaId = emrVaoKhoaId);
            
            emrVaoKhoa.emrHoiChans = emrVaoKhoa.emrHoiChans.stream()
                                        .map(x -> emrHoiChanService.createOrUpdate(x))
                                        .collect(Collectors.toList());
        }
        
        return emrVaoKhoa;
    }
    
    public void saveByEmrHoSoBenhAnId(ObjectId hsbaId, List<EmrVaoKhoa> emrVaoKhoas) {
        for(var emrVaoKhoa : getByEmrHoSoBenhAnId(hsbaId, false)) {
            emrVaoKhoaRespository.delete(emrVaoKhoa);
        }
        
        for(var emrVaoKhoa : emrVaoKhoas) {
            emrVaoKhoa.id = null;
            emrVaoKhoa.emrHoSoBenhAnId = hsbaId;
            emrVaoKhoaRespository.save(emrVaoKhoa);
        }
    }
}
