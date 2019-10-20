package vn.ehealth.emr.service;

import java.util.Date;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import vn.ehealth.emr.EmrLog;
import vn.ehealth.emr.repository.EmrActionRepository;
import vn.ehealth.emr.repository.EmrLogRepository;
import vn.ehealth.emr.utils.FieldUtil;
import vn.ehealth.emr.utils.JsonUtil;

public class EmrLogService {

    Logger logger = LoggerFactory.getLogger(EmrLogService.class);
    @Autowired EmrActionRepository emrActionRepository;
    @Autowired EmrLogRepository emrLogRepository;
    
    public EmrLog logAction(Object obj, String maHanhDong, Date ngayThucHien, ObjectId nguoiThucHienId, String noiDung, String ghiChu) {
        var hanhDongId = emrActionRepository.findByMa(maHanhDong).map(x -> x.id).orElse(null);
        var objectId = (ObjectId) FieldUtil.getField(obj, "id");;
        
        if(hanhDongId != null && objectId != null && nguoiThucHienId != null) {
            var emrLog = new EmrLog();
            emrLog.nguoiThucHienId = nguoiThucHienId;
            emrLog.ngayThucHien = new Date();
            emrLog.objectId = (ObjectId) FieldUtil.getField(obj, "id");
            emrLog.objectClass = obj.getClass().getName();
            emrLog.hanhDongId = hanhDongId;
            emrLog.ghiChu = ghiChu != null? ghiChu : "";
            if(noiDung != null) {
                emrLog.noiDung = noiDung;
            }else {
                emrLog.noiDung = JsonUtil.dumpObject(obj);
            }
            return emrLogRepository.save(emrLog);
        }else {
            logger.error("Cannot log action, hanhDongId=" + hanhDongId + ", objectId=" + objectId + ", nguoiThucHienId=" + nguoiThucHienId);
        }
        
        return null;        
    }
}
