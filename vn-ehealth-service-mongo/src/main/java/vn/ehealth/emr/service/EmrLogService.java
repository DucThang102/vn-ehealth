package vn.ehealth.emr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.ehealth.emr.model.EmrLog;
import vn.ehealth.emr.repository.EmrActionRepository;
import vn.ehealth.emr.repository.EmrLogRepository;

@Service
public class EmrLogService {

    Logger logger = LoggerFactory.getLogger(EmrLogService.class);
    @Autowired EmrActionRepository emrActionRepository;
    @Autowired EmrLogRepository emrLogRepository;
    
    public EmrLog logAction(String objectClass, ObjectId objectId, String maHanhDong, Date ngayThucHien, ObjectId nguoiThucHienId, String noiDung, String ghiChu) {
        var hanhDongId = emrActionRepository.findByMa(maHanhDong).map(x -> x.id).orElse(null);
        
        if(hanhDongId != null && objectId != null) {
            var emrLog = new EmrLog();
            emrLog.nguoiThucHienId = nguoiThucHienId;
            emrLog.ngayThucHien = new Date();
            emrLog.objectId = objectId;
            emrLog.objectClass = objectClass;
            emrLog.hanhDongId = hanhDongId;
            emrLog.ghiChu = ghiChu != null? ghiChu : "";
            emrLog.noiDung = noiDung;
            return emrLogRepository.save(emrLog);
        }else {
            logger.error("Cannot log action, hanhDongId=" + hanhDongId + ", objectId=" + objectId + ", nguoiThucHienId=" + nguoiThucHienId);
        }
        
        return null;        
    }
    
    public List<EmrLog> getLogs(String objectClass, ObjectId objectId, String maHanhDong, boolean asc) {
        var hanhDongId = emrActionRepository.findByMa(maHanhDong).map(x -> x.id).orElse(null);
        
        if((hanhDongId != null && objectId != null)) {
            var sortDirection = asc? Sort.Direction.ASC: Sort.Direction.DESC;
            var sort = new Sort(sortDirection, "ngayThucHien");
            return emrLogRepository.findByObjectClassAndObjectIdAndHanhDongId(objectClass, objectId, hanhDongId, sort);
        }
        
        return new ArrayList<>();        
    }
}
