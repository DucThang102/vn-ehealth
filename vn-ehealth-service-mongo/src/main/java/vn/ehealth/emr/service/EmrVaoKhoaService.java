package vn.ehealth.emr.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.EmrVaoKhoa;
import vn.ehealth.emr.repository.EmrChamSocRepository;
import vn.ehealth.emr.repository.EmrChucNangSongRepository;
import vn.ehealth.emr.repository.EmrDieuTriRepository;
import vn.ehealth.emr.repository.EmrHoiChanRepository;
import vn.ehealth.emr.repository.EmrVaoKhoaRespository;

@Service
public class EmrVaoKhoaService {

    @Autowired EmrVaoKhoaRespository emrVaoKhoaRespository;
    @Autowired EmrChamSocRepository emrChamSocRepository;
    @Autowired EmrChucNangSongRepository emrChucNangSongRepository;
    @Autowired EmrDieuTriRepository emrDieuTriRepository;
    @Autowired EmrHoiChanRepository emrHoiChanRepository;
    
    public List<EmrVaoKhoa> getEmrVaoKhoaByHsbaId(ObjectId hsbaId) {
        var lst = emrVaoKhoaRespository.findByEmrHoSoBenhAnId(hsbaId);
        
        for(var item : lst) {
            item.emrChamSocs = emrChamSocRepository.findByEmrVaoKhoaId(item.id);
            item.emrChucNangSongs = emrChucNangSongRepository.findByEmrVaoKhoaId(item.id);
            item.emrDieuTris = emrDieuTriRepository.findByEmrVaoKhoaId(item.id);
            item.emrHoiChans = emrHoiChanRepository.findByEmrVaoKhoaId(item.id);             
        }
        
        return lst;
    }
    
    public EmrVaoKhoa save(@Nonnull EmrVaoKhoa emrVaoKhoa) {
        emrVaoKhoa = emrVaoKhoaRespository.save(emrVaoKhoa);
        final ObjectId emrVaoKhoaId = emrVaoKhoa.id;
        
        if(emrVaoKhoa.emrChamSocs != null) {
            emrVaoKhoa.emrChamSocs.forEach(x -> x.emrVaoKhoaId = emrVaoKhoaId);
            
            emrVaoKhoa.emrChamSocs = emrVaoKhoa.emrChamSocs.stream()
                                        .map(x -> emrChamSocRepository.save(x))
                                        .collect(Collectors.toList());
        }
        
        if(emrVaoKhoa.emrChucNangSongs != null) {
            emrVaoKhoa.emrChucNangSongs.forEach(x -> x.emrVaoKhoaId = emrVaoKhoaId);
            
            emrVaoKhoa.emrChucNangSongs = emrVaoKhoa.emrChucNangSongs.stream()
                                        .map(x -> emrChucNangSongRepository.save(x))
                                        .collect(Collectors.toList());
        }
        
        if(emrVaoKhoa.emrDieuTris != null) {
            emrVaoKhoa.emrDieuTris.forEach(x -> x.emrVaoKhoaId = emrVaoKhoaId);
            
            emrVaoKhoa.emrDieuTris = emrVaoKhoa.emrDieuTris.stream()
                                        .map(x -> emrDieuTriRepository.save(x))
                                        .collect(Collectors.toList());
        }
        
        if(emrVaoKhoa.emrHoiChans != null) {
            emrVaoKhoa.emrHoiChans.forEach(x -> x.emrVaoKhoaId = emrVaoKhoaId);
            
            emrVaoKhoa.emrHoiChans = emrVaoKhoa.emrHoiChans.stream()
                                        .map(x -> emrHoiChanRepository.save(x))
                                        .collect(Collectors.toList());
        }
        
        return emrVaoKhoa;
    }
}
